import { ExceptionJSONInfoDTO } from "./ExceptionJSONInfoDTO";

export class MainResponseDTO<T> {
 
    id:string;

	version:string;

	responsetime:string;

	response:T;

	status:boolean;

	errors:ExceptionJSONInfoDTO[];

}