import {Injectable} from '@angular/core';
import {AccountDetails} from "../shared/models/account.model";
import {ApiClient} from "./apiclient.service";

@Injectable()
export class AccountService {

    constructor(private api: ApiClient) {
    }

    async getAccountDetails(): Promise<AccountDetails> {
        return this.api.getAndConvert(AccountDetails, '/api/accountdetails');
    }

}
