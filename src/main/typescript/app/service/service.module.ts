import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';

import {NewsletterService} from "./api/newsletter.service";
import {AccountService} from "./api/account.service";
import {AuthService} from "./auth/auth.service";
import {InvoiceService} from "./api/invoice.service";
import {LoginService} from "./api/login.service";
import {OrderAdminService} from "./api/orderadmin.service";
import {AuthGuardUser} from "./guard/auth-guard-login.service";
import {CityService} from "./api/city.service";
import {ProductService} from "./api/product.service";
import {RecurringOrderService} from "./api/recurringorder.service";
import {ApiClient} from "./api/apiclient.service";
import {AuthGuardLoggedIn} from "./guard/auth-guard-loggedin.service";
import {AuthGuardAdmin} from "./guard/auth-guard-admin.service";
import {OrderService} from "./api/order.service";

@NgModule({
    imports: [
        HttpClientModule,
    ],
    exports: [
        HttpClientModule,
    ],
    declarations: [],
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
    ]
})
export class ServiceModule {
}
