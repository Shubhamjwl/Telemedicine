import { ErrorDTO } from "./ErrorDTO";
import { OnFetchModesAuthDTO } from "./OnFetchModesAuthDTO";
import { RespDTO } from "./RespDTO";

export class OnFetchModesRequestDTO {
    requestId: string;
    timeStamp: string;
    auth: OnFetchModesAuthDTO;
    error: ErrorDTO;
    resp: RespDTO;
}