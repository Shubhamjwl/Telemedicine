import { VerifyHealthIdRequesterDTO } from "./VerifyHealthIdRequesterDTO";

export class OnConfirmValidityDTO {
    purpose: string;
    requester: VerifyHealthIdRequesterDTO;
    expiry: string;
    limit: string;
}