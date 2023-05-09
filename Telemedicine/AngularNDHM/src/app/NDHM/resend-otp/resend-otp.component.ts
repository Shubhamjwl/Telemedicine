import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-resend-otp',
  templateUrl: './resend-otp.component.html',
  styleUrls: ['./resend-otp.component.css'],
})
export class ResendOTPComponent implements OnInit {
  timeLeft: number = 60;
  interval;
  constructor(private router: Router) {}

  ngOnInit(): void {
    this.startTimer();
  }
  submit() {
    this.router.navigate(['/Nextpatientregister']);
  }
  startTimer() {
    this.interval = setInterval(() => {
      if (this.timeLeft > 0) {
        this.timeLeft--;
      }
      // else {
      //   this.timeLeft = 60;
      // }
    }, 1000);
  }
}
