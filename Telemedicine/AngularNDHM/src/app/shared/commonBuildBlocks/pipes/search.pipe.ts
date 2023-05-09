import { pipe } from "rxjs";

import { Pipe, PipeTransform } from '@angular/core';
import { MatTableDataSource } from "@angular/material/table";

@Pipe({
    name:  'docFilter',
    pure: false
})

export class Docfilter implements PipeTransform{
    transform(items: any, filter: any): any{
        if(!items || !filter){
            return items;
        }
        if(items.filteredData){
            return items.filteredData.filter(item => {
                let name = item.drFullName ? item.drFullName.toLowerCase() : item.docName ? item.docName.toLowerCase() : '';
                let smc =  item.drSMCNo ? item.drSMCNo.toLowerCase() : item.smcno ? item.smcno.toLowerCase() : '';
                let mci =  item.drMCINo ? item.drMCINo.toLowerCase() : item.micno ? item.micno.toLowerCase() : '';

                return (name.includes(filter.toLowerCase()) || smc.includes(filter.toLowerCase()) || mci.includes(filter.toLowerCase()));
            })
        } else {
            return items.filter(item => {
                let name = item.drFullName ? item.drFullName.toLowerCase() : item.docName ? item.docName.toLowerCase() : '';
                let smc =  item.drSMCNo ? item.drSMCNo.toLowerCase() : item.smcno ? item.smcno.toLowerCase() : '';
                let mci =  item.drMCINo ? item.drMCINo.toLowerCase() : item.micno ? item.micno.toLowerCase() : '';

                return (name.includes(filter.toLowerCase()) || smc.includes(filter.toLowerCase()) || mci.includes(filter.toLowerCase()));
            })
        }
        
    }
}