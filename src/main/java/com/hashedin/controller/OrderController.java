package com.hashedin.controller;

import com.hashedin.constants.Constants;
import com.hashedin.dto.AddOderDto;
import com.hashedin.dto.GetOrderIDDto;
import com.hashedin.entity.OrdersInfo;
import com.hashedin.service.OrderService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Soundbank;
import java.util.List;

@RestController
@Log4j2
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping(value = "/buyer/addOrder")
    public ResponseEntity<String> addOrders(@RequestBody AddOderDto addOderDto, @RequestAttribute("id") Long id)
    {
        return new ResponseEntity<>(orderService.addOrder(addOderDto,id), HttpStatus.OK);
    }

    @GetMapping(value = "/buyer/getOrders")
    public ResponseEntity<List<JSONObject>> getOrders(@RequestAttribute("id") Long id)
    {
        return new ResponseEntity<>(orderService.getOrders(id),HttpStatus.OK);
    }

    @PostMapping(value = "/buyer/getOrderId")
    public ResponseEntity<JSONObject> addOrders(@RequestAttribute("id") Long id, @RequestBody GetOrderIDDto getOrderIDDto) throws RazorpayException, ParseException {
        return new ResponseEntity<>(orderService.getOrderID(getOrderIDDto,id),HttpStatus.OK);
    }

}
