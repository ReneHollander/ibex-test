import {Injectable} from '@angular/core';
import {ProductAmount} from "../shared/models/product.model";
import {ApiClient} from "./apiclient.service";
import {Order} from "../shared/models/order.model";

@Injectable()
export class OrderAdminService {

    constructor(private api: ApiClient) {
    }

    async getProductAmounts(date: Date): Promise<ProductAmount[]> {
        return this.api.getAndConvertArray(ProductAmount, '/api/admin/productamounts/' + date.toISOString().slice(0, 10));
    }

    async getOrders(date: Date): Promise<Order[]> {
        return this.api.getAndConvertArray(Order, '/api/admin/ordersummary/' + date.toISOString().slice(0, 10));
    }

    async createOrdersFromRecurringOrders(date: Date): Promise<void> {
        return this.api.post('/api/admin/ordersummary/' + date.toISOString().slice(0, 10) + '/create', {});
    }
}
