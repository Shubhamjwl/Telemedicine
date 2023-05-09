import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
  HttpHeaders
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { AuthService } from '../../commonBuildBlocks/services/authSercies/auth.service';
import { ToastMessageService } from '../../commonBuildBlocks/toaster/toast-message.service';

@Injectable()
export class TelemedicineInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService, private route: Router, private toastrMessage:ToastMessageService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {

    if(this.authService.getToken()){
      request = this.addToken(request, this.authService.getToken());
    } else {
      request = request.clone({
        url: `${environment["baseUrl"]}${request["url"]}`
      });
    }
    // request = request.clone({
    //     body: this.encrypt(request.body)
    // })
    return next.handle(request).pipe(catchError(error => {
      console.log(error);
      // if(error instanceof HttpErrorResponse && error.status === 0){
      //       this.authService.logout();
      //       this.route.navigate(['sign-in']);
      // }
      if(error instanceof HttpErrorResponse && error.status === 401 ) {
        // this.toastrMessage.showErrorMsg(error.message, "Error");
         this.handleError(request, next);
      } else {
        return throwError(error);
      }
    }));
  }

  private addToken(request: HttpRequest<any>, token: string){
    return request.clone({
      url: `${environment["baseUrl"]}${request["url"]}`,
      setHeaders: {
        Authorization: token,
        "Content-Type": "application/json"
      }
    });
  }

  private handleError(request: HttpRequest<any>, next: HttpHandler) {
        sessionStorage.removeItem(this.authService.loggedUserID);
        sessionStorage.removeItem(this.authService.getToken());
        // this.route.navigate(['']);
        this.route.navigate(['sign-in']);
        sessionStorage.clear();
  }
}
