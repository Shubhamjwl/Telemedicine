export interface ConsultationPaymentRequest {
  totalWalletAmount: number;
  usedWalletAmount: number;
  consultationFee: number;
  apptId: string; // TODO: check from BE from the response
  transId: string;
  convenienceCharge: number;
}

export interface ConsultationPaymentResponse {
  razorPay: ConsultationRazorPayment;
}

export interface ConsultationRazorPayment {
  applicationFee: string;
  razorpayOrderId: string;
  secretKey: string;
  paymentId: string;
  notes: string;
  imageURL: string;
  theme: string;
  merchantName: string;
  purchaseDescription: string;
  customerName: string;
  customerEmail: string;
  customerContact: string;
}

export interface PayResponseRequest {
  orderId: string;
  paymentId: string;
  signature: string;
  paymentMethod: string;
  totalWalletAmount: number;
  usedWalletAmount: number;
}
