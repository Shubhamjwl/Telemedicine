import { ExceptionJSONInfoDTO } from "./ExceptionJSONInfoDTO";

export class MainRequestDTO<T> {
 
    id:string;

	version:string;

	responsetime:string;

	request:T;

	status:boolean;

	errors:ExceptionJSONInfoDTO[];

}