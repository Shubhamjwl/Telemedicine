import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CalendarEvent, CalendarMonthViewBeforeRenderEvent, CalendarView } from 'angular-calendar';
import { startOfDay } from 'date-fns';
import * as moment from 'moment';
import { NgxSpinnerService } from 'ngx-spinner';
import { AppointmentService } from 'src/app/shared/commonBuildBlocks/services/appointmentServices/appointment.service';
import { ConsultationService } from 'src/app/shared/commonBuildBlocks/services/ConsultationServices/consultation.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';

@Component({
  selector: 'app-appointment-history',
  templateUrl: './appointment-history.component.html',
  styleUrls: ['./appointment-history.component.scss']
})
export class AppointmentHistoryComponent implements OnInit {
  monthList: any[];
  index: number;
  viewDate: Date;
  getMonthDetailsResponse: any[];
  events:CalendarEvent[] = [];
  view: CalendarView = CalendarView.Month;
  previousMonth: any = {};
  currentMonth: any = {};
  prevDate: any;
  currentDate: any;
  constructor(public consultationService: ConsultationService,
	  		private spinner: NgxSpinnerService,
			private toastrMessage: ToastMessageService,
			public appointmentService: AppointmentService,
			public router:Router,
			public datePipe: DatePipe,) {
		this.monthList = new Array();
		this.index = 0;
		this.viewDate = new Date();
		let date = new Date();
	 	let yesterday =  new Date() ;
		let nw = new Date(yesterday.setMonth(yesterday.getMonth() - 1));

		this.previousMonth = {
				completed: '-',
				noShow: '-',
				cancel: '-',
				avgTime: '-',
				previousDate: ' ' + nw.getDate() + ' ' + nw.toLocaleDateString('default', { month: 'short' })  + ' ' + nw.getFullYear()
		}
		this.currentMonth = {
			completed: '-',
			noShow: '-',
			cancel: '-',
			avgTime: '-',
			currentDate: ' ' + date.getDate() + ' ' + date.toLocaleDateString('default', { month: 'short' })+ ' ' + date.getFullYear()
	  }
   }

	ngOnInit(): void {
		this.createMonthList();
		this.getAvailableSlotByMonth();
		this.getAppointmentHistory();
	}
	createMonthList(){
		let day = new Date();
		let startDate = moment(day);
		startDate.subtract(5, 'month');
	 	let tdate = moment(day).format('MMM-YYYY');
	 
		for(let i = 0; i < 11; i++){
			
			startDate.add(1, 'month');
			let month = startDate.format('MMM-YYYY');
			let year = startDate.format('YYYY-MM');

			this.monthList.push(
				{
					key: month,
					value: year
				}
			);
		}
		this.index = this.monthList.findIndex(element => element.key == tdate);
		setTimeout(()=> {
			this.scrollTo();
		},1000);
	}

	createMonthLists(){
		 let day = new Date();
		 let tdate = day.toLocaleDateString('default', { month: 'long' }) + ' ' + day.getFullYear();
		for(let i = 0; i < 11; i++){
			let today = new Date();
			let startDate = new Date(today.setMonth(today.getMonth() - 5));
			let date = new Date(startDate.setMonth(startDate.getMonth() + i));
		    date.toLocaleString('default', { month: 'long' }) + ' ' + date.getFullYear();
			let month =  date.toLocaleString('default', { month: 'long' }) + ' ' + date.getFullYear();
			let m = (date.getMonth() + 1).toString();
			let convertedMonth = m.length == 1 ? '0' + m : m;
			let year = date.getFullYear() +  '-' + convertedMonth;
			this.monthList.push(
				{
					key: month,
					value: year
				}
			);
		}

		this.index = this.monthList.findIndex(element => element.key == tdate);
		setTimeout(()=> {
			this.scrollTo();
		},1000);

	}
	goto(data, i){
		this.index = i;
		this.viewDate = new Date(data.value);
		this.getAvailableSlotByMonth();

	}
	scrollTo(){
		let scroll =  this.index > 0 ? this.index - 4 : 0;
		scroll = scroll + 2;
		let value  = scroll * 50;
		document.getElementById('month-container').scrollTop += value;
	}
	prev(){
		if(this.index == 0){
			return;
		}
	//	this.index-- ;
		document.getElementById('month-container').scrollTop -= 50;
	}
	next(){
		if(this.index == this.monthList.length - 1){
			 return;
		}
		//this.index++;
		document.getElementById('month-container').scrollTop += 50;
	}
	getAvailableSlotByMonth() {
		this.spinner.show();
		let request = {
		  "id": "slot",
		  "request": {
			"month": this.datePipe.transform(this.viewDate, "yyyy-MM")
		  }
		}

		this.appointmentService.getHistoricalSlotDetailsByMonth(request).subscribe(data => {
		  this.getMonthDetailsResponse = [];
		  this.events = [];
		  if (data.response) {
			if (data.response.length > 0) {

			  this.getMonthDetailsResponse = data.response;
			  for (let item of this.getMonthDetailsResponse) {
				this.viewDate = new Date(item.slotDate);
				this.events.push(
				  {
					start: startOfDay(this.viewDate),
					title: 'Available Slots ' + item.numberOfSlot +  "\n Completed Slots " + item.noOfBookedSlot + "\n Booked Slots " + item.noOfBookedSlot,
				  }
				);
				let day = new Date(item.slotDate);
				let dateOfDay = day.getDate();
			  }
			}
		  } else {
			if (data.errors) {
			  if (data.errors.length > 0) {
				let errMsg = '';
				for(let msg of data.errors) {
				  errMsg = errMsg + " " + msg.message;
				}
				this.toastrMessage.showErrorMsg(errMsg, 'Error');
			  }
			}
		  }
		  this.spinner.hide();
		}, error => {
		  this.spinner.hide();
		  this.technicalBackendErrorMsg();
		});
	
	}
	technicalBackendErrorMsg() {
		throw new Error('Method not implemented.');
	}
	beforeMonthViewRender(renderEvent: CalendarMonthViewBeforeRenderEvent): void {
		renderEvent.body.forEach((day) => {
		  // Set color for holiday
		  const dayOfMonth = day.date.getDate();
		//   for(let item of this.holidayList) {
		// 	if (dayOfMonth == item.holidayDate && day.inMonth) {
		// 	  day.cssClass = 'bg-pink';
		// 	}
		//   }
		});
	}
	getAppointmentHistory(){
		this.appointmentService.getAppointmentHistory().subscribe((resp:any) => {
			if(resp){
				if(resp.response){
					if(resp.response['Previous Month']){
					this.previousMonth.completed = resp.response['Previous Month'].completed ? resp.response['Previous Month'].completed : '-';
					this.previousMonth.noShow =  resp.response['Previous Month'].noShow ? resp.response['Previous Month'].noShow : '-';
					this.previousMonth.cancel =  resp.response['Previous Month'].cancel ? resp.response['Previous Month'].cancel : '-';
					this.previousMonth.avgTime =  resp.response['Previous Month'].avgTime ? resp.response['Previous Month'].avgTime : '-' ;
					}
					if(resp.response['Current Month']){
						this.currentMonth.completed = resp.response['Current Month'].completed ? resp.response['Current Month'].completed : '-';
						this.currentMonth.noShow =  resp.response['Current Month'].noShow ? resp.response['Current Month'].noShow : '-';
						this.currentMonth.cancel =  resp.response['Current Month'].cancel ? resp.response['Current Month'].cancel : '-';
						this.currentMonth.avgTime =  resp.response['Current Month'].avgTime ? resp.response['Current Month'].avgTime : '-';
					}
				}
			}
		}, (error) => {
			this.spinner.hide();
			this.toastrMessage.showErrorMsg(error && error.message ? error.message : 'Something went wrong' , 'Error');
		})
	}
	gotoDashboard(){
		this.router.navigate(['/appointments'])
   }

}
