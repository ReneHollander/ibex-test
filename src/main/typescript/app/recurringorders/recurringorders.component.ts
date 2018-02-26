import {Component, OnInit, ViewChild} from '@angular/core';
import {RecurringOrderService} from "../services/recurringorder.service";
import {RecurringOrder, RecurringOrderItem} from "../shared/models/recurringorder.model";
import {Product} from "../shared/models/product.model";
import {ToastComponent} from "../shared/toast/toast.component";
import {ProductService} from "../services/product.service";
import {Observable} from 'rxjs/Observable';
import {debounceTime, distinctUntilChanged, filter, map, merge} from 'rxjs/operators';
import {NgbTypeahead} from "@ng-bootstrap/ng-bootstrap";
import {Subject} from 'rxjs/Subject';

@Component({
    selector: 'app-recurring-orders',
    templateUrl: './recurringorders.component.html',
    styleUrls: ['./recurringorders.component.css'],
})
export class RecurringOrdersComponent implements OnInit {

    recurringOrders: RecurringOrder[] = [];
    isLoading = true;

    constructor(private recurringOrderService: RecurringOrderService,
                private productService: ProductService,
                public toast: ToastComponent) {
    }

    product = new Product();
    products: Product[] = [];
    isEditing = false;

    public productModel: Product;

    @ViewChild('instance') instance: NgbTypeahead;
    focus$ = new Subject<string>();
    click$ = new Subject<string>();

    productSearch = (text$: Observable<string>) =>
        text$
            .pipe(debounceTime(200))
            .pipe(distinctUntilChanged())
            .pipe(merge(this.focus$))
            .pipe(merge(this.click$.pipe(filter(() => !this.instance.isPopupOpen()))))
            .pipe(map(term => term === '' ? this.products
                : this.products.filter(v => v.name.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10)));

    productFormatter = (x: { name: string }) => x.name;

    getProducts() {
        this.productService.getProducts().subscribe(
            data => this.products = data,
            error => console.log(error),
            () => this.isLoading = false
        );
    }

    addProduct() {
        console.log(this.productModel);
        this.recurringOrders[0].items.push(new RecurringOrderItem(this.productModel, 1));
        this.productModel = null;
    }


    ngOnInit() {
        this.getRecurringOrders();
        this.getProducts();
    }

    getRecurringOrders() {
        this.recurringOrderService.getRecurringOrders().subscribe(
            data => this.recurringOrders = [...data],
            error => console.log(error),
            () => this.isLoading = false
        );
    }

}
