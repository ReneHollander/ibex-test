import {Component, EventEmitter, Input, Output} from '@angular/core';
import {RecurringOrderItem} from "../models/recurringorder.model";

@Component({
    selector: '[recurring-orders-row]',
    templateUrl: './recurringorderrow.component.html',
    styleUrls: ['./recurringorderrow.component.css']
})
export class RecurringOrderRowComponent {

    @Output() onDelete = new EventEmitter<RecurringOrderItem>();

    @Output() onUpdate = new EventEmitter<RecurringOrderItem>();

    @Input()
    item: RecurringOrderItem;

    constructor() {
    }

    delete(): void {
        this.onDelete.emit(this.item)
    }

    onAmountChange() {
        this.onUpdate.emit(this.item);
    }
}
