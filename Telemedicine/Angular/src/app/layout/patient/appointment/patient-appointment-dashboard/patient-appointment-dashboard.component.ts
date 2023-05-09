import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { PtDocumentType } from 'src/app/shared/commonBuildBlocks';
import { ErrorSuccessMessage } from 'src/app/shared/commonBuildBlocks/enum/error-success-message.enum';
import { MasterTypes } from 'src/app/shared/commonBuildBlocks/enum/master-type.enum';
import { PatientDocumentService } from 'src/app/shared/commonBuildBlocks/services';
import { AppointmentService } from 'src/app/shared/commonBuildBlocks/services/appointmentServices/appointment.service';
import { AuthService } from 'src/app/shared/commonBuildBlocks/services/authSercies/auth.service';
import { CheckerService } from 'src/app/shared/commonBuildBlocks/services/checkerServices/checker.service';
import { ConsultationService } from 'src/app/shared/commonBuildBlocks/services/ConsultationServices/consultation.service';
import { DataPassingService } from 'src/app/shared/commonBuildBlocks/services/dataPassingServices/dataPassing.service';
import { DocumentService } from 'src/app/shared/commonBuildBlocks/services/documentServices/document.service';
import { PatientService } from 'src/app/shared/commonBuildBlocks/services/patientServices/patient.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-patient-appointment-dashboard',
  templateUrl: './patient-appointment-dashboard.component.html',
  styleUrls: ['./patient-appointment-dashboard.component.scss'],
})
export class PatientAppointmentDashboardComponent implements OnInit {
  doctorListMappedWithPatientId = [];
  appointmentList;
  userID = sessionStorage.getItem('USER_ID');
  timeInterval: any;
  timer: any;
  filesArray = [];
  uploadedFileNames = [];
  modelRef: any;
  user: any;
  reportGroup: FormGroup;
  reasonGroup: FormGroup;
  reasonModelRef: any;
  documentNameList:PtDocumentType[];
  uploadedFile = '';
  itemsPerSlide = 5;
  Valid: any;
  isHealthIdNo: boolean = false;
  displayedColumns: string[] = ['date', 'pname', 'dname', 'symptom', 'view', 'status', 'consultation'];
  isHealthIdPopUp: boolean = false;
  columns: string[] = [];
  ndhmname: any;
  ndhmgender: any;
  ndhmmobileNo: any;
  ndhmemailId: any;
  ndhmdob: any;
  ndhmaddress: any;
  ndhmHVStatus: any;
  ndhmHealthId: any;
  ptUserId: any;
  ndhmFlag: boolean;
  ndhmLinkURL = environment.ndhmURL;
  hasRecheduled: boolean;
  dataSource = new MatTableDataSource<any>([]);

  @ViewChild(MatPaginator) paginator: MatPaginator;
  mobileNo: any;

  constructor(
    private authService: AuthService,
    private patientService: PatientService,
    private consultationService: ConsultationService,
    private dataPassingService: DataPassingService,
    public toastMessage: ToastMessageService,
    private appointmentService: AppointmentService,
    private datePipe: DatePipe,
    private spinner: NgxSpinnerService,
    public modelService: NgbModal,
    private fb: FormBuilder,
    private router: Router,
    private sanitizer: DomSanitizer,
    private checkerService: CheckerService,
    public documentService: DocumentService,
    private patientDocumentService: PatientDocumentService,
  ) {
    this.hasRecheduled = this.router.url === '/reschedule-book-appointments';
  }

  ngOnInit(): void {
    this.getNdhmFlag();
    this.getPetientDetails();
    this.getDoctorList();
    this.getpatientAppointmentDetails();
    this.getReportTypes();

    this.reportGroup = this.fb.group({
      documentName: ['', [Validators.required]],
    });
    this.reasonGroup = this.fb.group({
      apptId: [''],
      error: [''],
    });
  }
  ngOnDestroy() {
    if (this.modelRef) {
      this.modelRef.close();
    }
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  getNdhmFlag() {
    this.patientService.getNdhmFlag().subscribe({
      next: (res) => {
        this.ndhmFlag = res.ndhmFlag;
      },
      error: (err) => {
        console.log('enter in error');
      },
    });
  }

  getDoctorList() {
    let data = {
      ptRegId: this.authService.getUserId(),
    };
    this.patientService.getMappedDrListByPatientId(data).subscribe(
      (resp: any) => {
        if (resp) {
          if (resp.response && resp.response.length) {
            resp.response.forEach((element) => {
              element.photopath = element.photopath
                ? element.photopath.replace('/var/telemedicine/', `${environment['baseUrl']}`)
                : 'assets/images/img_avatar.png';
            });
            this.doctorListMappedWithPatientId = resp.response;
          } else if (resp.errors) {
            this.doctorListMappedWithPatientId = [];
          }
        }
      },
      (error) => {
        this.spinner.hide();
      },
    );
  }

  getpatientAppointmentDetails() {
    this.spinner.show();
    let data = { ptRegID: this.authService.getUserId() };
    if (this.hasRecheduled) {
      this.appointmentService.getScheduledAndRescheduledConsultationListByPatientId(data.ptRegID).subscribe(
        (resp: any) => {
          this.spinner.hide();
          if (resp) {
            if (resp.response && resp.response.apptDtls && resp.response.apptDtls.length) {
              resp.response.apptDtls.forEach((element) => {
                element.profilePhoto = element.profilePhoto
                  ? element.profilePhoto.replace('/var/telemedicine/', `${environment['baseUrl']}`)
                  : 'assets/images/img_avatar.png';
              });
              this.appointmentList = resp.response.apptDtls;
              this.dataSource = new MatTableDataSource<any>(this.appointmentList);
              this.dataSource.paginator = this.paginator;
            } else if (resp.errors) {
            }
          }
        },
        (error) => {
          this.spinner.hide();
        },
      );
    } else {
      let data = { ptRegID: this.authService.getUserId() };
      this.consultationService.getPatientAppoinments(data.ptRegID).subscribe(
        (resp: any) => {
          this.spinner.hide();
          if (resp) {
            if (resp.response && resp.response.apptDtls && resp.response.apptDtls.length) {
              resp.response.apptDtls.forEach((element) => {
                element.profilePhoto = element.profilePhoto
                  ? element.profilePhoto.replace('/var/telemedicine/', `${environment['baseUrl']}`)
                  : 'assets/images/img_avatar.png';
              });
              this.appointmentList = resp.response.apptDtls;
              this.dataSource = new MatTableDataSource<any>(this.appointmentList);

              this.dataSource.paginator = this.paginator;
            } else if (resp.errors) {
            }
          }
        },
        (error) => {
          this.spinner.hide();
        },
      );
    }
  }

  private getPetientDetails() {
    this.patientService.getPatientDetails(this.userID).subscribe((result) => {
      if (result.response) {
        let res = result.response;
        this.ptUserId = res.ptUserID;
        let setProfileDetails = {
          profile: res.ptProfilePhoto,
          name: res.ptFullName,
          gender: res.gender,
          mobileNo: res.ptMobNo,
          emailId: res.ptEmail,
          dob: this.datePipe.transform(res.dob, 'dd-MM-yyyy'),
          bloodGroup: res.bloodGroup,
          height: res.height,
          weight: res.weight,
          healthNumber: res.healthNumber,
          wallet: res.wallet || 0, // wallet amount for testing
        };
        //for create ndhm health issued
        this.ndhmname = res.ptFullName;
        this.ndhmgender = res.gender;
        this.ndhmmobileNo = res.ptMobNo;
        this.ndhmemailId = res.ptEmail;
        this.ndhmdob = this.datePipe.transform(res.dob, 'dd-MM-yyyy');
        this.ndhmaddress = res.address1;
        this.ndhmHVStatus = res.isHelathIDExists;
        this.ndhmHealthId = res.healthNumber;

        this.dataPassingService.setProfileDetails.next(setProfileDetails);
        this.dataPassingService.setPatientDetails(setProfileDetails);
      }
    });
  }

  startVideoConsultation(appointmentDetails) {
    let isValid = this.consultationService.validateConsultation(
      appointmentDetails.appointmentTime,
      appointmentDetails.appointmentDate,
    );
    if (isValid) {
      this.startBBBVideoConsultation(appointmentDetails.appointmentID);
    } else {
      this.toastMessage.showWarningMsg(ErrorSuccessMessage.VALIDCONSULTATION, 'Warning');
    }
  }

  startVConsultation(apptID) {
    this.consultationService.startVideoConsultation(apptID).subscribe(
      (result) => {
        if (result.response && result.status) {
          window.open(result.response.message);
        } else {
          this.toastMessage.showErrorMsg(result.errors[0].message, 'Error');
        }
      },
      (error) => {
        this.toastMessage.showErrorMsg(error.errors[0].message, 'Error');
      },
    );
  }

  /**
   * Used for Big blue button
   */

  startBBBVideoConsultation(appointmentDetails) {
    let joinpayload = {
      meetingID: appointmentDetails,
      userDisplayName: this.ndhmname,
      password: this.ndhmmobileNo,
      redirect: true,
    };

    this.consultationService.joinMeetingBBB(joinpayload).subscribe(
      (result) => {
        if (result.response && result.status) {
          window.open(result.response.url);
        } else {
          if (result.errors[0].message) {
            this.toastMessage.showErrorMsg(result.errors[0].message, 'Error');
          }
          this.spinner.hide();
        }
      },
      (errors) => {
        console.log('in eror');
        this.toastMessage.showWarningMsg(ErrorSuccessMessage.VALIDCONSULTATIONBB, 'Warning');
      },
    );
  }

  /*
     used to get karza meeting url
  */

  karzaVideoConsultation(appointmentDetails) {
    console.log('enter in ');
    //  let request ={
    //   appointmentId:appointmentDetails.appointmentID,
    //   patientId:this.ptUserId,
    //  }
    let request = '?appointmentId=' + appointmentDetails.appointmentID + '&patientId=' + this.ptUserId;
    console.log(request, 'result');
    this.consultationService.getKarzaMeetingUrl(request).subscribe(
      (result) => {
        if (result['response'] && result['status']) {
          window.open(result['response'].meetingUrl);
        } else {
          this.toastMessage.showErrorMsg(result['errors'].message, 'Error');
        }
      },
      (error) => {
        this.toastMessage.showErrorMsg(error.errors[0].message, 'Error');
      },
    );
  }

  openModel(content, data, index?) {
    if (this.timeInterval) {
      this.resetInterval();
    }
    this.timer = '';
    this.filesArray = [];
    this.uploadedFileNames = [];
    this.modelRef = this.modelService.open(content, { size: 'xl', backdrop: 'static' });
    this.user = data;
    this.dataPassingService.rescheduleApptDetails = { ...data };
    this.upcomingAppointmentCountdown(data.appointmentDate, data.appointmentTime);
    let todaysDate1 = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
    this.Valid = this.consultationService.validateConsultation(data.appointmentTime, data.appointmentDate);
  }

  resetInterval() {
    clearInterval(this.timeInterval);
  }

  /**
   * Used to find the time remaing for upcoming appointment & show remaining time to scribe
   */
  upcomingAppointmentCountdown(date, time) {
    this.timeInterval = null;
    let upcomingAppointmentDateTime = this.consultationService.getNextAppointmentTime(date, time);
    this.timeInterval = setInterval(() => {
      // Today's date & time
      let now = new Date().getTime();
      let differenceStart = upcomingAppointmentDateTime[0] - now;
      let differenceEnd = upcomingAppointmentDateTime[1] - now;
      if (differenceStart < 0 && differenceEnd < 0) {
        clearInterval(this.timeInterval);
        this.timer = 'Appointment is not attended by Patient.';
      } else {
        if (differenceStart > 0) {
          let days = Math.floor(differenceStart / (1000 * 60 * 60 * 24));
          let hours = Math.floor((differenceStart % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
          let minutes = Math.floor((differenceStart % (1000 * 60 * 60)) / (1000 * 60));
          let seconds = Math.floor((differenceStart % (1000 * 60)) / 1000);
          this.timer = days + 'd : ' + hours + 'h : ' + minutes + 'm : ' + seconds + 's';
        } else {
          let hours = Math.floor((differenceEnd % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
          let minutes = Math.floor((differenceEnd % (1000 * 60 * 60)) / (1000 * 60));
          let seconds = Math.floor((differenceEnd % (1000 * 60)) / 1000);
          this.timer = hours + 'h : ' + minutes + 'm : ' + seconds + 's';
          this.timer = 'You can start consultation till  ' + this.timer;
        }
      }
    }, 1000);
  }

  openReasonModel(content, appID) {
    this.reasonGroup.reset();
    this.reasonGroup.get('apptId').patchValue(appID);
    this.reasonModelRef = this.modelService.open(content, { backdrop: 'static' });
  }

  cancelAppointent() {
    if (!this.reasonGroup.valid) {
      return;
    }
    let data = this.reasonGroup.getRawValue();
    this.reasonGroup.get('error').reset();
    delete data.error;
    this.appointmentService.cancelAppointment(data).subscribe(
      (resp: any) => {
        if (resp) {
          if (resp.status && resp.response && resp.response.info) {
            this.toastMessage.showSuccessMsg(resp.response.info, 'Success');
            this.reasonModelRef.close();
            this.modelRef.close();
            this.router.navigate(['/patient-dashboard']);
            this.getpatientAppointmentDetails();
          } else if (resp.errors) {
            this.reasonGroup.get('error').patchValue(resp.errors[0].message);
          }
        }
      },
      (error) => { },
    );
  }

  rescheduleAppointment() {
    this.modelRef.close();
    this.patientService.currentAction({ isRescedule: true, data: this.user });
    // this.dataPassingService.rescheduleApptDetails = this.user;
    this.router.navigate(['/doctor-appointments'], { queryParams: { isReschedule: true } });

    // {relativeTo: this.route}
  }

  getReportTypes() {
    this.patientDocumentService.getDocumentTypes().subscribe(
      (result) => {
        if (result.status && result.response) {
          this.documentNameList = result.response;
        } else if (result.errors) {
          this.toastMessage.showErrorMsg(result.errors[0].message, 'Error');
        }
      },
      (error) => {
        this.toastMessage.showErrorMsg(error['errors'][0].message, 'Error');
      },
    );
  }

  showSelectedFiles(event) {
    let validatedFiles = [];
    let hasSize = event.target.files[0].size <= 2000000 ? true : false; //1000000
    let file = event.target.files.length
      ? event.target.files[0].name
        ? event.target.files[0].name.toLowerCase()
        : null
      : null;
    let fileTypes = ['.pdf', '.doc', '.docx', '.xls', '.xlsx', '.jpg', '.jpeg', '.png'];
    let isFile = fileTypes.some((el) => file.includes(el));

    if (isFile) {
      if (hasSize) {
        const files = event.target.files;
        const file = files[0];
        if (files && file) {
          for (let file of event.target.files) {
            this.uploadedFile = event.target.files[0];
            this.uploadedFileNames.push(file);
            const reader = new FileReader();
            reader.onload = (e) => {
              let file = JSON.stringify(e.target.result);
              let name = `:${event.target.files[0].name}/${this.reportGroup.get('documentName').value};`;
              file = file.replace(/:[a-zA-z]*\/[a-zA-Z-]*;/g, name);
              this.filesArray.push({ files: file });
            };
            reader.readAsDataURL(event.target.files[0]);
          }
        }
      } else {
        if (!hasSize) {
          this.toastMessage.showWarningMsg(ErrorSuccessMessage.IMGSIZE, 'Warning');
          event.target.files = '';
        }
      }
    } else {
      this.toastMessage.showWarningMsg(ErrorSuccessMessage.FILEFORMATE, 'Warning');
      event.target.files = '';
    }
  }

  uploadFile() {
    if (this.filesArray && this.filesArray.length == 0) {
      this.toastMessage.showInfoMsg('Please select document to upload', 'Info');
      return;
    }
    let data = {
      apptID: this.user.appointmentID,
      document: this.filesArray,
    };
    this.spinner.show();
    this.patientService.uploadPatientReport(data).subscribe(
      (result) => {
        this.filesArray = [];
        this.spinner.hide();
        this.reportGroup.reset();
        if (result.status && result.response) {
          this.toastMessage.showSuccessMsg('File Uploaded Successfully', 'Success');
        } else if (result.errors) {
          this.toastMessage.showErrorMsg(result.errors[0].message, 'Error');
        }
      },
      (error) => {
        this.toastMessage.showErrorMsg(error['errors'][0].message, 'Error');
        this.filesArray = [];
      },
    );
  }

  removeFiles(index) {
    this.uploadedFileNames.splice(index, 1);
    this.filesArray.splice(index, 1);
  }

  openDialog(element) {
    if (element.status == 'Completed') {
      if (element.path) {
        let imagePath = this.sanitizer.bypassSecurityTrustResourceUrl(
          'data:' + element.mimetype + ';base64,' + element.report,
        );
        let reportPath = element.path.replace('/var/telemedicine/', `${environment['baseUrl']}`);
        let reportPathUrl = this.sanitizer.bypassSecurityTrustResourceUrl(reportPath);
        window.open(reportPath);
      }
    } else {
      this.toastMessage.showWarningMsg(ErrorSuccessMessage.INVALIDVIEWANDDOWN, 'Warning');
    }
  }

  download(obj) {
    if (obj.status == 'Completed') {
      if (obj.path) {
        let data = {
          api: 'downoad document',
          request: {
            ddtDocsPath: obj.path ? obj.path : null,
            ddtDocsType: 'application/pdf', //obj.mimetype,
            drdUserId: this.authService.getUserId(),
          },
          mimeType: 'application/json',
        };

        this.checkerService.downloadDoc(data).subscribe((resp: any) => {
          if (resp) {
            if (resp.status) {
              const fileName = obj.path.slice(obj.path.lastIndexOf('/') + 1, obj.path.length);
              this.documentService.downloadFileToBrowser(resp.response, resp.mimeType, fileName);
            } else {
              this.toastMessage.showErrorMsg(
                resp.errors.message ? resp.errors.message : 'Internal Server Error',
                'Error',
              );
            }
          }
        });
      }
    } else {
      this.toastMessage.showWarningMsg(ErrorSuccessMessage.INVALIDVIEWANDDOWN, 'Warning');
    }
  }

  gotoBookAppointment() {
    this.router.navigateByUrl('book-appointment-dashboard');
  }

  previousClick() {
    document.getElementById('docContaint').scrollLeft -= 800;
  }

  nextClick() {
    document.getElementById('docContaint').scrollLeft += 800;
  }

  onClickBookAppointment(data) {
    let mappedDocdata = {
      doctorName: data ? (data.docName ? data.docName : null) : null,
      doctorId: data ? (data.docUserID ? data.docUserID : null) : null,
      drProfilePhoto: data ? (data.photopath ? data.photopath : null) : null,
    };
    this.dataPassingService.setDoctorDetails(mappedDocdata);
    this.router.navigate(['doctor-appointments']);
  }

  healthidYes() {
    if (this.ndhmgender == null || 'Male') {
      //window.location.href=environment.ndhmURL+"#/?name="+this.ndhmname+"&gender=M"+"&mobileNoSel="+this.ndhmmobileNo+"&emailId="+this.ndhmemailId+"&dob="+this.ndhmdob+"&hvStatus="+this.ndhmHVStatus+"&txnId="+'';
      window.open(
        this.ndhmLinkURL +
        '#/?name=' +
        this.ndhmname +
        '&gender=M' +
        '&mobileNoSel=' +
        this.ndhmmobileNo +
        '&emailId=' +
        this.ndhmemailId +
        '&dob=' +
        this.ndhmdob +
        '&address=' +
        this.ndhmaddress +
        '&hvStatus=' +
        this.ndhmHVStatus +
        '&txnId=' +
        '',
        '_blank',
      );
    } else {
      //window.location.href=environment.ndhmURL+"#/?name="+this.ndhmname+"&gender=F"+"&mobileNoSel="+this.ndhmmobileNo+"&emailId="+this.ndhmemailId+"&dob="+this.ndhmdob+"&hvStatus="+this.ndhmHVStatus+"&txnId="+'';
      window.open(
        this.ndhmLinkURL +
        '#/?name=' +
        this.ndhmname +
        '&gender=F' +
        '&mobileNoSel=' +
        this.ndhmmobileNo +
        '&emailId=' +
        this.ndhmemailId +
        '&dob=' +
        this.ndhmdob +
        '&address=' +
        this.ndhmaddress +
        '&hvStatus=' +
        this.ndhmHVStatus +
        '&txnId=' +
        '',
        '_blank',
      );
    }
  }
  healthidNo() {
    this.isHealthIdNo = false;
    this.isHealthIdPopUp = false;
  }

  /**
   * Used to create ABHA
   */

  createHealthID() {
    if (this.ndhmgender == null || 'Male') {
      //window.location.href=environment.ndhmURL+"#/otp?name="+this.ndhmname+"&gender=M"+"&mobileNoSel="+this.ndhmmobileNo+"&emailId="+this.ndhmemailId+"&dob="+this.ndhmdob+"&hvStatus="+this.ndhmHVStatus+"&txnId="+'';
      window.open(
        this.ndhmLinkURL +
        '#/verfymobile?name=' +
        btoa(this.ndhmname) +
        '&gender=' +
        btoa('M') +
        '&mobileNoSel=' +
        btoa(this.ndhmmobileNo) +
        '&emailId=' +
        btoa(this.ndhmemailId) +
        '&dob=' +
        btoa(this.ndhmdob) +
        '&address=' +
        btoa(this.ndhmaddress) +
        '&hvStatus=' +
        btoa(this.ndhmHVStatus) +
        '&txnId=' +
        btoa(''),
        '_blank',
      );
    } else {
      //window.location.href=environment.ndhmURL+"#/otp?name="+this.ndhmname+"&gender=F"+"&mobileNoSel="+this.ndhmmobileNo+"&emailId="+this.ndhmemailId+"&dob="+this.ndhmdob+"&hvStatus="+this.ndhmHVStatus+"&txnId="+'';
      window.open(
        this.ndhmLinkURL +
        '#/verfymobile?name=' +
        btoa(this.ndhmname) +
        '&gender=' +
        btoa('F') +
        '&mobileNoSel=' +
        btoa(this.ndhmmobileNo) +
        '&emailId=' +
        btoa(this.ndhmemailId) +
        '&dob=' +
        btoa(this.ndhmdob) +
        '&address=' +
        btoa(this.ndhmaddress) +
        '&hvStatus=' +
        btoa(this.ndhmHVStatus) +
        '&txnId=' +
        btoa(''),
        '_blank',
      );
    }
  }

  /**
   * Used to Link ABHA
   */

  seedHealthID() {
    if (this.ndhmgender == null || 'Male') {
      //window.location.href=environment.ndhmURL+"#/AssistedHealthidCreationMobile?name="+this.ndhmname+"&gender=M"+"&mobileNoSel="+this.ndhmmobileNo+"&emailId="+this.ndhmemailId+"&dob="+this.ndhmdob+"&hvStatus="+this.ndhmHVStatus+"&txnId="+'';
      window.open(
        this.ndhmLinkURL +
        '#/ProfileCreate?name=' +
        btoa(this.ndhmname) +
        '&gender=' +
        btoa('M') +
        '&mobileNoSel=' +
        btoa(this.ndhmmobileNo) +
        '&emailId=' +
        btoa(this.ndhmemailId) +
        '&dob=' +
        btoa(this.ndhmdob) +
        '&hvStatus=' +
        btoa(this.ndhmHVStatus) +
        '&txnId=' +
        btoa('') +
        '&healthId=' +
        btoa(this.ndhmHealthId),
        '_blank',
      );
    } else {
      //window.location.href=environment.ndhmURL+"#/AssistedHealthidCreationMobile?name="+this.ndhmname+"&gender=F"+"&mobileNoSel="+this.ndhmmobileNo+"&emailId="+this.ndhmemailId+"&dob="+this.ndhmdob+"&hvStatus="+this.ndhmHVStatus+"&txnId="+'';
      window.open(
        this.ndhmLinkURL +
        '#/ProfileCreate?name=' +
        btoa(this.ndhmname) +
        '&gender=' +
        btoa('F') +
        '&mobileNoSel=' +
        btoa(this.ndhmmobileNo) +
        '&emailId=' +
        btoa(this.ndhmemailId) +
        '&dob=' +
        btoa(this.ndhmdob) +
        '&hvStatus=' +
        btoa(this.ndhmHVStatus) +
        '&txnId=' +
        btoa('') +
        '&healthId=' +
        btoa(this.ndhmHealthId),
        '_blank',
      );
    }
  }
}
