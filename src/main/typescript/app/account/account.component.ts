import {Component, OnInit} from '@angular/core';
import {ToastComponent} from '../shared/toast/toast.component';
import {AuthService} from '../services/auth.service';
import {AccountService} from '../services/account.service';
import {User} from "../shared/models/user.model";

@Component({
    selector: 'app-account',
    templateUrl: './account.component.html'
})
export class AccountComponent implements OnInit {

    user: User;
    isLoading = true;

    constructor(private auth: AuthService,
                public toast: ToastComponent,
                private userService: AccountService) {
    }

    async ngOnInit(): Promise<void> {
        await this.getAccountDetails();
    }

    async getAccountDetails(): Promise<void> {
        this.user = await this.userService.getAccountDetails();
        this.isLoading = false;
    }

}
