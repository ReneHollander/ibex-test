import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {AuthService} from './auth.service';

@Injectable()
export class AuthGuardLogin implements CanActivate {

    constructor(private authService: AuthService, private router: Router) {
    }

    async canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean> {
        await this.authService.initial();
        if (!this.authService.loggedIn) {
            this.router.navigate(['/login']);
            return false;
        } else {
            return true;
        }
    }

}
