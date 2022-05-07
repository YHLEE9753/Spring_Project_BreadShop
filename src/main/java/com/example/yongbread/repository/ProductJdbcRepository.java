package com.example.yongbread.repository;

import com.example.yongbread.exception.QueryException;
import com.example.yongbread.model.product.Category;
import com.example.yongbread.model.product.ProductStatus;
import com.example.yongbread.model.product.SaleStatus;
import com.example.yongbread.util.RepositoryUtil;
import com.example.yongbread.model.product.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProductJdbcRepository implements ProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProductJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Product> findAll() {
        final String findAllQuery = "select * from products";
        return jdbcTemplate.query(findAllQuery, productRowMapper);
    }

    @Override
    public Product insert(Product product) {
        final String insertQuery = "INSERT INTO products(product_id, product_name, category, product_status, sale_status, price, sale_price, stock, created_at, updated_at)" +
                " VALUES(UUID_TO_BIN(:productId), :productName, :category, :productStatus, :saleStatus, :price, :salePrice, :stock, :createdAt, :updatedAt)";
        int update = jdbcTemplate.update(insertQuery, toParamMap(product));
        if (update != 1) {
            throw new QueryException("Nothing was inserted");
        }
        return product;
    }

    @Override
    public Product update(Product product) {
        final String updateQuery = "UPDATE products SET product_name = :productName, category = :category, product_status = :productStatus, sale_status = :saleStatus, price = :price, sale_price = :salePrice, stock = :stock, created_at = :createdAt, updated_at = :updatedAt " +
                "WHERE product_id = UUID_TO_BIN(:productId)";
        int update = jdbcTemplate.update(updateQuery, toParamMap(product));

        if (update != 1) {
            throw new QueryException("Nothing was updated");
        }
        return product;
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        final String idQuery = "SELECT * FROM products WHERE product_id = UUID_TO_BIN(:productId)";
        try {
            return Optional.of(
                    jdbcTemplate.queryForObject(idQuery, Collections.singletonMap("productId", productId.toString().getBytes()), productRowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> findByName(String productName) {
        final String nameQuery = "SELECT * FROM products WHERE product_name = :productName";
        try {
            return Optional.of(
                    jdbcTemplate.queryForObject(nameQuery, Collections.singletonMap("productName", productName), productRowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findByCategory(Category category) {
        final String categoryQuery = "SELECT * FROM products WHERE category = :category";
        return jdbcTemplate.query(categoryQuery, Collections.singletonMap("category",category.toString()), productRowMapper);

    }

    @Override
    public List<Product> findBySaleStatus(SaleStatus saleStatus) {
        final String saleStatusQuery = "SELECT * FROM products WHERE sale_status = :saleStatus";
        return jdbcTemplate.query(saleStatusQuery, Collections.singletonMap("saleStatus",saleStatus.toString()), productRowMapper);
    }

    @Override
    public List<Product> findByProductStatus(ProductStatus productStatus) {
        final String productStatusQuery = "SELECT * FROM products WHERE product_status = :productStatus";
        return jdbcTemplate.query(productStatusQuery, Collections.singletonMap("productStatus",productStatus.toString()), productRowMapper);
    }

    @Override
    public int count() {
        final String countQuery = "select count(*) from products";
        return jdbcTemplate.queryForObject(countQuery, Collections.emptyMap(), Integer.class);
    }

    @Override
    public void deleteAll() {
        final String deleteAllQuery = "DELETE FROM products";
        jdbcTemplate.update(deleteAllQuery, Collections.emptyMap());
    }

    @Override
    public void deleteById(UUID productId) {
        final String deleteByIdQuery = "DELETE FROM products WHERE product_id = UUID_TO_BIN(:productId)";
        jdbcTemplate.update(deleteByIdQuery, Collections.singletonMap("productId", productId.toString().getBytes()));
    }


    private final RowMapper<Product> productRowMapper = (resultSet, i) -> {
        var productId = RepositoryUtil.toUUID(resultSet.getBytes("product_id"));
        var productName = resultSet.getString("product_name");
        var category = Category.valueOf(resultSet.getString("category"));
        var productStatus = ProductStatus.valueOf(resultSet.getString("product_status"));
        var saleStatus = SaleStatus.valueOf(resultSet.getString("sale_status"));
        var price = resultSet.getLong("price");
        var salePrice = resultSet.getLong("sale_price");
        var stock = resultSet.getLong("stock");
        var createdAt = RepositoryUtil.toLocalDateTime(resultSet.getTimestamp("created_at"));
        var updatedAt = RepositoryUtil.toLocalDateTime(resultSet.getTimestamp("updated_at"));

        return new Product(productId, productName, category, productStatus, saleStatus, price, salePrice, stock, createdAt, updatedAt);
    };

    private final Map<String, Object> toParamMap(Product product) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("productId", product.getProductId().toString().getBytes());
        paramMap.put("productName", product.getProductName());
        paramMap.put("category", product.getCategory().toString());
        paramMap.put("productStatus", product.getProductStatus().toString());
        paramMap.put("saleStatus", product.getSaleStatus().toString());
        paramMap.put("price", product.getPrice());
        paramMap.put("salePrice", product.getSalePrice());
        paramMap.put("stock", product.getStock());
        paramMap.put("createdAt", product.getCreatedAt());
        paramMap.put("updatedAt", product.getUpdatedAt());

        return paramMap;
    }

}
