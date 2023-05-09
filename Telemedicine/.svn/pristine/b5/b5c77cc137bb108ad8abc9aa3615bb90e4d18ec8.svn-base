import { Component, Input, OnInit } from '@angular/core';
import { ConsultationService } from 'src/app/shared/commonBuildBlocks/services/ConsultationServices/consultation.service';
import { Clipboard } from "@angular/cdk/clipboard";
import { MatTableDataSource } from '@angular/material/table';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';


@Component({
    selector: 'app-patient-submitted-form',
    templateUrl: './patient-submitted-form.component.html',
    styleUrls: ['./patient-submitted-form.component.scss']
})
export class PatientSubmittedFormComponent implements OnInit {

    displayedColumns: string[] = ['date', 'flag', 'link'];
    // dataSource;
    dataSource:any =null ;
    url;
    getPatientDetails: any;
    @Input() ptMobileNumber;
    sections: any;
    keys: string[];
   
    constructor(
        private consultationService: ConsultationService,
        private clipboard: Clipboard,
        private toastrMessage: ToastMessageService,
    ) { }

    ngOnInit(): void {
        this.getDrRedflagDetails();
    }
    getDrRedflagDetails(){
        let emailid = sessionStorage.getItem('emailID');
        this.consultationService.getDrRedflagDetails(emailid,this.ptMobileNumber).subscribe(result => {
            {
              this.dataSource = result.response;
              this.sections=this.groupArrayOfObjects(this.dataSource,"date_added");
              this.keys = Object.keys(this.sections);
            }
        })
    }
    copyUrl(redFlag) {
        let details={
           drID:this.consultationService.drRegId,
           redflag: redFlag
        }
        this.consultationService.getDrRedflagMapURL(details).subscribe(result => {
            {
                if(result.status){
                this.url = result.response;
                this.clipboard.copy(this.url);
                this.toastrMessage.showSuccessMsg( "Message copied to clipboard" , 'Success');


                }else {
                    this.toastrMessage.showErrorMsg( result.errors.message , 'Error');
                }
            }
        })
    }
    groupArrayOfObjects(list, key) {
        return list.reduce(function(rv, x) {
          (rv[x[key]] = rv[x[key]] || []).push(x);
          return rv;
        }, {});
      }
}