import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { AuthService } from 'src/app/shared/commonBuildBlocks/services/authSercies/auth.service';
import { DataPassingService } from 'src/app/shared/commonBuildBlocks/services/dataPassingServices/dataPassing.service';
import { DocumentService } from 'src/app/shared/commonBuildBlocks/services/documentServices/document.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';

@Component({
  selector: 'app-tm-resource',
  templateUrl: './tm-resource.component.html',
  styleUrls: ['./tm-resource.component.scss']
})
export class TmResourceComponent implements OnInit {
  

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private activateRoute: ActivatedRoute,
    private toastMessage: ToastMessageService,
    public authService: AuthService,
    private dataPassingService: DataPassingService,
    private dialog: MatDialog,
    private spinner: NgxSpinnerService,
    private documentService: DocumentService,
    
  ) { }

  ngOnInit(): void {
  }

  onClickHome(){
    this.router.navigate(['../sign-in'], { relativeTo: this.activateRoute });
   
  }

  reSource(){
    console.log("enter in resource")
    this.router.navigate(['./reSource']); 
  }

  programOverview(){
    this.router.navigate(['./programOverview']); 
  }

  download() {
    let filedata = {
			request:{
				pdfName:"Protean Clinic brochure_revised_28feb22 (1).pdf",
			},
		}
		this.authService.downloadAboutUsPdf(filedata).subscribe((resp:any) => {
			if(resp){
				if(resp.status && resp.response.encodedPdfData){
          console.log(resp.response.encodedPdfData,"response file")
          const fileName1 = "Protean Clinic brochure_revised_28feb22 (1).pdf"
					this.documentService.downloadFileToBrowser(resp.response.encodedPdfData, 'application/pdf',fileName1);
				}else {
					this.toastMessage.showErrorMsg(resp.errors.message ? resp.errors.message: 'Internal Server Error', "Error");
				}
			}
		})
  }

  downloadOne() {
    let filedata = {
			request:{
				pdfName:"Protean Clinic Leaflet _Final_1.pdf",
			},
		}
		this.authService.downloadAboutUsPdf(filedata).subscribe((resp:any) => {
			if(resp){
				if(resp.status && resp.response.encodedPdfData){
          console.log(resp.response.encodedPdfData,"response file")
         const fileName = "Protean Clinic Leaflet _Final_1.pdf";
					this.documentService.downloadFileToBrowser(resp.response.encodedPdfData, 'application/pdf',fileName);
				}else {
					this.toastMessage.showErrorMsg(resp.errors.message ? resp.errors.message: 'Internal Server Error', "Error");
				}
			}
		})
  }

  downloadTwo() {
    let filedata = {
			request:{
				pdfName:"Protean Clinic Leaflet _Final_2.pdf",
			},
		}
    this.spinner.show();
		this.authService.downloadAboutUsPdf(filedata).subscribe((resp:any) => {
			if(resp){
				if(resp.status && resp.response.encodedPdfData){
          this.spinner.hide();
          console.log(resp.response.encodedPdfData,"response file")
         const fileName = "Protean Clinic Leaflet _Final_2.pdf";
					this.documentService.downloadFileToBrowser(resp.response.encodedPdfData, 'application/pdf',fileName);
				}else {
					this.toastMessage.showErrorMsg(resp.errors.message ? resp.errors.message: 'Internal Server Error', "Error");
				}
			}
		})
  }

  downloadThree() {
    let filedata = {
			request:{
				pdfName:"Protean Clinic Leaflet _Final_3.pdf",
			},
		}
		this.authService.downloadAboutUsPdf(filedata).subscribe((resp:any) => {
			if(resp){
				if(resp.status && resp.response.encodedPdfData){
          console.log(resp.response.encodedPdfData,"response file")
         const fileName = "Protean Clinic Leaflet _Final_3.pdf";
					this.documentService.downloadFileToBrowser(resp.response.encodedPdfData, 'application/pdf',fileName);
				}else {
					this.toastMessage.showErrorMsg(resp.errors.message ? resp.errors.message: 'Internal Server Error', "Error");
				}
			}
		})
  }

  downloadFour() {
    let filedata = {
			request:{
				pdfName:"Protean Clinic Leaflet _Final_4.pdf",
			},
		}
   // let filedata = "?pdfName="+ "Protean Clinic Leaflet _Final_1.pdf"
		this.authService.downloadAboutUsPdf(filedata).subscribe((resp:any) => {
			if(resp){
				if(resp.status && resp.response.encodedPdfData){
          console.log(resp.response.encodedPdfData,"response file")
         const fileName ="Protean Clinic Leaflet _Final_4.pdf";
					this.documentService.downloadFileToBrowser(resp.response.encodedPdfData, 'application/pdf',fileName);
				}else {
					this.toastMessage.showErrorMsg(resp.errors.message ? resp.errors.message: 'Internal Server Error', "Error");
				}
			}
		})
  }

}
