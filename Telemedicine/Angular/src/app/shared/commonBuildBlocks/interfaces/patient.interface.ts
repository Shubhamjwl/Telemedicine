import { IPatientAddressDetails } from "./patient-address-details.interface";

export interface IPatient {
  ptUserID: string;
  ptPassword: string;
  captchaCode: string;
  gender: string;
  bloodGroup: string;
  emergContanctNo: string;
  pincode: string;
  sessionId: string;
  isHelathIDExists: string;
  healthID: string;
  healthNumber: string;
  healthidVerificationStatus: string;
  ptFullName: string;
  ptMobNo: number;
  ptEmail: string;
  height: string;
  weight: string;
  bloodgrp: string;
  dob: string;
  ptProfilePhoto: string;
  address: IPatientAddressDetails;
  address1: string;
  address2: string;
  address3: string;
  ptGender: string;
  ptCity: string;
  ptState: string;
  ptCountry: string;
  ptUserId: string;
  wallet: number;
}
