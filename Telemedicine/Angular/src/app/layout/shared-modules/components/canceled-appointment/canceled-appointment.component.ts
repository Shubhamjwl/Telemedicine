import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { PatientService } from 'src/app/shared/commonBuildBlocks/services/patientServices/patient.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';

@Component({
  selector: 'app-canceled-appointment',
  templateUrl: './canceled-appointment.component.html',
  styleUrls: ['./canceled-appointment.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class CanceledAppointmentComponent implements OnInit {
  displayedColumns = [];
  dataSource = new MatTableDataSource<any>();
  @ViewChild(MatSort) sort: MatSort;
  searchFormDate: FormGroup;
  searchForm: FormGroup;
  isValid = false;
  startDate: Date = new Date();
  @ViewChild(MatPaginator) paginator: MatPaginator;

  /**
   * Used to check logged in user role
   */
   userRole = sessionStorage.getItem('ROLE');

  

  constructor(
    private fb: FormBuilder,
    private patientService: PatientService,
    private datePipe: DatePipe,
    private toastMessage: ToastMessageService,
  ) {
    this.createForm();

    if(this.userRole == 'DOCTOR'){
      this.displayedColumns = ['patientName' ,'appointmentId','date', 'time'];
    }
    else{
      this.displayedColumns = ['appointmentId','date', 'time'];
    }
  }

  ngOnInit(): void {
    this.search();
    this.onChanges();
    this.dataSource.filterPredicate = (data: any, filter: string) => {
      return data.appointmentId == filter;
    };
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  onChanges() {
    this.searchForm.get('fromDate').valueChanges.subscribe((value) => {
      this.searchForm.get('toDate').markAsTouched();
      this.searchForm.get('toDate').updateValueAndValidity();
    });
  }

  createForm() {
    this.searchForm = this.fb.group({
      apptId: [],
      name: [],
      fromDate: [],
      toDate: ['', this.validatorforToDate.bind(this)],
    });
  }
  search() {
    this.dataSource.data = [];

    this.patientService.getCancelAppointListDocorPatient().subscribe(
      (resp: any) => {
        if (resp && resp.response && resp.status) {
          this.dataSource.data = resp.response;
          setTimeout(() => {
            this.dataSource.paginator = this.paginator;
          }, 500);
        } else if (resp && resp.errors && !resp.status) {
          this.toastMessage.showErrorMsg(resp.errors[0].message, 'Error');
        }
      },
      (error) => {
        this.toastMessage.showErrorMsg(error?.error?.errors[0]?.message, 'Error');
      },
    );
  }

  searchByFilter() {
    if (!this.searchForm.valid) {
      this.toastMessage.showErrorMsg('Invalid Filters', 'Error');
      return;
    }
    const { apptId, fromDate, toDate } = this.searchForm.getRawValue();
    if (!apptId && !fromDate && !toDate) {
      this.search();
    } else {
      const payload: any = {
        appointmentId: apptId,
        fromDate: this.datePipe.transform(fromDate, 'yyyy-MM-dd'),
        toDate: this.datePipe.transform(toDate, 'yyyy-MM-dd'),
      };
      this.dataSource.data = [];
      this.patientService.getCancelAppointListByFilter(payload).subscribe(
        (resp: any) => {
          if (resp && resp.response && resp.status) {
            this.dataSource.data = resp.response;
            setTimeout(() => {
              this.dataSource.paginator = this.paginator;
            }, 500);
          } else if (resp.errors) {
            this.toastMessage.showErrorMsg(resp.errors[0].message, 'Error');
          }
        },
        (error) => {
          this.toastMessage.showErrorMsg('No cancel appointments found', 'Error');
        },
      );
    }
  }

  clear() {
    this.searchForm.reset();
    this.search();
  }

  validatorforToDate(control: FormControl) {
    if (!control) return;

    if (!control.parent) return;

    let group = control.parent;
    let fromDate = group.get('fromDate').value;

    if (!control.value && fromDate) {
      return { required: true };
    } else {
      if (fromDate) {
        let fd = new Date(this.datePipe.transform(fromDate, 'yyyy-MM-dd')).getTime();
        let td = new Date(this.datePipe.transform(control.value, 'yyyy-MM-dd')).getTime();
        if (fd > td) {
          return { max: true };
        }
      }
    }
    return null;
  }
}
