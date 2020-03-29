import {City} from './city.model';
import {Type} from 'class-transformer';
import {serializeType} from '../util';

export class NewsletterRegistration {
    email?: string;
    @Type(serializeType(City))
    city?: City;
}
