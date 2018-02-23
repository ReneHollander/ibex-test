import {Component, EventEmitter, HostListener, Input, Output} from '@angular/core';
import {RecurringOrderItem} from "../shared/models/recurringorder.model";

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

    edit: boolean = false;

    value: number;

    constructor() {
    }

    delete(): void {
        this.onDelete.emit(this.item)
    }

    onAmountClick($event: Event) {
        $event.preventDefault();
        $event.stopPropagation();
        if (!this.edit) {
            this.value = this.item.amount;
            this.edit = true;
        }
    }

    @HostListener('document:click', ['$event']) clickedOutside($event: Event) {
        this.edit = false;
    }

    onExitEdit($event: Event) {
        $event.preventDefault();
        $event.stopPropagation();
        this.edit = false;
    }

    onSaveEdit($event: Event) {
        $event.preventDefault();
        $event.stopPropagation();
        if (this.value == 0) {
            this.onDelete.emit(this.item);
        } else {
            this.item.amount = this.value;
            this.onUpdate.emit(this.item);
            this.edit = false;
        }
    }
}
