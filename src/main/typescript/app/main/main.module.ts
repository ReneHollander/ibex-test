import {NgModule} from '@angular/core';
import {SharedModule} from "../shared/shared.module";
import {LoginComponent} from "./login/login.component";
import {HomeComponent} from "./home/home.component";
import {ImpressComponent} from "./impress/impress.component";
import {LogoutComponent} from "./logout/logout.component";
import {NewsletterRegisterComponent} from "./newsletterregister/newsletterregister.component";
import {RegisterComponent} from "./register/register.component";
import {AccountComponent} from "./account/account.component";
import {NotFoundComponent} from "./not-found/not-found.component";

@NgModule({
    imports: [
        SharedModule,
    ],
    exports: [
        HomeComponent,
        RegisterComponent,
        LoginComponent,
        LogoutComponent,
        AccountComponent,
        NotFoundComponent,
        ImpressComponent,
    ],
    declarations: [
        HomeComponent,
        RegisterComponent,
        LoginComponent,
        LogoutComponent,
        AccountComponent,
        NotFoundComponent,
        ImpressComponent,
        NewsletterRegisterComponent,
    ],
    providers: []
})
export class MainModule {
}
