import {Injectable} from '@angular/core';
import {User} from "../shared/models/user.model";
import {ApiClient} from "./apiclient.service";

@Injectable()
export class LoginService {

    constructor(private api: ApiClient) {
    }

    async initial(): Promise<User> {
        return this.api.getAndConvert(User, '/api/initial');
    }

    async login(email: String, password: String): Promise<User> {
        return this.api.postAndConvert(User, '/api/login', {
            email: email,
            password: password
        });
    }

    async logout(): Promise<any> {
        return this.api.get<any>('/api/logout');
    }

}
