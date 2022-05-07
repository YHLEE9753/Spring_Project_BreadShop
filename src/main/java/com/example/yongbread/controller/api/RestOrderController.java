package com.example.yongbread.controller.api;


import com.example.yongbread.controller.api.dto.Message;
import com.example.yongbread.controller.api.dto.StatusEnum;
import com.example.yongbread.model.Order;
import com.example.yongbread.model.product.Product;
import com.example.yongbread.service.OrderService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class RestOrderController {

    private final OrderService orderService;

    public RestOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String mainOrder(Model model){
        return "/api/orders/list";
    }

    @GetMapping("/detail")
    public ResponseEntity<Message> orderDetail(@PathVariable UUID orderId){
        // @PathVariable UUID orderId
        // 예시 ID
//        UUID orderId = UUID.fromString("0604aaea-a3f9-424b-9d59-27ef2c6a2c9b");
        Order order = orderService.orderFindById(orderId);

        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatus(StatusEnum.OK);
        message.setMessage("성공 코드");
        message.setData(order);

        return new ResponseEntity<>(message, headers, HttpStatus.OK);

    }
}
