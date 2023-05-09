import { Component, OnInit, OnDestroy, Input, ChangeDetectionStrategy, TemplateRef, ViewChild, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { startOfDay, eachDayOfInterval } from 'date-fns';
import { CalendarEvent, CalendarView, CalendarMonthViewBeforeRenderEvent, CalendarMonthViewDay } from 'angular-calendar';
import { Subject, Subscription } from 'rxjs';
import { DatePipe } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { SubjectMessageService } from 'src/app/shared/subject-message.service';
import * as moment from 'moment';
declare var $: any;
import { NgxSpinnerService } from 'ngx-spinner';
import { ConsultationService } from 'src/app/shared/commonBuildBlocks/services/ConsultationServices/consultation.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { AuthService } from 'src/app/shared/commonBuildBlocks/services/authSercies/auth.service';
import { BlockUnblockHolidayComponent } from '../block-unblock-holiday/block-unblock-holiday.component';
import { ErrorSuccessMessage } from 'src/app/shared/commonBuildBlocks/enum/error-success-message.enum';

@Component({
  selector: 'app-modify-time-slot',
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './modify-time-slot.component.html',
  styleUrls: ['./modify-time-slot.component.scss'],
  encapsulation: ViewEncapsulation.None,
  providers: [ConsultationService]
})
export class ModifyTimeSlotComponent implements OnInit, OnDestroy {
  @ViewChild('modalContent', { static: true }) modalContent: TemplateRef<any>;

  slotsOfMonth: any = [];

  /**
   * Used to slot Form.
   */
  form: FormGroup ;

  /**
   * Used fro calendar view
   */
  view: CalendarView = CalendarView.Month;
  viewDate: Date = new Date();
  events: CalendarEvent[] = [];
  selectedUnselectedDays: any = [];
  selectedMonthViewDay: CalendarMonthViewDay;
  refresh: Subject<any> = new Subject();
  holidayList: any = [];
  selectedDateInCalendar: any;
  getMonthDetailsResponse: any;

  // disable filter options
  disableIsHoliday: boolean = true;
  disableCancelHoliday: boolean = true;

  // Used to send / receive sent message
  subscription: Subscription;
  receivedMessage: any = [];

  constructor(
    private fb: FormBuilder,
    private datePipe: DatePipe,
    private consultationService: ConsultationService,
    private toastrMessage: ToastMessageService,
    public dialog: MatDialog,
    private messageService: SubjectMessageService,
    private spinner: NgxSpinnerService,
    public authService : AuthService
  ) { }

  ngOnInit(): void {
    this.formValidation();
    this.set12Months(12);
    this.getAvailableSlotByMonth();

  }
  
  /**
   * Open filter options on block / unblock option
   */
  openDialog() { 
    const dialogRef = this.dialog.open(BlockUnblockHolidayComponent, {
      width: 'auto',
      data: 'Closed'
    });
    
    //---------------- Send selected days to the modal/popup -------------
    // this.messageService.sendMessage(this.selectedUnselectedDays);
    //-----END----------- Send selected days to the modal/popup -------------

    // ---------------Receive filter option values from block / unblock modal-----------
    this.receivedMessage = [];
    this.subscription = this.messageService.onMessage().subscribe(message => {
      if (message) {
        this.confirmHoliday(message.text);        
      } else {
        this.receivedMessage = [];
      }
    });
    // -------End--------Receive message from block / unblock popup/modal-----------

    dialogRef.afterClosed().subscribe(result => {
      
    });
  }

  getDateRange(startDate, endDate, dateFormat) {
    let dates = [], end = moment(endDate), diff = endDate.diff(startDate, 'days');

    if (!startDate.isValid() || !endDate.isValid() || diff <= 0) {
      return
    }

    for(let i = 0; i < diff; i++) {
      dates.push(end.subtract(1, 'd').format(dateFormat));
    }

    return dates;
  }

  /**
   * Used to show 12 months from current month
   * @param numberOfMonths is a number of next months from current month
   */
  set12Months(numberOfMonths) {
    this.slotsOfMonth = [];
    let date = new Date();
    var dateStart = moment(date);
    for (let index = 0; index < numberOfMonths; index++) {
      this.slotsOfMonth.push({
        //"months": date.toLocaleDateString(),
        // "monthTodisplay" : date.toLocaleDateString('default', { month: 'long' }) + ' ' + date.getFullYear(),
		    // "date" : date.toUTCString()
         monthTodisplay: dateStart.format('MMM-YYYY'),
         date: dateStart.utc().format()
      })
       dateStart.add(1,'month');
       //date.setMonth(date.getMonth() + 1);
    }
  }

  /**
   * Used to show selected month details by API call
   */
  getMonthDetails(index) {
    this.viewDate = new Date(this.slotsOfMonth[index].date);
    this.getAvailableSlotByMonth();
  }

  formValidation() {
    this.form = this.fb.group({
      holiday: ['',Validators.required],
      reason: ['']
    })
  }

  /**
   * Used to get month details from API response
   */
  getAvailableSlotByMonth() {
    this.spinner.show();
    let request = {
      "id": "slot",
      "version": "1.0",
      "requestTime": "2020-01-02T10:01:21.331Z",
      "request": {
        "month": this.datePipe.transform(this.viewDate, "yyyy-MM")
      }
    }    
    this.consultationService.getAvailableSlotByMonth(request).subscribe(data => {
      this.getMonthDetailsResponse = [];
      this.holidayList = [];
      this.events = [];
      if (data.response) {
        if (data.response.length > 0) {
  
          this.getMonthDetailsResponse = data.response;
          for (let item of this.getMonthDetailsResponse) {
            this.viewDate = new Date(item.slotDate);
            this.events.push(
              {
                start: startOfDay(this.viewDate),
                title: 'Available Slots ' + item.numberOfSlot + "\n Booked Slots " + item.noOfBookedSlot,
              }
            );
            
            let day = new Date(item.slotDate);
            let dateOfDay = day.getDate();              
            if (item.holiday === true) {
              this.holidayList.push({
                  "holidayDate": dateOfDay
              });
              this.selectedUnselectedDays.push({
                "selectedDate": this.datePipe.transform(day, "yyyy/MM/dd"),
                "flag": "Already Holiday"
              });
            } else if (item.holiday === false) {
              const holidayIndex = this.holidayList.findIndex((date) => {
                date.holidayDate === dateOfDay;
              });
              if (holidayIndex > -1) {
                this.holidayList.splice(holidayIndex, 1);
              }
            }
            
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

  /**
   * Used to call before the rendering of calendar
   */
  beforeMonthViewRender(renderEvent: CalendarMonthViewBeforeRenderEvent): void {
    renderEvent.body.forEach((day) => {
      // Set color for holiday
      const dayOfMonth = day.date.getDate();
      for(let item of this.holidayList) {
        if (dayOfMonth == item.holidayDate && day.inMonth) {
          day.cssClass = 'bg-pink';
        }
      }
    });
    
  }

  /**
   * Used to get details of selected day in month view calendar
   */
  dayClicked(day: CalendarMonthViewDay): void {
    this.selectedMonthViewDay = day;
    this.selectedDateInCalendar = this.datePipe.transform(this.selectedMonthViewDay.date, "yyyy/MM/dd");

    // selected unselected days
    const index = this.selectedUnselectedDays.findIndex(data => data.selectedDate == this.selectedDateInCalendar);
    
    if (index === -1) {
      this.selectedUnselectedDays.push({
        "selectedDate": this.selectedDateInCalendar,
        "flag": "Mark as holiday"
      });
      this.selectedMonthViewDay.cssClass = 'bg-pink';
      this.disableCancelHoliday = true;
      this.disableIsHoliday = false;
      this.form.get('holiday').setValue("setHoliday");
      this.form.get('holiday').updateValueAndValidity();
    } else {

      if (this.selectedUnselectedDays[index].flag =="Already Holiday") {
        // this.selectedUnselectedDays[index].push({
        //   "status": "Already Holiday Cancelled"
        // });
        this.disableIsHoliday = true;
        this.disableCancelHoliday = false;
        this.form.get('holiday').setValue("cancelHoliday");
        this.form.get('holiday').updateValueAndValidity();
      } 
      else {
        delete day.cssClass;
        this.selectedUnselectedDays.splice(index, 1);
        this.disableCancelHoliday = true;
        this.disableIsHoliday = true;
        this.form.get('holiday').setValue("");
        this.form.get('holiday').updateValueAndValidity();
      }

    }
  }

  /**
   * Used to confirm day as holiday or cancel holiday by API call
   */
  confirmHoliday(value) {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    } else {
      this.callHolidayManagement(value);
    }

  }

  callHolidayManagement(value) {
    this.spinner.show();
    let request = {
      "id": "slot",
      "version": "1.0",
      "requestTime": "2020-01-02T10:01:21.331Z",
      "request": {
        "holidayDate": [],
        "isHoliday": value.holiday == "setHoliday" ? true : false,
        "holidayReason": value.reason
      }
    }

    // request.request.holidayDate.push(value.range.from);
    // request.request.holidayDate.push(value.range.to);

    this.selectedUnselectedDays.forEach(element => {
      if (request.request.isHoliday == true) {
        if (element.flag === "Mark as holiday") {
          
          let date = this.datePipe.transform(element.selectedDate, "yyyy-MM-dd");
          request.request.holidayDate.push(date);
        }
      } else {
        if (element.flag === "Already Holiday") {

          let date = this.datePipe.transform(element.selectedDate, "yyyy-MM-dd");
          request.request.holidayDate.push(date);
        }
      }
    });
    
    this.consultationService.holidayManagement(request).subscribe(data => {
      this.spinner.hide();
      if (data.response) {        
        if (data.status === true || data.status === "Success") {
          
          this.toastrMessage.showSuccessMsg(data.response, 'Success');
          this.selectedUnselectedDays = [];
          $('#staticBackdrop').modal('hide');
          //window.location.reload();
          this.set12Months(12);
          this.getAvailableSlotByMonth();

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
          // this.selectedUnselectedDays = [];
          $('#staticBackdrop').modal('hide');
      
         // window.location.reload();
         this.set12Months(12);
         this.getAvailableSlotByMonth();
        }

      }

    }, error => {
      this.spinner.hide();
      this.technicalBackendErrorMsg();
    });

  }

  /**
   * Used to cancel selected holiday
   */
  cancelHoliday() {
    let day = new Date(this.selectedDateInCalendar);
    let dateOfDay = day.getDate();
    const cancelIndex = this.holidayList.findIndex( data => data.holidayDate == dateOfDay);
    
    if (cancelIndex > -1) {
      this.holidayList.splice(cancelIndex, 1);
    }
    this.refreshView();

  }

  /**
   * Used to set holiday to selected day
   */
  setAsHoliday() {
    let day = new Date(this.selectedDateInCalendar);
    let dateOfDay = day.getDate();
    
    const setIndex = this.holidayList.findIndex( data => data.holidayDate == dateOfDay );
    
    if (setIndex == -1) {
      this.holidayList.push({
          "holidayDate": dateOfDay
        });
    }
    this.refreshView();

  }

  /**
   * Used to refresh calendar view
   */
  refreshView(): void {
    this.refresh.next();
  }

  resetFilterOptions() {
    
  }

  technicalBackendErrorMsg() {
    this.toastrMessage.showErrorMsg(ErrorSuccessMessage.CODEFAILUIR, "Error");
  }
  cancel(){
    this.set12Months(12);
    this.getAvailableSlotByMonth();
    //this.selectedUnselectedDays = [];
  }

  ngOnDestroy() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }


}
