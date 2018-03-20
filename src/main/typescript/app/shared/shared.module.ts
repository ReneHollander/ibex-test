import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {LoadingCardComponent} from './loadingcard/loadingcard.component';
import {LoadingComponent} from "./loading/loading.component";
import {IbanPipe} from "./pipe/iban";

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
    ],
    exports: [
        // Shared Modules
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        // Shared Components
        LoadingComponent,
        LoadingCardComponent,
        IbanPipe,
    ],
    declarations: [
        LoadingComponent,
        LoadingCardComponent,
        IbanPipe,
    ],
    providers: []
})
export class SharedModule {
}
