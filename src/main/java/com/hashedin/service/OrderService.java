package com.hashedin.service;

import com.hashedin.dto.AddOderDto;
import com.hashedin.dto.GetOrderIDDto;
import com.hashedin.entity.OrdersInfo;
import com.razorpay.RazorpayException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.List;

public interface OrderService {
    String addOrder(AddOderDto addOderDto, Long id);

    List<JSONObject> getOrders(Long id);

    JSONObject getOrderID(GetOrderIDDto getOrderIDDto,Long id) throws RazorpayException, ParseException;
}
