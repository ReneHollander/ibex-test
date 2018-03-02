import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {ToastComponent} from './toast/toast.component';
import {LoadingCardComponent} from './loadingcard/loadingcard.component';
import {LoadingComponent} from "./loading/loading.component";

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
        LoadingCardComponent
    ],
    declarations: [
        ToastComponent,
        LoadingComponent,
        LoadingCardComponent
    ],
    providers: [
        ToastComponent
    ]
})
export class SharedModule {
}
