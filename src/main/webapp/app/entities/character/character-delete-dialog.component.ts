import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { Character } from './character.model';
import { CharacterPopupService } from './character-popup.service';
import { CharacterService } from './character.service';

@Component({
    selector: 'jhi-character-delete-dialog',
    templateUrl: './character-delete-dialog.component.html'
})
export class CharacterDeleteDialogComponent {

    character: Character;

    constructor(
        private characterService: CharacterService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.characterService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'characterListModification',
                content: 'Deleted an character'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Character is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-character-delete-popup',
    template: ''
})
export class CharacterDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private characterPopupService: CharacterPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.characterPopupService
                .open(CharacterDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
