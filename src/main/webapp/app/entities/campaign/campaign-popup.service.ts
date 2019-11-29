import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Campaign } from './campaign.model';
import { CampaignService } from './campaign.service';

@Injectable()
export class CampaignPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private campaignService: CampaignService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.campaignService.find(id).subscribe((campaign) => {
                this.campaignModalRef(component, campaign);
            });
        } else {
            return this.campaignModalRef(component, new Campaign());
        }
    }

    campaignModalRef(component: Component, campaign: Campaign): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.campaign = campaign;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
