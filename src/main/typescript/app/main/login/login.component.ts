import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';

import {AuthService} from '../../service/auth/auth.service';
import {ToastrService} from "ngx-toastr";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {

    loginForm: FormGroup;
    email = new FormControl('', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(100)
    ]);
    password = new FormControl('', [
        Validators.required,
        Validators.minLength(4)
    ]);

    constructor(private auth: AuthService,
                private formBuilder: FormBuilder,
                private router: Router,
                private toastr: ToastrService) {
    }

    ngOnInit() {
        if (this.auth.isLoggedIn) {
            this.router.navigate(['/']);
        }
        this.loginForm = this.formBuilder.group({
            email: this.email,
            password: this.password
        });
    }

    setClassEmail() {
        return {'has-danger': !this.email.pristine && !this.email.valid};
    }

    setClassPassword() {
        return {'has-danger': !this.password.pristine && !this.password.valid};
    }

    async login() {
        try {
            await this.auth.login(this.loginForm.value.email, this.loginForm.value.password);
            await this.router.navigate(['/']);
        } catch (e) {
            this.toastr.error('Ungültige E-Mail Adresse oder Passwort.')
        }
    }

}
