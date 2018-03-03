import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AboutComponent} from './about/about.component';
import {RegisterComponent} from './register/register.component';
import {LoginComponent} from './login/login.component';
import {LogoutComponent} from './logout/logout.component';
import {AccountComponent} from './account/account.component';
import {AdminComponent} from './admin/admin.component';
import {NotFoundComponent} from './not-found/not-found.component';

import {AuthGuardLogin} from './services/auth-guard-login.service';
import {AuthGuardAdmin} from './services/auth-guard-admin.service';
import {ProductsComponent} from "./products/products.component";
import {RecurringOrdersComponent} from "./recurringorders/recurringorders.component";
import {InvoicesComponent} from "./invoices/invoices.component";
import {InvoiceComponent} from "./invoice/invoice.component";
import {OrderComponent} from "./order/order.component";

const routes: Routes = [
    {path: '', component: AboutComponent},
    {path: 'login', component: LoginComponent},
    {path: 'logout', component: LogoutComponent},
    {path: 'register', component: RegisterComponent},
    {path: 'recurringorders', component: RecurringOrdersComponent, canActivate: [AuthGuardLogin]},
    {path: 'products', component: ProductsComponent, canActivate: [AuthGuardLogin]},
    {path: 'invoices', component: InvoicesComponent, canActivate: [AuthGuardLogin]},
    {path: 'invoice/:id', component: InvoiceComponent, canActivate: [AuthGuardLogin]},
    {path: 'order/:id', component: OrderComponent, canActivate: [AuthGuardLogin]},
    {path: 'account', component: AccountComponent, canActivate: [AuthGuardLogin]},
    {path: 'admin', component: AdminComponent, canActivate: [AuthGuardAdmin]},
    {path: 'notfound', component: NotFoundComponent},
    {path: '**', redirectTo: '/notfound'},
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})

export class RoutingModule {
}
