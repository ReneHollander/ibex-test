import {Component, forwardRef, Input, ViewChild} from '@angular/core';
import {Product} from '../../models/product.model';
import {Observable, Subject} from 'rxjs';
import {debounceTime, distinctUntilChanged, filter, map, merge} from 'rxjs/operators';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from '@angular/forms';
import { NgbTypeahead } from "@ng-bootstrap/ng-bootstrap";

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
    @Input()
    placeholderEmpty = '';
    @ViewChild('instance') instance: NgbTypeahead;
    focus$ = new Subject<string>();
    click$ = new Subject<string>();
    onChange = (product: Product) => {
    }
    onTouched = () => {
    }
    productSearch = (text$: Observable<string>) =>
        text$
            .pipe(debounceTime(200))
            .pipe(distinctUntilChanged())
            .pipe(merge(this.focus$))
            .pipe(merge(this.click$.pipe(filter(() => !this.instance.isPopupOpen()))))
            .pipe(map(term => term === '' ? this.products
                : this.products.filter(v => v.name.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10)))
    productFormatter = (x: { name: string }) => x.name;

    private _placeholder = '';

    get placeholder(): string {
        return this.disabled ? this.placeholderEmpty : this._placeholder;
    }

    @Input()
    set placeholder(str: string) {
        this._placeholder = str;
    }

    private _disabled = false;

    get disabled() {
        return this._disabled || this.products.length == 0;
    }

    @Input()
    set disabled(b: boolean) {
        this._disabled = b;
    }

    setDisabledState(isDisabled: boolean): void {
        this._disabled = isDisabled;
    }

    registerOnTouched(fn: any): void {
        this.onTouched = fn;
    }

    writeValue(obj: any): void {
        this.productModel = obj;
        this.onChange(this.productModel);
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
