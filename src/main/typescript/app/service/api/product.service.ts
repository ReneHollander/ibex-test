import {Injectable} from '@angular/core';
import {Product} from '../../models/product.model';
import {ApiClient} from './apiclient.service';
import {CachedValue} from '../../util';

@Injectable()
export class ProductService {

    private productsCache: CachedValue<Product[]>;

    constructor(private api: ApiClient) {
        this.productsCache = new CachedValue<Product[]>(() => this.api.getAndConvertArray(Product, '/api/products'), 600);
    }

    async getProducts(): Promise<Product[]> {
        return this.api.getAndConvertArray(Product, '/api/products');
    }

    async countProducts(): Promise<number> {
        return this.api.get<number>('/api/products/count');
    }

    async getProductsCached(): Promise<Product[]> {
        return this.productsCache.get();
    }

}
