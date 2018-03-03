import {Injectable} from '@angular/core';
import {User} from "../shared/models/user.model";
import {HttpClient} from "@angular/common/http";
import {plainToClass} from "class-transformer";

@Injectable()
export class LoginService {

    constructor(private http: HttpClient) {
    }

    async initial(): Promise<User> {
        return plainToClass(User, await this.http.get<User>('/api/initial').toPromise());
    }

    async login(email: String, password: String): Promise<User> {
        return plainToClass(User, await this.http.post<User>('/api/login', {
            email: email,
            password: password
        }).toPromise());
    }

    async logout(): Promise<void> {
        await this.http.get('/api/logout').toPromise();
    }

}
