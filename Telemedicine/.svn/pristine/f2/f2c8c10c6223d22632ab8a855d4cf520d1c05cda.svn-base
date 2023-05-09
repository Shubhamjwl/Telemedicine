import { TmBookAppointmentComponent } from './../doctor/appointment/tm-book-appointment/tm-book-appointment.component';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { SharedModulesRoutingModule } from './shared-modules-routing.module';
import { BookDoctorsAppointmentComponent } from './components/book-doctor-appointment/book-doctor-appointment.component';
import { OtpModalComponent } from './modals/otp-modal/otp-modal.component';
import { CongratulationsModalComponent } from './modals/congratulations-modal/congratulations-modal.component';
import { CompletedAppointmentsComponent } from './components/completed-appointments/completed-appointments.component';
import { AppointmentHistoryComponent } from './components/appointment-history/appointment-history.component';
import { LangModule } from 'src/app/lang/lang.module';
import { BookAppointmentModalComponent } from './modals/book-appointment-modal/book-appointment-modal.component';
import { ViewPatientListComponent } from './components/view-patient-list/view-patient-list.component';
import { UpdateProfileComponent } from './components/update-profile/update-profile.component';
import { DeActivateProfileComponent } from './components/de-activate-profile/de-activate-profile.component';
import { HistoricalCompletedAppointmentsModalComponent } from './modals/historical-completed-appointments-modal/historical-completed-appointments-modal.component';
import { UploadHeaderFooterTemplateComponent } from './components/upload-header-footer-template/upload-header-footer-template.component';
import { BookAppointmentDashboardComponent } from './components/book-appointment-dashboard/book-appointment-dashboard.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { CustomValidators } from 'src/app/shared/commonBuildBlocks/services/Validators/customValidator.service';
import { PaymentOptionModalComponent } from './modals/payment-option-modal/payment-option-modal.component';
import { UploadedDocumentsReportComponent } from './components/uploaded-documents-report/uploaded-documents-report.component';
import { PatientSubmittedFormComponent } from './components/patient-submitted-form/patient-submitted-form.component';
import { ViewReportModalComponent } from './modals/view-report-modal/view-report-modal.component';
import { RazorpayPaymentGatewayComponent } from './components/payment/razorpay-payment-gateway/razorpay-payment-gateway.component';
import { CreateSlotComponent } from './modals//create-slot/create-slot.component';
import { OrderHistoryComponent } from './components/order-history/order-history.component';
import { DeleteAppointmentModalComponent } from './modals/delete-appointment-modal/delete-appointment-modal/delete-appointment-modal.component';
import { TCbookAppointmentModalComponent } from './modals/tcbook-appointment-modal/tcbook-appointment-modal.component';
import { TcdoctorRegisterModalComponent } from './modals/tcdoctor-register-modal/tcdoctor-register-modal.component';
import { FooterComponent } from './components/footer/footer.component';
import { PrescribeServiceComponent } from './components/prescribe-service/prescribe-service.component';
import { CanceledAppointmentComponent } from './components/canceled-appointment/canceled-appointment.component';
import { RescheduleBookAppointmentComponent } from './components/reschedule-book-appointment/reschedule-book-appointment.component';
import { DoctorRescheduleBookAppointmentComponent } from './components/doctor-reschedule-book-appointment/doctor-reschedule-book-appointment.component';
import { FormatTimePipe } from './components/update-profile/format-time.pipe';
import { RichTextEditorComponent } from './components/rich-text-editor/rich-text-editor.component';
import { PtDocumentManagementComponent } from '../patient/components/pt-document-management/pt-document-management.component';

@NgModule({
  declarations: [
    BookAppointmentModalComponent,
    TmBookAppointmentComponent,
    BookAppointmentDashboardComponent,
    UploadedDocumentsReportComponent,
    PatientSubmittedFormComponent,
    ViewReportModalComponent,
    CongratulationsModalComponent,
    OtpModalComponent,
    BookDoctorsAppointmentComponent,
    AppointmentHistoryComponent,
    CompletedAppointmentsComponent,
    ViewPatientListComponent,
    UpdateProfileComponent,
    DeActivateProfileComponent,
    HistoricalCompletedAppointmentsModalComponent,
    UploadHeaderFooterTemplateComponent,
    RazorpayPaymentGatewayComponent,
    PaymentOptionModalComponent,
    CreateSlotComponent,
    OrderHistoryComponent,
    DeleteAppointmentModalComponent,
    TCbookAppointmentModalComponent,
    TcdoctorRegisterModalComponent,
    FooterComponent,
    PrescribeServiceComponent,
    CanceledAppointmentComponent,
    RescheduleBookAppointmentComponent,
    DoctorRescheduleBookAppointmentComponent,
    FormatTimePipe,
    RichTextEditorComponent,
    PtDocumentManagementComponent
  ],
  imports: [CommonModule, LangModule, SharedModulesRoutingModule, SharedModule],
  exports: [
    UploadedDocumentsReportComponent,
    PatientSubmittedFormComponent,
    ViewReportModalComponent,
    FooterComponent,
    RichTextEditorComponent,
    SharedModule,
  ],
  providers: [DatePipe, CustomValidators],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class SharedModulesModule { }
