import {Component, OnInit} from '@angular/core';
import {City} from "../shared/models/city.model";
import {CityService} from "../services/city.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ToastComponent} from "../shared/toast/toast.component";
import {NewsletterService} from "../services/newsletter.service";

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
                private toast: ToastComponent,
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
                this.toast.setMessage("Sie erhalten nun updates an Ihre E-Mail Adresse.", "success");
            } catch (e) {
                console.log(e);
                if (e.error.message === 'email already subscribed') {
                    this.toast.setMessage("Die angegebene E-Mail Adresse wurde schon registriert!", "danger");
                } else {
                    this.toast.setMessage("Ein unbekannter Fehler ist aufgetreten!", "danger");
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
