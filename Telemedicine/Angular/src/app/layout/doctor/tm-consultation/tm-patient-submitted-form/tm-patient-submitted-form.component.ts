import { Clipboard } from '@angular/cdk/clipboard';
import { Component, Input, OnChanges } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { IRedflagDetail } from 'src/app/shared/commonBuildBlocks';
import { IPatientPersonalDetails } from 'src/app/shared/commonBuildBlocks/interfaces/patient-personal-details.interface';
import { ConsultationService } from 'src/app/shared/commonBuildBlocks/services/ConsultationServices/consultation.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';

@Component({
  selector: 'app-tm-patient-submitted-form',
  templateUrl: './tm-patient-submitted-form.component.html',
  styleUrls: ['./tm-patient-submitted-form.component.scss'],
})
export class TmPatientSubmittedFormComponent implements OnChanges {
  displayedColumns = ['date', 'flag', 'link'];
  dataSource = new MatTableDataSource<IRedflagDetail>();
  @Input() ptPersonalDetails: IPatientPersonalDetails;
  constructor(
    private consultationService: ConsultationService,
    private clipboard: Clipboard,
    private toastrMessage: ToastMessageService,
  ) {}

  ngOnChanges(): void {
    this.getDrRedflagDetails();
  }

  getDrRedflagDetails() {
    if (this.ptPersonalDetails) {
      const emailid = sessionStorage.getItem('emailID');
      this.consultationService.getDrRedflagDetails(emailid, this.ptPersonalDetails?.mobileNo).subscribe({
        next: (result) => {
          if (result.status) {
            this.dataSource.data = result.response;
          } else {
            this.toastrMessage.showErrorMsg(result.errors?.message, 'Error');
          }
        },
        error: (err) => this.toastrMessage.showErrorMsg(err.errors?.message, 'Error'),
      });
    }
  }

  copyUrl(redFlag: string) {
    let details = {
      drID: this.consultationService.drRegId,
      redflag: redFlag,
    };
    this.consultationService.getDrRedflagMapURL(details).subscribe({
      next: (result) => {
        if (result.status) {
          this.clipboard.copy(result.response);
          this.toastrMessage.showSuccessMsg('Message copied to clipboard', 'Success');
        } else {
          this.toastrMessage.showErrorMsg(result.errors.message, 'Error');
        }
      },
      error: (err) => console.log(err),
    });
  }
}
