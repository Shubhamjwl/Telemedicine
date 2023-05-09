import { IPatientAddressDetails } from "./patient-address-details.interface";

export interface IPatient {
  ptFullName: string;
  ptMobNo: number;
  ptEmail: string;
  height?: any;
  weight?: any;
  bloodgrp?: any;
  dob?: any;
  ptProfilePhoto?: any;
  address: IPatientAddressDetails;
  ptGender?: any;
  ptCity?: any;
  ptState?: any;
  ptCountry?: any;
  ptUserId: string;
}
