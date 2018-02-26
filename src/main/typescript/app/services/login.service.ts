import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {User} from "../shared/models/user.model";
import {plainToClassOp} from "../util";

@Injectable()
export class LoginService {

    constructor(private http: HttpClient) {
    }

    initial(): Observable<User> {
        return this.http.get<User>('/api/initial').pipe(plainToClassOp(User));
    }

    login(email: String, password: String): Observable<User> {
        return this.http.post<User>('/api/login', {email: email, password: password}).pipe(plainToClassOp(User));
    }

    logout(): Observable<any> {
        return this.http.get<any>('/api/logout');
    }

}
