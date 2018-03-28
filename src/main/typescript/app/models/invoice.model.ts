import {Type} from "class-transformer";
import {Order} from "./order.model";
import {serializeType} from "../util";
import {ProductAmount} from "./product.model";
import {DeliveryFeeAmount} from "./city.model";

export class Invoice {
    id?: number;
    @Type(serializeType(Date))
    date?: Date;
    accountName: string;
    iban: string;
    priceTotal: number;
    @Type(serializeType(Order))
    orders: Order[];
    @Type(serializeType(ProductAmount))
    productAmounts: ProductAmount[];
    @Type(serializeType(DeliveryFeeAmount))
    deliveryFeeAmounts: DeliveryFeeAmount[];

    constructor(id: number, date: Date, accountName: string, iban: string) {
        this.id = id;
        this.date = date;
        this.accountName = accountName;
        this.iban = iban;
    }

    get total(): number {
        return this.productAmounts.map(amount => amount.total).reduce((v, k) => v + k, 0) +
            this.deliveryFeeAmounts.map(amount => amount.total).reduce((v, k) => v + k, 0);
    }
}
