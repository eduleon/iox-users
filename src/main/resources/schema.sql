CREATE TABLE user (
    id SERIAL,
    email VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    user_name VARCHAR(255),
    phone VARCHAR(20),
    PRIMARY KEY (id),
    UNIQUE (email)
);
