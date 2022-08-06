package com.hashedin.service.Impl;

import com.hashedin.constants.Constants;
import com.hashedin.dto.AddOderDto;
import com.hashedin.dto.GetOrderIDDto;
import com.hashedin.entity.EmailTemplate;
import com.hashedin.entity.OrdersInfo;
import com.hashedin.entity.ProductInfo;
import com.hashedin.repository.AddToCartRepository;
import com.hashedin.repository.OrderInfoRepository;
import com.hashedin.repository.ProductInfoRepository;
import com.hashedin.repository.UserInfoRepository;
import com.hashedin.service.OrderService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import freemarker.template.TemplateException;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderInfoRepository orderInfoRepository;
    @Autowired
    ProductInfoRepository productInfoRepository;
    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    EmailServiceImpl emailServiceImpl;
    @Autowired
    AddToCartRepository addToCartRepository;
    @Override
    public String addOrder(AddOderDto addOderDto, Long id) {
        ProductInfo productInfo = productInfoRepository.getById(addOderDto.getProductId());
        OrdersInfo ordersInfo = new OrdersInfo();
        ordersInfo.setOrderIdString(addOderDto.getOrderIdString());
        ordersInfo.setProductId(addOderDto.getProductId());
        ordersInfo.setUserId(addOderDto.getUserId());
        ordersInfo.setTransactionIdString(addOderDto.getTransactionIdString());
        ordersInfo.setPlanDuration(productInfo.getPlanDuration());
        ordersInfo.setCreatedAt(LocalDate.now());
        orderInfoRepository.save(ordersInfo);

        addToCartRepository.deleteById(addOderDto.getCartId());
        //Email to user for order confirmation

        EmailTemplate email = new EmailTemplate();
        String userEmailId= userInfoRepository.getEmailByUserId(id);
        email.setTo(userEmailId);
        email.setFrom(Constants.SENTFROM);
        email.setSubject(Constants.MAILSUBJECTORDERCONFIRMATION);
        email.setContent(Constants.MAILCONTENTORDERCONFIRMATION);

        float finalPrice = Math.round(productInfo.getMrp() - (float) ((productInfo.getMrp()*productInfo.getDiscount())/100));


        Map<String, Object> model = new HashMap<>();

        // email content
        model.put("orderId", addOderDto.getOrderIdString());
        model.put("productId", addOderDto.getProductId());
        model.put("transactionId", addOderDto.getTransactionIdString());
        model.put("planDuration", productInfo.getPlanDuration());
        model.put("mrpOfPlan", productInfo.getMrp());
        model.put("discountOffered", productInfo.getDiscount());
        model.put("planName", productInfo.getPlanName());
        model.put("finalPrice",finalPrice);


        email.setModel((HashMap<String, Object>) model);
        try {
            emailServiceImpl.sendEmailTemplateToUserOrderConfirmation(email);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return Constants.OrderAddedMSG;
    }

    @Override
    public List<JSONObject> getOrders(Long id) {
        List<OrdersInfo> ordersInfos = orderInfoRepository.getOrders(id);

        List<JSONObject> resultSet = new ArrayList<>();
        for(OrdersInfo ordersInfo : ordersInfos)
        {
            JSONObject j = new JSONObject();
            j.put("product",productInfoRepository.getProductByPID(ordersInfo.getProductId()));
            j.put("orderIdString",ordersInfo.getOrderIdString());
            j.put("transactionIdString",ordersInfo.getTransactionIdString());
            j.put("planDuration",ordersInfo.getPlanDuration());
            j.put("createdAt",ordersInfo.getCreatedAt());
            resultSet.add(j);
        }

        return resultSet;
    }

    @Override
    public JSONObject getOrderID(GetOrderIDDto getOrderIDDto,Long userId) throws RazorpayException, ParseException {
        RazorpayClient razorpay = new RazorpayClient(Constants.KEY, Constants.SECRET);
        Random random = new Random();
        String id = String.format("%04d", random.nextInt(10000));
        String rcp = "rcp"+id;
        org.json.JSONObject orderRequest = new org.json.JSONObject();
        orderRequest.put("amount", getOrderIDDto.getAmount()); // amount in the smallest currency unit
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", rcp);
        Order order = razorpay.Orders.create(orderRequest);
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(order.toString());
        json.put("email",userInfoRepository.getEmailByUserId(userId));
        return json;
    }
}
