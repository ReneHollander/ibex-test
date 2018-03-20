import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {OrderComponent} from "./order/order.component";
import {InvoicesComponent} from "./invoices/invoices.component";
import {RecurringOrdersComponent} from "./recurringorders/recurringorders.component";
import {InvoiceComponent} from "./invoice/invoice.component";
import {AuthGuardUser} from "../service/guard/auth-guard-login.service";

const routes: Routes = [
    {path: 'recurringorders', component: RecurringOrdersComponent, canActivate: [AuthGuardUser]},
    {path: 'invoices', component: InvoicesComponent, canActivate: [AuthGuardUser]},
    {path: 'invoice/:id', component: InvoiceComponent, canActivate: [AuthGuardUser]},
    {path: 'order/:id', component: OrderComponent, canActivate: [AuthGuardUser]},
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class UserRoutingModule {
}
