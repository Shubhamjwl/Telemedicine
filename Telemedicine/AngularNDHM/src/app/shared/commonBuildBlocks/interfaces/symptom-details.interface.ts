import { Severity } from '../enum/severity.enum';

export interface ISymptomDetails {
    name: string;
    duration: any;
    severity: Severity;
    comment: string;
}

