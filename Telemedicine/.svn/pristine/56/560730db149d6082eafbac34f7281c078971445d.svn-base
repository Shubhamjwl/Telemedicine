import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { AppConstants } from "src/app/app-constants";
import { SeverUrlConstant } from '../../constant/serverUrlConstant';

@Injectable({
    providedIn: "root"
})

export class UserManagementService {

 /**
   * Used to store BaseUrl
  */
  private baseUrl : string;

  constructor(private httpClient: HttpClient) {
      this.baseUrl = SeverUrlConstant.devBaseUrl;
  }

  /**
    * @description Used to store SignIn API call
    * @type POST
   */
    signIn() {
        let payload = {
            "id":"user",
            "version":"1.0",
            "requesttime":"2020-01-02T10:01:21.331Z",
            "request":{
                "userId":"sayali25",
                "password":"Pas12t&*"
            }
        }
        return this.httpClient.post(this.baseUrl + 'usermanagement/v1/user/login/signin', payload);
    }


    /**
     * @description Used to store Create API Call
     * @type POST
     */
    createUser() {
        let payload = {
            "id":"user.create",
            "version":"1.0",
            "requesttime":"2020-01-02T10:01:21.331Z",
            "request":{
                "userFullName":"saili26",
                "userId":"sayali26",
                "password":"Pas12t&*",
                "mobileNumber":"7032286317",
                "email":"test@gmail.com",
                "userType":"Doctor",
                "smcNumber": "1234AB",
                "mciNumber": "0KASS"
            }
        }
        return this.httpClient.post(this.baseUrl + 'usermanagement/v1/user/login/createuser', payload);
    }


    /**
     * @description Used to store sign out user API Call
     * @type GET
     */
    signOut() {
        return this.httpClient.get(this.baseUrl + 'usermanagement/v1/user/login/signout',  { params: { loginId: 'TESTADMIN' } });

    }


    /**
     * @description Used to store reset Password API call
     * @type POST
     */
     resetPassword() {
         let payload = {
            "id":"user",
            "version":"1.0",
            "requesttime":"2020-01-02T10:01:21.331Z",
            "request":{
                "userId":"TESTADMIN",
                "oldPwd":"Pass12!@",
                "newPwd":"12!@Pass",
                "confirmPwd":"12!@Pass"
            }
        }
        return this.httpClient.post(this.baseUrl + 'usermanagement/v1/user/login/signout',  payload);
     }
  
    /**
     * @description Used to store API call of verify Generated OTP
     * @type POST
     */
    verifyOtp(otp?) {
        let payload = {
            "id":"1",
            "version":"v1",
            "requesttime":"",
            "method": "POST",
            "request":{
                            "userId":"jeevan",
                            "mobileOTP": otp ? otp : "vMSgBF^g",
                            "emailOTP": otp ? otp : "vMSgBF^g"
            }
        }
        return this.httpClient.post(this.baseUrl + 'OTPManager/verifyOTP',  payload);
    }
}
