import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Character } from './character.model';
import { CharacterService } from './character.service';

@Component({
    selector: 'jhi-character-detail',
    templateUrl: './character-detail.component.html'
})
export class CharacterDetailComponent implements OnInit, OnDestroy {

    character: Character;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private characterService: CharacterService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCharacters();
    }

    load(id) {
        this.characterService.find(id).subscribe((character) => {
            this.character = character;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCharacters() {
        this.eventSubscriber = this.eventManager.subscribe(
            'characterListModification',
            (response) => this.load(this.character.id)
        );
    }
}
