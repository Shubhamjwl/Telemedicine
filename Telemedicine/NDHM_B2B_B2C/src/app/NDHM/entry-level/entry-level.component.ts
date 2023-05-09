import { Component, OnInit } from '@angular/core';
import { ERR_MSGS, REGEX } from '../../../../src/config/constant';
import { ActivatedRoute, Router } from '@angular/router';
// import { FormsModule }   from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-entry-level',
  templateUrl: './entry-level.component.html',
  styleUrls: ['./entry-level.component.css'],
})
export class EntryLevelComponent implements OnInit {
  PATTERN = REGEX;
  healthId = '';
  abhaAddress = '';
  show: boolean = false;
  dateOfBirth: any;
  gender: any;
  mobileNoSel: any;
  lastName: any;
  firstName: any;
  middleName: any;
  name: any;
  selectOptions = ['Male', 'Female', 'Transgender'];

  selectedOption = '';
  isMobileNoDisabled: boolean = true;
  yearOfBirth: any;
  monthOfBirth: any;
  dayOfBirth: any;
  mobileNo: string;
  constructor(private router: Router, private route: ActivatedRoute) {
    this.abhaAddress = localStorage.getItem('ABHA_Address');
    console.log('ABHA_Address', this.healthId);

    this.healthId = localStorage.getItem('ABHA_ID');
    console.log('ABHA_ID', this.healthId);

    this.mobileNo = localStorage.getItem('mobileNoSel');
    console.log('mobileNoSel', this.mobileNoSel);
  }

  ngOnInit(): void {}
  showForm() {
    this.show = !this.show;
    // this.show = true;
    // localStorage.clear();
    // this.healthId = '';
    // this.abhaAddress = '';
    // this.mobileNo = '';
  }

  showProfile() {
    this.router.navigate(['/ProfileCreate'], {
      queryParams: {
        healthId: this.healthId,
        mobileNoSel: this.mobileNo,
      },
      skipLocationChange: true,
    });
  }

  dateOfBirthSplit() {
    var dateOfBirth = this.dateOfBirth;
    //console.log('this.dateOfBirth::' + this.dateOfBirth);
    if (dateOfBirth) {
      var splitted = dateOfBirth.split('-', 3);
      this.yearOfBirth = splitted[0];
      this.monthOfBirth = splitted[1];
      this.dayOfBirth = splitted[2];
      if (this.monthOfBirth === '10') {
      } else {
        this.monthOfBirth = this.monthOfBirth.replace('0', '');
      }
      console.log(this.dateOfBirth);
      //console.log(splitted);
      //console.log('this.dayOfBirth', this.dayOfBirth);
      //console.log('this.monthOfBirth', this.monthOfBirth);
      //console.log('this.yearOfBirth', this.yearOfBirth);
    }
  }

  onSubmit() {
    // localStorage.setItem('mobileNoSel', this.mobileNoSel);
    if (!this.firstName) {
      this.firstName = '';
    }
    if (!this.middleName) {
      this.middleName = '';
    }
    if (!this.lastName) {
      this.lastName = '';
    }
    this.name = this.firstName + ' ' + this.middleName + ' ' + this.lastName;
    console.log('NAme', this.name);

    this.router.navigate(['/verfymobile'], {
      queryParams: {
        name: this.name,
        gender: this.selectedOption,
        mobileNoSel: this.mobileNoSel,
        dob: this.dateOfBirth,
      },
      skipLocationChange: true,
    });
  }
}
