import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { OtpModalComponent } from '../otp-modal/otp-modal.component';

@Component({
  selector: 'app-congratulations-modal',
  templateUrl: './congratulations-modal.component.html',
  styleUrls: ['./congratulations-modal.component.scss']
})
export class CongratulationsModalComponent implements OnInit {

  
  constructor(
    public dialogRef: MatDialogRef<OtpModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit(): void {
  }

}
