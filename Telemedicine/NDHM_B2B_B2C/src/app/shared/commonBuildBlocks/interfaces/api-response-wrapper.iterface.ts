import { IError } from './error.interface'

export interface IAPIResponseWrapper<T> {
    id: string;
    version: string;
    responsetime: string;
    response: T;
    status: string;
    errors: IError;
}