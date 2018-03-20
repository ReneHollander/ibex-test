import {Injectable} from '@angular/core';
import {ApiClient} from "./apiclient.service";
import {Order} from "../../models/order.model";

@Injectable()
export class OrderService {

    constructor(private api: ApiClient) {
    }

    async getPendingOrders(): Promise<Order[]> {
        return this.api.getAndConvertArray(Order, '/api/orders/pending');
    }

    async getOrder(id: number): Promise<Order> {
        return this.api.getAndConvert(Order, '/api/order/' + id);
    }

}
