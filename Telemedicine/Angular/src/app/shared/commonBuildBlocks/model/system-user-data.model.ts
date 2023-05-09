
export class UserData {
    details: Details[]
    constructor(jsonObj){
        if(jsonObj && jsonObj.length){
            this.details = new Array();
            jsonObj.forEach((element, index) => {
                this.details.push(new Details(element, index));
            });
        } else {
            this.details = [];
        }
    }
}   

export class Details {
    currentStatus: String;
    drEmail: String;
    drFullName: String;
    drMCINo: String;
    drMobNo: String;
    drSMCNo: String;
    drSpecilization: String;
    drUserID: [];
    index: any;

    constructor(jsonObj, i){
        if(jsonObj){
            this.currentStatus = jsonObj.currentStatus ? jsonObj.currentStatus : '';
            this.drEmail = jsonObj.drEmail ? jsonObj.drEmail : '';
            this.drFullName = jsonObj.drFullName ? jsonObj.drFullName : '';
            this.drMCINo = jsonObj.drMCINo ? jsonObj.drMCINo : '';
            this.drMobNo = jsonObj.drMobNo ? jsonObj.drMobNo : '';
            this.drSMCNo = jsonObj.drSMCNo ? jsonObj.drSMCNo : '';
            this.drSpecilization = jsonObj.drSpecilization ? jsonObj.drSpecilization : '';
            this.drUserID = jsonObj.drUserID ? jsonObj.drUserID : '';
            this.index = i;
        }
    }
}