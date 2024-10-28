-- Create User table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) DEFAULT 'CUSTOMER'
);

-- Create Category table
CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- Create Product table
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    image_url VARCHAR(255),
    stock INT DEFAULT 0,
    category_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Create CartItem table
CREATE TABLE cart_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    product_id BIGINT,
    quantity INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Create Order table
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    total_amount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) DEFAULT 'PENDING',
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    shipping_address VARCHAR(255),
    payment_method VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create OrderItem table
CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT,
    product_id BIGINT,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

data
-- Insert sample users
INSERT INTO users (username, email, password, role) VALUES
('john_doe', 'john@example.com', 'password123', 'CUSTOMER'),
('admin_user', 'admin@example.com', 'adminpassword', 'ADMIN');

-- Insert sample categories
INSERT INTO categories (name) VALUES
('Books'),
('Electronics'),
('Clothing');

-- Insert sample products
INSERT INTO products (name, description, price, image_url, stock, category_id) VALUES
('Java Programming Book', 'A comprehensive guide to Java programming.', 49.99, 'https://images-na.ssl-images-amazon.com/images/I/61tv9vyDz2L.jpg', 50, 1),
('Smartphone', 'Latest model with 5G support.', 699.99, 'https://cdn.tgdd.vn/Products/Images/42/329150/iphone-16-pro-max-tu-nhien-thumb-600x600.jpg', 100, 2),
('T-Shirt', 'Comfortable cotton t-shirt.', 19.99, 'https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg', 200, 3);

-- Insert sample cart items (for user with id 1)
INSERT INTO cart_items (user_id, product_id, quantity) VALUES
(1, 1, 2), -- 2 Java books for user 1
(1, 2, 1); -- 1 Smartphone for user 1

-- Insert sample orders (for user with id 1)
INSERT INTO orders (user_id, total_amount, status, shipping_address, payment_method) VALUES
(1, 89.98, 'PENDING', '123 Main St', 'Credit Card');

-- Insert sample order items (for order with id 1)
INSERT INTO order_items (order_id, product_id, quantity, price) VALUES
(1, 1, 2, 49.99); -- 2 Java books at 49.99 each
