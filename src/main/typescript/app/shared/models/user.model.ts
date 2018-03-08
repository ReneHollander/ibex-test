import {Account} from "./account.model";
import {Transform, Type} from "class-transformer";

export enum Role {
    USER, ADMIN
}

export class User {
    email?: string;
    name?: string;
    @Transform(value => Role[value], {toClassOnly: true})
    role?: Role;
    @Type(() => Account)
    account?: Account;
}
