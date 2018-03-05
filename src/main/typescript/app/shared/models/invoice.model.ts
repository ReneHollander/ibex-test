import {Type} from "class-transformer";
import {Order} from "./order.model";

// TODO: remove once released: https://github.com/angular/angular-cli/pull/9225
function order() {
    return () => Order
}

export class Invoice {
    id?: number;
    date?: Date;
    accountName: Date;
    iban: string;
    priceTotal: number;

    // @Type(order)
    @Type(() => Order)
    orders: Order[];

    constructor(id: number, date: Date, accountName: Date, iban: string) {
        this.id = id;
        this.date = date;
        this.accountName = accountName;
        this.iban = iban;
    }
}
