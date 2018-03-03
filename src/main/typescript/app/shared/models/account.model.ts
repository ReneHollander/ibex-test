import {City} from "./city.model";
import {Type} from "class-transformer";

// TODO: remove once released: https://github.com/angular/angular-cli/pull/9225
function city() {
    return () => City
}

export class AccountDetails {
    name?: string;
    email?: string;
    postcode?: number;
    // @Type(city)
    @Type(() => City)
    city?: City;
    address?: string;
    deliveryNote?: string;
    accountName?: string;
    iban?: string;
}
