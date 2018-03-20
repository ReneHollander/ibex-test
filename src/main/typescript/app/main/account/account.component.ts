import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../service/auth/auth.service';
import {AccountService} from '../../service/api/account.service';
import {User} from "../../models/user.model";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {validateEqual} from "../../util";
import {ToastrService} from "ngx-toastr";

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
                private toastr: ToastrService,
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
                this.toastr.success("Ihr Passwort wurde geändert.");
                this.passwordChangeForm.reset();
            } catch (e) {
                if (e.error.message === 'invalid password') {
                    this.toastr.error("Das angegebene aktuelle Passwort ist ungültig.");
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
