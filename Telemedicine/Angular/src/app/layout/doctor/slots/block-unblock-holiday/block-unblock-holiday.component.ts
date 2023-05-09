import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { Subscription } from 'rxjs';
import { SubjectMessageService } from 'src/app/shared/subject-message.service';

@Component({
  selector: 'app-block-unblock-holiday',
  templateUrl: './block-unblock-holiday.component.html',
  styleUrls: ['./block-unblock-holiday.component.scss']
})
export class BlockUnblockHolidayComponent implements OnInit {

  /**
   * Used to slot Form.
   */
  form: FormGroup;
  minDate = new Date();
  data = 'Closed'

  // Used to send / receive sent message
  subscription: Subscription;
  receivedMessage: any = [];

  constructor(
    private fb: FormBuilder,
    private messageService: SubjectMessageService,

  ) { }

  ngOnInit(): void {
    this.formValidation();
    this.receiveMessage();

  }

  formValidation() {
    this.form = this.fb.group({
      holiday: ['', Validators.required],
      reason: ['']
    })
  }

  confirmHoliday(value) {
    this.messageService.sendMessage(value);
  }

  receiveMessage() {

    // ---------------Receive filter option values from block / unblock modal-----------
    this.receivedMessage = [];
    this.subscription = this.messageService.onMessageToModal().subscribe(message => {
      if (message) {
        this.receivedMessage = message.text;
      } else {
        this.receivedMessage = [];
      }
    });
    // -------End--------Receive message from block / unblock popup/modal-----------
  }

}
