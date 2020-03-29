import {Component} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ProductAmount} from '../../models/product.model';
import {OrderAdminService} from '../../service/api/orderadmin.service';
import {BsLocaleService} from 'ngx-bootstrap';
import {Order} from '../../models/order.model';

@Component({
    selector: 'ordersummary',
    templateUrl: './ordersummary.component.html',
    styleUrls: ['./ordersummary.component.css'],
})
export class OrderSummaryComponent {

    isLoading = false;
    hasOrders = false;

    selectedDate: Date;
    productAmounts: ProductAmount[];
    orders: Order[];

    constructor(private route: ActivatedRoute,
                private orderAdminService: OrderAdminService,
                private localeService: BsLocaleService) {
        this.selectedDate = new Date(Date.now());
        this.selectedDate.setDate(this.selectedDate.getDate() + 1);
        this.localeService.use('de');
    }

    get shouldShow(): boolean {
        return !this.isLoading && this.hasOrders;
    }

    onClickProductAmountDownload() {
        window.open('/api/admin/productamounts/' + this.selectedDate.toISOString().slice(0, 10) + '/pdf', '_blank');
    }

    onClickOrdersDownload() {
        window.open('/api/admin/ordersummary/' + this.selectedDate.toISOString().slice(0, 10) + '/pdf', '_blank');
    }

    async onSelectDate() {
        this.isLoading = true;
        await this.loadDay();
        this.isLoading = false;
    }

    async onCreateOrdersFromRecurringOrdersClick() {
        this.isLoading = true;
        await this.orderAdminService.createOrdersFromRecurringOrders(this.selectedDate);
        await this.loadDay();
        this.isLoading = false;
    }

    async loadDay() {
        const [amounts, orders] = await Promise.all([this.orderAdminService.getProductAmounts(this.selectedDate), this.orderAdminService.getOrders(this.selectedDate)]);
        if (amounts.length > 0 && orders.length > 0) {
            this.productAmounts = amounts;
            this.orders = orders;
            this.hasOrders = true;
        } else {
            this.hasOrders = false;
        }
    }

}
