export interface RazorpayPaymentOption {
  key: string; // Enter the Key ID generated from the Dashboard
  amount: string; // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
  currency: string;
  name: string; //NSDL's Arogya Saarathi
  description: string;
  image: any; //https://example.com/your_logo
  order_id: string; //This is a sample Order ID. Pass the `id` obtained in the response of Step 1
  handler: any;
  prefill: RazorpayPaymentOptionPrefill;
}

export interface RazorpayPaymentOptionPrefill {
  name: string;
  email: string;
  contact: string;
}
