import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../service/auth/auth.service';

@Component({
    selector: 'app-logout',
    template: ''
})
export class LogoutComponent implements OnInit {

    constructor(private auth: AuthService) {
    }

    ngOnInit() {
        this.auth.logout();
    }

}
