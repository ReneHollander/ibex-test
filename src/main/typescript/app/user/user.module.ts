import {NgModule} from '@angular/core';
import {SharedModule} from '../shared/shared.module';
import {OrderComponent} from './order/order.component';
import {InvoicesComponent} from './invoices/invoices.component';
import {RecurringOrdersComponent} from './recurringorders/recurringorders.component';
import {InvoiceComponent} from './invoice/invoice.component';
import {RecurringOrderComponent} from './recurringorder/recurringorder.component';
import {ProductSearchComponent} from './productsearch/productsearch.component';
import {RecurringOrderRowComponent} from './recurringorderrow/recurringorderrow.component';
import {TabsModule} from 'ngx-bootstrap';
import {NgToggleModule} from '@nth-cloud/ng-toggle';
import {NgbTypeaheadModule} from '@ng-bootstrap/ng-bootstrap';
import {UserRoutingModule} from './user-routing.module';

@NgModule({
    imports: [
        SharedModule,
        TabsModule.forRoot(),
        NgbTypeaheadModule.forRoot(),
        NgToggleModule,
        UserRoutingModule,
    ],
    declarations: [
        RecurringOrdersComponent,
        RecurringOrderComponent,
        RecurringOrderRowComponent,
        ProductSearchComponent,
        InvoicesComponent,
        InvoiceComponent,
        OrderComponent,
    ],
})
export class UserModule {
}
