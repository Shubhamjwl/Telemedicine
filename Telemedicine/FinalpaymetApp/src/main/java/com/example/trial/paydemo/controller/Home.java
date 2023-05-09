package com.example.trial.paydemo.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.example.trial.paydemo.logger.LoggingClientInfo;
import com.example.trial.paydemo.model.Customer;
import com.example.trial.paydemo.model.RazorPay;
import com.example.trial.paydemo.model.Response;
import com.google.gson.Gson;
//import com.google.gson.Gson;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;




@Controller
@LoggingClientInfo
public class Home {
	
	private static final Logger logger = LoggerFactory.getLogger(Home.class);
	private static Gson gson = new Gson();
	
	
	@Value("${razorpay.key.id}")
	private String razorPayKeyId;
	
	@Value("${razorpay.key.secret}")
	private String razorPayKeySecret;
	
	
	@RequestMapping(value="/")
	public String getHome() {
		return "redirect:/home";
	}
	@RequestMapping(value="/home")
	public String getHomeInit() {
		return "home";
	}
	
	@RequestMapping(value="/pay", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> createOrder(@RequestBody Customer customer) {
			
		//testMethod();
		try {
					 
			/**
			 * creating an order in RazorPay.
			 * new order will have order id. you can get this order id by calling  order.get("id") */
			logger.info("Inside Pay Method..");
	//	RazorpayClient razorPayclient =new RazorpayClient("rzp_test_A73gWmyQVDczqX","02AXp9Fv3Va3cLKMJytMVCB9");
		RazorpayClient razorPayclient =new RazorpayClient(razorPayKeyId,razorPayKeySecret);
			//Order order = createRazorPayOrder( customer.getAmount() );
		logger.info("Razorpay Client created Successfully with KeyID.."+razorPayKeyId+"..and Key Secret.."+razorPayKeySecret);
		JSONObject options = new JSONObject();
		options.put("amount", customer.getAmount());
		options.put("currency", "INR");
		options.put("receipt", "txn_123456");
		options.put("payment_capture", 0); // You can enable this if you want to do Auto Capture. 
		//return client.Orders.create(options);
		//SSLVerification.disableSslVerification();
		logger.info	("Trying to connect with razorpay....");
		Order order =razorPayclient.Orders.create(options);
		logger.info("Connected Successsfully!!");
		logger.info("Order Created Successsfully!!");
		RazorPay razorPay = getRazorPay((String)order.get("id"), customer);
		logger.info("Requested order :"+gson.toJson(getResponse(razorPay, 200)));
			return new ResponseEntity<String>(gson.toJson(getResponse(razorPay, 200)),
					HttpStatus.OK);
		} catch (RazorpayException e) {
			logger.info("Exception occurred while processing:");
			e.printStackTrace();
		} 
		return new ResponseEntity<String>(gson.toJson(getResponse(new RazorPay(), 500)),
				HttpStatus.EXPECTATION_FAILED);
	}
	
	private Response getResponse(RazorPay razorPay, int statusCode) {
		Response response = new Response();
		response.setStatusCode(statusCode);
		response.setRazorPay(razorPay);
		return response;
	}	
	
	private RazorPay getRazorPay(String orderId, Customer customer) {
		RazorPay razorPay = new RazorPay();
		razorPay.setApplicationFee(convertRupeeToPaise(customer.getAmount()));
		razorPay.setCustomerName(customer.getCustomerName());
		razorPay.setCustomerEmail(customer.getEmail());
		razorPay.setMerchantName("Test");
		razorPay.setPurchaseDescription("TEST PURCHASES");
		razorPay.setRazorpayOrderId(orderId);
		razorPay.setSecretKey("rzp_test_A73gWmyQVDczqX");
		razorPay.setImageURL("/logo");
		razorPay.setTheme("#F37254");
		razorPay.setNotes("notes"+orderId);
		
		return razorPay;
	}
	
	/*
	 * private Order createRazorPayOrder(String amount) throws RazorpayException {
	 * 
	 * JSONObject options = new JSONObject(); options.put("amount",
	 * convertRupeeToPaise(amount)); options.put("currency", "INR");
	 * options.put("receipt", "txn_123456"); options.put("payment_capture", 1); //
	 * You can enable this if you want to do Auto Capture. return
	 * client.Orders.create(options); }
	 */
	
	private String convertRupeeToPaise(String paise) {
		BigDecimal b = new BigDecimal(paise);
		BigDecimal value = b.multiply(new BigDecimal("100"));
		return value.setScale(0, RoundingMode.UP).toString();
		 
	}
	
	public static void testMethod()
	{
		final String uri = "https://api.razorpay.com/v1/orders";

	    RestTemplate restTemplate = new RestTemplate();

	    HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

	    ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);

	   // System.out.println(result);
	}
	
	
}
