import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { NgxSpinnerService } from 'ngx-spinner';
import { debounceTime } from 'rxjs/operators';
import { Details, PTDetails } from 'src/app/shared/commonBuildBlocks/model/remove-element.model';
import { AuthService } from 'src/app/shared/commonBuildBlocks/services/authSercies/auth.service';
import { DoctorService } from 'src/app/shared/commonBuildBlocks/services/doctorServices/doctor.service';
import { PatientService } from 'src/app/shared/commonBuildBlocks/services/patientServices/patient.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-view-patient-list',
  templateUrl: './view-patient-list.component.html',
  styleUrls: ['./view-patient-list.component.scss'],
})
export class ViewPatientListComponent implements OnInit {
  displayedColumns = ['profile', 'details', 'action'];
  dataSource = new MatTableDataSource([]);
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  private ndhmFlag: boolean;
  searchControl = new FormControl('');

  constructor(
    public authService: AuthService,
    public patientService: PatientService,
    public doctorService: DoctorService,
    private spinner: NgxSpinnerService,
    public toastMessage: ToastMessageService,
  ) { }

  ngOnInit(): void {
    if (this.authService.isUserPatient()) {
      this.getDoctorList();
    } else {
      this.getPatientList();
    }
    this.getNdhmFlag();

    this.initForm();
  }

  private initForm() {
    this.searchControl.valueChanges.pipe(debounceTime(500)).subscribe(value => {
      this.dataSource.filter = value?.trim()?.toLocaleLowerCase();
    })
  }

  private getNdhmFlag() {
    this.patientService.getNdhmFlag().subscribe({
      next: (res) => {
        this.ndhmFlag = res.ndhmFlag;
        console.log('ndhmFlag', this.ndhmFlag);
      },
      error: (err) => {
        console.log('enter in error');
      },
    });
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }
  getDoctorList() {
    let data = { ptRegId: this.authService.getUserId() };
    this.patientService.getMappedDrListByPatientId(data).subscribe(
      (resp: any) => {
        if (resp) {
          if (resp.response && resp.response.length) {
            // resp.response.forEach(element => {
            // 	element.photopath = element.photopath ? element.photopath.replace('/var/telemedicine/', `${environment["baseUrl"]}`) : 'assets/images/img_avatar.png'
            // });
            let details = new Details(resp.response);
            this.dataSource = new MatTableDataSource(details.data);

            setTimeout(() => {
              this.dataSource.paginator = this.paginator;
            }, 500);
          } else if (resp.errors) {
            this.dataSource = new MatTableDataSource([]);
            this.dataSource.paginator = this.paginator;
            //this.toastMessage.showErrorMsg(resp.errors[0].message, "Error");
          }
        }
      },
      (error) => {
        this.spinner.hide();
        // / this.errorMessage = error.errors[0].message;
      },
    );
  }
  getPatientList() {
    let data = {
      drRegId: this.authService.isUserDoctor() ? this.authService.getUserId() : this.authService.getDrId(),
    };

    this.doctorService.getMappedDrListByPatientId(data).subscribe(
      (resp: any) => {
        if (resp) {
          if (resp.response && resp.response.length) {
            resp.response.forEach((element) => {
              element.photopath = element.photopath
                ? element.photopath.replace('/var/telemedicine/', `${environment['baseUrl']}`)
                : 'assets/images/img_avatar.png';
            });
            let details = new PTDetails(resp.response);
            this.dataSource = new MatTableDataSource(details.data);
            console.log('this.dataSource', resp.response);
            setTimeout(() => {
              this.dataSource.paginator = this.paginator;
            }, 10);
          } else if (resp.errors) {
            this.dataSource = new MatTableDataSource([]);
            this.toastMessage.showErrorMsg(resp.errors[0].message, 'Error');
          }
        }
      },
      (error) => {
        this.spinner.hide();
        // / this.errorMessage = error.errors[0].message;
      },
    );
  }
  remove(id) {
    this.patientService.unMappedPatientOrDrById(id).subscribe(
      (resp: any) => {
        if (resp) {
          if (resp.status) {
            // this.toastMessage.showSuccessMsg(resp.response.message, "Success");
            if (this.authService.isUserPatient()) {
              this.getDoctorList();
              this.toastMessage.showSuccessMsg('Doctor removed successfully', 'Success');
            } else {
              this.getPatientList();
              this.toastMessage.showSuccessMsg('Patient removed successfully', 'Success');
            }
          } else if (resp.errors) {
            this.toastMessage.showErrorMsg(resp.errors[0].message, 'Error');
          }
        }
      },
      (error) => {
        this.spinner.hide();
        // / this.errorMessage = error.errors[0].message;
      },
    );
  }

  /* createHealthID(element) {
    console.log('element', element);
    const date = this.datePipe.transform(element?.dob, 'yyyy-MM-dd');
    var dateObj = new Date(element?.dob);
    this.month = dateObj.getMonth();
    this.date = dateObj.getDate();
    this.year = dateObj.getFullYear();
    this.changeDate = this.date + '-' + this.month + '-' + this.year;
    console.log(this.changeDate);
    if (this.ndhmgender == null || 'Male') {
      //window.location.href=environment.ndhmURL+"#/otp?name="+this.ndhmname+"&gender=M"+"&mobileNoSel="+this.ndhmmobileNo+"&emailId="+this.ndhmemailId+"&dob="+this.ndhmdob+"&hvStatus="+this.ndhmHVStatus+"&txnId="+'';
      window.open(
        this.ndhmLinkURL +
        '#/verfymobile?name=' +
        btoa(element?.name) +
        '&gender=' +
        btoa(element.gender === 'Female' ? 'F' : element.gender === 'Male' ? 'M' : '') +
        '&mobileNoSel=' +
        btoa(element?.mobile) +
        '&emailId=' +
        btoa(element?.email) +
        '&dob=' +
        btoa(this.changeDate) +
        '&address=' +
        btoa(element?.ndhmaddress) +
        '&hvStatus=' +
        btoa(element?.ndhmHVStatus) +
        '&txnId=' +
        btoa(''),
        '_blank',
      );
    } else {
      //window.location.href=environment.ndhmURL+"#/otp?name="+this.ndhmname+"&gender=F"+"&mobileNoSel="+this.ndhmmobileNo+"&emailId="+this.ndhmemailId+"&dob="+this.ndhmdob+"&hvStatus="+this.ndhmHVStatus+"&txnId="+'';
      window.open(
        this.ndhmLinkURL +
        '#/verfymobile?name=' +
        btoa(element?.name) +
        '&gender=' +
        btoa(element.gender === 'Female' ? 'F' : element.gender === 'Male' ? 'M' : '') +
        '&mobileNoSel=' +
        btoa(element?.mobile) +
        '&emailId=' +
        btoa(element?.email) +
        '&dob=' +
        btoa(this.changeDate) +
        '&address=' +
        btoa(element?.ndhmaddress) +
        '&hvStatus=' +
        btoa(element?.ndhmHVStatus) +
        '&txnId=' +
        btoa(''),
        '_blank',
      );
    }
  }

  seedHealthID(element) {
    console.log('element', element);
    const date = this.datePipe.transform(element?.dob, 'yyyy-MM-dd');
    var dateObj = new Date(element?.dob);
    this.month = dateObj.getMonth();
    this.date = dateObj.getDate();
    this.year = dateObj.getFullYear();
    this.changeDate = this.date + '-' + this.month + '-' + this.year;
    console.log(this.changeDate);
    if (this.ndhmgender == null || 'Male') {
      //window.location.href=environment.ndhmURL+"#/AssistedHealthidCreationMobile?name="+this.ndhmname+"&gender=M"+"&mobileNoSel="+this.ndhmmobileNo+"&emailId="+this.ndhmemailId+"&dob="+this.ndhmdob+"&hvStatus="+this.ndhmHVStatus+"&txnId="+'';
      window.open(
        this.ndhmLinkURL +
        '#/ProfileCreate?name=' +
        btoa(element?.name) +
        '&gender=' +
        btoa(element.gender === 'Female' ? 'F' : element.gender === 'Male' ? 'M' : '') +
        '&mobileNoSel=' +
        btoa(element?.mobile) +
        '&emailId=' +
        btoa(element?.email) +
        '&dob=' +
        btoa(this.changeDate) +
        '&hvStatus=' +
        btoa(element?.state) +
        '&txnId=' +
        btoa('') +
        '&healthId=' +
        btoa(element?.abhaId),
        '_blank',
      );
    } else {
      //window.location.href=environment.ndhmURL+"#/AssistedHealthidCreationMobile?name="+this.ndhmname+"&gender=F"+"&mobileNoSel="+this.ndhmmobileNo+"&emailId="+this.ndhmemailId+"&dob="+this.ndhmdob+"&hvStatus="+this.ndhmHVStatus+"&txnId="+'';
      window.open(
        this.ndhmLinkURL +
        '#/ProfileCreate?name=' +
        btoa(element?.name) +
        '&gender=' +
        btoa(element.gender === 'Female' ? 'F' : element.gender === 'Male' ? 'M' : '') +
        '&mobileNoSel=' +
        btoa(element?.mobile) +
        '&emailId=' +
        btoa(element?.email) +
        '&dob=' +
        btoa(this.changeDate) +
        '&hvStatus=' +
        btoa(element?.state) +
        '&txnId=' +
        btoa('') +
        '&healthId=' +
        btoa(element?.abhaId),
        '_blank',
      );
    }
  } */
}
