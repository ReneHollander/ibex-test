import {Product} from "./product.model";
import {Type} from "class-transformer";
import {Account} from "./account.model";

export class Order {
    id?: number;
    @Type(() => Date)
    deliveryTime?: Date;
    @Type(() => Date)
    orderTime: Date;
    address: string;
    postcode: number;
    city: string;
    deliveryNote: string;
    priceShipping: number;
    priceTotal: number;
    @Type(() => Account)
    account: Account;
    @Type(() => OrderItem)
    items: OrderItem[];
}

export class OrderItem {
    @Type(() => Product)
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
