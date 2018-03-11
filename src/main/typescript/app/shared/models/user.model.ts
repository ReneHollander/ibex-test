import {Account} from "./account.model";
import {Transform, Type} from "class-transformer";
import {serializeType} from "../../util";

export enum Role {
    USER = 0, ADMIN = 1
}

function roleTransformer(value: any) {
    return Role[value];
}

export class User {
    email?: string;
    name?: string;
    @Transform(roleTransformer, {toClassOnly: true})
    role?: Role;
    @Type(serializeType(Account))
    account?: Account;
}
