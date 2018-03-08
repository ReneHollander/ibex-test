import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {Role, User} from '../shared/models/user.model';
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
        if (this.currentUser == null) {
            try {
                this.currentUser = await this.loginService.initial();
                if (this.currentUser.role == Role.ADMIN) {
                    this.isAdmin = true;
                }
                this.loggedIn = true;
                return true;
            } catch (e) {
                return false;
            }
        } else {
            return this.loggedIn;
        }
    }

    async login(email: String, password: String): Promise<User> {
        this.currentUser = await this.loginService.login(email, password);
        if (this.currentUser.role == Role.ADMIN) {
            this.isAdmin = true;
        }
        this.loggedIn = true;
        return this.currentUser;
    }

    clear(): void {
        this.loggedIn = false;
        this.isAdmin = false;
        this.currentUser = null;
    }

    async logout(): Promise<void> {
        await this.loginService.logout();
        this.clear();
        await this.router.navigate(['/']);
    }

}
