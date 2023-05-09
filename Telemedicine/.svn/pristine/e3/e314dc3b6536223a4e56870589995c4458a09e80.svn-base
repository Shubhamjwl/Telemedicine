import { AppConstants } from './../../../app-constants';
import { NotificationTypeEnum } from 'src/app/shared/commonBuildBlocks/model/notification.model';
import { Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MasterTypes } from 'src/app/shared/commonBuildBlocks/enum/master-type.enum';
import { IAssociation } from 'src/app/shared/commonBuildBlocks/interfaces/association.interface';
import { ConsultationService } from 'src/app/shared/commonBuildBlocks/services/ConsultationServices/consultation.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { MatStepper } from '@angular/material/stepper';
import { NotificationService } from 'src/app/shared/commonBuildBlocks/services/notification/notification.service';
import {
  AdminFilterRequest,
  AdminFilterResponse,
  SendNotificationRequest,
} from 'src/app/shared/commonBuildBlocks';
import { AdminService } from 'src/app/shared/commonBuildBlocks/services/checkerServices/admin.service';
import { AdminDoctorList } from 'src/app/shared/commonBuildBlocks/model/admin/admin-doctor-list.model';
import { NgxSpinnerService } from 'ngx-spinner';
import { MatSelectionList, MatSelectionListChange } from '@angular/material/list';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class AdminDashboardComponent implements OnInit {
  @ViewChild('stepper') private myStepper: MatStepper;
  @ViewChild('userSelection') private userSelection: MatSelectionList;
  specializationList = [];
  states = [];
  cities = [];
  associationList: IAssociation[];
  doctorList: AdminDoctorList[] = [];
  selectAllControl = new FormControl();
  userList: AdminFilterResponse[] = [];
  form: FormGroup = this.fb.group({
    state: [''],
    city: [''],
    date: [''],
    doctorIds: [],
    speciality: [''],
    associationName: [''],
  });

  notificationForm = this.fb.group({
    type: ['', Validators.required],
    template: ['', Validators.required],
    amount: [0],
  });

  notificationTypeEnum = NotificationTypeEnum;

  selectedFile: File;
  fileBase64: any;

  get stateControl(): AbstractControl {
    return this.form.get('state');
  }

  get notificationTypeControl(): AbstractControl {
    return this.notificationForm.get('type');
  }


  get amountControl(): AbstractControl {
    return this.notificationForm.get('amount');
  }

  notificationType = [
    {
      property: NotificationTypeEnum.GeneralInformation,
      label: 'General Information',
    },
    {
      property: NotificationTypeEnum.Wallet,
      label: 'Wallet',
    },
  ];

  selectedRadio = AppConstants.patient;

  radioButtonLList = [
    {
      name: 'Patient Selection',
      value: AppConstants.patient,
    },
    {
      name: 'Doctor Selection',
      value: AppConstants.doctor,
    },
  ];

  constructor(
    private fb: FormBuilder,
    private toastrMessage: ToastMessageService,
    private consultationService: ConsultationService,
    private notificationService: NotificationService,
    private adminService: AdminService,
    private spinner: NgxSpinnerService,
  ) { }

  ngOnInit(): void {
    this.initForm();
    this.getDoctorSpecialization();
    this.getState();
    this.getAssociationNameList();
    this.getDoctorList();
  }

  private initForm() {
    const eventEmit = {
      emitEvent: false,
    };
    this.selectAllControl.valueChanges.subscribe((res) => {
      if (res) {
        this.form.disable(eventEmit);
      } else {
        this.form.enable(eventEmit);
        this.userList.length = 0;
      }
    });
    this.stateControl.valueChanges.subscribe((res) => {
      if (res) {
        this.getCity(res);
      } else {
        this.cities = [];
      }
    });
    this.form.valueChanges.subscribe((res) => {
      if (res) {
        this.selectAllControl.disable(eventEmit);
      } else {
        this.selectAllControl.enable(eventEmit);
      }
    });

    this.notificationTypeControl.valueChanges.subscribe(res => {
      if (res === this.notificationTypeEnum.Wallet) {
        this.amountControl.setValidators([Validators.required, Validators.min(1)]);
      } else {
        this.amountControl.clearValidators();
      }
      this.amountControl.updateValueAndValidity();
    })
  }

  private getState() {
    this.consultationService.getStateList().subscribe(
      (result) => {
        if (result.status) {
          this.states = result.response;
        } else if (result.errors) {
          this.toastrMessage.showErrorMsg(result.errors[0]?.message, 'Error');
        }
      },
      (error) => {
        this.toastrMessage.showErrorMsg(error.error?.errors[0]?.message, 'Error');
      },
    );
  }

  private getDoctorList() {
    this.adminService.getAllDoctorList().subscribe({
      next: (res) => {
        if (res.status) {
          this.doctorList = res.response;
        }
      },
    });
  }

  private getCity(value: string) {
    this.consultationService.getCityList(value).subscribe(
      (result) => {
        if (result.status && result.response) {
          this.cities = result.response;
        } else if (result.errors) {
          this.cities = [];
          this.toastrMessage.showErrorMsg(result.errors[0]?.message, 'Error');
        }
      },
      (error) => {
        this.cities = [];
        this.toastrMessage.showErrorMsg(error.error?.errors[0]?.message, 'Error');
      },
    );
  }

  private getDoctorSpecialization() {
    this.consultationService.getMasterDetailsListByMasterName(MasterTypes.SPECIALIZATION).subscribe(
      (result) => {
        if (result.status && result.response) {
          this.specializationList = result.response;
        } else if (result.errors) {
          this.toastrMessage.showErrorMsg(result.errors[0]?.message, 'Error');
        }
      },
      (error) => {
        this.toastrMessage.showErrorMsg(error.error?.errors[0]?.message, 'Error');
      },
    );
  }

  private getAssociationNameList() {
    this.consultationService.getAssociationNameList().subscribe(
      (result) => {
        if (result.status && result.response) {
          this.associationList = result.response;
        } else if (result.errors) {
          this.toastrMessage.showErrorMsg(result.errors[0]?.message, 'Error');
        }
      },
      (error) => {
        this.toastrMessage.showErrorMsg(error.error?.message, 'Error');
      },
    );
  }

  userTypeChange(event: string) {
    this.clear();
    // show wallet in case of patient only
    if (event === AppConstants.doctor) {
      this.notificationType = this.notificationType.filter((n) => n.property !== NotificationTypeEnum.Wallet);
    } else {
      const hasWalletNotification = this.notificationType.find((n) => n.property === NotificationTypeEnum.Wallet);
      if (!hasWalletNotification) {
        this.notificationType.push({
          property: NotificationTypeEnum.Wallet,
          label: 'Wallet',
        });
      }
    }
  }

  clear() {
    this.form.reset();
    this.form.markAsPristine();
    this.selectAllControl.setValue(false);
    this.selectAllControl.enable({
      emitEvent: false,
    });
    this.clearList();
  }

  private clearList() {
    this.userList.length = 0;
  }

  apply() {
    const { state, city, date, doctorIds, speciality, associationName } = this.form.value;
    const filter = {
      selectionType: this.selectedRadio,
      associationName,
      city,
      regDate: date,
      docUserIdList: doctorIds,
      sendToAll: this.selectAllControl.value || false,
      specialization: speciality,
    } as AdminFilterRequest;
    this.spinner.show();
    this.adminService.getUsersByFilter(filter).subscribe({
      next: (res) => {
        this.spinner.hide();
        if (res.status) {
          this.userList = res.response;
          if (this.selectAllControl.value) {
            setTimeout(() => {
              this.userSelection.selectAll();
              this.userSelection.setDisabledState(true);
            });
          }
        } else {
          this.toastrMessage.showErrorMsg(res.errors[0]?.message, 'Error');
          this.clearList();
        }
      },
      error: (err) => {
        this.spinner.hide();
        this.toastrMessage.showErrorMsg(err.error?.errors?.message, 'Error');
        this.clearList();
      },
    });
  }

  goBack() {
    this.myStepper.previous();
  }

  goForward() {
    this.myStepper.next();
  }

  submit() {
    const { type, template, amount } = this.notificationForm.value;
    const selectedUserIds = this.userSelection?.selectedOptions?.selected.map((s) => s.value);
    const request = {
      notificationType: type,
      template,
      attachmentFile: this.fileBase64,
      attachmentFileName: this.selectedFile?.name,
      userIds: selectedUserIds,
      userType: this.selectedRadio,
      amount: type === NotificationTypeEnum.Wallet ? amount : 0,
    } as SendNotificationRequest;
    this.spinner.show();
    this.notificationService.sendNotification(request).subscribe({
      next: (res) => {
        this.spinner.hide();
        if (res.status) {
          this.notificationForm.reset();
          this.selectedFile = null;
          this.myStepper.previous();
          this.form.reset();
          this.form.markAsPristine();
          this.clearList();
          this.toastrMessage.showSuccessMsg(res.response, 'Success');
        } else {
          this.toastrMessage.showErrorMsg(res?.errors?.message, 'Error');
        }
      },
      error: (err) => {
        this.spinner.hide();
        this.toastrMessage.showErrorMsg(err.error?.errors?.message, 'Error');
      },
    });
  }

  onFileSelected(event: any): void {
    this.selectedFile = event?.target?.files[0] ?? null;
    if (this.selectedFile) {
      const reader = new FileReader();
      reader.onload = (e) => {
        this.fileBase64 = e.target.result;
      };
      reader.readAsDataURL(event.target.files[0]);
    }
  }

  removeFile() {
    this.selectedFile = null;
    this.fileBase64 = null;
  }

  selectionChange(event: MatSelectionListChange) {
    if (event.option.value === 'ALL') {
      if (event.option.selected) {
        this.userSelection.selectAll();
      } else {
        this.userSelection.deselectAll();
      }
    } else {
      const allSelected = this.userSelection.options.find((o) => o.value === 'ALL');
      if (allSelected) {
        allSelected._setSelected(false);
      }
    }
  }
}
