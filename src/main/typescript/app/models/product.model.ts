import {Type} from "class-transformer";
import {serializeType} from "../util";

export class Product {
    id?: number;
    name?: string;
    price?: number;

    constructor(id?: number, name?: string, price?: number) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

export class ProductAmount {
    @Type(serializeType(Product))
    product?: Product;
    amount?: number;

    get total(): number {
        return this.product.price * this.amount;
    }
}
