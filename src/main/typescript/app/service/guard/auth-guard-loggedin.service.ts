import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {AuthService} from '../auth/auth.service';

@Injectable()
export class AuthGuardLoggedIn implements CanActivate {

    constructor(private authService: AuthService, private router: Router) {
    }

    async canActivate() {
        await this.authService.initial();
        if (this.authService.isLoggedIn) {
            return true;
        } else {
            this.router.navigate(['/login']);
            return false;
        }
    }

}
