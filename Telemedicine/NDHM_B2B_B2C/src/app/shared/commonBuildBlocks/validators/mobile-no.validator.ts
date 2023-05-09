import { AbstractControl, FormControl } from '@angular/forms';

export interface ValidationResult {
    [key: string]: boolean;
}

export class MobileNoValidator {

    public static strong(control: AbstractControl): ValidationResult {
        let isValid = /^[6-9]\d{9}$/.test(control.value);
        const valid = isValid
        if (!valid) {
            // return what´s not valid
            return { strong: true };
        }
        return null;
    }
}