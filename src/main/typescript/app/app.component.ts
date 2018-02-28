import {Component, OnInit} from '@angular/core';
import {AuthService} from './services/auth.service';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {

    constructor(public auth: AuthService) {
    }

    async ngOnInit(): Promise<void> {
        await this.auth.initial();
    }

}
