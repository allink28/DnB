import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Character } from './character.model';
import { CharacterPopupService } from './character-popup.service';
import { CharacterService } from './character.service';

@Component({
    selector: 'jhi-character-dialog',
    templateUrl: './character-dialog.component.html'
})
export class CharacterDialogComponent implements OnInit {

    character: Character;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private characterService: CharacterService,
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
        if (this.character.id !== undefined) {
            this.subscribeToSaveResponse(
                this.characterService.update(this.character), false);
        } else {
            this.subscribeToSaveResponse(
                this.characterService.create(this.character), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Character>, isCreated: boolean) {
        result.subscribe((res: Character) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Character, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Character is created with identifier ${result.id}`
            : `A Character is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'characterListModification', content: 'OK'});
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
    selector: 'jhi-character-popup',
    template: ''
})
export class CharacterPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private characterPopupService: CharacterPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.characterPopupService
                    .open(CharacterDialogComponent, params['id']);
            } else {
                this.modalRef = this.characterPopupService
                    .open(CharacterDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
