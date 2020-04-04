import {NgModule} from '@angular/core';
import {LoadingCardComponent} from './loadingcard/loadingcard.component';
import {LoadingComponent} from './loading/loading.component';
import {IbanPipe} from './pipe/iban';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
    ],
    exports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
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
