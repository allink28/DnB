import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { Campaign } from './campaign.model';
import { CampaignPopupService } from './campaign-popup.service';
import { CampaignService } from './campaign.service';

@Component({
    selector: 'jhi-campaign-delete-dialog',
    templateUrl: './campaign-delete-dialog.component.html'
})
export class CampaignDeleteDialogComponent {

    campaign: Campaign;

    constructor(
        private campaignService: CampaignService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.campaignService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'campaignListModification',
                content: 'Deleted an campaign'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Campaign is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-campaign-delete-popup',
    template: ''
})
export class CampaignDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private campaignPopupService: CampaignPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.campaignPopupService
                .open(CampaignDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
