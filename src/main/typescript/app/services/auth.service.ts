import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {User} from '../shared/models/user.model';
import {LoginService} from "./login.service";

@Injectable()
export class AuthService {
    loggedIn = false;
    isAdmin = false;

    currentUser: User;

    constructor(private loginService: LoginService,
                private router: Router) {
    }

    async initial(): Promise<boolean> {
        try {
            this.currentUser = await this.loginService.initial();
            if (this.currentUser.role == 'ADMIN') {
                this.isAdmin = true;
            }
            this.loggedIn = true;
            return true;
        } catch (e) {
            return false;
        }
    }

    async login(email: String, password: String): Promise<User> {
        this.currentUser = await this.loginService.login(email, password);
        if (this.currentUser.role == 'ADMIN') {
            this.isAdmin = true;
        }
        this.loggedIn = true;
        return this.currentUser;
    }

    async logout(): Promise<void> {
        await this.loginService.logout();
        this.loggedIn = false;
        this.currentUser = null;
        await this.router.navigate(['/']);
    }

}
