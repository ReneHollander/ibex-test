import {Component, OnInit} from '@angular/core';
import {AuthService} from './service/auth/auth.service';
import {buildVersion} from "../version";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

    buildVersion = buildVersion;

    constructor(public auth: AuthService) {
    }

    async ngOnInit(): Promise<void> {
        await this.auth.initial();
    }

}
