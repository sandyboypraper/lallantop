package com.lallantop.Service;

import com.lallantop.domain.Payment;
import com.lallantop.domain.UserPayments;

public interface PaymentService {
	Payment setByUserPayment(UserPayments userPayment, Payment payment);
}
