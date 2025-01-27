CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    email    VARCHAR(100) NOT NULL,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    name     VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS topics
(
    id         SERIAL PRIMARY KEY,
    user_id    INT NOT NULL,
    title       VARCHAR(255) NOT NULL,
    description       VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user_posts FOREIGN KEY (user_id) REFERENCES users (id)
);

