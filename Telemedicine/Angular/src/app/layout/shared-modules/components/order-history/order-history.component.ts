import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { OrderHistoryService } from 'src/app/shared/commonBuildBlocks/services/orderHistoryServices/order-history.service'
import { NgxSpinnerService } from 'ngx-spinner';
import { OrderHistory } from 'src/app/shared/commonBuildBlocks/interfaces/order-history.interface';
import { AuthService } from 'src/app/shared/commonBuildBlocks/services/authSercies/auth.service';
import { CheckerService } from 'src/app/shared/commonBuildBlocks/services/checkerServices/checker.service';
import { DocumentService } from 'src/app/shared/commonBuildBlocks/services/documentServices/document.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { environment } from 'src/environments/environment';
import { CdkTextareaAutosize } from '@angular/cdk/text-field';

@Component({
  selector: 'app-order-history',
  templateUrl: './order-history.component.html',
  styleUrls: ['./order-history.component.scss']
})
export class OrderHistoryComponent implements OnInit {
  orderHistoryFormGroup: FormGroup;
  displayedColumns: string[] = ['orderId', 'orderDate', 'patientName', 'specialistDoctorName', 'productName', 'productPrice', 'view/download','prescriptionText'];
  
  /**
   * Used to check logged in user role
   */
   userRole = sessionStorage.getItem('ROLE');

  
  historyData = new MatTableDataSource<OrderHistory>();
  
  constructor(
    private fb: FormBuilder,
    private OrderHistoryService: OrderHistoryService,
    private authService : AuthService,
    private checkerService: CheckerService,
    private documentService: DocumentService,
    private toastMessage : ToastMessageService,
    private spinner: NgxSpinnerService,

  ) {

  }
  @ViewChild('autosize') autosize: CdkTextareaAutosize;

  @ViewChild(MatPaginator) paginator: MatPaginator;

 
  ngOnInit() {
    this.orderHistoryFormGroup = this.fb.group({
      ptName: [''],
      orderId: [''],
      orderDate: [''],
    })
  }

  ngAfterViewInit() {
    this.historyData.paginator = this.paginator;
  }

  orderHistorySearch() {
    var ptName = this.orderHistoryFormGroup.get('ptName')?.value || null
    var orderId = this.orderHistoryFormGroup.get('orderId')?.value || null
    var orderDate = this.orderHistoryFormGroup.get('orderDate')?.value || null
    if(ptName || orderId || orderDate){
    var data = {
      request: {
        patientName: ptName,
        orderId: orderId,
        orderDate: orderDate
      }
    };
    this.OrderHistoryService.ordersearch(data).subscribe((result: any) => {
      if (result.response) {
        this.historyData = new MatTableDataSource<OrderHistory>(result.response);
        setTimeout(()=> {
          this.historyData.paginator = this.paginator;
        }, 500);
      }else if(result.error){
        this.historyData = new MatTableDataSource<OrderHistory>([]);
        this.historyData.paginator = this.paginator;
        this.toastMessage.showErrorMsg(result.error.message, "Error");
        }
    }, error => {
      this.toastMessage.showErrorMsg(error.errors[0].message, "Error");
    });
  }else {
    //this.spinner.hide();
    this.toastMessage.showWarningMsg('Please select one required field', 'Warning');
  }
  }

  clearForm() {
    this.orderHistoryFormGroup.reset();
    this.historyData = new MatTableDataSource<OrderHistory>([]);
  }
  View(data) {
    let url= environment["baseUrl"] + data.prescriptionPath.replace("/var/telemedicine/", "")
    window.open(url, "_system",'location=no');
  }

  download(data) {
    let filedata = {
			api:"downoad document",
			request:{
				ddtDocsPath: data.prescriptionPath,
				ddtDocsType: 'application/json',
				drdUserId: this.authService.getUserId(),
			},
			mimeType:"application/json"
		}
		this.checkerService.downloadDoc(filedata).subscribe((resp:any) => {
			if(resp){
				if(resp.status){
          const fileName = data.prescriptionPath.slice(data.prescriptionPath.lastIndexOf('/')+1,data.prescriptionPath.length);
					this.documentService.downloadFileToBrowser(resp.response, resp.mimeType,fileName);
				}else {
					this.toastMessage.showErrorMsg(resp.errors.message ? resp.errors.message: 'Internal Server Error', "Error");
				}
			}
		})
  }
}