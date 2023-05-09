package com.nsdl.payment.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.payment.dto.CommonResponseDTO;
import com.nsdl.payment.dto.MainResponseDTO;
import com.nsdl.payment.dto.PayLinkDTO;
import com.nsdl.payment.dto.PayRequest;
import com.nsdl.payment.dto.PaymentRequest;
import com.nsdl.payment.dto.PaymentResponse;
import com.nsdl.payment.dto.RazorPayResponse;
import com.nsdl.payment.logger.LoggingClientInfo;
import com.nsdl.payment.service.PaymentService;

@RestController
@CrossOrigin("*")
@LoggingClientInfo
public class PaymentController {
	
	
	
	@Autowired
	PaymentService payService;
	
	@RequestMapping(value="/pay", method=RequestMethod.POST)
	public ResponseEntity<MainResponseDTO<PaymentResponse>> createOrder(@RequestBody PayRequest paymentRequest) {
		return ResponseEntity.status(HttpStatus.OK).body(payService.createOrder(paymentRequest)); 
	}
	

	
	@RequestMapping(value="/payResponse", method=RequestMethod.POST)
	public ResponseEntity<MainResponseDTO<CommonResponseDTO>> updatePayment(@RequestBody RazorPayResponse razorPayResponse) {
		return ResponseEntity.status(HttpStatus.OK).body(payService.updatePaymentResponse(razorPayResponse)); 
	}
		
	
	
	@RequestMapping(value="/payLater", method=RequestMethod.POST)
	public ResponseEntity<MainResponseDTO<CommonResponseDTO>> createPaymentLink(@RequestBody PayLinkDTO payLinkDTO) {
		return ResponseEntity.status(HttpStatus.OK).body(payService.createPaymentLink(payLinkDTO)); 
	}
	

	/*
	 * @RequestMapping(value="/payLaterResponse", method=RequestMethod.GET) public
	 * ResponseEntity<MainResponseDTO<CommonResponseDTO>>
	 * updatePaymentLinkResponse(@RequestParam Map<String,String> allParams) {
	 * return ResponseEntity.status(HttpStatus.OK).body(payService.
	 * updatePaymentLinkResponse(allParams)); }
	 */
	
	@RequestMapping(value="/payLaterResponse", method=RequestMethod.GET)
	public String updatePaymentLinkResponse(@RequestParam Map<String,String> allParams) {
		return payService.updatePaymentLinkResponse(allParams); 
	}
	/*
	 * SayaliA:Code added for payment by patient using external link oof doctor shared with patient
	 * No token required for this, as doctor and patient are not logging to system
	 */
	@RequestMapping(value="/payUsingLink", method=RequestMethod.POST)
	public ResponseEntity<MainResponseDTO<PaymentResponse>> createOrderForLink(@RequestBody PaymentRequest paymentRequest) {
		return ResponseEntity.status(HttpStatus.OK).body(payService.createOrderForLink(paymentRequest)); 
	}
	
	/*
	 * SayaliA:Code added for payment by patient using external link oof doctor shared with patient
	 * No token required for this, as doctor and patient are not logging to system
	 */
	@RequestMapping(value="/payResponseForBookingLink", method=RequestMethod.POST)
	public ResponseEntity<MainResponseDTO<CommonResponseDTO>> updatePaymentDetails(@RequestBody RazorPayResponse razorPayResponse) {
		return ResponseEntity.status(HttpStatus.OK).body(payService.updatePaymentDetails(razorPayResponse)); 
	}
}
