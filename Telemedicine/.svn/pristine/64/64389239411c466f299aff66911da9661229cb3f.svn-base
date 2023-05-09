import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-ask-to-logout-modal',
  templateUrl: './ask-to-logout-modal.component.html',
  styleUrls: ['./ask-to-logout-modal.component.scss']
})
export class AskToLogoutModalComponent implements OnInit {

  description: string;

  constructor(
    public dialogRef: MatDialogRef<AskToLogoutModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.description = this.data?.description || 'Your last session was terminated incorrectly or is currently active. Please try logging in again after some time.'
  }

  onClickYesOrNo(status) {
    this.dialogRef.close({ data: status });
  }
}
