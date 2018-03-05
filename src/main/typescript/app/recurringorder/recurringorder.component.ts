import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {RecurringOrder, RecurringOrderItem} from "../shared/models/recurringorder.model";
import {Product} from "../shared/models/product.model";
import {ProductService} from "../services/product.service";
import {classToClass} from "class-transformer";
import {RecurringOrderService} from "../services/recurringorder.service";
import {ToastComponent} from "../shared/toast/toast.component";
import deepEqual = require("deep-equal");

@Component({
    selector: 'recurring-order',
    templateUrl: './recurringorder.component.html',
    styleUrls: ['./recurringorder.component.css']
})
export class RecurringOrderComponent implements OnInit, OnChanges {

    @Input()
    recurringOrder: RecurringOrder = new RecurringOrder();

    initialRecurringOrder: RecurringOrder = new RecurringOrder();

    deliveryFee: number = 1.5;
    total: number = this.deliveryFee;
    products: Product[] = [];
    selectedProduct: Product;

    private isLoading: boolean = true;

    constructor(private productService: ProductService,
                private recurringOrderService: RecurringOrderService,
                private toast: ToastComponent) {
    }

    async ngOnInit(): Promise<void> {
        this.isLoading = true;
        this.products = await this.productService.getProductsCached();
        this.isLoading = false;
    }

    get availableProducts(): Product[] {
        return this.products.filter((p) => this.recurringOrder.items.map(item => item.product.id).indexOf(p.id) < 0);
    }

    get saveButtonDisabled(): boolean {
        return deepEqual(this.recurringOrder, this.initialRecurringOrder);
    }

    updateTotal() {
        this.total = this.recurringOrder.items.map(item => item.amount * item.product.price).reduce((sum, current) => sum + current, 0) + this.deliveryFee;
    }

    ngOnChanges(changes: SimpleChanges) {
        this.initialRecurringOrder = classToClass(this.recurringOrder);
        this.updateTotal();
    }

    onDeleteItem(item: RecurringOrderItem) {
        const index = this.recurringOrder.items.indexOf(item);
        if (index !== -1) this.recurringOrder.items.splice(index, 1);
        this.updateTotal();
    }

    onUpdate(item: RecurringOrderItem) {
        this.updateTotal();
    }

    addProductCancelButtonClick() {
        this.selectedProduct = null;
    }

    addProductAddButtonClick() {
        this.recurringOrder.items.push(new RecurringOrderItem(this.selectedProduct, 1));
        this.selectedProduct = null;
        this.updateTotal();
    }

    async saveButton() {
        for (let i = 0; i < this.recurringOrder.items.length; i++) {
            if (this.recurringOrder.items[i].amount <= 0) {
                this.recurringOrder.items.splice(i, 1);
            }
        }
        this.recurringOrder = await this.recurringOrderService.update(this.recurringOrder);
        this.initialRecurringOrder = classToClass(this.recurringOrder);
        this.updateTotal();
        this.toast.setMessage(`Bestellvorlage für ${this.recurringOrder.deliverySlot.name} gespeichert!`, 'success')
    }

    cancelButton() {
        this.recurringOrder = classToClass(this.initialRecurringOrder);
        this.updateTotal();
    }
}
