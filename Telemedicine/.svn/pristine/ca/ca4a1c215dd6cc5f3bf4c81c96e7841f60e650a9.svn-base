import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-self-patient-registered-next',
  templateUrl: './self-patient-registered-next.component.html',
  styleUrls: ['./self-patient-registered-next.component.css'],
})
export class SelfPatientRegisteredNextComponent implements OnInit {
  constructor(private router: Router) {}
  ngOnInit(): void {}
  public imagePath: any;
  imgURL: any;
  public message: string | undefined;

  preview(files: any) {
    if (files.length === 0) return;

    var mimeType = files[0].type;
    if (mimeType.match(/image\/*/) == null) {
      this.message = 'Only images are supported.';
      return;
    }

    var reader = new FileReader();
    this.imagePath = files;
    reader.readAsDataURL(files[0]);
    reader.onload = (_event) => {
      this.imgURL = reader.result;
    };
  }

  submit() {
    this.router.navigate(['/selfyesNomessage']);
  }
}
