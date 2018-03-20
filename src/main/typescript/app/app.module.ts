import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {RoutingModule} from './routing.module';
import {AppComponent} from './app.component';
import {AdminModule} from "./admin/admin.module";
import {MainModule} from "./main/main.module";
import {UserModule} from "./user/user.module";
import {SharedModule} from "./shared/shared.module";

@NgModule({
    declarations: [
        AppComponent,
    ],
    imports: [
        SharedModule,
        RoutingModule,
        AdminModule,
        MainModule,
        UserModule,
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    bootstrap: [AppComponent]
})
export class AppModule {
}
