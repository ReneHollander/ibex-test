import {Component, forwardRef, Input, ViewChild} from '@angular/core';
import {Product} from "../models/product.model";
import {NgbTypeahead} from "@ng-bootstrap/ng-bootstrap";
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
export class ProductSearchComponent implements ControlValueAccessor {

    @Input()
    products: Product[];

    public productModel: Product;

    private _placeholder: string = "";

    @Input()
    placeholderEmpty: string = "";

    @Input()
    set placeholder(str: string) {
        this._placeholder = str;
    }

    get placeholder(): string {
        return this.disabled ? this.placeholderEmpty : this._placeholder;
    }

    private _disabled: boolean = false;

    @Input()
    set disabled(b: boolean) {
        this._disabled = b;
    }

    setDisabledState(isDisabled: boolean): void {
        this._disabled = isDisabled;
    }

    get disabled() {
        return this._disabled || this.products.length == 0;
    }

    @ViewChild('instance') instance: NgbTypeahead;
    focus$ = new Subject<string>();
    click$ = new Subject<string>();

    registerOnTouched(fn: any): void {
        this.onTouched = fn;
    }

    onChange = (product: Product) => {
    };

    onTouched = () => {
    };

    productSearch = (text$: Observable<string>) =>
        text$
            .pipe(debounceTime(200))
            .pipe(distinctUntilChanged())
            .pipe(merge(this.focus$))
            .pipe(merge(this.click$.pipe(filter(() => !this.instance.isPopupOpen()))))
            .pipe(map(term => term === '' ? this.products
                : this.products.filter(v => v.name.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10)));

    productFormatter = (x: { name: string }) => x.name;

    writeValue(obj: any): void {
        this.productModel = obj;
        this.onChange(this.productModel)
    }

    registerOnChange(fn: (product: Product) => void): void {
        this.onChange = fn;
    }

    selectProduct(item: Product) {
        if (!this.disabled) {
            this.writeValue(item);
        }
    }
}
