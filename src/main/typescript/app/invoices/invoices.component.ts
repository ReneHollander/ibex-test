import {Component} from '@angular/core';
import {Router} from "@angular/router";

@Component({
    selector: 'invoices',
    templateUrl: './invoices.component.html'
})
export class InvoicesComponent {

    isLoading: boolean = false;

    constructor(private router: Router) {
    }

    onOrderClick(id: number) {
        this.router.navigate(['/order/' + id])
    }

    onInvoiceClick(id: number) {
        this.router.navigate(['/invoice/' + id])
    }
}
