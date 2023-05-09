import { AddressDTO } from "./AddressDTO";
import { IdentifierDTO } from "./IdentifierDTO";

export class PatientDTO {
    id: string;

    name: string;

    gender: string;

    yearOfBirth: string;

    address: AddressDTO;

    identifiers: IdentifierDTO[];
}