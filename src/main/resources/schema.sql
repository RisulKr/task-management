CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       enabled BOOLEAN DEFAULT TRUE
);

CREATE TABLE roles (
                       id SERIAL PRIMARY KEY,
                       role_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE user_roles (
                            user_id INTEGER REFERENCES users(id),
                            role_id INTEGER REFERENCES roles(id),
                            PRIMARY KEY (user_id, role_id)
);