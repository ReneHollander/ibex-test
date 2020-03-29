import {NgModule} from '@angular/core';
import {OrderSummaryComponent} from './ordersummary/ordersummary.component';
import {SharedModule} from '../shared/shared.module';
import {BsDatepickerModule} from 'ngx-bootstrap';
import {defineLocale} from 'ngx-bootstrap/chronos';
import {deLocale} from 'ngx-bootstrap/locale';
import {AdminRoutingModule} from './admin-routing.module';

defineLocale('de', deLocale);

@NgModule({
    imports: [
        AdminRoutingModule,
        SharedModule,
        BsDatepickerModule.forRoot(),
    ],
    declarations: [
        OrderSummaryComponent,
    ],
})
export class AdminModule {
}
