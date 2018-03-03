import {Injectable} from '@angular/core';
import {ApiClient} from "./apiclient.service";
import {City} from "../shared/models/city.model";

@Injectable()
export class CityService {

    constructor(private api: ApiClient) {
    }

    async getDisabledCities(): Promise<City[]> {
        return this.api.getAndConvertArray(City, '/api/cities/disabled');
    }

    async getEnabledCities(): Promise<City[]> {
        return this.api.getAndConvertArray(City, '/api/cities/enabled');
    }

}
