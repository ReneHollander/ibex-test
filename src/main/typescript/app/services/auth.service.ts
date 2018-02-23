import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {User} from '../shared/models/user.model';

import {Observable} from 'rxjs/Observable';
import {fromPromise} from 'rxjs/observable/fromPromise';
import {from} from 'rxjs/observable/from';
import {catchError, map, mapTo, switchMap} from 'rxjs/operators';
import {LoginService} from "./login.service";

@Injectable()
export class AuthService {
    loggedIn = false;
    isAdmin = false;

    currentUser: User;

    constructor(private loginService: LoginService,
                private router: Router) {
    }

    initial(): Observable<boolean> {
        return this.loginService.initial()
            .pipe(switchMap((user) => {
                    this.currentUser = user;
                    if (this.currentUser.role == 'ADMIN') {
                        this.isAdmin = true;
                    }
                    this.loggedIn = true;
                    return from([true]);
                }
            ))
            .pipe(catchError((err, caught) => {
                return from([false])
            }));
    }

    login(email: String, password: String): Observable<User> {
        return this.loginService.login(email, password).pipe(map((user) => {
                this.currentUser = user;
                if (this.currentUser.role == 'ADMIN') {
                    this.isAdmin = true;
                }
                this.loggedIn = true;
                return user;
            }
        ));
    }

    logout(): Observable<any> {
        return this.loginService.logout().pipe(switchMap(() => {
                this.loggedIn = false;
                this.currentUser = null;
                console.log("here");
                return fromPromise(this.router.navigate(['/'])).pipe(mapTo(void 0))
            }
        ));
    }

}
