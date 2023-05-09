export interface AdminFilterRequest {
  selectionType: 'DOCTOR' | 'PATIENT';
  sendToAll: boolean;
  city: string;
  regDate: string;
  specialization: string;
  associationName: string;
  docUserIdList: string[];
}
