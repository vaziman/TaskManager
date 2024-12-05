CREATE TABLE tasks (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       description TEXT,
                       completed BOOLEAN,
                       priority INT,
                       created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       status VARCHAR(50),
                       user_id BIGINT
);