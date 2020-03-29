import {Product} from './product.model';
import {Type} from 'class-transformer';
import {Account} from './account.model';
import {serializeType} from '../util';

export class OrderItem {
    @Type(serializeType(Product))
    product?: Product;
    amount?: number;

    constructor(product: Product, amount: number) {
        this.product = product;
        this.amount = amount;
    }

    get priceTotal(): number {
        return this.product.price * this.amount;
    }
}

export class Order {
    id?: number;
    @Type(serializeType(Date))
    deliveryTime?: Date;
    @Type(serializeType(Date))
    orderTime: Date;
    address: string;
    postcode: number;
    city: string;
    deliveryNote: string;
    priceShipping: number;
    priceTotal: number;
    @Type(serializeType(Account))
    account: Account;
    @Type(serializeType(OrderItem))
    items: OrderItem[];
}
