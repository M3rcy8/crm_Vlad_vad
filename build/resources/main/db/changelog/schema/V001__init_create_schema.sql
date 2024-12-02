-- Создание таблицы пользователей
CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(50)  NOT NULL
);

-- Создание таблицы категорий
CREATE TABLE IF NOT EXISTS categories
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Создание таблицы продуктов
CREATE TABLE IF NOT EXISTS products
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    price       DECIMAL(10, 2) NOT NULL,
    category_id INT REFERENCES categories (id)
);

-- Создание таблицы клиентов
CREATE TABLE IF NOT EXISTS customers
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(255)        NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL
);

-- Создание таблицы заказов
CREATE TABLE IF NOT EXISTS orders
(
    id          SERIAL PRIMARY KEY,
    customer_id INT REFERENCES customers (id),
    order_date  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Создание таблицы сделки
CREATE TABLE IF NOT EXISTS deals
(
    id          SERIAL PRIMARY KEY,
    amount      DECIMAL(10, 2) NOT NULL,
    status      VARCHAR(255)   NOT NULL,
    customer_id BIGINT         NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    product_id  BIGINT         NOT NULL REFERENCES products (id) ON DELETE CASCADE
);
