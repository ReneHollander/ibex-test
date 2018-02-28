import {Component, OnInit} from '@angular/core';
import {RecurringOrderService} from "../services/recurringorder.service";
import {RecurringOrder} from "../shared/models/recurringorder.model";
import {ToastComponent} from "../shared/toast/toast.component";

@Component({
    selector: 'app-recurring-orders',
    templateUrl: './recurringorders.component.html',
    styleUrls: ['./recurringorders.component.css'],
})
export class RecurringOrdersComponent implements OnInit {

    recurringOrders: RecurringOrder[] = [];
    isLoading = true;

    constructor(private recurringOrderService: RecurringOrderService,
                public toast: ToastComponent) {
    }

    async ngOnInit(): Promise<void> {
        await this.getRecurringOrders();
    }

    async getRecurringOrders(): Promise<void> {
        this.recurringOrders = await this.recurringOrderService.getRecurringOrders();
        this.isLoading = false;
    }

}
