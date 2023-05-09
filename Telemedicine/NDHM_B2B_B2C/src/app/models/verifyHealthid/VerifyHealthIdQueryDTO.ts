import { VerifyHealthIdRequesterDTO } from "./VerifyHealthIdRequesterDTO";


export class VerifyHealthIdQueryDTO {
	id: string;
	purpose: string;
	authMode: string;
	requester: VerifyHealthIdRequesterDTO;
}