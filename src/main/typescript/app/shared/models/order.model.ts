import {Product} from "./product.model";
import {Type} from "class-transformer";

// TODO: remove once released: https://github.com/angular/angular-cli/pull/9225
function orderitem() {
    return () => OrderItem
}

export class Order {
    id?: number;
    deliveryTime?: Date;
    orderTime: Date;
    address: string;
    postcode: number;
    city: string;
    deliveryNote: string;
    priceShipping: number;
    priceTotal: number;

    // @Type(orderitem)
    @Type(() => OrderItem)
    items: OrderItem[];

}

// TODO: remove once released: https://github.com/angular/angular-cli/pull/9225
function product() {
    return () => Product
}

export class OrderItem {
    // @Type(product)
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
