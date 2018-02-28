import {Product} from "./product.model";
import {Type} from "class-transformer";

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

// TODO: remove once released: https://github.com/angular/angular-cli/pull/9225
function recurringorderitem() {
    return () => RecurringOrderItem
}

export class RecurringOrder {
    deliverySlot?: DeliverySlot;
    enabled?: boolean;
    // @Type(recurringorderitem)
    @Type(() => RecurringOrderItem)
    items?: RecurringOrderItem[];

    constructor(deliverySlot?: DeliverySlot, enabled?: boolean, items?: RecurringOrderItem[]) {
        this.deliverySlot = deliverySlot;
        this.enabled = enabled;
        this.items = items;
    }
}

// TODO: remove once released: https://github.com/angular/angular-cli/pull/9225
function product() {
    return () => Product
}

export class RecurringOrderItem {
    // @Type(product)
    @Type(() => Product)
    product?: Product;
    amount?: number;

    constructor(product?: Product, amount?: number) {
        this.product = product;
        this.amount = amount;
    }

}
