import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Spell } from './spell.model';
import { SpellService } from './spell.service';

@Component({
    selector: 'jhi-spell-detail',
    templateUrl: './spell-detail.component.html'
})
export class SpellDetailComponent implements OnInit, OnDestroy {

    spell: Spell;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private spellService: SpellService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSpells();
    }

    load(id) {
        this.spellService.find(id).subscribe((spell) => {
            this.spell = spell;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSpells() {
        this.eventSubscriber = this.eventManager.subscribe(
            'spellListModification',
            (response) => this.load(this.spell.id)
        );
    }
}
