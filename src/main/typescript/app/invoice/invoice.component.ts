import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";

@Component({
    selector: 'invoice',
    templateUrl: './invoice.component.html'
})
export class InvoiceComponent implements OnInit, OnDestroy {

    id: number;

    isLoading: boolean = true;
    private routeParamsSub: any;

    constructor(private router: Router, private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.routeParamsSub = this.route.params.subscribe(params => {
            this.id = +params['id'];
            this.isLoading = false;
        });
    }

    ngOnDestroy() {
        this.routeParamsSub.unsubscribe();
    }

    onOrderClick(id: number) {
        this.router.navigate(['/order/' + id])
    }

}
