import {Product} from "./product.model";
import {Type} from "class-transformer";
import {serializeType} from "../util";

export class DeliverySlot {
    id?: number;
    name?: string;
    @Type(serializeType(Date))
    deliverBy: Date;

    constructor(id?: number, name?: string, deliverBy?: Date) {
        this.id = id;
        this.name = name;
        this.deliverBy = deliverBy;
    }
}

export class RecurringOrderItem {
    @Type(serializeType(Product))
    product?: Product;
    amount?: number;

    constructor(product?: Product, amount?: number) {
        this.product = product;
        this.amount = amount;
    }
}

export class RecurringOrder {
    deliverySlot?: DeliverySlot;
    enabled?: boolean;
    @Type(serializeType(RecurringOrderItem))
    items?: RecurringOrderItem[];

    constructor(deliverySlot?: DeliverySlot, enabled?: boolean, items?: RecurringOrderItem[]) {
        this.deliverySlot = deliverySlot;
        this.enabled = enabled;
        this.items = items;
    }
}

