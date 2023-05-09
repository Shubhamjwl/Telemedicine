import { IError } from './error.interface'

export interface IAPIResponseWrapper<T> {
  id: string;
  version: string;
  responseTime: string;
  response: T;
  status: string;
  errors: IError;
  mimeType: string; // for download file
}
