import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class ToastMessageService {

  constructor(
    private toastr: ToastrService,
  ) { }

  showSuccessMsg(message, title, config?) {
    this.toastr.success(message, title, config);
  }

  showInfoMsg(message, title, config?) {
    this.toastr.info(message, title, config);
  }

  showErrorMsg(message, title, config?) {
    this.toastr.error(message, title, config);
  }

  showWarningMsg(message, title, config?) {
    this.toastr.warning(message, title, config);
  }

  clearToastr() {
    this.toastr.clear();
  }

}
