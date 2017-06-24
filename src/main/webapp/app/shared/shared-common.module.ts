import { NgModule } from '@angular/core';
import { Title } from '@angular/platform-browser';

import { WindowRef } from './tracker/window.service';
import {
    DnBSharedLibsModule,
    JhiAlertComponent,
    JhiAlertErrorComponent
} from './';

@NgModule({
    imports: [
        DnBSharedLibsModule
    ],
    declarations: [
        JhiAlertComponent,
        JhiAlertErrorComponent
    ],
    providers: [
        WindowRef,
        Title
    ],
    exports: [
        DnBSharedLibsModule,
        JhiAlertComponent,
        JhiAlertErrorComponent
    ]
})
export class DnBSharedCommonModule {}
