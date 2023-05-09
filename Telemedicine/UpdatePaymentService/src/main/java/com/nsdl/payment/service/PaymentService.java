package com.nsdl.payment.service;

import com.nsdl.payment.dto.AppointmentTransIDDTO;
import com.nsdl.payment.dto.CommonResponseDTO;
import com.nsdl.payment.dto.InvoiceDetail;
import com.nsdl.payment.dto.MainResponseDTO;
import com.nsdl.payment.dto.RazorPayResponse;
import com.nsdl.payment.dto.UpdateRequestDto;

public interface PaymentService {

	MainResponseDTO<CommonResponseDTO> updateOrder(UpdateRequestDto paymentRequest);

	MainResponseDTO<CommonResponseDTO> updatePaymentResponse(RazorPayResponse paymentResponse);


	MainResponseDTO<CommonResponseDTO> createPaymentLink(InvoiceDetail invoiceDetail);

	MainResponseDTO<CommonResponseDTO> updatePaymentLinkDtls(RazorPayResponse paymentResponse);

	MainResponseDTO<CommonResponseDTO> saveAppointmentDetails(AppointmentTransIDDTO transID);

	public void deleteslotsWithoutpayment();




}
