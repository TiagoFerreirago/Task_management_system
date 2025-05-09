CREATE TABLE user_permission (
id_user bigint NOT NULL,
id_permission bigint NOT NULL,
PRIMARY KEY (id_user, id_permission),
KEY fk_user_permission (id_permission),
CONSTRAINT fk_user_permission_users FOREIGN KEY (id_user) REFERENCES users (id),
CONSTRAINT fk_user_permission FOREIGN KEY (id_permission) REFERENCES permission (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;