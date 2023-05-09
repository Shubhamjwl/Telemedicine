import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-view-report-modal',
  templateUrl: './view-report-modal.component.html',
  styleUrls: ['./view-report-modal.component.scss']
})
export class ViewReportModalComponent implements OnInit {

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  imgUrl: string;
  reportName: string = ''
  imgPath
  ngOnInit(): void {
    this.imgPath = this.data ? this.data.imgPath ? this.data.imgPath : '' : '';
    this.imgUrl = this.data ? this.data.imgUrl ? this.data.imgUrl : '' : '';
    this.reportName = this.data ? this.data.reportName ? this.data.reportName : '' : '';
  }

}
