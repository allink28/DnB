import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Spell } from './spell.model';
import { SpellPopupService } from './spell-popup.service';
import { SpellService } from './spell.service';
import { Character, CharacterService } from '../character';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-spell-dialog',
    templateUrl: './spell-dialog.component.html'
})
export class SpellDialogComponent implements OnInit {

    spell: Spell;
    authorities: any[];
    isSaving: boolean;

    characters: Character[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private spellService: SpellService,
        private characterService: CharacterService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.characterService.query()
            .subscribe((res: ResponseWrapper) => { this.characters = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.spell.id !== undefined) {
            this.subscribeToSaveResponse(
                this.spellService.update(this.spell), false);
        } else {
            this.subscribeToSaveResponse(
                this.spellService.create(this.spell), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Spell>, isCreated: boolean) {
        result.subscribe((res: Spell) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Spell, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Spell is created with identifier ${result.id}`
            : `A Spell is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'spellListModification', content: 'OK'});
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

    trackCharacterById(index: number, item: Character) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-spell-popup',
    template: ''
})
export class SpellPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private spellPopupService: SpellPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.spellPopupService
                    .open(SpellDialogComponent, params['id']);
            } else {
                this.modalRef = this.spellPopupService
                    .open(SpellDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
