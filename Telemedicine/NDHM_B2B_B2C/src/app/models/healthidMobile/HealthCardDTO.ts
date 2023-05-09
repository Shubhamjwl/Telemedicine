import { TagsDTO } from "./TagsDTO";

export class HealthCardDTO{
    address:string;
    authMethods:string[];
    dayOfBirth:string;
    districtCode:string;
    districtName:string;
    email:string;
    firstName:string;
    gender:string;
    healthId:string;
    healthIdNumber:string;
    kycPhoto:string;
    kycVerified:boolean;
    lastName:string;
    middleName:string;
    mobile:string;
    monthOfBirth:string;
    name:string;
    isNew:boolean;
    pinCode:string;
    profilePhoto:string;
    stateCode:string;
    stateName:string;
    subDistrictCode:string;
    subDistrictName:string;
    tags:TagsDTO;
    townCode:string;
    townName:string;
    villageCode:string;
    villageName:string;
    wardCode:string;
    wardName:string;
    yearOfBirth:string;  
}