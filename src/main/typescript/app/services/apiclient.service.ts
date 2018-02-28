import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ClassType} from "class-transformer/ClassTransformer";
import {plainToClass} from "class-transformer";

@Injectable()
export class ApiClient {

    constructor(private http: HttpClient) {
    }

    async get<T>(url: string): Promise<T> {
        return this.http.get<T>(url).toPromise();
    }

    async getAndConvert<T>(cls: ClassType<T>, url: string): Promise<T> {
        let result = await this.http.get<T>(url).toPromise();
        return plainToClass<T, T>(cls, result);
    }

    async getAndConvertArray<T>(cls: ClassType<T>, url: string): Promise<T[]> {
        let result = await this.http.get<T[]>(url).toPromise();
        return plainToClass(cls, result);
    }

    async post<T>(url: string, data: any): Promise<T> {
        return this.http.post<T>(url, data).toPromise();
    }

    async postAndConvert<T>(cls: ClassType<T>, url: string, data: any): Promise<T> {
        let result = await this.http.post<T>(url, data).toPromise();
        return plainToClass<T, T>(cls, result);
    }

    async postAndConvertArray<T>(cls: ClassType<T>, url: string, data: any): Promise<T[]> {
        let result = await this.http.post<T[]>(url, data).toPromise();
        return plainToClass(cls, result);
    }

}
