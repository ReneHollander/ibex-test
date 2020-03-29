import {Operator} from 'rxjs/Operator';
import {Observable} from 'rxjs/Observable';
import {Subscriber} from 'rxjs/Subscriber';
import {OperatorFunction} from 'rxjs/interfaces';
import {ClassType} from 'class-transformer/ClassTransformer';
import {plainToClass} from 'class-transformer';
import {FormGroup} from '@angular/forms';

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

export class PlainToClassOperator<T, R> implements Operator<T, R> {
    constructor(private cls: ClassType<R>) {
    }

    call(subscriber: Subscriber<R>, source: any): any {
        return source.subscribe(new PlainToClassSubscriber(subscriber, this.cls));
    }
}

export function plainToClassOp<T, R>(cls: ClassType<R>): OperatorFunction<T, R> {
    return function mapOperation(source: Observable<T>): Observable<R> {
        return source.lift(new PlainToClassOperator(cls));
    };
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

export class PlainToClassArrayOperator<T extends Array<any>, R> implements Operator<T, R[]> {
    constructor(private cls: ClassType<R>) {
    }

    call(subscriber: Subscriber<R[]>, source: any): any {
        return source.subscribe(new PlainToClassArraySubscriber(subscriber, this.cls));
    }
}

export function plainToClassArrayOp<T extends Array<any>, R>(cls: ClassType<R>): OperatorFunction<T, R[]> {
    return function mapOperation(source: Observable<T>): Observable<R[]> {
        return source.lift(new PlainToClassArrayOperator(cls));
    };
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
            for (const f of this.waiting) {
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

export function validateEqual(key1: string, key2: string) {
    return (group: FormGroup) => {
        const control1 = group.controls[key1];
        const control2 = group.controls[key2];
        let errors = control2.errors;
        if (!errors) {
            errors = {};
        }
        if (control1.value !== control2.value) {
            errors.equal = false;
        } else {
            delete errors.equal;
            if (Object.keys(errors).length === 0) {
                errors = null;
            }
        }
        if (control2.pristine && !control1.pristine) {
            control2.markAsDirty();
        }
        control2.setErrors(errors);
    };
}

export function serializeType<T>(object: T) {
    return function () {
        return object;
    };
}
