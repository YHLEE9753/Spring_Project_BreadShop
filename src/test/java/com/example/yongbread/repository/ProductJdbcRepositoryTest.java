package com.example.yongbread.repository;

import com.example.yongbread.model.product.Category;
import com.example.yongbread.model.product.Product;
import com.example.yongbread.model.product.ProductStatus;
import com.example.yongbread.model.product.SaleStatus;
import com.example.yongbread.repository.ProductRepository;
import com.example.yongbread.testcontainer.AbstractContainerDatabaseTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductJdbcRepositoryTest extends AbstractContainerDatabaseTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeAll
    void beforeAll() {

    }

    private Product makeProduct(UUID id, LocalDateTime time){
        Product product = Product.builder()
                .productId(id)
                .productName("식빵")
                .category(Category.BREAD)
                .productStatus(ProductStatus.MAKING)
                .saleStatus(SaleStatus.SALE)
                .price(4000)
                .salePrice(300)
                .stock(10)
                .createdAt(time)
                .updatedAt(time)
                .build();
        return product;
    }

    @Test
    @Order(1)
    @DisplayName("table 에 있는 모든 products 를 확인한다.")
    public void allProductes() throws Exception {
        // given
        List<Product> all = productRepository.findAll();

        // when

        // then
        assertEquals("Lee", all.get(0).getProductName());
        assertEquals("Yong", all.get(1).getProductName());
        assertEquals("Hoon", all.get(2).getProductName());
        assertEquals("Choi", all.get(3).getProductName());
        assertEquals("She", all.get(4).getProductName());
    }

    @Test
    @Order(2)
    @DisplayName("table 에 있는 products 개수를 센다.")
    public void countTest() throws Exception {
        // given
        int count = productRepository.count();

        // when

        // then
        assertEquals(5, count);
    }

    @Test
    @Order(3)
    @DisplayName("table 에 product 를 insert 하고 findby Id 를 통해 같은 객체인지 확인.")
    public void insertAndFindByIdTest() throws Exception {
        // given
        UUID id = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now();
        Product product = makeProduct(id, time);

        // when
        productRepository.insert(product);

        // then
        Product insertedProduct = productRepository.findById(id).get();
        assertEquals("식빵", insertedProduct.getProductName());
        assertEquals(Category.BREAD, insertedProduct.getCategory());
        assertEquals(ProductStatus.MAKING, insertedProduct.getProductStatus());
        assertEquals(SaleStatus.SALE, insertedProduct.getSaleStatus());
        assertEquals(4000, insertedProduct.getPrice());
        assertEquals(300, insertedProduct.getSalePrice());
        assertEquals(10, insertedProduct.getStock());
        assertEquals(time, insertedProduct.getCreatedAt());
        assertEquals(time, insertedProduct.getUpdatedAt());
    }

    @Test
    @Order(4)
    @DisplayName("table 에있는 객체를 update 한다.")
    public void updateTest() throws Exception {
        // given
        UUID id = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now();
        Product product = makeProduct(id, time);
        productRepository.insert(product);

        // when
        product.changeProductStatus(ProductStatus.SOLD_OUT);
        productRepository.update(product);

        // then
        Product updatedProduct = productRepository.findById(id).get();
        assertEquals(ProductStatus.SOLD_OUT, updatedProduct.getProductStatus());
    }

    @Test
    @Order(5)
    @DisplayName("table 에있는 객체를 all delete 한다.")
    public void deleteAll() throws Exception {
        // given

        // when
        productRepository.deleteAll();

        // then
        assertEquals(0, productRepository.count());
    }

    @Test
    @Order(6)
    @DisplayName("Category 로 찾기")
    public void findByCategoryTest() throws Exception {
        // given
        UUID id = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now();
        Product product = makeProduct(id, time);
        productRepository.insert(product);

        // when
        List<Product> productByCategory = productRepository.findByCategory(Category.BREAD);
        Product getProduct = productByCategory.get(0);

        // then
        assertEquals(id, getProduct.getProductId());
    }

    @Test
    @Order(7)
    @DisplayName("SaleStatus 로 찾기")
    public void findBySaleStatus () throws Exception{
        // given
        productRepository.deleteAll();
        UUID id = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now();
        Product product = makeProduct(id, time);
        productRepository.insert(product);

        // when
        List<Product> productByCategory = productRepository.findBySaleStatus(SaleStatus.SALE);
        Product getProduct = productByCategory.get(0);

        // then
        assertEquals(id, getProduct.getProductId());
    }

    @Test
    @Order(8)
    @DisplayName("ProductStatus 로 찾기")
    public void findByProductStatus () throws Exception{
        // given
        productRepository.deleteAll();
        UUID id = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now();
        Product product = makeProduct(id, time);
        productRepository.insert(product);

        // when
        List<Product> productByCategory = productRepository.findByProductStatus(ProductStatus.MAKING);
        Product getProduct = productByCategory.get(0);

        // then
        assertEquals(id, getProduct.getProductId());
    }

    @Test
    @Order(9)
    @DisplayName("name 로 찾기")
    public void findByName () throws Exception{
        // given
        productRepository.deleteAll();
        UUID id = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now();
        Product product = makeProduct(id, time);
        productRepository.insert(product);

        // when
        Product getProduct = productRepository.findByName("식빵").get();

        // then
        assertEquals(id, getProduct.getProductId());
    }

}