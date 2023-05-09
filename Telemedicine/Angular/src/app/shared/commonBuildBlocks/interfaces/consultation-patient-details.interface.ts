import { IPatientAddressDetails } from './patient-address-details.interface';
import { IPatientLifestyleDetails } from './patient-lifestyle-details.inteface';
import { IPatientMedicalDetails } from './patient-medical-details.interface';
import { IPatientPersonalDetails } from './patient-personal-details.interface';

export interface IConsultationPatientDetails {
  ptUserId: any;
  apptId: string;
  height: string;
  weight: string;
  bloodgrp: string;
  ptPersonalDetals: IPatientPersonalDetails;
  address: IPatientAddressDetails;
  ptMedicalDtls: IPatientMedicalDetails;
  ptLifeStyleDtls: IPatientLifestyleDetails;
  symptoms: any;
}

