import {Operator} from 'rxjs/Operator';
import {Observable} from 'rxjs/Observable';
import {Subscriber} from 'rxjs/Subscriber';
import {OperatorFunction} from 'rxjs/interfaces';
import {ClassType} from "class-transformer/ClassTransformer";
import {plainToClass} from "class-transformer";

export function plainToClassOp<T, R>(cls: ClassType<R>): OperatorFunction<T, R> {
    return function mapOperation(source: Observable<T>): Observable<R> {
        return source.lift(new PlainToClassOperator(cls));
    };
}

export class PlainToClassOperator<T, R> implements Operator<T, R> {
    constructor(private cls: ClassType<R>) {
    }

    call(subscriber: Subscriber<R>, source: any): any {
        return source.subscribe(new PlainToClassSubscriber(subscriber, this.cls));
    }
}

class PlainToClassSubscriber<T, R> extends Subscriber<T> {
    constructor(destination: Subscriber<R>, private cls: ClassType<R>) {
        super(destination);
    }

    protected _next(value: T) {
        let result: any;
        try {
            result = plainToClass(this.cls, value);
            console.log(result);
        } catch (err) {
            this.destination.error(err);
            return;
        }
        this.destination.next(result);
    }
}

export function plainToClassArrayOp<T extends Array<any>, R>(cls: ClassType<R>): OperatorFunction<T, R[]> {
    return function mapOperation(source: Observable<T>): Observable<R[]> {
        return source.lift(new PlainToClassArrayOperator(cls));
    };
}

export class PlainToClassArrayOperator<T extends Array<any>, R> implements Operator<T, R[]> {
    constructor(private cls: ClassType<R>) {
    }

    call(subscriber: Subscriber<R[]>, source: any): any {
        return source.subscribe(new PlainToClassArraySubscriber(subscriber, this.cls));
    }
}

class PlainToClassArraySubscriber<T extends Array<any>, R> extends Subscriber<T> {
    constructor(destination: Subscriber<R[]>, private cls: ClassType<R>) {
        super(destination);
    }

    protected _next(value: T) {
        let result: any;
        try {
            result = plainToClass(this.cls, value);
            console.log(result);
        } catch (err) {
            this.destination.error(err);
            return;
        }
        this.destination.next(result);
    }
}

export class CachedValue<T> {
    private _value: T;
    private time: number;
    private loading: boolean;
    private waiting: ((value?: T | PromiseLike<T>) => void)[] = [];

    constructor(private supplier: () => Promise<T>, private maxAge?: number) {
    }

    async get(): Promise<T> {
        if (!this.loading && !this._value || (this.maxAge && Date.now() > this.time + this.maxAge * 1000)) {
            this.loading = true;
            this._value = await this.supplier();
            this.time = Date.now();
            this.loading = false;
            for (let f of this.waiting) {
                f(this._value);
            }
        }
        return new Promise<T>(resolve => {
            if (this.loading) {
                this.waiting.push(resolve);
            } else {
                resolve(this._value);
            }
        });
    }
}