import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {RecurringOrder, RecurringOrderItem} from "../shared/models/recurringorder.model";

@Component({
    selector: 'recurring-order',
    templateUrl: './recurringorder.component.html',
    styleUrls: ['./recurringorder.component.css']
})
export class RecurringOrderComponent implements OnChanges {

    @Input()
    recurringOrder: RecurringOrder;

    deliveryFee: number = 1.5;

    total: number = this.deliveryFee;

    constructor() {
    }

    updateTotal() {
        this.total = this.recurringOrder.items.map(item => item.amount * item.product.price).reduce((sum, current) => sum + current) + this.deliveryFee;
    }

    ngOnChanges(changes: SimpleChanges) {
        this.updateTotal();
    }

    onDeleteItem(item: RecurringOrderItem) {
        const index = this.recurringOrder.items.indexOf(item);
        if (index !== -1) this.recurringOrder.items.splice(index, 1);
        this.updateTotal();
    }

    onUpdate(item: RecurringOrderItem) {
        this.updateTotal();
    }
}
