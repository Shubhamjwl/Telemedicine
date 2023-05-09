import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { DateAdapter, MAT_DATE_FORMATS } from '@angular/material/core';
import { MatTableDataSource } from '@angular/material/table';
import { DdateAdapter, MY_FORMATS } from 'src/app/shared/commonBuildBlocks/derectives/formatDatepickers';
import { MasterTypes } from 'src/app/shared/commonBuildBlocks/enum/master-type.enum';
import { IAssociation } from 'src/app/shared/commonBuildBlocks/interfaces/association.interface';
import { CheckerService } from 'src/app/shared/commonBuildBlocks/services/checkerServices/checker.service';
import { ConsultationService } from 'src/app/shared/commonBuildBlocks/services/ConsultationServices/consultation.service';
import { MatPaginator } from '@angular/material/paginator';
import { IFeaturesCategory } from 'src/app/shared/commonBuildBlocks/interfaces/ifeatures-category';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import {
  AdminConfig,
  AdminConfigCategory,
  AdminConfigRequest,
  FeaturesCategoryEnum,
  UpdateCategoryRequest,
} from 'src/app/shared/commonBuildBlocks';
import { IAPIResponseWrapper } from 'src/app/shared/commonBuildBlocks/interfaces/api-response-wrapper.interface';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { AdminService } from 'src/app/shared/commonBuildBlocks/services/checkerServices/admin.service';

@Component({
  selector: 'app-config-doctor-list',
  templateUrl: './config-doctor-list.component.html',
  styleUrls: ['./config-doctor-list.component.scss'],
  providers: [
    { provide: DateAdapter, useClass: DdateAdapter },
    { provide: MAT_DATE_FORMATS, useValue: MY_FORMATS },
  ],
})
export class ConfigDoctorListComponent implements OnInit {
  displayedColumns: string[] = [
    'doctorName',
    'city',
    'associationName',
    'registeredOn',
    'createLinkABHA',
    'doctorSpeak',
    'appointmentBookLink',
    'preAssessmentLink',
    'doctorWebsiteLink',
    'routineDiagnosticServices',
    'specialist',
    'specializedServices'
  ];
  //displayedColumns: string[] = [];
  form: FormGroup;
  startDate: Date = new Date();
  historyData = new MatTableDataSource<AdminConfig>();
  specializationList = [];
  states = [];
  cities = [];
  associationList: IAssociation[];
  categoryNameList: IFeaturesCategory[];
  FeaturesCategoryEnum = FeaturesCategoryEnum;
  checkedList: boolean;
  disabled: boolean = true;
  disableTextbox =  true;

  constructor(
    private fb: FormBuilder,
    private toastrMessage: ToastMessageService,
    private consultationService: ConsultationService,
    private datePipe: DatePipe,
    private checkerService: CheckerService,
    private adminService: AdminService,
  ) {}

  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngOnInit(): void {
    this.formValidation();
    this.getDoctorSpecialization();
    this.getState();
    this.getAssociationNameList();
    this.getAllFeaturesCategoryList();
  }

  ngAfterViewInit() {
    this.historyData.paginator = this.paginator;
  }

  formValidation() {
    this.form = this.fb.group({
      city: [''],
      state: [''],
      speciality: [''],
      associationName: [''],
      setDateCriteria: [''],
      fromDate: [''],
      toDate: ['',this.validatorforToDate.bind(this)],
      categoryName: [''],
    });

    this.form.get('state').valueChanges.subscribe((res) => {
      if (res) {
        this.getCity(res);
      } else {
        this.cities = [];
      }
    });
    this.form.valueChanges.subscribe((res) => {
      if (this.form.valid) {
        this.displayedColumns = this.buildTableColumns();
        //this.doctorLinkList();
      }
    });

    this.form.get('setDateCriteria').valueChanges.subscribe((res) =>{
      if(res){
        this.disableTextbox = false;
      }
    });

  }

  private buildTableColumns(): string[] {
    const columns = ['doctorName', 'city', 'associationName', 'registeredOn'];
    const categories: string[] = this.form?.controls?.categoryName?.value;
    if (categories) {
      if (categories.includes(FeaturesCategoryEnum.CREATE_LINK_ABHA)) {
        columns.push('createLinkABHA');
      }
      if (categories.includes(FeaturesCategoryEnum.DOCTOR_WEBSITE_LINK)) {
        columns.push('doctorWebsiteLink');
      }
      if (categories.includes(FeaturesCategoryEnum.PRE_ASSESSMENT_LINK)) {
        columns.push('preAssessmentLink');
      }
      if (categories.includes(FeaturesCategoryEnum.APPOINTMENT_BOOK_LINK)) {
        columns.push('appointmentBookLink');
      }
      if (categories.includes(FeaturesCategoryEnum.DOCTOR_SPEAK)) {
        columns.push('doctorSpeakLink');
      }
      if (categories.includes(FeaturesCategoryEnum.ROUTINE_DIAGNOSTIC_SERVICES)) {
        columns.push('routineDiagnosticServices');
      }
      if (categories.includes(FeaturesCategoryEnum.SPECIALIST)) {
        columns.push('specialist');
      }
      if (categories.includes(FeaturesCategoryEnum.SPEIALIZED_SERVICES)) {
        columns.push('specializedServices');
      }
    }
    return columns;
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

  private getAllFeaturesCategoryList() {
    this.checkerService.getAllFeaturesCategoryList().subscribe(
      (result) => {
        if (result.status && result.response) {
          this.categoryNameList = result.response;
        } else if (result.errors) {
          this.toastrMessage.showErrorMsg(result.errors[0]?.message, 'Error');
        }
      },
      (error) => {
        this.toastrMessage.showErrorMsg(error.error?.errors[0]?.message, 'Error');
      },
    );
  }

  private validatorforToDate(control: FormControl) {
    if (!control) return;

    if (!control.parent) return;

    const group = control.parent;
    const fromDate = group.get('fromDate').value;

    if (!control.value && fromDate) {
      return { required: true };
    } else {
      if (fromDate) {
        const fd = new Date(this.datePipe.transform(fromDate, 'yyyy-MM-dd')).getTime();
        const td = new Date(this.datePipe.transform(control.value, 'yyyy-MM-dd')).getTime();
        if (fd > td) {
          return { max: true };
        }
      }
    }
    return null;
  }

  doctorLinkList() {
    const { setDateCriteria, fromDate, toDate, speciality, associationName, city, categoryName } = this.form.value;
    const data = {
      criteriaType: setDateCriteria ? setDateCriteria : null,
      fromDate: this.datePipe.transform(fromDate, 'yyyy-MM-dd'),
      toDate: this.datePipe.transform(toDate, 'yyyy-MM-dd'),
      specialization: speciality?.toUpperCase() || null,//speciality?.toUpperCase() ? speciality?.toUpperCase() : null,
      associationName: associationName ? associationName : null,
      city: city ? city : null,
      categoryType: categoryName ? categoryName : null,
    } as AdminConfigRequest;
    this.adminService.getAdminConfig(data).subscribe(
      (result: IAPIResponseWrapper<AdminConfig[]>) => {
        if (result.response) {
          this.historyData.data = result.response;
          setTimeout(() => {
            this.historyData.paginator = this.paginator;
          });
        } else if (result.errors[0].message) {
          this.historyData.data = [];
          this.toastrMessage.showErrorMsg(result.errors[0].message, 'Error');
        }
      },
      (error) => {
        this.toastrMessage.showErrorMsg(error.error?.errors[0]?.message, 'Record not Found');
      },
    );
  }

  valueChange(event: MatCheckboxChange, config: AdminConfig, categoryVal: FeaturesCategoryEnum) {
    const category: AdminConfigCategory =
      config.category?.find((c) => c.categoryName === categoryVal) || ({} as AdminConfigCategory);
    category.categoryFlag = event.checked;
  }

  onFeatureSelect() {
    const config = this.historyData.data;
    const data = [] as UpdateCategoryRequest[];
    config.forEach((c) => {
      const d = {
        drEmailId: c.drEmail,
        drUserId: c.drUserID,
        category: c.category,
      } as UpdateCategoryRequest;
      data.push(d);
    });
    this.adminService.updateCategoryStatus(data).subscribe({
      next: (res) => {
        if (res.status) {
          this.toastrMessage.showSuccessMsg(res.response?.msg, 'Success');
        } else {
          this.toastrMessage.showErrorMsg(res.errors[0]?.message, 'Error');
        }
      },
      error: (err) => this.toastrMessage.showErrorMsg(err.error?.errors[0]?.message, 'Error'),
    });
  }

  getFeatureSelection(config: AdminConfig, category: FeaturesCategoryEnum): boolean {
    const categories = config.category;
    return categories?.find((c) => c.categoryName === category)?.categoryFlag;
  }

  clear(){
    this.form.reset();
    this.form.markAsPristine();
    this.historyData.data = [];
    this.disableTextbox = true;
  }
}
