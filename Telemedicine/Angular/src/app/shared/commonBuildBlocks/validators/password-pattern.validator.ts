
import { AbstractControl, FormControl } from '@angular/forms';

export interface ValidationResult {
    [key: string]: boolean;
}

export class PasswordPatternValidator {
    // [!@#$%^&*]$/
    public static strong(control: AbstractControl): ValidationResult {
        let hasNumber = /\d/.test(control.value);
        let hasUpper = /[A-Z]/.test(control.value);
        let hasLower = /[a-z]/.test(control.value);
        let hasSpecialSymbol = /[!@#$%^&*]/.test(control.value);
        const valid = hasNumber && hasUpper && hasLower && hasSpecialSymbol;
        if (!valid) {
            // return what´s not valid
            return { strong: true };
        }
        return null;
    }
}