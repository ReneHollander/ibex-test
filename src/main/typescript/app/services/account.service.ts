import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {AccountDetails} from "../shared/models/account.model";

@Injectable()
export class AccountService {

    constructor(private http: HttpClient) {
    }

    getAccountDetails(): Observable<AccountDetails> {
        return this.http.get<AccountDetails>('/api/accountdetails');
    }

}
