import {Injectable} from '@angular/core';
import {ApiClient} from "./apiclient.service";
import {City} from "../../models/city.model";
import {Product} from "../../models/product.model";
import {CachedValue} from "../../util";

@Injectable()
export class CityService {

    private disabledCitiesCache: CachedValue<Product[]>;
    private enabledCitiesCache: CachedValue<Product[]>;

    constructor(private api: ApiClient) {
        this.disabledCitiesCache = new CachedValue<Product[]>(() => this.getDisabledCities(), 600);
        this.enabledCitiesCache = new CachedValue<Product[]>(() => this.getEnabledCities(), 600);
    }

    async getDisabledCities(): Promise<City[]> {
        return this.api.getAndConvertArray(City, '/api/cities/disabled');
    }

    async getEnabledCities(): Promise<City[]> {
        return this.api.getAndConvertArray(City, '/api/cities/enabled');
    }

    async getDisabledCitiesCached(): Promise<City[]> {
        return this.disabledCitiesCache.get();
    }

    async getEnabledCitiessCached(): Promise<City[]> {
        return this.enabledCitiesCache.get();
    }
}
