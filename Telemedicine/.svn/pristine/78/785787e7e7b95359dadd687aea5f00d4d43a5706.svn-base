import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators, FormControlName } from '@angular/forms';
import { DateAdapter, MAT_DATE_FORMATS } from '@angular/material/core';
import { WebcamImage, WebcamInitError } from 'ngx-webcam';
import { Observable, Subject } from 'rxjs';
import { Router, ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { DdateAdapter, MY_FORMATS } from 'src/app/shared/commonBuildBlocks/derectives/formatDatepickers';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { DoctorService } from 'src/app/shared/commonBuildBlocks/services/doctorServices/doctor.service';
import { ScribeService } from 'src/app/shared/commonBuildBlocks/services/scribeServices/scribe.service';
import { AuthService } from 'src/app/shared/commonBuildBlocks/services/authSercies/auth.service';
import { ConstantsService } from 'src/app/shared/commonBuildBlocks/services/ConstantsServices/constants.service';
import { CheckerService } from 'src/app/shared/commonBuildBlocks/services/checkerServices/checker.service';
import { DocumentService } from 'src/app/shared/commonBuildBlocks/services/documentServices/document.service';
import { PatientService } from 'src/app/shared/commonBuildBlocks/services/patientServices/patient.service';
import { ConsultationService } from 'src/app/shared/commonBuildBlocks/services/ConsultationServices/consultation.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AllowSpecialCharacterValidator } from 'src/app/shared/commonBuildBlocks/validators/allow-special-character.validator';
import { MobileNoValidator } from 'src/app/shared/commonBuildBlocks/validators/mobile-no.validator';
import { DocProfileDetails } from 'src/app/shared/commonBuildBlocks/model/doctor.model';
import { MasterTypes } from 'src/app/shared/commonBuildBlocks/enum/master-type.enum';

@Component({
  selector: 'app-de-activate-profile',
  templateUrl: './de-activate-profile.component.html',
  styleUrls: ['./de-activate-profile.component.scss'],
  providers: [
    { provide: DateAdapter, useClass: DdateAdapter },
    { provide: MAT_DATE_FORMATS, useValue: MY_FORMATS }
  ],
})
export class DeActivateProfileComponent implements OnInit {

  form: FormGroup;
  progress: number = 90;
  cities: [];
  states:[];
  specializationList:any;
  userEnteredOtp:any;
  modalref:any

  profilePhotoBase64: any;
  isSwitcheChecked = false;
  uploadedImage = 'assets/images/img_avatar.png';
  public webcamImage: WebcamImage = null;
  private trigger: Subject<void> = new Subject<void>();  
  disableDob: boolean = false;
  disableBloodGroup: boolean = false;
  disableHeight: boolean = false;
  disableWeight: boolean = false;
  maxDate = new Date();

  displayedColumns:any;
  constructor(
    private fb: FormBuilder,
    private toastrMessage: ToastMessageService,
    private doctorService: DoctorService,
    private scribeServiceInstance: ScribeService,
    public authService: AuthService,
  	public constantsService: ConstantsService,
    public checkerService: CheckerService,
    public documentService: DocumentService,
    private patientServiceInstance: PatientService,
    private consultationService: ConsultationService,
    private spinner: NgxSpinnerService,
  	public modelService : NgbModal,
    private router: Router,
    private routes: ActivatedRoute,

  ) {
	this.displayedColumns = ['Document Name', 'Status', 'Visibility', 'Upgrade'];
   }

  ngOnInit(): void {
    this.createForm();
    this.getDoctorSpecialization();
    this.getState();
    this.getCity();
    this.getDoctorData();
  }    
  createForm() {
    this.form = this.fb.group({
		profilePhoto: [''],
		dmdUserId:[{value:'',disabled:true}],
		fullName: [{value:'',disabled:true}, AllowSpecialCharacterValidator.strong],
		mobileNo: [{value:'',disabled:true}, MobileNoValidator.strong],
		email: [{value:'',disabled:true}, Validators.email],
		gender: [{value:'',disabled:true}],
		dob: [{value:'',disabled:true}],
		// bloodGroup: [{value:'',disabled:true}],
		height: [{value:'',disabled:true}],
		weight: [{value:'',disabled:true}],
		state: [{value:'',disabled:true}],
		city: [{value:'',disabled:true}],
		smcNo: [{value:'',disabled:true}],
		micNo: [{value:'',disabled:true}],
		consultationFee: [{value:'',disabled:true}],
		speciality: [{value:'',disabled:true}],
		address1: [{value:'',disabled:true}],
		address2: [{value:'',disabled:true}],
		address3: [{value:'',disabled:true}],

    });
  }
  /* Used to set selected profile photo*/
  uploadProfilePhoto(event) {
    const files = event.target.files;
    const file = files[0];
    if(files && file) {
      const reader = new FileReader();
      reader.onload = (e) => { 
        this.profilePhotoBase64 = e.target.result;
      }
      reader.readAsDataURL(event.target.files[0]);
    }
    document.getElementById('ProfilePhoto').setAttribute("src", window.URL.createObjectURL(event.target.files[0]));
  }
  
  /** Used for upload profile photo*/
  browse() {
    document.getElementById("browse").click();
  }
  
  onClickSwitch() {
    this.isSwitcheChecked = !this.isSwitcheChecked; 
  }

  public handleInitError(error: WebcamInitError): void {
    if (error.mediaStreamError && error.mediaStreamError.name === "NotAllowedError") {
      this.toastrMessage.showWarningMsg("Camera access was not allowed by user!", "Information");
    }
  }
  
  triggerSnapshot(): void { 
    this.isSwitcheChecked = !this.isSwitcheChecked; 
    this.trigger.next(); 
  } 

  handleImage(webcamImage: WebcamImage): void { 
  this.profilePhotoBase64 = webcamImage['_imageAsDataUrl']
  this.uploadedImage = this.profilePhotoBase64;
  } 
  
  public get triggerObservable(): Observable<void> { 
  return this.trigger.asObservable(); 
  } 

	getDoctorData() {
    this.spinner.show();
		this.doctorService.getDoctorProfile(sessionStorage.getItem('USER_ID')).subscribe((data) => {
      		this.spinner.hide();
			if(data && data.response){
				let docDetails = new DocProfileDetails(data.response);
				this.form.patchValue(docDetails);
			}

		}, error => {

		});
	}
  	back(){
      this.router.navigate(['/appointments']);
	}
	cancel(){
		this.modalref.close();
	}
	deRegisterProfile(otp){
		let data = {
			docName: this.authService.getUserId(),
			action: 'deregister'
		}
      this.doctorService.deRegisterDoctor(data).subscribe((resp:any)=> {
        if (resp) {
          this.toastrMessage.showSuccessMsg(resp.response.info, "Congratulation");
          this.modalref = this.modelService.open(otp);
        } else if (resp.errors) {
          this.toastrMessage.showInfoMsg(resp.errors[0].message, "Information");
        }
      })
  }
  /**
 * Used to get specialization list 
 */
getCity(){
  this.consultationService.getCityList('MAHARASHTRA').subscribe(result => {
    if(result['status'] && result['response']){
      this.cities = result['response']
    }else if(result['errors']) {
      this.toastrMessage.showErrorMsg( result['errors'][0].message, 'Error');
    }
  },error => {
    this.toastrMessage.showErrorMsg(error['errors'][0].message, 'Error');
  })
}

getDoctorSpecialization() {
  
  this.consultationService.getMasterDetailsListByMasterName(MasterTypes.SPECIALIZATION).subscribe(result => {
    if(result.status && result.response){
      this.specializationList = result.response
    }else if(result.errors) {
      this.toastrMessage.showErrorMsg( result.errors[0].message, 'Error');
    }
  },error => {
    this.toastrMessage.showErrorMsg(error['errors'][0].message, 'Error');
  })
}
getState(){
  this.consultationService.getStateList().subscribe(result => {
    if(result['status'] && result['response']){
      this.states = result['response']
    }else if(result['errors']) {
      this.toastrMessage.showErrorMsg( result['errors'][0].message, 'Error');
    }
  },error => {
    this.toastrMessage.showErrorMsg(error['errors'][0].message, 'Error');
  })
}
	submit(otp){
		let postdata = {
			docName: this.authService.getUserId(),
			otp: otp,
			userRole: this.authService.getUserRole() ? this.authService.getUserRole().toLowerCase() : ''
		}

			this.doctorService.VerifyDoctor(postdata).subscribe((result:any) => {
				if (result && result.status && result.response != null) {
					this.toastrMessage.showSuccessMsg(result.response.info, "Congratulation");
					this.modalref.close();
					this.authService.logout();
					// this.router.navigate(['']);
          this.router.navigate(['sign-in']);
				} else if (result.errors) {
					this.toastrMessage.showInfoMsg(result.errors[0].message, "Information");
				}
			}, error => {
				this.toastrMessage.showErrorMsg("Error occured at the backend!", "Error");
			});
	}
	resendOtp(){
		let userId = this.form.get('dmdUserId').value;
		let mobileNo = this.form.get('mobileNo').value;
		let emailID = this.form.get('email').value;
		this.authService.generateOtp(userId,mobileNo,emailID).subscribe(result => {

		})
	}
}



