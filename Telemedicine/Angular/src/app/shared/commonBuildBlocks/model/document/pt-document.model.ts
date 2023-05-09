export interface PtDocumentType {
  id: number;
  documentName: string;
}

export interface PtDocumentResponse {
  docId: number;
  documentName: string;
  documentType: string;
  documentPath: string;
  uploadDate: string;
  uploadedBy: string;
}


