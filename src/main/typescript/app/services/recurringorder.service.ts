import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {RecurringOrder} from "../shared/models/recurringorder.model";
import {map} from 'rxjs/operators';
import {plainToClassArrayOp} from "../util";

@Injectable()
export class RecurringOrderService {

    constructor(private http: HttpClient) {
    }

    getRecurringOrders(): Observable<RecurringOrder[]> {
        return this.http.get<RecurringOrder[]>('/api/recurringorders').pipe(plainToClassArrayOp(RecurringOrder));
    }

    countRecurringOrders(): Observable<number> {
        return this.getRecurringOrders().pipe(map(orders => orders.length));
    }

}
