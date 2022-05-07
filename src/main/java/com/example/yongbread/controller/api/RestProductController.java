package com.example.yongbread.controller.api;

import com.example.yongbread.controller.api.dto.Message;
import com.example.yongbread.controller.api.dto.StatusEnum;
import com.example.yongbread.model.product.Product;
import com.example.yongbread.service.ProductService;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
public class RestProductController {

    private final ProductService productService;

    public RestProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Message> mainAdmin(){
        List<Product> products = productService.findAllProducts();

        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatus(StatusEnum.OK);
        message.setMessage("성공 코드");
        message.setData(products);

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Message> productDetail(@PathVariable UUID productId){
        // @PathVariable UUID productId
        // 예시 ID
//        UUID productId = UUID.fromString("ee64117e-a9d8-42bb-8dd4-85808dc8055a");
        Product product = productService.findById(productId);

        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatus(StatusEnum.OK);
        message.setMessage("성공 코드");
        message.setData(product);

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
}
