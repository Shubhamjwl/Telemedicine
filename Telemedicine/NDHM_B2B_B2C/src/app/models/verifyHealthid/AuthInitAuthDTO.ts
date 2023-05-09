import { AuthInitMetaDTO } from "./AuthInitMetaDTO";

export class AuthInitAuthDTO {
    transactionId: string;
    mode: string;
    meta: AuthInitMetaDTO;
}