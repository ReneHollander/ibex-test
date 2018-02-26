import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Product} from "../shared/models/product.model";
import {plainToClassArrayOp} from "../util";

@Injectable()
export class ProductService {

    constructor(private http: HttpClient) {
    }

    getProducts(): Observable<Product[]> {
        return this.http.get<Product[]>('/api/products').pipe(plainToClassArrayOp(Product));
    }

    countProducts(): Observable<number> {
        return this.http.get<number>('/api/products/count');
    }

}
