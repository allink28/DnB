import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DnBSharedModule } from '../../shared';
import {
    SpellService,
    SpellPopupService,
    SpellComponent,
    SpellDetailComponent,
    SpellDialogComponent,
    SpellPopupComponent,
    SpellDeletePopupComponent,
    SpellDeleteDialogComponent,
    spellRoute,
    spellPopupRoute,
    SpellResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...spellRoute,
    ...spellPopupRoute,
];

@NgModule({
    imports: [
        DnBSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SpellComponent,
        SpellDetailComponent,
        SpellDialogComponent,
        SpellDeleteDialogComponent,
        SpellPopupComponent,
        SpellDeletePopupComponent,
    ],
    entryComponents: [
        SpellComponent,
        SpellDialogComponent,
        SpellPopupComponent,
        SpellDeleteDialogComponent,
        SpellDeletePopupComponent,
    ],
    providers: [
        SpellService,
        SpellPopupService,
        SpellResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DnBSpellModule {}
