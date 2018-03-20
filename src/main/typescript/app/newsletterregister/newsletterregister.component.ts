import {Component, OnInit} from '@angular/core';
import {City} from "../models/city.model";
import {CityService} from "../service/api/city.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {NewsletterService} from "../service/api/newsletter.service";
import {ToastrService} from "ngx-toastr";

@Component({
    selector: 'newsletterregister',
    templateUrl: './newsletterregister.component.html',
    styleUrls: ['./newsletterregister.component.css']
})
export class NewsletterRegisterComponent implements OnInit {

    isLoading: boolean = true;
    cities: City[];

    form: FormGroup;
    city = new FormControl('', [
        Validators.required,
    ]);
    email = new FormControl('', [
        Validators.required,
        Validators.email
    ]);

    constructor(private cityService: CityService,
                private formBuilder: FormBuilder,
                private toastr: ToastrService,
                private newsletterService: NewsletterService) {
    }

    async ngOnInit(): Promise<void> {
        this.isLoading = true;
        this.cities = await this.cityService.getDisabledCitiesCached();
        this.form = this.formBuilder.group({
            city: this.city,
            email: this.email,
        });
        this.isLoading = false;
    }

    async subscribe() {
        if (this.form.valid) {
            try {
                await this.newsletterService.register(this.form.value);
                this.toastr.success("Sie erhalten nun updates an Ihre E-Mail Adresse.");
            } catch (e) {
                console.log(e);
                if (e.error.message === 'email already subscribed') {
                    this.toastr.error("Die angegebene E-Mail Adresse wurde schon registriert!");
                } else {
                    this.toastr.error("Ein unbekannter Fehler ist aufgetreten!");
                }
            }
            this.form.reset();
        }
    }

    setClass(fc: FormControl) {
        return {
            'is-invalid': fc.invalid && (fc.dirty || fc.touched),
            'is-valid': fc.valid && (fc.dirty || fc.touched)
        }
    }
}
