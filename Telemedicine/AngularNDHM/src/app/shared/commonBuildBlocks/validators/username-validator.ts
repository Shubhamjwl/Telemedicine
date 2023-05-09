import { AbstractControl, FormControl } from '@angular/forms';

export interface ValidationResult {
    [key: string]: boolean;
}

export class UserNameValidator {

    public static strong(control: AbstractControl): ValidationResult {
        let hasNumber = /\d/.test(control.value);
        let hasUpper = /[A-Z]/.test(control.value);
        let hasLower = /[a-z]/.test(control.value);
        const valid =  (hasUpper || hasLower) && hasNumber;
        // let isValid = /^[A-Za-z]([ ]?[A-Za-z0-9]+)*$/.test(control.value);
        // const valid = isValid
        if (!valid) {
            // return what´s not valid
            return { strong: true };
        }
        return null;
    }
}