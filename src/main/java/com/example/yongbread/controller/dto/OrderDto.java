package com.example.yongbread.controller.dto;

import com.example.yongbread.model.Email;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private final List<Long> breadCount;
    private final List<Long> cookieCount;
    private final Email email;
    private String address;
    private String postcode;
}
