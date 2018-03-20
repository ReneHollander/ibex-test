import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {RoutingModule} from './routing.module';
import {SharedModule} from './shared/shared.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './main/home/home.component';
import {RegisterComponent} from './main/register/register.component';
import {LoginComponent} from './main/login/login.component';
import {LogoutComponent} from './main/logout/logout.component';
import {AccountComponent} from './main/account/account.component';
import {NotFoundComponent} from './main/not-found/not-found.component';
import {RecurringOrdersComponent} from "./user/recurringorders/recurringorders.component";
import {RecurringOrderComponent} from "./user/recurringorder/recurringorder.component";
import {RecurringOrderRowComponent} from "./user/recurringorderrow/recurringorderrow.component";
import {NgPipesModule} from "ngx-pipes";
import {AccordionModule, BsDatepickerModule, TabsModule, TypeaheadModule} from "ngx-bootstrap";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {ProductSearchComponent} from "./user/productsearch/productsearch.component";
import {InvoicesComponent} from "./user/invoices/invoices.component";
import {InvoiceComponent} from "./user/invoice/invoice.component";
import {OrderComponent} from "./user/order/order.component";
import {NgxToggleModule} from "ngx-toggle";
import {OrderSummaryComponent} from "./admin/ordersummary/ordersummary.component";
import {defineLocale} from 'ngx-bootstrap/chronos';
import {deLocale} from 'ngx-bootstrap/locale';
import {ImpressComponent} from "./main/impress/impress.component";
import {NewsletterRegisterComponent} from "./main/newsletterregister/newsletterregister.component";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ToastrModule} from "ngx-toastr";
import {ServiceModule} from "./service/service.module";

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
        BrowserAnimationsModule,
        ToastrModule.forRoot({
            positionClass: 'toast-bottom-center',
            timeOut: 3000,
        }),
        ServiceModule,
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    bootstrap: [AppComponent]
})
export class AppModule {
}
