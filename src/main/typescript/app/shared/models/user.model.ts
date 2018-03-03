import {AccountDetails} from "./account.model";
import {Type} from "class-transformer";

// TODO: remove once released: https://github.com/angular/angular-cli/pull/9225
function accountdetails() {
    return () => AccountDetails
}

export class User {
    // @Type(accountdetails)
    @Type(() => AccountDetails)
    account?: AccountDetails;
    role?: string;

    get name(): string {
        return this.account.name;
    }

    get email(): string {
        return this.account.email;
    }
}
