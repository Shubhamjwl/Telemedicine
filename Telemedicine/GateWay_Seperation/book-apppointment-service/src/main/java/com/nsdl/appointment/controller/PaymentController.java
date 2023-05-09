package com.nsdl.appointment.controller;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.appointment.entity.PaymentDtlsEntity;
import com.nsdl.appointment.repository.PaymentDtlsRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/")
public class PaymentController {
	
	@Autowired
	private PaymentDtlsRepository paymentDtlsRepository;
	
	@PostMapping(value = "/dummyPaymentApi", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> savePayment() {
		
		PaymentDtlsEntity paymentDtlsEntity = new PaymentDtlsEntity();
		
		UUID uuid = UUID.randomUUID();
		paymentDtlsEntity.setPdPmtTransId(uuid.toString());
		paymentDtlsEntity.setPdPmtMode("online");
		paymentDtlsEntity.setPdAmount(100);
		paymentDtlsEntity.setPdBankRefTransId(new Random().nextInt(100000000));
		paymentDtlsEntity.setPdPmtDate(new Date());
		paymentDtlsEntity.setPdPmtStatus("Y");
		paymentDtlsEntity.setPdCardType("visa");
		paymentDtlsEntity.setPdCardNo(123463);
		paymentDtlsEntity.setPdNameOnCard("adam");
		paymentDtlsEntity.setPdCardValidity("07-2024");
		paymentDtlsEntity.setPrdOptiVersion(1);
		
		paymentDtlsRepository.save(paymentDtlsEntity);
		return ResponseEntity.status(HttpStatus.OK).body(uuid.toString());
	}

}
