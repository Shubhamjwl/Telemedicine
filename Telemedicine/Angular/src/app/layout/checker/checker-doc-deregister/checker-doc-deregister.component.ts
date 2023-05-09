import { Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { NgxSpinnerService } from 'ngx-spinner';
import { DoctorList } from 'src/app/shared/commonBuildBlocks/model/doctor.model';
import { CheckerService } from 'src/app/shared/commonBuildBlocks/services/checkerServices/checker.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';

@Component({
	selector: 'app-checker-doc-deregister',
	templateUrl: './checker-doc-deregister.component.html',
	styleUrls: ['./checker-doc-deregister.component.scss'],
	encapsulation: ViewEncapsulation.None
})
export class CheckerDocDeRegisterComponent implements OnInit {
	doctorData = new MatTableDataSource<any>();
	q:string;
	selectAll: boolean;
	displayedColumns = ['checkbox', 'profile', 'doctorName'];
	@ViewChild(MatPaginator) paginator: MatPaginator;

  /**
   * Set the paginator after the view init since this component will
   * be able to query its view for the initialized paginator.
   */
	ngAfterViewInit() {
		this.doctorData.paginator = this.paginator;
	}
	constructor(
		public checkerService:CheckerService,
		private toastMessage: ToastMessageService,
		private spinner: NgxSpinnerService,
	) {
	}

	ngOnInit(): void {
		this.getDoctorsList();
	}
	getDoctorsList(){
		this.spinner.show();
		this.checkerService.getDoctorListToDeRegister().subscribe((resp:any) => {
				this.spinner.hide();
			if(resp.response){
				let docDetails = new DoctorList(resp);
				this.doctorData = new MatTableDataSource(docDetails.doctorDetails);
				this.doctorData.paginator = this.paginator;
			}else if(resp.errors){
			this.toastMessage.showErrorMsg(resp.errors[0].message, "Error");
			}
		}, error => {
			this.spinner.hide();
		// / this.errorMessage = error.errors[0].message;
		})
	}
	selectAllDocs(){
		this.selectAll = !this.selectAll;
		let pageSize = this.doctorData.paginator.pageSize;
		let pageIndex = this.doctorData.paginator.pageIndex;
	
		let i = pageIndex * pageSize;
		for (let index = 0; index < pageSize; index++) {
			if(this.doctorData.filteredData && this.doctorData.filteredData[i]){
				this.doctorData.filteredData[i].select = this.selectAll;
			}
			i++;
		}
	}
	checkSelectedAll(i){
		let pageSize = this.doctorData.paginator.pageSize;
		let pageIndex = this.doctorData.paginator.pageIndex;
		
		let selectedIndex = (pageSize * pageIndex) + i;
		this.doctorData.filteredData[selectedIndex].select = !this.doctorData.filteredData[selectedIndex].select;
		
	}
	deRegister(){
		let selected = this.doctorData.data.filter((item) =>  item.select == true);
		
			if(!selected.length){
			this.toastMessage.showInfoMsg('Please select Doctor to De-Register', 'Info');
			return;
		}
		this.spinner.show();

		let doctor = [];
		selected.forEach((element)=> {
			doctor.push({userID: element.docUserID});
		})
		let data = {
			doctor: doctor
		}
		this.checkerService.deRegisterDoctors(data).subscribe((resp:any) => {
			this.spinner.hide();
			if(resp && resp.status && resp.response){
					this.toastMessage.showSuccessMsg(resp.response.info ? resp.response.info : 'Doctors are successfully Deregister....', 'Success');
			this.getDoctorsList();
				}else if(resp.errors){
				this.toastMessage.showErrorMsg(resp.errors[0].message, "Error");
				}
		})
		
	}
	pageEvents(event: any) {
		let pageSize = this.doctorData.paginator.pageSize;
		let pageIndex = this.doctorData.paginator.pageIndex;
		
		let i = pageIndex * pageSize;
		let cnt = 0;
		for (let index = 0; index < pageSize; index++) {
			if(this.doctorData.data && this.doctorData.data[i] && this.doctorData.data[i].select){
			cnt ++;
			}
			
			if(cnt == pageSize){
			this.selectAll = true;
			}else {
			this.selectAll = false;
			}
			i++;
		}
	}
}
