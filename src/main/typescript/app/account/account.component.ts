import {Component, OnInit} from '@angular/core';
import {ToastComponent} from '../shared/toast/toast.component';
import {AuthService} from '../services/auth.service';
import {AccountService} from '../services/account.service';
import {User} from "../shared/models/user.model";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {validateEqual} from "../util";

@Component({
    selector: 'app-account',
    templateUrl: './account.component.html'
})
export class AccountComponent implements OnInit {

    user: User;
    isLoading = true;

    passwordChangeForm: FormGroup;
    currentPassword = new FormControl('', [
        Validators.required,
    ]);
    newPassword = new FormControl('', [
        Validators.required,
        Validators.minLength(8),
    ]);
    newPasswordRepeat = new FormControl('', [
        Validators.required,
        Validators.minLength(8),
    ]);

    constructor(private auth: AuthService,
                private formBuilder: FormBuilder,
                private toast: ToastComponent,
                private accountService: AccountService) {
    }

    async ngOnInit(): Promise<void> {
        this.isLoading = true;
        this.passwordChangeForm = this.formBuilder.group({
            currentPassword: this.currentPassword,
            newPassword: this.newPassword,
            newPasswordRepeat: this.newPasswordRepeat,
        }, {
            validator: validateEqual('newPassword', 'newPasswordRepeat'),
        });
        await this.getAccountDetails();
        this.isLoading = false;
    }

    async getAccountDetails(): Promise<void> {
        this.user = await this.accountService.getAccountDetails();
    }

    async changePassword() {
        if (this.passwordChangeForm.valid) {
            try {
                await this.accountService.changePassword(this.currentPassword.value, this.newPassword.value);
                this.toast.setMessage("Ihr Passwort wurde geändert.", "success");
                this.passwordChangeForm.reset();
            } catch (e) {
                if (e.error.message === 'invalid password') {
                    this.toast.setMessage("Das angegebene aktuelle Passwort ist ungültig.", "danger");
                }
            }
        }
    }

    setClass(fc: FormControl) {
        return {
            'is-invalid': fc.invalid && (fc.dirty || fc.touched),
            'is-valid': fc.valid && (fc.dirty || fc.touched)
        }
    }
}
