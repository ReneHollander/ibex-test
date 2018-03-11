import {City} from "./city.model";
import {Type} from "class-transformer";

export class NewsletterRegistration {
    email?: string;
    @Type(() => City)
    city?: City;
}