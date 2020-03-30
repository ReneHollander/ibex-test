import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {Role, User} from '../../models/user.model';
import {LoginService} from '../api/login.service';

@Injectable()
export class AuthService {

    private _loggedIn = false;

    constructor(private loginService: LoginService,
                private router: Router) {
    }

    private _user: User;

    get user(): User {
        return this._user;
    }

    get isLoggedIn(): boolean {
        return this._loggedIn;
    }

    get isUser(): boolean {
        return this.isLoggedIn && this._user.role === Role.USER;
    }

    get isAdmin(): boolean {
        return this.isLoggedIn && this._user.role === Role.ADMIN;
    }

    async initial(): Promise<boolean> {
        if (this._user === null) {
            try {
                this._user = await this.loginService.initial();
                this._loggedIn = true;
                return true;
            } catch (e) {
                return false;
            }
        } else {
            return this._loggedIn;
        }
    }

    async login(email: String, password: String): Promise<User> {
        this._user = await this.loginService.login(email, password);
        this._loggedIn = true;
        return this._user;
    }

    clear(): void {
        this._loggedIn = false;
        this._user = null;
    }

    async logout(): Promise<void> {
        await this.loginService.logout();
        this.clear();
        await this.router.navigate(['/']);
    }

}
