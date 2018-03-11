import {City} from "./city.model";
import {Type} from "class-transformer";
import {User} from "./user.model";

export class Account {
    id?: number;
    @Type(() => City)
    city?: City;
    address?: string;
    deliveryNote?: string;
    accountName?: string;
    iban?: string;
    phone: string;
    @Type(() => User)
    user: User;
}
