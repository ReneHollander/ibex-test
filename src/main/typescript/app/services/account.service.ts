import {Injectable} from '@angular/core';
import {ApiClient} from "./apiclient.service";
import {User} from "../shared/models/user.model";

@Injectable()
export class AccountService {

    constructor(private api: ApiClient) {
    }

    async getAccountDetails(): Promise<User> {
        return this.api.getAndConvert(User, '/api/account/details');
    }

    async changePassword(oldPassword: string, newPassword: string): Promise<any> {
        return this.api.post('/api/account/password', {'oldPassword': oldPassword, 'newPassword': newPassword});
    }

}
