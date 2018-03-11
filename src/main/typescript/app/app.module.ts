import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {RoutingModule} from './routing.module';
import {SharedModule} from './shared/shared.module';
import {AccountService} from './services/account.service';
import {AuthService} from './services/auth.service';
import {AuthGuardUser} from './services/auth-guard-login.service';
import {AuthGuardAdmin} from './services/auth-guard-admin.service';
import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {RegisterComponent} from './register/register.component';
import {LoginComponent} from './login/login.component';
import {LogoutComponent} from './logout/logout.component';
import {AccountComponent} from './account/account.component';
import {NotFoundComponent} from './not-found/not-found.component';
import {ProductService} from "./services/product.service";
import {RecurringOrdersComponent} from "./recurringorders/recurringorders.component";
import {RecurringOrderComponent} from "./recurringorder/recurringorder.component";
import {RecurringOrderRowComponent} from "./recurringorderrow/recurringorderrow.component";
import {RecurringOrderService} from "./services/recurringorder.service";
import {LoginService} from "./services/login.service";
import {NgPipesModule} from "ngx-pipes";
import {AccordionModule, BsDatepickerModule, TabsModule, TypeaheadModule} from "ngx-bootstrap";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {ProductSearchComponent} from "./productsearch/productsearch.component";
import {ApiClient} from "./services/apiclient.service";
import {InvoicesComponent} from "./invoices/invoices.component";
import {InvoiceComponent} from "./invoice/invoice.component";
import {OrderComponent} from "./order/order.component";
import {InvoiceService} from "./services/invoice.service";
import {OrderService} from "./services/order.service";
import {CityService} from "./services/city.service";
import {NgxToggleModule} from "ngx-toggle";
import {AuthGuardLoggedIn} from "./services/auth-guard-loggedin.service";
import {OrderAdminService} from "./services/orderadmin.service";
import {OrderSummaryComponent} from "./ordersummary/ordersummary.component";
import {defineLocale} from 'ngx-bootstrap/chronos';
import {deLocale} from 'ngx-bootstrap/locale';
import {ImpressComponent} from "./impress/impress.component";
import {NewsletterRegisterComponent} from "./newsletterregister/newsletterregister.component";
import {NewsletterService} from "./services/newsletter.service";

defineLocale('de', deLocale);

@NgModule({
    declarations: [
        AppComponent,
        RecurringOrdersComponent,
        RecurringOrderComponent,
        RecurringOrderRowComponent,
        HomeComponent,
        RegisterComponent,
        LoginComponent,
        LogoutComponent,
        AccountComponent,
        NotFoundComponent,
        ProductSearchComponent,
        InvoicesComponent,
        InvoiceComponent,
        OrderComponent,
        OrderSummaryComponent,
        ImpressComponent,
        NewsletterRegisterComponent,
    ],
    imports: [
        RoutingModule,
        SharedModule,
        NgPipesModule,
        TabsModule.forRoot(),
        TypeaheadModule.forRoot(),
        AccordionModule.forRoot(),
        NgbModule.forRoot(),
        NgxToggleModule,
        BsDatepickerModule.forRoot(),
    ],
    providers: [
        ApiClient,
        AuthService,
        AuthGuardUser,
        AuthGuardAdmin,
        AuthGuardLoggedIn,
        RecurringOrderService,
        ProductService,
        AccountService,
        LoginService,
        InvoiceService,
        OrderService,
        CityService,
        OrderAdminService,
        NewsletterService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    bootstrap: [AppComponent]
})

export class AppModule {
}
