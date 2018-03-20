import {NgModule} from '@angular/core';
import {OrderSummaryComponent} from "./ordersummary/ordersummary.component";
import {SharedModule} from "../shared/shared.module";
import {BsDatepickerModule} from "ngx-bootstrap";
import {defineLocale} from 'ngx-bootstrap/chronos';
import {deLocale} from 'ngx-bootstrap/locale';

defineLocale('de', deLocale);

@NgModule({
    imports: [
        SharedModule,
        BsDatepickerModule.forRoot(),
    ],
    exports: [
        OrderSummaryComponent,
    ],
    declarations: [
        OrderSummaryComponent,
    ],
    providers: []
})
export class AdminModule {
}
