import { FormGroup } from '@angular/forms';
import { IAPIRequestWrapper } from '../shared/commonBuildBlocks/interfaces/api-request-wrapper.interface';

export class Utility {
  /**
   * Used to sset control value.
   * @param formControl Used to store form.
   * @param controlName Used to store control name.
   * @param value Used to store value.
   * @param updateValueAndValidity used to store status update value flag.
   */
  setControlValue(form: FormGroup, controlName: string, value: any, updateValueAndValidity = true) {
    if (value === undefined) {
      return;
    }

    if (!form.get(controlName)) {
      throw new Error(`${controlName} not available in form`);
    }
    form.get(controlName).setValue(value);
    if (updateValueAndValidity) {
      form.get(controlName).updateValueAndValidity();
    }
  }

  /**
   * Used to format request payload.
   * @param requestPayload Used to store request payload.
   */
  static formatRequestPayload<T>(requestPayload: T): IAPIRequestWrapper<T> {
    return {
      id: "master",
      version: "1.0",
      requestTime: new Date()?.toISOString(),
      request: requestPayload
    }
  }
}
