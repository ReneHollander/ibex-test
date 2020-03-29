import {NgModule} from '@angular/core';
import {SharedModule} from '../shared/shared.module';
import {LoginComponent} from './login/login.component';
import {HomeComponent} from './home/home.component';
import {ImpressComponent} from './impress/impress.component';
import {LogoutComponent} from './logout/logout.component';
import {NewsletterRegisterComponent} from './newsletterregister/newsletterregister.component';
import {RegisterComponent} from './register/register.component';
import {AccountComponent} from './account/account.component';
import {NotFoundComponent} from './not-found/not-found.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';
import {BuildInformationComponent} from './buildinfo/build-information.component';

@NgModule({
    imports: [
        SharedModule,
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
    ],
    exports: [
        HomeComponent,
        RegisterComponent,
        LoginComponent,
        LogoutComponent,
        AccountComponent,
        NotFoundComponent,
        ImpressComponent,
        BuildInformationComponent,
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
        BuildInformationComponent,
    ],
    providers: []
})
export class MainModule {
}
