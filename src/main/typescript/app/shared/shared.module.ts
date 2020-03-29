import {NgModule} from '@angular/core';
import {LoadingCardComponent} from './loadingcard/loadingcard.component';
import {LoadingComponent} from './loading/loading.component';
import {IbanPipe} from './pipe/iban';
import {ServiceModule} from '../service/service.module';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        ServiceModule,
    ],
    exports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
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
