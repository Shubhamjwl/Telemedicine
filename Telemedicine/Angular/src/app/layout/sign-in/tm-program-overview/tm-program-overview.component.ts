import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { AuthService } from 'src/app/shared/commonBuildBlocks/services/authSercies/auth.service';
import { DataPassingService } from 'src/app/shared/commonBuildBlocks/services/dataPassingServices/dataPassing.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';

@Component({
  selector: 'app-tm-program-overview',
  templateUrl: './tm-program-overview.component.html',
  styleUrls: ['./tm-program-overview.component.scss']
})
export class TmProgramOverviewComponent implements OnInit {

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private activateRoute: ActivatedRoute,
    private toastMessage: ToastMessageService,
    public authService: AuthService,
    private dataPassingService: DataPassingService,
    private dialog: MatDialog,
    private spinner: NgxSpinnerService
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

}
