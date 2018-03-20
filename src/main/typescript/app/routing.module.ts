import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './main/home/home.component';
import {RegisterComponent} from './main/register/register.component';
import {LoginComponent} from './main/login/login.component';
import {LogoutComponent} from './main/logout/logout.component';
import {AccountComponent} from './main/account/account.component';
import {NotFoundComponent} from './main/not-found/not-found.component';

import {AuthGuardUser} from './service/guard/auth-guard-login.service';
import {AuthGuardAdmin} from './service/guard/auth-guard-admin.service';
import {RecurringOrdersComponent} from "./user/recurringorders/recurringorders.component";
import {InvoicesComponent} from "./user/invoices/invoices.component";
import {InvoiceComponent} from "./user/invoice/invoice.component";
import {OrderComponent} from "./user/order/order.component";
import {AuthGuardLoggedIn} from "./service/guard/auth-guard-loggedin.service";
import {OrderSummaryComponent} from "./admin/ordersummary/ordersummary.component";
import {ImpressComponent} from "./main/impress/impress.component";

const routes: Routes = [
    {path: '', component: HomeComponent},
    {path: 'login', component: LoginComponent},
    {path: 'logout', component: LogoutComponent},
    {path: 'register', component: RegisterComponent},
    {path: 'recurringorders', component: RecurringOrdersComponent, canActivate: [AuthGuardUser]},
    {path: 'invoices', component: InvoicesComponent, canActivate: [AuthGuardUser]},
    {path: 'invoice/:id', component: InvoiceComponent, canActivate: [AuthGuardUser]},
    {path: 'order/:id', component: OrderComponent, canActivate: [AuthGuardUser]},
    {path: 'account', component: AccountComponent, canActivate: [AuthGuardLoggedIn]},
    {path: 'admin/ordersummary', component: OrderSummaryComponent, canActivate: [AuthGuardAdmin]},
    {path: 'impress', component: ImpressComponent},
    {path: 'notfound', component: NotFoundComponent},
    {path: '**', redirectTo: '/notfound'},
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class RoutingModule {
}
