import {Product} from "./product.model";

export class DeliverySlot {
    id?: number;
    name?: string;
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
    items?: RecurringOrderItem[];

    constructor(deliverySlot?: DeliverySlot, enabled?: boolean, items?: RecurringOrderItem[]) {
        this.deliverySlot = deliverySlot;
        this.enabled = enabled;
        this.items = items;
    }
}

export class RecurringOrderItem {
    product?: Product;
    amount?: number;

    constructor(product?: Product, amount?: number) {
        this.product = product;
        this.amount = amount;
    }

}
