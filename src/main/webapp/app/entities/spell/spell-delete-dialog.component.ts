import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { Spell } from './spell.model';
import { SpellPopupService } from './spell-popup.service';
import { SpellService } from './spell.service';

@Component({
    selector: 'jhi-spell-delete-dialog',
    templateUrl: './spell-delete-dialog.component.html'
})
export class SpellDeleteDialogComponent {

    spell: Spell;

    constructor(
        private spellService: SpellService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.spellService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'spellListModification',
                content: 'Deleted an spell'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Spell is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-spell-delete-popup',
    template: ''
})
export class SpellDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private spellPopupService: SpellPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.spellPopupService
                .open(SpellDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
