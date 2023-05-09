import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-self-resend-otp',
  templateUrl: './self-resend-otp.component.html',
  styleUrls: ['./self-resend-otp.component.css'],
})
export class SelfResendOtpComponent implements OnInit {
  constructor(private router: Router) {}
  timeLeft: number = 60;
  interval;
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

  ngOnInit(): void {
    this.startTimer();
  }
  submit() {
    this.router.navigate(['/selfpatientregnxt']);
  }
}
