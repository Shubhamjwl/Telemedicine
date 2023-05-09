import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { environment } from 'src/environments/environment';
import { NgxSpinnerService } from 'ngx-spinner';
import { ScribeService } from 'src/app/shared/commonBuildBlocks/services/scribeServices/scribe.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { ConsultationService } from 'src/app/shared/commonBuildBlocks/services/ConsultationServices/consultation.service';

@Component({
  selector: 'app-assign-scribe',
  templateUrl: './assign-scribe.component.html',
  styleUrls: ['./assign-scribe.component.scss']
})
export class AssignScribeComponent implements OnInit {
  scribeGroup: FormGroup;
  reportGroup: FormGroup;
  startDate:Date = new Date();
  filteredScribeList:any;
  scribes: any;
  displayedColumns = ['profile','details'];

  scribeList = new MatTableDataSource([]);
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(public fb: FormBuilder,
        public datePipe: DatePipe,
        public scribeService: ScribeService,
        public toastrMessage: ToastMessageService,
        public spinner: NgxSpinnerService,
			  public consultationService: ConsultationService) {
		this.createForm();
  }

  ngOnInit(): void {
	  this.getScribeListByDoctorID();
  }
  ngAfterViewInit() {
		this.scribeList.paginator = this.paginator;
	}
  createForm(){
    this.scribeGroup = this.fb.group({
        date:['', Validators.required],
        scribe:['', Validators.required]
	});

	this.reportGroup = this.fb.group({
		reportType: ['', [Validators.required]]
	})
  }
  getScribeListByDoctorID() {
    this.spinner.show();
    this.consultationService.getScribeList().subscribe(result => {
      this.spinner.hide();
      if(result.response){
			result.response.scribeDtls.forEach(element => {
			element.profilePhoto = element.profilePhoto ? element.profilePhoto.replace('/var/telemedicine/', `${environment["baseUrl"]}`) : 'assets/images/img_avatar.png';
			});
			this.scribeList = new MatTableDataSource(result.response.scribeDtls);
			this.filteredScribeList = result.response.scribeDtls.filter((scribe) =>  scribe.isDefaultScribe != 'Y');
		
			this.scribeList.paginator = this.paginator;
      }else if(result.errors) {
      }
    }, error =>{
		this.spinner.hide();
    })
  }
  assignScribe(){
    this.spinner.show();
    let selectedDate = this.datePipe.transform(this.scribeGroup.get('date').value,"yyyy-MM-dd");
    // this.changeDateFormat();
    let payload = {
		drRegID: sessionStorage.getItem('USER_ID'),
		assignDate: selectedDate,
		scribeID: this.scribeGroup.get('scribe').value,
    }
    this.scribeService.selectedScribeByDoctor(payload).subscribe((result:any) => {
		this.spinner.hide();
		if (result.response != null) {
			this.toastrMessage.showSuccessMsg(result.response.info, "Congratulation");
			this.getScribeListByDoctorID();
			this.scribeGroup.reset();
		} else if (result.errors) {
			this.toastrMessage.showInfoMsg(result.errors[0].message, "Information");
		}
    }, error => {
        this.spinner.hide();
        this.toastrMessage.showErrorMsg("Error occured at the backend!", "Error");
    });
  }
  cancel(){

  }
  
}
