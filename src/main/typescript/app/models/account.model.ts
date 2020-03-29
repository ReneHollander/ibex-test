import {City} from './city.model';
import {Type} from 'class-transformer';
import {User} from './user.model';
import {serializeType} from '../util';

export class Account {
    id?: number;
    @Type(serializeType(City))
    city?: City;
    address?: string;
    deliveryNote?: string;
    accountName?: string;
    iban?: string;
    phone: string;
    @Type(serializeType(User))
    user: User;
}
