import { OnConfirmValidityDTO } from "./OnConfirmValidityDTO";
import { PatientDTO } from "./PatientDTO";

export class OnConfirmAuthDTO {
    accessToken: string;
    validity: OnConfirmValidityDTO;
    patient: PatientDTO;
}