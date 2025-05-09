CREATE TABLE users (
id bigint NOT NULL AUTO_INCREMENT,
user_name varchar(255) NOT NULL,
full_name varchar(255) NOT NULL,
password varchar(255) NOT NULL,
account_non_expired boolean NOT NULL DEFAULT TRUE,
account_non_locked boolean NOT NULL DEFAULT TRUE,
credentials_non_expired boolean NOT NULL DEFAULT TRUE,
enabled boolean NOT NULL DEFAULT TRUE,
PRIMARY KEY (id),
UNIQUE KEY uk_username (user_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;