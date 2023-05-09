import { IPatientPersonalDetails } from './../../../../shared/commonBuildBlocks/interfaces/patient-personal-details.interface';
import { Component, Inject, OnInit, ViewChild, Input } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { AuthService } from 'src/app/shared/commonBuildBlocks/services/authSercies/auth.service';
import { DocumentService } from 'src/app/shared/commonBuildBlocks/services/documentServices/document.service';
import { PatientService } from 'src/app/shared/commonBuildBlocks/services/patientServices/patient.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';

@Component({
  selector: 'app-tm-historical-completed-appointments',
  templateUrl: './tm-historical-completed-appointments.component.html',
  styleUrls: ['./tm-historical-completed-appointments.component.scss']
})
export class TmHistoricalCompletedAppointmentsComponent implements OnInit {

  displayedColumns = ['appointmentId', 'name', 'date', 'view', 'download'];
  dataSource = new MatTableDataSource<any>();
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @Input() ptPersonalDetails: IPatientPersonalDetails;

  constructor(
    public patientService: PatientService,
    public authService: AuthService,
    public toastMessage: ToastMessageService,
    public documentService: DocumentService
  ) { }

  ngOnInit(): void {
    this.search();
  }

  search() {

    let payload: any = {
      apptId: null,
      doctorName: this.ptPersonalDetails?.name,
      fromDate: null,
      toDate: null
    }

    this.dataSource.data = [];

    this.patientService.searchCompletedAppointments(payload).subscribe((resp: any) => {
      if (resp && resp.response && resp.status) {
        this.dataSource.data = resp.response;
        setTimeout(() => {
          this.dataSource.paginator = this.paginator;
        }, 500)

      } else if (resp && resp.errors && !resp.status) {
        this.toastMessage.showErrorMsg(resp.errors[0].message, 'Error');
      }
    }, error => {
      this.toastMessage.showErrorMsg(error.errors[0].message, 'Error');
    })
  }

  getPrescriptionDetails(data, type) {
    let postData = {
      request: {
        ctApptId: data.appointmentId,
        ctDoctorId: data.doctorId,
        ctPatientId: this.authService.isUserPatient() ? this.authService.getUserId() : data.patientId
      },
    }
    this.patientService.getPrescriptionDetails(postData).subscribe((resp: any) => {
      if (resp && resp.response && resp.status) {
        var pdfpath = resp.response.pdfpath
        var filename = pdfpath.substring(pdfpath.lastIndexOf('/') + 1);
        var name = filename.split('.').slice(0, -1).join('.');
        sessionStorage.setItem('ctAppId', name);
        if (resp && resp.response.pdfData) {
          if (type == 'view') {
            this.documentService.downloadFileAndView(resp.response.pdfData, 'application/pdf');
          } else {
            this.documentService.downloadFileToBrowser(resp.response.pdfData, 'application/pdf');
          }
        }
        //this.toastMessage.showSuccessMsg(resp.response.message, "Success");
      } else if (resp && resp.errors && !resp.status) {
        this.toastMessage.showErrorMsg(resp.errors[0].message, 'Error');
      }
    }, error => {
      this.toastMessage.showErrorMsg(error.errors[0].message, 'Error');
    })
  }
}