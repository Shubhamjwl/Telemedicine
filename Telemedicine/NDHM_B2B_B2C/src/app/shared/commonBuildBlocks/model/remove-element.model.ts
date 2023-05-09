import { environment } from "src/environments/environment";

export class Data {
    userId: string;
    name: string;
    mobile: string;
    email: string;
    photopath: string;
    smcno: string;
    micno: string;

    constructor(jsonObj){
        this.userId = jsonObj.docUserID ? jsonObj.docUserID:'';
        this.name = jsonObj.docName ? 'Dr. ' + jsonObj.docName:'';
        this.mobile = jsonObj.mobile ? jsonObj.mobile:'';
        this.email = jsonObj.emailID ? jsonObj.emailID:''; 
        this.photopath = jsonObj.photopath ? jsonObj.photopath.replace('/var/telemedicine/', `${environment["baseUrl"]}`) : 'assets/images/img_avatar.png'
        this.smcno = jsonObj.smcno ? jsonObj.smcno:'';
        this.micno = jsonObj.micno ? jsonObj.micno:'';
    }
}
export class Details {
    data = [];
    constructor(jsonObj){
        this.data = new Array();
        if(jsonObj && jsonObj.length){
            jsonObj.forEach(element => {
                this.data.push(new Data(element));
            });
        }
    }
}
export class PatientData {
    userId: string;
    name: string;
    mobile: string;
    email: string;
    photopath: string;
    smcno: string;
    micno: string;

    constructor(jsonObj){
        //todo Update as per api response
        this.userId = jsonObj.ptUserId ? jsonObj.ptUserId:'';
        this.name = jsonObj.ptFullName ? jsonObj.ptFullName:'';
        this.mobile = jsonObj.ptMobNo ? jsonObj.ptMobNo:'';
        this.email = jsonObj.ptEmail ? jsonObj.ptEmail:''; 
        this.photopath = jsonObj.ptProfilePhoto ? jsonObj.ptProfilePhoto.replace('/var/telemedicine/', `${environment["baseUrl"]}`) : 'assets/images/img_avatar.png'
        this.smcno = jsonObj.smcno ? jsonObj.smcno:'';
        this.micno = jsonObj.micno ? jsonObj.micno:'';
    }
}
export class PTDetails {
    data = [];
    constructor(jsonObj){
        this.data = new Array();
        if(jsonObj && jsonObj.length){
            jsonObj.forEach(element => {
                this.data.push(new PatientData(element));
            });
        }
    }
}