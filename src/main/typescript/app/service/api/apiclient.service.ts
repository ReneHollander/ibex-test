import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ClassType} from 'class-transformer/ClassTransformer';
import {plainToClass} from 'class-transformer';
import {AuthService} from '../auth/auth.service';

@Injectable()
export class ApiClient {

    constructor(private http: HttpClient, private auth: AuthService) {
    }

    async guard<T>(p: Promise<T>): Promise<T> {
        try {
            return await p;
        } catch (e) {
            if (e.status === 403) {
                this.auth.clear();
                // TODO: maybe better handling?
                return Promise.resolve(null);
            }
            return Promise.reject(e);
        }
    }

    async get<T>(url: string): Promise<T> {
        return this.guard(this.http.get<T>(url).toPromise());
    }

    async getAndConvert<T>(cls: ClassType<T>, url: string): Promise<T> {
        return plainToClass<T, T>(cls, await this.get<T>(url));
    }

    async getAndConvertArray<T>(cls: ClassType<T>, url: string): Promise<T[]> {
        return plainToClass(cls, await this.get<T[]>(url));
    }

    async post<T>(url: string, data: any): Promise<T> {
        return this.guard(this.http.post<T>(url, data).toPromise());
    }

    async postAndConvert<T>(cls: ClassType<T>, url: string, data: any): Promise<T> {
        return plainToClass<T, T>(cls, await this.post<T>(url, data));
    }

    async postAndConvertArray<T>(cls: ClassType<T>, url: string, data: any): Promise<T[]> {
        return plainToClass(cls, await this.post<T[]>(url, data));
    }

}
