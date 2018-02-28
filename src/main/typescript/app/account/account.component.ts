import {Component, OnInit} from '@angular/core';
import {ToastComponent} from '../shared/toast/toast.component';
import {AuthService} from '../services/auth.service';
import {AccountService} from '../services/account.service';
import {AccountDetails} from "../shared/models/account.model";

@Component({
    selector: 'app-account',
    templateUrl: './account.component.html'
})
export class AccountComponent implements OnInit {

    accountDetails: AccountDetails;
    isLoading = true;

    constructor(private auth: AuthService,
                public toast: ToastComponent,
                private userService: AccountService) {
    }

    async ngOnInit(): Promise<void> {
        await this.getAccountDetails();
    }

    async getAccountDetails(): Promise<void> {
        this.accountDetails = await this.userService.getAccountDetails();
        this.isLoading = false;
    }

}
