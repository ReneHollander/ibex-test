import {Injectable} from '@angular/core';
import {ApiClient} from './apiclient.service';
import {Invoice} from '../../models/invoice.model';

@Injectable()
export class InvoiceService {

    constructor(private api: ApiClient) {
    }

    async getInvoices(): Promise<Invoice[]> {
        return this.api.getAndConvertArray(Invoice, '/api/invoices');
    }

    async countInvoices(): Promise<number> {
        return this.api.get<number>('/api/invoices/count');
    }

    async getInvoice(id: number): Promise<Invoice> {
        return this.api.getAndConvert(Invoice, '/api/invoice/' + id);
    }

}
