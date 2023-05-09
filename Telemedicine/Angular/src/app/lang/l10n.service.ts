import { Injectable } from '@angular/core';
import * as CommonLabel from "../../assets/lang/telemedicine.json";

@Injectable({
    providedIn: "root"
})
export class L10nService {

    /**
     * Used to store default language
     */
    defaultLanguage = 'en-US'

    /**
     * Used to strore Common Labels
     */
    applicationLabels = (CommonLabel as any).default;

    Label(key: string): String{
        if(!key) {
            return '';
        }
        
        const extractModule = key.split('.');
        const module = extractModule.length === 2 ? extractModule[0] : 'application';
        if (!this[`${module}Labels`][this.defaultLanguage][key]) {
            return '';
        }
        return this[`${module}Labels`][this.defaultLanguage][key];
    }
}