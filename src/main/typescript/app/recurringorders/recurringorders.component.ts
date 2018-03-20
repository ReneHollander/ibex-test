import {Component, OnInit} from '@angular/core';
import {RecurringOrderService} from "../service/api/recurringorder.service";
import {RecurringOrder} from "../shared/models/recurringorder.model";

@Component({
    selector: 'app-recurring-orders',
    templateUrl: './recurringorders.component.html',
    styleUrls: ['./recurringorders.component.css'],
})
export class RecurringOrdersComponent implements OnInit {

    recurringOrders: RecurringOrder[] = [];
    isLoading = true;

    constructor(private recurringOrderService: RecurringOrderService) {
    }

    async ngOnInit(): Promise<void> {
        await this.getRecurringOrders();
    }

    async getRecurringOrders(): Promise<void> {
        this.recurringOrders = await this.recurringOrderService.getRecurringOrders();
        this.isLoading = false;
    }

}
