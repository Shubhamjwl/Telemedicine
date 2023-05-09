import { FormGroup, ValidationErrors, ValidatorFn } from "@angular/forms";

export const passwordMatchValidator: ValidatorFn = (formGroup: FormGroup): ValidationErrors | null => {
    if (formGroup.get('password').value === formGroup.get('reEnterPassword').value)
      return null;
    else
      return {passwordMismatch: true};
  };
export const reEnterPassword: ValidatorFn = (control: FormGroup): ValidationErrors | null => {
    if(!control || !control.parent){
      return null;
    }
    let group = control.parent;
    let password = group.get('password').value;

    if (password === control.value)
      return null;
    else
      return {passwordMismatch: true};
 };