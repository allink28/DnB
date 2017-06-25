import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { DnBCharacterModule } from './character/character.module';
import { DnBSpellModule } from './spell/spell.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        DnBCharacterModule,
        DnBSpellModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DnBEntityModule {}
