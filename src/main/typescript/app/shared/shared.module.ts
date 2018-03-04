import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {ToastComponent} from './toast/toast.component';
import {LoadingCardComponent} from './loadingcard/loadingcard.component';
import {LoadingComponent} from "./loading/loading.component";
import {IbanPipe} from "./pipe/iban";

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule
    ],
    exports: [
        // Shared Modules
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
        // Shared Components
        ToastComponent,
        LoadingComponent,
        LoadingCardComponent,
        IbanPipe,
    ],
    declarations: [
        ToastComponent,
        LoadingComponent,
        LoadingCardComponent,
        IbanPipe,
    ],
    providers: [
        ToastComponent
    ]
})
export class SharedModule {
}
