export class City {
    postcode?: number;
    name?: string;
    enabled?: boolean;
    priceShipping?: number;
}

export class DeliveryFeeAmount {
    priceShipping?: number;
    amount?: number;

    get total(): number {
        return this.priceShipping * this.amount;
    }
}
