import { Injectable } from '@angular/core';
import { NativeDateAdapter, MatDateFormats } from '@angular/material/core';

@Injectable()
export class DdateAdapter extends NativeDateAdapter {
    format(date: Date, displayFormat: Object) {
      if (displayFormat === 'input') {
        let day = date.getDate().toString();
        day = +day < 10 ? '0' + day : day;
        let month = (date.getMonth() + 1).toString();
        month = +month < 10 ? '0' + month : month;
        let year = date.getFullYear();
        return `${day}/${month}/${year}`;
      }
      return date.toDateString();
    }
  } 
  
  export const MY_FORMATS: MatDateFormats = {
    parse: {
      dateInput: { month: 'short', year: 'numeric', day: 'numeric' }
    },
    display: {
      dateInput: 'input',
      monthYearLabel: { year: 'numeric', month: 'numeric' },
      dateA11yLabel: { year: 'numeric', month: 'long', day: 'numeric' },
      monthYearA11yLabel: { year: 'numeric', month: 'long' }
    }
  }
  