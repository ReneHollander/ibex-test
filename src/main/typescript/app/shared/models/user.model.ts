import {Account} from "./account.model";
import {Type} from "class-transformer";

// TODO: remove once released: https://github.com/angular/angular-cli/pull/9225
function account() {
    return () => Account
}

export enum Role {
    USER, ADMIN
}

export class User {
    email?: string;
    name?: string;
    role?: Role;
    // @Type(account)
    @Type(() => Account)
    account?: Account;
}
