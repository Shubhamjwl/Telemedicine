import { environment } from "src/environments/environment";

export class DoctorDetails {

        dmdIdPk: string;
        dmdConsulFee: string;
        dmdDrName: string;
        dmdEmail: string;
        dmdGender: string;
        dmdIsRegByIpan: string;
        dmdIsverified: string;
        dmdMciNumber: string;
        dmdMobileNo: string;
        dmdModifiedBy: string;
        dmdModifiedTmstmp: string;
        dmdOptiVersion: string;
        dmdPassword: string;
        dmdSmcNumber: string;
        dmdSpecialiazation: string;
        dmdUserId: string;
        drDocsDtls: DocumentDetails[];
        profilePhoto : string;
        dmdAddress1 : string;
        dmdAddress2: string;
        dmdAddress3: string;
        dmdState: string;
        dmdCity: string;
        drScribeDtls: any;

        constructor(jsonObj){
            this.dmdIdPk = jsonObj.dmdIdPk ? jsonObj.dmdIdPk : '';
            this.dmdConsulFee = jsonObj.dmdConsulFee ? jsonObj.dmdConsulFee : '',
            this.dmdDrName = jsonObj.dmdDrName ? jsonObj.dmdDrName : '',
            this.dmdEmail = jsonObj.dmdEmail ? jsonObj.dmdEmail : '',
            this.dmdGender =  jsonObj.dmdGender ? jsonObj.dmdGender : '',
            this.dmdIsRegByIpan =  jsonObj.dmdIsRegByIpan ? jsonObj.dmdIsRegByIpan : '',
            this.dmdIsverified =  jsonObj.dmdIsverified ? jsonObj.dmdIsverified : '',
            this.dmdMciNumber =  jsonObj.dmdMciNumber ? jsonObj.dmdMciNumber : '',
            this.dmdMobileNo = jsonObj.dmdMobileNo ? jsonObj.dmdMobileNo : '',
            this.dmdModifiedBy = jsonObj.dmdModifiedBy ? jsonObj.dmdModifiedBy : '',
            this.dmdModifiedTmstmp = jsonObj.dmdModifiedTmstmp ? jsonObj.dmdModifiedTmstmp : '', 
            this.dmdOptiVersion = jsonObj.dmdOptiVersion ? jsonObj.dmdOptiVersion : '',
            this.dmdPassword = jsonObj.dmdPassword ? jsonObj.dmdPassword : '',
            this.dmdSmcNumber = jsonObj.dmdSmcNumber ? jsonObj.dmdSmcNumber : '',
            this.dmdSpecialiazation = jsonObj.dmdSpecialiazation ? jsonObj.dmdSpecialiazation : '',
            this.dmdUserId = jsonObj.dmdUserId ? jsonObj.dmdUserId : '',
            this.profilePhoto = jsonObj.profilePhoto ? jsonObj.profilePhoto : '',
            this.dmdAddress1 = jsonObj.dmdAddress1 ? jsonObj.dmdAddress1 : '',
            this.dmdAddress2 = jsonObj.dmdAddress2 ? jsonObj.dmdAddress2 : '',
            this.dmdAddress3 = jsonObj.dmdAddress3 ? jsonObj.dmdAddress3 : '',
            this.dmdState = jsonObj.dmdState ? jsonObj.dmdState : '',
            this.dmdCity = jsonObj.dmdCity ? jsonObj.dmdCity : '',
            this.drScribeDtls = jsonObj.drScribeDtls ? jsonObj.drScribeDtls: '';
          

            this.drDocsDtls = new Array();
            if(jsonObj.drDocsDtls && jsonObj.drDocsDtls.length){
                 jsonObj.drDocsDtls.forEach(element => {
                     this.drDocsDtls.push(new DocumentDetails(element))
                 });
            }
        }         
}

export class DocumentDetails {

    ddtDocIdPk: any;
    ddtDocsName: any;
    ddtDocsPath: any;
    ddtDocsRemark: any;
    ddtDocsType: any;

    constructor(jsonObj){
        if(jsonObj){
            this.ddtDocIdPk =  jsonObj.ddtDocIdPk ? jsonObj.ddtDocIdPk : '';
            this.ddtDocsName = jsonObj.ddtDocsName ? jsonObj.ddtDocsName : '';
            this.ddtDocsPath = jsonObj.ddtDocsPath ? jsonObj.ddtDocsPath : '';
            this.ddtDocsRemark =  jsonObj.ddtDocsRemark ? jsonObj.ddtDocsRemark : '';
            this.ddtDocsType = jsonObj.ddtDocsType ? jsonObj.ddtDocsType : '';
        }
    }
}
export class DoctorList {
    doctorDetails: Doctor[];

    constructor(jsonObj){
        this.doctorDetails = new Array();

        if(jsonObj.response && jsonObj.response.length){
            jsonObj.response.forEach(element => {
                this.doctorDetails.push(new Doctor(element))
            });
        }
    }
}
export class Doctor {

    docName: string;
    docUserID: string;
    emailID: string;
    micno: string;
    mobile: string;
    photopath: string;
    smcno: string;
    select: boolean;

    constructor(jsonObj){
    
        // this.drSpecilization = jsonObj.drSpecilization ? jsonObj.drSpecilization : '';
        this.docUserID = jsonObj.docUserID ? jsonObj.docUserID : '';
        this.docName = jsonObj.docName ? jsonObj.docName : '';
        this.emailID = jsonObj.emailID ? jsonObj.emailID : '';
        this.micno = jsonObj.micno ? jsonObj.micno : '';
        this.mobile = jsonObj.mobile ? jsonObj.mobile : '';
        this.smcno = jsonObj.smcno ? jsonObj.smcno : '';
        this.photopath = jsonObj.photopath ? jsonObj.photopath.replace('/var/telemedicine/', `${environment["baseUrl"]}`) : 'assets/images/img_avatar.png';
        this.select = false;
    }
}
export class DocProfileDetails {
    profilePhoto: string;
    dmdUserId: string;
    fullName: string;
    mobileNo: string;
    email: string;
    gender: string;
    dob: string;
    bloodGroup: string;
    height: string;
    weight: string;
    state: string;
    city: string;
    smcNo: string;
    micNo: string;
    consultationFee: string;
    speciality: string;
    address1: string;
    address2: string;
    address3: string;

    constructor(jsonObj){
        if(jsonObj){
            this.profilePhoto = jsonObj.profilePhoto ? jsonObj.profilePhoto.replace('/var/telemedicine/', `${environment["baseUrl"]}`) : 'assets/images/img_avatar.png';
            this.dmdUserId = jsonObj.dmdUserId ? jsonObj.dmdUserId : '';
            this.fullName = jsonObj.dmdDrName ? jsonObj.dmdDrName : '';
            this.mobileNo = jsonObj.dmdMobileNo ? jsonObj.dmdMobileNo : '';
            this.email = jsonObj.dmdEmail ? jsonObj.dmdEmail : '';
            this.gender = jsonObj.dmdGender ? jsonObj.dmdGender.trim() : '';
            this.dob = jsonObj.dob ? jsonObj.dob : '';
            this.bloodGroup = jsonObj.bloodGroup ? jsonObj.bloodGroup : '';
            this.height = jsonObj.height ? jsonObj.height : '';
            this.weight = jsonObj.weight ? jsonObj.weight : '';
            this.state = jsonObj.dmdState ? jsonObj.dmdState.trim() : '';
            this.city = jsonObj.dmdCity ? jsonObj.dmdCity.trim() : '';
            this.smcNo = jsonObj.dmdSmcNumber ? jsonObj.dmdSmcNumber : '';
            this.micNo = jsonObj.dmdMciNumber ? jsonObj.dmdMciNumber : '';
            this.consultationFee = jsonObj.dmdConsulFee ? jsonObj.dmdConsulFee : '';
            this.speciality = jsonObj.dmdSpecialiazation ? jsonObj.dmdSpecialiazation : '';
            this.address1 = jsonObj.dmdAddress1 ? jsonObj.dmdAddress1 : '';
            this.address2 = jsonObj.dmdAddress2 ? jsonObj.dmdAddress2 : '';
            this.address3 = jsonObj.dmdAddress3 ? jsonObj.dmdAddress3 : '';
        }
    }
}