import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
@Component({
  selector: 'app-privacy-policy',
  templateUrl: './privacy-policy.component.html',
  styleUrls: ['./privacy-policy.component.scss']
})
export class PrivacyPolicyComponent implements OnInit {
 

  constructor(
    private router: Router,
    private routes: ActivatedRoute,
  ) { }

  ngOnInit(): void {
  }
     
  navigateToPage(value) {
    if (value === 'home') {
      this.router.navigate(['../'], 
      // { relativeTo: this.router }
      );
    } else if (value === 'aboutUs') {
      window.location.href = 'https://nsdl.co.in/about/index.php';
      // this.router.navigate(['../aboutUs'], { relativeTo: this.routes }); //'../aboutUs'
    } else if (value === 'contactUs') {
      window.location.href = 'https://nsdl.co.in/contactus.php';
      // this.router.navigate(['../programs'], { relativeTo: this.routes });
    }
  }
}
