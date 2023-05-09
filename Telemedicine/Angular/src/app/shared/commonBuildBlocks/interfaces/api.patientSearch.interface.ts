import { IError } from './error.interface';

export interface patientSearch<T> {
  id: string;
  version: string;
  responseTime: string;
  response: T;
  status: string;
  errors: IError;
}
