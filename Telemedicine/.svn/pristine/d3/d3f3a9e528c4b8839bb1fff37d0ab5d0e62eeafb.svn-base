import { Component, OnDestroy, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { Subscription } from 'rxjs';
import { ConsultationService } from 'src/app/shared/commonBuildBlocks/services/ConsultationServices/consultation.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { environment } from 'src/environments/environment';
import { PrescriptionDataSharingService } from '../prescription-data-sharing.service';

@Component({
  selector: 'app-tm-consultation-priscription',
  templateUrl: './tm-consultation-priscription.component.html',
  styleUrls: ['./tm-consultation-priscription.component.scss'],
})
export class TmConsultationPriscriptionComponent implements OnInit, OnDestroy {

  private subscriptions = new Subscription();

  constructor(
    private consultationService: ConsultationService,
    private sanitizer: DomSanitizer,
    private router: Router,
    private toastrMessage: ToastMessageService,
    private spinner: NgxSpinnerService,
    private prescriptionDataSharingService: PrescriptionDataSharingService
  ) { }

  pdfURL: any;
  prescriptionData;
  error: string;

  ngOnInit(): void {
    this.subscriptions.add(this.prescriptionDataSharingService.prescriptionDataFilled.subscribe(res => {
      if (res) {
        this.getPrescriptionMedicationDetails();
      } else {
        this.pdfURL = null;
      }
    }))

  }

  ngOnDestroy(): void {
    this.subscriptions?.unsubscribe();
  }


  private getPrescriptionMedicationDetails() {
    this.spinner.show();
    this.consultationService.getPrescriptionMedicationDetails().subscribe(
      (result) => {
        this.spinner.hide();
        if (result.status) {
          this.prescriptionData = result;
          const urlOfPDF = result.response?.pdfpath?.replace('/var/telemedicine/', `${environment['baseUrl']}`);
          this.pdfURL = this.sanitizer.bypassSecurityTrustResourceUrl(urlOfPDF);
        } else {
          this.error = result?.errors[0]?.message;
          this.toastrMessage.showErrorMsg(this.error, 'Error');
        }
      },
      (error) => {
        this.spinner.hide();
        this.toastrMessage.showErrorMsg(error.errors[0].message, 'Error');
      },
    );
  }

  savePrescriptionDetails() {
    this.spinner.show();
    let request = {
      ctApptId: null,
      ctDoctorId: null,
      ctPatientId: null,
      ctAppointmentStatus: 'completed',
      ctPrescriptionPath: this.prescriptionData['response'].pdfpath,
    };
    this.consultationService.saveAndUploadPrescription(request).subscribe(
      (result) => {
        this.spinner.hide();
        if (result.response && result.status) {
          this.toastrMessage.showSuccessMsg(result.response.message, 'Consultation Details updated Successfully..');
          this.router.navigate(['appointments']);
        } else {
          this.toastrMessage.showErrorMsg(result.errors[0].message, 'Error');
        }
      },
      (error) => {
        this.spinner.hide();
        const preerror = 'Something went Wrong';
        this.toastrMessage.showErrorMsg(preerror, 'Error');
      },
    );
  }
}
