import {Type} from "class-transformer";
import {Order} from "./order.model";

export class Invoice {
    id?: number;
    @Type(() => Date)
    date?: Date;
    @Type(() => Date)
    accountName: Date;
    iban: string;
    priceTotal: number;
    @Type(() => Order)
    orders: Order[];

    constructor(id: number, date: Date, accountName: Date, iban: string) {
        this.id = id;
        this.date = date;
        this.accountName = accountName;
        this.iban = iban;
    }
}
