```sql
-- Tabla de usuarios del sistema
CREATE TABLE users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(255),
  name VARCHAR(255),
  lastname VARCHAR(255),
  email VARCHAR(255),
  pass VARCHAR(255), -- Guardar siempre hasheada (ej: bcrypt)
  photo VARCHAR(255),
  address VARCHAR(255), -- Texto libre (si no se usa direction_id)
  role VARCHAR(50),     -- Ej: 'admin', 'cliente'
  direction_id BIGINT,
  phone BIGINT,
  created_at TIMESTAMP,
  FOREIGN KEY (direction_id) REFERENCES adress(id)
);

-- Dirección estructurada (mejor que texto libre)
CREATE TABLE adress (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  street VARCHAR(255),
  num VARCHAR(10),
  flat VARCHAR(10),
  door VARCHAR(10),
  city VARCHAR(100),
  provincia VARCHAR(100),
  code VARCHAR(10)
);

-- Categorías de productos
CREATE TABLE categories (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100),
  description VARCHAR(255)
);

-- Productos disponibles en la tienda
CREATE TABLE products (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  description VARCHAR(1000),
  price DECIMAL(10,2),
  alergens JSON, -- Alternativa: usar tabla relacional product_alergen
  unit VARCHAR(20), -- Ej: 'kg', 'unidad', 'caja'
  stock_min DECIMAL(10,2),
  category_id BIGINT,
  FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Alérgenos presentes en productos
CREATE TABLE alergens (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100),
  photo VARCHAR(255)
);

-- Relación N:M entre productos y alérgenos
CREATE TABLE product_alergen (
  product_id BIGINT,
  alergen_id BIGINT,
  FOREIGN KEY (product_id) REFERENCES products(id),
  FOREIGN KEY (alergen_id) REFERENCES alergens(id),
  PRIMARY KEY (product_id, alergen_id)
);

-- Control del stock actual por producto
CREATE TABLE stock (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  product_id BIGINT,
  quantity DECIMAL(10,2),
  updated_at DATETIME,
  FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Registro histórico de movimientos de stock
CREATE TABLE stock_movements (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  product_id BIGINT,
  type ENUM('entrada', 'salida'),
  quantity DECIMAL(10,2),
  unit VARCHAR(20),
  reason VARCHAR(255),
  created_at DATETIME,
  FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Pedidos realizados por los usuarios
CREATE TABLE orders (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT,
  status ENUM('pendiente', 'pagado', 'enviado', 'entregado', 'cancelado') DEFAULT 'pendiente',
  total DECIMAL(10,2),
  shipping_cost DECIMAL(10,2) DEFAULT 0.00,
  payment_method_id BIGINT,
  shipping_address_id BIGINT,
  billing_address_id BIGINT,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (payment_method_id) REFERENCES payment_methods(id),
  FOREIGN KEY (shipping_address_id) REFERENCES adress(id),
  FOREIGN KEY (billing_address_id) REFERENCES adress(id)
);

-- Productos incluidos en cada pedido
CREATE TABLE order_items (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_id BIGINT,
  product_id BIGINT,
  quantity DECIMAL(10,2),
  unit_price DECIMAL(10,2),
  total_price DECIMAL(10,2),
  FOREIGN KEY (order_id) REFERENCES orders(id),
  FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Métodos de pago registrados por los usuarios
CREATE TABLE payment_methods (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT,
  provider VARCHAR(50),  -- Ej: 'stripe', 'paypal'
  token VARCHAR(255),    -- Token seguro proporcionado por la pasarela
  type VARCHAR(50),      -- Ej: 'card', 'paypal'
  last4 VARCHAR(4),
  brand VARCHAR(50),
  expiry_month INT,
  expiry_year INT,
  created_at DATETIME,
  FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Pagos realizados para pedidos
CREATE TABLE payments (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT,
  order_id BIGINT,
  payment_method_id BIGINT,
  provider VARCHAR(50),
  status VARCHAR(50),
  currency VARCHAR(3),
  paid_at DATETIME,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (order_id) REFERENCES orders(id),
  FOREIGN KEY (payment_method_id) REFERENCES payment_methods(id)
);

-- Imágenes adicionales de productos
CREATE TABLE product_photos (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  product_id BIGINT,
  url VARCHAR(255),
  alt_text VARCHAR(255),
  FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Valoraciones de productos por usuarios
CREATE TABLE reviews (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT,
  product_id BIGINT,
  rating INT, -- Valor entre 1 y 5
  comment TEXT,
  created_at TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Seguimiento de envíos
CREATE TABLE shipping_tracking (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_id BIGINT,
  carrier VARCHAR(100),
  tracking_number VARCHAR(100),
  status VARCHAR(100),
  updated_at TIMESTAMP,
  FOREIGN KEY (order_id) REFERENCES orders(id)
);

```