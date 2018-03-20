import {Injectable} from '@angular/core';
import {ApiClient} from "./apiclient.service";
import {NewsletterRegistration} from "../../shared/models/newsletter.model";

@Injectable()
export class NewsletterService {

    constructor(private api: ApiClient) {
    }

    async register(request: NewsletterRegistration): Promise<any> {
        return this.api.post<any>('/api/newsletter/register', request);
    }

}
