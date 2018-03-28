import {Type} from "class-transformer";
import {Order} from "./order.model";
import {serializeType} from "../util";

export class Invoice {
    id?: number;
    @Type(serializeType(Date))
    date?: Date;
    accountName: string;
    iban: string;
    priceTotal: number;
    @Type(serializeType(Order))
    orders: Order[];

    constructor(id: number, date: Date, accountName: string, iban: string) {
        this.id = id;
        this.date = date;
        this.accountName = accountName;
        this.iban = iban;
    }
}
