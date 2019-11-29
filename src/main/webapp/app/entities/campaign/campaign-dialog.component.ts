import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Campaign } from './campaign.model';
import { CampaignPopupService } from './campaign-popup.service';
import { CampaignService } from './campaign.service';

@Component({
    selector: 'jhi-campaign-dialog',
    templateUrl: './campaign-dialog.component.html'
})
export class CampaignDialogComponent implements OnInit {

    campaign: Campaign;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private campaignService: CampaignService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.campaign.id !== undefined) {
            this.subscribeToSaveResponse(
                this.campaignService.update(this.campaign), false);
        } else {
            this.subscribeToSaveResponse(
                this.campaignService.create(this.campaign), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Campaign>, isCreated: boolean) {
        result.subscribe((res: Campaign) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Campaign, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Campaign is created with identifier ${result.id}`
            : `A Campaign is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'campaignListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-campaign-popup',
    template: ''
})
export class CampaignPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private campaignPopupService: CampaignPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.campaignPopupService
                    .open(CampaignDialogComponent, params['id']);
            } else {
                this.modalRef = this.campaignPopupService
                    .open(CampaignDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
