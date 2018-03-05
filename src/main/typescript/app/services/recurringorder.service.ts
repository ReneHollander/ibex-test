import {Injectable} from '@angular/core';
import {RecurringOrder} from "../shared/models/recurringorder.model";
import {ApiClient} from "./apiclient.service";

@Injectable()
export class RecurringOrderService {

    constructor(private api: ApiClient) {
    }

    async getRecurringOrders(): Promise<RecurringOrder[]> {
        return this.api.getAndConvertArray(RecurringOrder, '/api/recurringorders');
    }

    async countRecurringOrders(): Promise<number> {
        return (await this.getRecurringOrders()).length;
    }

    async update(recurringOrder: RecurringOrder): Promise<RecurringOrder> {
        return this.api.postAndConvert(RecurringOrder, '/api/recurringorder', recurringOrder);
    }
}
