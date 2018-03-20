import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {InvoiceService} from "../../service/api/invoice.service";
import {Invoice} from "../../models/invoice.model";
import {OrderService} from "../../service/api/order.service";
import {Order} from "../../models/order.model";

@Component({
    selector: 'invoices',
    templateUrl: './invoices.component.html'
})
export class InvoicesComponent implements OnInit {

    isLoadingInvoices: boolean = true;
    isLoadingPendingOrders: boolean = true;
    invoices: Invoice[];
    pendingOrders: Order[];

    constructor(private router: Router, private invoiceService: InvoiceService, private orderService: OrderService) {
    }

    get pendingOrdersTotal(): number {
        return this.pendingOrders.map(o => o.priceTotal).reduce((x, y) => x + y, 0);
    }

    async ngOnInit(): Promise<void> {
        await this.getInvoices();
        await this.getPendingOrders();
    }

    async getPendingOrders(): Promise<void> {
        this.isLoadingPendingOrders = true;
        this.pendingOrders = await this.orderService.getPendingOrders();
        this.isLoadingPendingOrders = false;
    }

    async getInvoices(): Promise<void> {
        this.isLoadingInvoices = true;
        this.invoices = await this.invoiceService.getInvoices();
        this.isLoadingInvoices = false;
    }

    onOrderClick(order: Order) {
        this.router.navigate(['/order/' + order.id])
    }

    onInvoiceClick(invoice: Invoice) {
        this.router.navigate(['/invoice/' + invoice.id])
    }
}
