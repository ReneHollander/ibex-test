import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {OrderSummaryComponent} from './ordersummary/ordersummary.component';
import {AuthGuardAdmin} from '../service/guard/auth-guard-admin.service';

const routes: Routes = [
    {path: 'ordersummary', component: OrderSummaryComponent, canActivate: [AuthGuardAdmin]},
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AdminRoutingModule {
}
