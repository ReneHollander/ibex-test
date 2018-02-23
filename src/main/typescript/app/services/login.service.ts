import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {User} from "../shared/models/user.model";

@Injectable()
export class LoginService {

    constructor(private http: HttpClient) {
    }

    initial(): Observable<User> {
        return this.http.get<User>('/api/initial');
    }

    login(email: String, password: String): Observable<User> {
        return this.http.post<User>('/api/login', {email: email, password: password});
    }

    logout(): Observable<any> {
        return this.http.get<any>('/api/logout');
    }

}
