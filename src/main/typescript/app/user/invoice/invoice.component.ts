import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Invoice} from "../../models/invoice.model";
import {InvoiceService} from "../../service/api/invoice.service";
import {Order} from "../../models/order.model";

@Component({
    selector: 'invoice',
    templateUrl: './invoice.component.html'
})
export class InvoiceComponent implements OnInit, OnDestroy {

    id: number;
    isLoading: boolean = true;
    invoice: Invoice;

    private routeParamsSub: any;

    constructor(private router: Router, private route: ActivatedRoute, private invoiceService: InvoiceService) {
    }

    ngOnInit() {
        this.routeParamsSub = this.route.params.subscribe(async params => {
            this.isLoading = true;
            this.id = +params['id'];

            this.invoice = await this.invoiceService.getInvoice(this.id);

            this.isLoading = false;
        });
    }

    ngOnDestroy() {
        this.routeParamsSub.unsubscribe();
    }

    onOrderClick(order: Order) {
        this.router.navigate(['/user/order/' + order.id])
    }

}
