import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {AuthService} from '../auth/auth.service';

@Injectable()
export class AuthGuardUser implements CanActivate {

    constructor(private authService: AuthService, private router: Router) {
    }

    async canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean> {
        await this.authService.initial();
        if (this.authService.isUser) {
            return true;
        } else {
            await this.router.navigate(['/login']);
            return false;
        }
    }

}
