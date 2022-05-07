package com.example.yongbread.controller.api;

import com.example.yongbread.model.product.Category;
import com.example.yongbread.model.product.Product;
import com.example.yongbread.model.product.ProductStatus;
import com.example.yongbread.model.product.SaleStatus;
import com.example.yongbread.repository.OrderRepository;
import com.example.yongbread.repository.ProductRepository;
import com.example.yongbread.service.OrderService;
import com.example.yongbread.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(RestProductController.class)
class RestProductControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;


    @Test
    @DisplayName("메인 화면 테스트 - Product List를 보여준다.")
    public void mainTest() throws Exception {
        // given
        String url = "/api/admin";
        String jjson = "{\"test\":\"test example\"}";

        // when

        // then
        mvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jjson))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Product Id 를 통한 Product 세부사항 표시")
    public void productDetailTest() throws Exception {
        // given
        UUID id = UUID.randomUUID();

        Product product = Product.builder()
                .productId(id)
                .productName("식빵")
                .category(Category.BREAD)
                .productStatus(ProductStatus.MAKING)
                .saleStatus(SaleStatus.SALE)
                .price(4000)
                .salePrice(300)
                .stock(10)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mockito.doAnswer(invocation -> 1L)
                .when(productService).createProduct(product);
        Mockito.doAnswer(invocation -> 1L)
                .when(productService).findById(id);

        // when
        productService.createProduct(product);
        productService.findById(id);

        // then
        Mockito.verify(productService, Mockito.times(1)).createProduct(product);
        Mockito.verify(productService, Mockito.times(1)).findById(id);

        String url = "/api/admin/" + id;

        mvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}