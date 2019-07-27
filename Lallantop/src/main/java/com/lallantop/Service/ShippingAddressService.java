package com.lallantop.Service;

import com.lallantop.domain.ShippingAddress;
import com.lallantop.domain.UserShipping;

public interface ShippingAddressService {
  ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress);

}
