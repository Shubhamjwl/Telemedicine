
import { AbstractControl, FormControl } from '@angular/forms';

export interface ValidationResult {
    [key: string]: boolean;
}

export class AllowSpecialCharacterValidator {

    public static strong(control: AbstractControl): ValidationResult {
        if(control.value){
            let isValid = /^[A-Za-z]([\\']?[A-Za-z]+)*( [A-Za-z]([\\']?[A-Za-z]+)*)*$/.test(control.value);
            const valid = isValid
            if (!valid) {
                // return what´s not valid
                return { strong: true };
            }
        }
        return null;
    }
}