
export interface IDoctorDetails {
    dmdIdPk: string;
    dmdConsulFee: number;
    dmdConvenienceCharge: number;
    dmdDrName: string;
    dmdEmail: string;
    dmdGender: string;
    dmdIsRegByIpan: string;
    dmdIsverified: string;
    dmdTcFlag: string;
    dmdMciNumber: string;
    dmdMobileNo: string;
    dmdModifiedBy: string;
    dmdModifiedTmstmp: string;
    dmdOptiVersion: string;
    dmdPassword: string;
    dmdSmcNumber: string;
    dmdSpecialiazation: string;
    dmdUserId: string;
    drDocsDtls: IDocumentDetails[];
    profilePhoto: string;
    dmdAddress1: string;
    dmdAddress2: string;
    dmdAddress3: string;
    dmdState: string;
    dmdCity: string;
    drScribeDtls: any;
    dmdDrLink: string;
    dmdPatientRegistrationLink: string;
    dmdDrProfileLink: string;
    dmdPreassessmentLink: string;
    dmdPreassessmentFlag: string;
    dmdAssociationName: string;
    dmdAssociationNumber: string;
}

export interface IDocumentDetails {
    ddtDocIdPk: any;
    ddtDocsName: any;
    ddtDocsPath: any;
    ddtDocsRemark: any;
    ddtDocsType: any;
}