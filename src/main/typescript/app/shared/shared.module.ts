import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {LoadingCardComponent} from './loadingcard/loadingcard.component';
import {LoadingComponent} from "./loading/loading.component";
import {IbanPipe} from "./pipe/iban";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ToastrModule} from "ngx-toastr";
import {ServiceModule} from "../service/service.module";

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        ServiceModule,
        BrowserAnimationsModule,
        ToastrModule.forRoot({
            positionClass: 'toast-bottom-center',
            timeOut: 3000,
        }),
    ],
    exports: [
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        ToastrModule,
        ServiceModule,
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
