import { Component, ElementRef, OnChanges, OnInit, SimpleChange, SimpleChanges, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { WebcamImage, WebcamInitError } from 'ngx-webcam';
import { Observable, Subject } from 'rxjs';
import { ErrorSuccessMessage } from 'src/app/shared/commonBuildBlocks/enum/error-success-message.enum';
import { DataPassingService } from 'src/app/shared/commonBuildBlocks/services/dataPassingServices/dataPassing.service';
import { DocumentService } from 'src/app/shared/commonBuildBlocks/services/documentServices/document.service';
import { PatientService } from 'src/app/shared/commonBuildBlocks/services/patientServices/patient.service';
import { CustomValidators } from 'src/app/shared/commonBuildBlocks/services/Validators/customValidator.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-patient-registration-by-doc-sc',
  templateUrl: './patient-registration-by-doc-sc.component.html',
  styleUrls: ['./patient-registration-by-doc-sc.component.scss']
})
export class PatientRegistrationByDocSCComponent implements OnInit{

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private spinner: NgxSpinnerService,
    private toastrMessage: ToastMessageService,
    private patientService: PatientService,
    private datapassingService: DataPassingService,
    private documentService: DocumentService
  ) { }

  /**
   * Used to store SignIn Form.
   */
  form: FormGroup ;

  isSwitcheChecked = false;
  profilePhotoBase64;
  selectedXlsxFile;
  uploadedFiles = [];
  xlsxFIle;
  // public webcamImage: WebcamImage = null; 
  private trigger: Subject<void> = new Subject<void>();
  uploadedImage = 'assets/images/img_avatar.png';
  uploadedFile;
  filesArray = []
  selectedFile: string;
  selectedFileName: string;
  @ViewChild('inputFile') xlsxInputFIle: ElementRef;
  /**
   * used to show uploaded files names
   */
  uploadedFileNames: any = [];

  patientForm = this.fb.group({
    profilePhoto: [''],
    patientName: ['', [Validators.required, CustomValidators.fullNameValidator(30)]],
    mobileNo: ['', [Validators.required, CustomValidators.mobileValidator]],
    email : ['', [Validators.email]],
    dob : [''],
  });
  selectedRadio: string = 'single'
  radioButtonLList = [
    {
      name : 'Single',
      value: 'single'
    },
    {
      name : 'File Upload',
      value: 'multiple'
    }
  ]
  /**
 * personal details of patient
 */
personalDataList = [
  {
    "stepName":"Full Name",
    "stepValue":"",
  },
  {
    "stepName":"Mobile No",
    "stepValue":"",
  },
  {
    "stepName":"Email Id",
    "stepValue":"",
  },
  {
    "stepName":"DOB",
    "stepValue":"",
  }
  
]; 
  ngOnInit(): void {
    this.uploadedFileNames = [];
    this.form = this.patientForm;
    this.setPersonalDetails();
  }

  onClickSwitch() {
    this.isSwitcheChecked = !this.isSwitcheChecked; 
  }

  triggerSnapshot(): void { 
    this.isSwitcheChecked = !this.isSwitcheChecked; 
    this.trigger.next(); 
  } 

  handleImage(webcamImage: WebcamImage): void { 
  this.profilePhotoBase64 = webcamImage['_imageAsDataUrl']
  this.uploadedImage = this.profilePhotoBase64;
  }

  public handleInitError(error: WebcamInitError): void {
    if (error.mediaStreamError && error.mediaStreamError.name === "NotAllowedError") {
      this.toastrMessage.showWarningMsg("Camera access was not allowed by user!", "Information");
    }
  }

  public get triggerObservable(): Observable<void> {
  return this.trigger.asObservable();
  }

  setPersonalDetails() {
    this.form.valueChanges.subscribe(value => {

        this.personalDataList[0]['stepValue'] = value.patientName;

        this.personalDataList[1]['stepValue'] = value.mobileNo;

        this.personalDataList[2]['stepValue'] = value.email;

        this.personalDataList[3]['stepValue'] = value.dob;

    });
  }
     /**
   * Used to set selected profile photo
   */
  uploadProfilePhoto(event) {
    let hasImg = event.target.files[0].type.includes('image') ? true : false;
    let hasSize = event.target.files[0].size <= 1000000 ? true : false; //1000000
    if(hasImg && hasSize) {
      const files = event.target.files;
      const file = files[0];
      if (files && file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.profilePhotoBase64 = e.target.result;
        };
        reader.readAsDataURL(event.target.files[0]);
      }
      document
        .getElementById('ProfilePhoto')
        .setAttribute('src', window.URL.createObjectURL(event.target.files[0]));
    }else{
      if(!hasImg){
        this.toastrMessage.showWarningMsg(ErrorSuccessMessage.IMGFORMATE, 'Warning');
        event.target.files = '';
      }
      if(!hasSize){
        this.toastrMessage.showWarningMsg(ErrorSuccessMessage.IMGSIZE, 'Warning');
        event.target.files = '';
      }
    }
  }

  /**
   * Used for upload profile photo
   */
  browse() {
    document.getElementById("browse").click();
  }
  /**
   * Used to show Selected Files
   */
  showSelectedFiles(event) {
    let file = event.target.files.length ? event.target.files[0].name ? event.target.files[0].name.toLowerCase() : null : null
    let fileTypes = ['.xlsx']
    let isFile = fileTypes.some(el => file.includes(el));
    let hasSize = event.target.files[0].size <= 2000000 ? true : false; //1000000
    let fileName =  event.target.files[0].name
    if(fileName === 'sample.xlsx'){
      if(isFile){
        if(hasSize){
          const files = event.target.files;
          const file = files[0];
          if (files && file) {
            for (let file of event.target.files) {
              this.uploadedFile = event.target.files[0];
              this.uploadedFileNames.push(file);
              const reader = new FileReader();
              reader.onload = (e) => {
                let file = JSON.stringify(e.target.result);
                this.selectedFileName = fileName;
                this.selectedFile = file;
              };
              reader.readAsDataURL(event.target.files[0]);
            }
          }
        }else{
          if(!hasSize){
            this.toastrMessage.showWarningMsg(ErrorSuccessMessage.FILESIZE, 'Warning');
            event.target.files = ''; 
          }
        }
      }else{
        this.toastrMessage.showWarningMsg(ErrorSuccessMessage.XLFILEFORMATE, 'Warning');
      }
    }else {
      this.toastrMessage.showWarningMsg(ErrorSuccessMessage.GIVENXLSXFILE, 'Warning');
    }
  }

  selectFiles(event?) {
    if(event) {
      let fileName = event.target.files[0].name
      this.selectedFileName = fileName
      if(fileName === 'sample.xlsx'){
        this.selectedXlsxFile = event.target.files[0]
          const reader = new FileReader();
          reader.onload = (e) => {
            let file = JSON.stringify(e.target.result);
            this.selectedFile = file;
          };
          reader.readAsDataURL(event.target.files[0]);
      }else {
        this.xlsxInputFIle.nativeElement.value = "";
        this.toastrMessage.showWarningMsg(ErrorSuccessMessage.GIVENXLSXFILE, 'Warning');
      }
    }else {
      this.xlsxInputFIle.nativeElement.value = "";
      this.toastrMessage.showWarningMsg(ErrorSuccessMessage.XLFILEFORMATE, 'Warning');
    }
  }

  uploadFiles() {
    if(this.selectedXlsxFile) {
        this.uploadedFiles = [];
        this.uploadedFiles.push(this.selectedXlsxFile)
        this.xlsxInputFIle.nativeElement.value = "";
        this.selectedXlsxFile = undefined;
    }else {
      this.xlsxInputFIle.nativeElement.value = "";
      this.toastrMessage.showWarningMsg(ErrorSuccessMessage.XLFILEFORMATE, 'Warning');
    }
  }

  removeFile(index) {
    this.uploadedFiles.splice(index,1);
  }
  /**
   * Used for Upload Documents
   */
  browseFile() {
    // let file = this.form.get('documentType').value
    // if(file){
      document.getElementById("browseFile").click();
    // }else{
    //   this.toastrMessage.showWarningMsg(ErrorSuccessMessage.SELECTDOCTYPE, 'Warning');
    // }
  }
    /**
   * Delete selected files
   */
  removeFiles(index) {
    this.uploadedFileNames.splice(index, 1);
    this.filesArray.splice(index, 1);
  }

  download() {
    let fileName = 'Sample.xlsx';
    let checkFileType = fileName.split('.').pop();
    var fileType;
   
    if (checkFileType == "xlsx") {
        fileType = "application/vnd.openxmlformats officedocument.spreadsheetml.sheet";
        let file = `${environment.baseUrl}/assets/files/sample.xlsx`;
       // let file = "assets/files/sample.xlsx";
        window.open(file);

				// this.documentService.downloadFileAndView(file, fileType);
      // const blob = new Blob([data], { type: fileType });
      // const url= window.URL.createObjectURL(blob);
    }
  }

  convertAndDownloadFile(fileData: string, fileName: string) {
   //const file = new Blob([fileData], { type: 'application/vnd.ms-excel' });
   const file = this.documentService.b64toBlob(fileData, "application/vnd.ms-excel");
    const fileURL = URL.createObjectURL(file);
    const fileLink = document.createElement('a');
    fileLink.href = fileURL;
    fileLink.download = fileName;
    fileLink.click();
    //window.open(fileURL);
  }

  onClickSubmit() {
    this.spinner.show();
    let {profilePhoto, patientName, mobileNo, email,dob} = this.form.value;
    if(this.selectedRadio === 'single') {
      if(patientName && mobileNo) {
        let payload = {
          patientName: patientName ? patientName : null,
          doctorUserID: sessionStorage.getItem('USER_ID') ? sessionStorage.getItem('USER_ID') : null, 
          ptMobileNo: mobileNo ? mobileNo : null,
          ptProfilePhoto: this.profilePhotoBase64 ? this.profilePhotoBase64 : null,
          ptEmailID : email ? email : null,
          ptDateOfBirth : dob ? dob : null,
        }
        this.patientService.patientRegistartionByScribeOrDoctor(payload).subscribe(result => {  this.spinner.hide();
          if (result.status) {
            this.router.navigate(['appointments']); 
            this.toastrMessage.showSuccessMsg(result.response.message, "Success");
           
          } else {
            if (result.errors) {
              this.toastrMessage.showErrorMsg( result.errors[0].message, 'Error');
            }
          }
        },error => {
          this.spinner.hide();
          this.toastrMessage.showErrorMsg(error.errors[0].message, 'Error');
        })
      }else {
        this.spinner.hide();
        this.toastrMessage.showWarningMsg('Please select all required fields', 'Warning');
      }
    } else {
      if(this.uploadedFiles && this.uploadedFiles.length) { 
        let payload = {
          doctorUserID: sessionStorage.getItem('USER_ID') ? sessionStorage.getItem('USER_ID') : null, 
          excelFileOfBulkPatientDtls: this.selectedFile ? this.selectedFile : null,
          fileName : this.selectedFileName ? this.selectedFileName : null
        }
        this.patientService.patientRegistrationByDocumentUpload(payload).subscribe(result => {  
          this.spinner.hide();
          if (result.status) {
            this.convertAndDownloadFile(result.response.fileData,result.response.fileName);
            this.router.navigate(['appointments']); 
            this.toastrMessage.showSuccessMsg(result.response.message, "Success");
          } else {
            if (result.errors) {
              this.toastrMessage.showErrorMsg( result.errors.message, 'Patient registartion failed by doctor.');
            }
          }
        },error => {
          this.spinner.hide();
          this.toastrMessage.showErrorMsg(error.errors[0].message, 'Error');
        })
      }else{
        this.spinner.hide();
        this.toastrMessage.showWarningMsg('Please select file', 'Warning');
      }
    }
  }

  reset() {
    this.uploadedFiles = [];
    this.form.reset();
    this.form.markAsPristine();
    document
    .getElementById('ProfilePhoto')
    .setAttribute('src', this.uploadedImage);
    // this.xlsxInputFIle.nativeElement.reset();

    // this.refreshCpacha();
  }

  onClickCancel(){
    // if(this.user === 'scribe'){
      this.router.navigate(['appointments']); 
    // }else {
    //   this.router.navigate(['']);
    // }
  }

}
