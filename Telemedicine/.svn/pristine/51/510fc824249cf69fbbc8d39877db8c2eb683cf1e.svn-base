package com.nsdl.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.payment.dto.AppointmentTransIDDTO;
import com.nsdl.payment.dto.CommonResponseDTO;
import com.nsdl.payment.dto.InvoiceDetail;
import com.nsdl.payment.dto.MainResponseDTO;
import com.nsdl.payment.dto.RazorPayResponse;
import com.nsdl.payment.dto.UpdateRequestDto;
import com.nsdl.payment.logger.LoggingClientInfo;
import com.nsdl.payment.service.PaymentService;

@RestController
@LoggingClientInfo
@CrossOrigin
public class UpdatePaymentController {
	

	@Autowired
	PaymentService payService;
	
	@RequestMapping(value="/updatePaymentOrder", method=RequestMethod.POST)
	public ResponseEntity<MainResponseDTO<CommonResponseDTO>> updateOrder(@RequestBody UpdateRequestDto paymentRequest) {
		return ResponseEntity.status(HttpStatus.OK).body(payService.updateOrder(paymentRequest)); 
	}
	
	@RequestMapping(value="/updatePaymentResponse", method=RequestMethod.POST)
	public ResponseEntity<MainResponseDTO<CommonResponseDTO>> updatePaymentResponse(@RequestBody RazorPayResponse paymentResponse) {
		return ResponseEntity.status(HttpStatus.OK).body(payService.updatePaymentResponse(paymentResponse)); 
	}
	
	@RequestMapping(value="/addPaymentLinkDtls", method=RequestMethod.POST)
	public ResponseEntity<MainResponseDTO<CommonResponseDTO>> createPaymentLink(@RequestBody InvoiceDetail invoiceDetail) {
		return ResponseEntity.status(HttpStatus.OK).body(payService.createPaymentLink(invoiceDetail)); 
	}
	
	@RequestMapping(value="/updatePaymentLinkDtls", method=RequestMethod.POST)
	public ResponseEntity<MainResponseDTO<CommonResponseDTO>> updatePaymentLinkDtls(@RequestBody RazorPayResponse paymentResponse) {
		return ResponseEntity.status(HttpStatus.OK).body(payService.updatePaymentLinkDtls(paymentResponse)); 
	}
	
	@RequestMapping(value="/saveAppointmentDetails", method=RequestMethod.POST)
	public ResponseEntity<MainResponseDTO<CommonResponseDTO>> saveAppointmentDetails(@RequestBody AppointmentTransIDDTO transID) {
		return ResponseEntity.status(HttpStatus.OK).body(payService.saveAppointmentDetails(transID)); 
	}

/*
 * Added by SayaliA:Empty slots for which payment has not done in pay later option till (2 hours), for testing it is added for 22 minutes
 * 
 */
	//@RequestMapping(value="/testcron", method=RequestMethod.POST)//after every minute cron will excute
	@Scheduled(cron="*/5 * * * * ?")	
	public void emptyslotWithoutPyment()
	{
		payService.deleteslotsWithoutpayment();
	}
}
