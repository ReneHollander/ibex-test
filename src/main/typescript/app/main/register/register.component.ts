import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';

import {AccountService} from '../../service/api/account.service';
import {City} from "../../models/city.model";
import {CityService} from "../../service/api/city.service";
import {ToastrService} from "ngx-toastr";

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html'
})
export class RegisterComponent implements OnInit {

    cities: City[];

    registerForm: FormGroup;
    name = new FormControl('', [
        Validators.required,
        // Validators.minLength(2),
        // Validators.maxLength(30),
        // Validators.pattern('[a-zA-Z0-9_-\\s]*')
    ]);
    email = new FormControl('', [
        Validators.required,
        // Validators.minLength(3),
        // Validators.maxLength(100),
        // Validators.email
    ]);
    password = new FormControl('', [
        Validators.required,
        // Validators.minLength(6)
    ]);
    passwordRepeat = new FormControl('', [
        Validators.required,
        // Validators.minLength(6)
    ]);
    address = new FormControl('', [
        Validators.required,
        // Validators.minLength(3)
    ]);
    city = new FormControl('', [
        Validators.required,
    ]);
    deliveryNote = new FormControl('');
    accountName = new FormControl('', [
        Validators.required,
        // Validators.minLength(3)
    ]);
    iban = new FormControl('', [
        Validators.required,
        // Validators.pattern('^AT\\d{18}$')
    ]);
    phone = new FormControl('', [
        Validators.required,
    ]);
    isLoading: boolean = true;

    constructor(private formBuilder: FormBuilder,
                private router: Router,
                private toastr: ToastrService,
                private userService: AccountService,
                private cityService: CityService) {
    }

    async getCities(): Promise<void> {
        this.isLoading = true;
        this.cities = await this.cityService.getEnabledCities();
        this.isLoading = false;
    }

    async ngOnInit() {
        await this.getCities();
        this.registerForm = this.formBuilder.group({
            name: this.name,
            email: this.email,
            password: this.password,
            passwordRepeat: this.passwordRepeat,
            address: this.address,
            city: this.city,
            deliveryNote: this.deliveryNote,
            accountName: this.accountName,
            iban: this.iban,
            phone: this.phone,
        });
    }

    setClass(fc: FormControl): any {
        return {'has-danger': !fc.pristine && !fc.valid};
    }

    register() {
        console.log(this.registerForm.value);
        // this.userService.register(this.registerForm.value).subscribe(
        //     res => {
        //         this.toast.setMessage('you successfully registered!', 'success');
        //         this.router.navigate(['/login']);
        //     },
        //     error => this.toast.setMessage('email already exists', 'danger')
        // );
    }
}
