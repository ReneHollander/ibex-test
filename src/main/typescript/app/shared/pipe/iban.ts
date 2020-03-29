import {Pipe} from '@angular/core';
import {friendlyFormatIBAN} from 'ibantools';

@Pipe({
    name: 'iban'
})
export class IbanPipe {
    transform(value: string): string {
        return friendlyFormatIBAN(value);
    }
}
