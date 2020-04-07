import {Component, OnInit} from '@angular/core';
import {AuthService} from './service/auth/auth.service';
import {IBEX_BUILD_GITHUB_TAG} from '../version';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

    IBEX_BUILD_GITHUB_TAG = IBEX_BUILD_GITHUB_TAG;

    constructor(public auth: AuthService) {
    }

    async ngOnInit(): Promise<void> {
        await this.auth.initial();
    }

}
