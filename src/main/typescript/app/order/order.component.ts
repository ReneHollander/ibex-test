import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {OrderService} from "../service/api/order.service";
import {Order} from "../models/order.model";

@Component({
    selector: 'order',
    templateUrl: './order.component.html'
})
export class OrderComponent implements OnInit, OnDestroy {

    id: number;
    isLoading: boolean = true;
    order: Order;

    private routeParamsSub: any;

    constructor(private route: ActivatedRoute, private orderService: OrderService) {
    }

    ngOnInit() {
        this.routeParamsSub = this.route.params.subscribe(async params => {
            this.isLoading = true;
            this.id = +params['id'];

            this.order = await this.orderService.getOrder(this.id);

            this.isLoading = false;
        });
    }

    ngOnDestroy() {
        this.routeParamsSub.unsubscribe();
    }

}
