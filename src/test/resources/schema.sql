CREATE TABLE products
(
    product_id     BINARY(16) PRIMARY KEY,
    product_name   VARCHAR(20) NOT NULL,
    category       VARCHAR(20) NOT NULL,
    product_status VARCHAR(20) NOT NULL,
    sale_status    VARCHAR(20) NOT NULL,
    price          bigint      NOT NULL,
    sale_price     bigint      NOT NULL,
    stock          bigint      NOT NULL,
    created_at     datetime(6) NOT NULL,
    updated_at     datetime(6) DEFAULT NULL
);

CREATE TABLE orders
(
    order_id     binary(16) PRIMARY KEY,
    email        VARCHAR(50)  NOT NULL,
    address      VARCHAR(200) NOT NULL,
    postcode     VARCHAR(200) NOT NULL,
    order_status VARCHAR(50)  NOT NULL,
    created_at   datetime(6)  NOT NULL,
    updated_at   datetime(6) DEFAULT NULL
);

CREATE TABLE order_items
(
    seq        bigint      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    order_id   binary(16)  NOT NULL,
    product_id binary(16)  NOT NULL,
    category   VARCHAR(50) NOT NULL,
    price      bigint      NOT NULL,
    quantity   int         NOT NULL,
    created_at datetime(6) NOT NULL,
    updated_at datetime(6) DEFAULT NULL
--     INDEX (order_id),
--     CONSTRAINT fk_order_items_to_order FOREIGN KEY (order_id) REFERENCES orders (order_id) ON DELETE CASCADE,
--     CONSTRAINT fk_order_items_to_product FOREIGN KEY (product_id) REFERENCES products (product_id)
);

INSERT INTO products(product_id, product_name, category, product_status, sale_status, price,sale_price, stock, created_at, updated_at)VALUES (1, "Lee", "BREAD", "MAKING", "NORMAL", 1000, 0, 4, "1998-12-31","1998-12-31");
INSERT INTO products(product_id, product_name, category, product_status, sale_status, price,sale_price, stock, created_at, updated_at)VALUES (2, "Yong", "BREAD", "ON_SALE", "NORMAL", 2000, 0, 10, "1998-12-31","1998-12-31");
INSERT INTO products(product_id, product_name, category, product_status, sale_status, price,sale_price, stock, created_at, updated_at)VALUES (3, "Hoon", "BREAD", "ON_SALE", "NORMAL", 3000, 0, 10, "1998-12-31","1998-12-31");
INSERT INTO products(product_id, product_name, category, product_status, sale_status, price,sale_price, stock, created_at, updated_at)VALUES (4, "Choi", "COOKIE", "ON_SALE", "SALE", 4000, 400, 10, "1998-12-31","1998-12-31");
INSERT INTO products(product_id, product_name, category, product_status, sale_status, price,sale_price, stock, created_at, updated_at)VALUES (5, "She", "COOKIE", "MAKING", "SALE", 5000, 500, 0, "1998-12-31","1998-12-31");
