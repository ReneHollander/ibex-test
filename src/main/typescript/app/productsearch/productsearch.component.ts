import {Component, forwardRef, Input, OnInit, ViewChild} from '@angular/core';
import {ProductService} from "../services/product.service";
import {Product} from "../shared/models/product.model";
import {NgbTypeahead} from "@ng-bootstrap/ng-bootstrap";
import {ToastComponent} from "../shared/toast/toast.component";
import {RecurringOrderService} from "../services/recurringorder.service";
import {Observable} from 'rxjs/Observable';
import {debounceTime, distinctUntilChanged, filter, map, merge} from 'rxjs/operators';
import {Subject} from 'rxjs/Subject';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from "@angular/forms";

@Component({
    selector: 'product-search',
    templateUrl: './productsearch.component.html',
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => ProductSearchComponent),
            multi: true
        }
    ]
})
export class ProductSearchComponent implements OnInit, ControlValueAccessor {
    registerOnTouched(fn: any): void {
        this.onTouched = fn;
    }

    // Function to call when the rating changes.
    onChange = (product: Product) => {
    };

    onTouched = () => {
    };

    writeValue(obj: any): void {
        this.productModel = obj;
        this.onChange(this.productModel)
    }

    registerOnChange(fn: (product: Product) => void): void {
        this.onChange = fn;
    }

    setDisabledState(isDisabled: boolean): void {
        this.disabled = isDisabled;
    }

    @Input()
    placeholder: string = "";

    @Input()
    disabled = false;

    isLoading = true;

    products: Product[] = [];
    public productModel: Product;

    @ViewChild('instance') instance: NgbTypeahead;
    focus$ = new Subject<string>();
    click$ = new Subject<string>();

    constructor(private recurringOrderService: RecurringOrderService,
                private productService: ProductService,
                public toast: ToastComponent) {
    }

    productSearch = (text$: Observable<string>) =>
        text$
            .pipe(debounceTime(200))
            .pipe(distinctUntilChanged())
            .pipe(merge(this.focus$))
            .pipe(merge(this.click$.pipe(filter(() => !this.instance.isPopupOpen()))))
            .pipe(map(term => term === '' ? this.products
                : this.products.filter(v => v.name.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10)));

    productFormatter = (x: { name: string }) => x.name;

    async getProducts(): Promise<void> {
        this.products = await this.productService.getProducts();
        this.isLoading = false;
    }

    async ngOnInit(): Promise<void> {
        await this.getProducts();
        console.log(this.placeholder)
    }

    selectProduct(item: Product) {
        if (!this.disabled) {
            this.writeValue(item);
        }
    }
}
