package com.lallantop.Service.impl;

import org.springframework.stereotype.Service;

import com.lallantop.Service.PaymentService;
import com.lallantop.domain.Payment;
import com.lallantop.domain.UserPayments;

@Service
public class PaymentServiceImpl implements PaymentService{
	
	@Override
	public Payment setByUserPayment(UserPayments userPayment, Payment payment) {
		payment.setType(userPayment.getType());
		payment.setHolderName(userPayment.getHolderName());
		payment.setCardNumber(userPayment.getCardNumber());
		payment.setExpiryMonth(userPayment.getExpiryMonth());
		payment.setExpiryYear(userPayment.getExpiryYear());
		payment.setCVC(userPayment.getCVC());
		
		return payment;
	}

}
