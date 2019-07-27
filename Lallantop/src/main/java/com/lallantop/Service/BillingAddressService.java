package com.lallantop.Service;

import com.lallantop.domain.BillingAddress;
import com.lallantop.domain.UserBilling;

public interface BillingAddressService {
	BillingAddress setByUserBilling(UserBilling userBilling, BillingAddress billingAddress);
}
