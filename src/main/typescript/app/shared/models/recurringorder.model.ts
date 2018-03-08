import {Product} from "./product.model";
import {Type} from "class-transformer";

export class DeliverySlot {
    id?: number;
    name?: string;
    @Type(() => Date)
    deliverBy: Date;

    constructor(id?: number, name?: string, deliverBy?: Date) {
        this.id = id;
        this.name = name;
        this.deliverBy = deliverBy;
    }
}

export class RecurringOrder {
    deliverySlot?: DeliverySlot;
    enabled?: boolean;
    @Type(() => RecurringOrderItem)
    items?: RecurringOrderItem[];

    constructor(deliverySlot?: DeliverySlot, enabled?: boolean, items?: RecurringOrderItem[]) {
        this.deliverySlot = deliverySlot;
        this.enabled = enabled;
        this.items = items;
    }
}

export class RecurringOrderItem {
    @Type(() => Product)
    product?: Product;
    amount?: number;

    constructor(product?: Product, amount?: number) {
        this.product = product;
        this.amount = amount;
    }

}
