import {Injectable} from '@angular/core';
import {Product} from "../shared/models/product.model";
import {ApiClient} from "./apiclient.service";

@Injectable()
export class ProductService {

    constructor(private api: ApiClient) {
    }

    async getProducts(): Promise<Product[]> {
        return this.api.getAndConvertArray(Product, '/api/products');
    }

    countProducts(): Promise<number> {
        return this.api.get<number>('/api/products/count');
    }

}
