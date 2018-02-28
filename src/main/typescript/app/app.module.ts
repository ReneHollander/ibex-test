import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {RoutingModule} from './routing.module';
import {SharedModule} from './shared/shared.module';
import {AccountService} from './services/account.service';
import {AuthService} from './services/auth.service';
import {AuthGuardLogin} from './services/auth-guard-login.service';
import {AuthGuardAdmin} from './services/auth-guard-admin.service';
import {AppComponent} from './app.component';
import {AboutComponent} from './about/about.component';
import {RegisterComponent} from './register/register.component';
import {LoginComponent} from './login/login.component';
import {LogoutComponent} from './logout/logout.component';
import {AccountComponent} from './account/account.component';
import {AdminComponent} from './admin/admin.component';
import {NotFoundComponent} from './not-found/not-found.component';
import {ProductService} from "./services/product.service";
import {ProductsComponent} from "./products/products.component";
import {RecurringOrdersComponent} from "./recurringorders/recurringorders.component";
import {RecurringOrderComponent} from "./recurringorder/recurringorder.component";
import {RecurringOrderRowComponent} from "./recurringorderrow/recurringorderrow.component";
import {RecurringOrderService} from "./services/recurringorder.service";
import {LoginService} from "./services/login.service";
import {NgPipesModule} from "ngx-pipes";
import {AccordionModule, TabsModule, TypeaheadModule} from "ngx-bootstrap";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {ProductSearchComponent} from "./productsearch/productsearch.component";
import {ApiClient} from "./services/apiclient.service";
import {InvoicesComponent} from "./invoices/invoices.component";
import {InvoiceComponent} from "./invoice/invoice.component";
import {OrderComponent} from "./order/order.component";

@NgModule({
    declarations: [
        AppComponent,
        RecurringOrdersComponent,
        RecurringOrderComponent,
        RecurringOrderRowComponent,
        ProductsComponent,
        AboutComponent,
        RegisterComponent,
        LoginComponent,
        LogoutComponent,
        AccountComponent,
        AdminComponent,
        NotFoundComponent,
        ProductSearchComponent,
        InvoicesComponent,
        InvoiceComponent,
        OrderComponent,
    ],
    imports: [
        RoutingModule,
        SharedModule,
        NgPipesModule,
        TabsModule.forRoot(),
        TypeaheadModule.forRoot(),
        AccordionModule.forRoot(),
        NgbModule.forRoot(),
    ],
    providers: [
        ApiClient,
        AuthService,
        AuthGuardLogin,
        AuthGuardAdmin,
        RecurringOrderService,
        ProductService,
        AccountService,
        LoginService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    bootstrap: [AppComponent]
})

export class AppModule {
}
