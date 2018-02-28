import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
    selector: 'order',
    templateUrl: './order.component.html'
})
export class OrderComponent implements OnInit, OnDestroy {

    id: number;

    isLoading: boolean = true;
    private routeParamsSub: any;

    constructor(private route: ActivatedRoute) {
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

}
