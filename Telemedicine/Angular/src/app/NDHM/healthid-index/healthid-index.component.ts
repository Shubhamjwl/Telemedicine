import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-healthid-index',
  templateUrl: './healthid-index.component.html',
  styleUrls: ['./healthid-index.component.css']
})
export class HealthidIndexComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }
  healthidYes() {
    this.router.navigate(['/healthidMobile']);
  }
  healthidNo() {
    this.router.navigate(['/patient-dashboard']),{
      queryParams: {
        isHealthIdNo:false,
      },skipLocationChange: true
    };
  }
  haveHealthid() {
    this.router.navigate(['/AssistedHealthidCreationMobile']);
  }
}
