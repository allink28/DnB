import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DnBSharedModule } from '../../shared';
import {
    CharacterService,
    CharacterPopupService,
    CharacterComponent,
    CharacterDetailComponent,
    CharacterDialogComponent,
    CharacterGenDialogComponent,
    CharacterPopupComponent,
    CharacterGenPopupComponent,
    CharacterDeletePopupComponent,
    CharacterDeleteDialogComponent,
    characterRoute,
    characterPopupRoute,
    CharacterResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...characterRoute,
    ...characterPopupRoute,
];

@NgModule({
    imports: [
        DnBSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CharacterComponent,
        CharacterDetailComponent,
        CharacterDialogComponent,
        CharacterGenDialogComponent,
        CharacterDeleteDialogComponent,
        CharacterPopupComponent,
        CharacterGenPopupComponent,
        CharacterDeletePopupComponent,
    ],
    entryComponents: [
        CharacterComponent,
        CharacterDialogComponent,
        CharacterGenDialogComponent,
        CharacterPopupComponent,
        CharacterGenPopupComponent,
        CharacterDeleteDialogComponent,
        CharacterDeletePopupComponent,
    ],
    providers: [
        CharacterService,
        CharacterPopupService,
        CharacterResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DnBCharacterModule {}
