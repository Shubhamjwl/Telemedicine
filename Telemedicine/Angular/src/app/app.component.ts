import { Component, HostListener, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './shared/commonBuildBlocks/services/authSercies/auth.service';
import { DataPassingService } from './shared/commonBuildBlocks/services/dataPassingServices/dataPassing.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'teleMedicines';
  constructor(
    private authService: AuthService,
    private router: Router,
    private datapassingService: DataPassingService,
  ) { 
    this.datapassingService.resetData();
  }
  @HostListener('window:beforeunload') signOut() {
      if(sessionStorage.length){
          setTimeout(() => {
            // For Safari
          this.authService.signOut().subscribe(result => {
            if(result && result.response) {
              sessionStorage.clear();
              this.router.navigate(['../sign-in']);
              this.datapassingService.scribeList = [];
            }else if(result && result.errors){
              alert(result.errors[0].message)
            }
          }), error => {
            console.log('error', error);
          }
        }, 1000);
      }
  }
}
