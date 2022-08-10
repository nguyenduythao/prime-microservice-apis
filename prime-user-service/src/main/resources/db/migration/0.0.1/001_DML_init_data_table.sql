# oauth_client_details data
INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, authorities,
                                  access_token_validity, refresh_token_validity)
VALUES ('clientId', '{bcrypt}$2a$10$fEb9KwsgjXwjIaHksKb1BuzUV2JvqEtSTEV3KRb9.S0dJO68qJrq.', 'read,write',
        'password,refresh_token,client_credentials', '', 3600, 86400);

# prime_role data
INSERT INTO prime_role (role_id, role_name) VALUES (1, 'ROLE_ADMIN');

INSERT INTO prime_role (role_id, role_name) VALUES (2, 'ROLE_MANAGER');

INSERT INTO prime_role (role_id, role_name) VALUES (3, 'ROLE_USER');

ALTER TABLE prime_role AUTO_INCREMENT=4;

# prime_user data
INSERT INTO prime_user (user_id, username, email, password, user_status, role_id)
VALUES (1, 'prime_admin','admin@gmail.com','{bcrypt}$2a$10$fEb9KwsgjXwjIaHksKb1BuzUV2JvqEtSTEV3KRb9.S0dJO68qJrq.',1,1);

INSERT INTO prime_user (user_id, username, email, password, user_status, role_id)
VALUES (2, 'prime_manager','manager@gmail.com','{bcrypt}$2a$10$fEb9KwsgjXwjIaHksKb1BuzUV2JvqEtSTEV3KRb9.S0dJO68qJrq.',1,1);

INSERT INTO prime_user (user_id, username, email, password, user_status, role_id)
VALUES (3, 'prime_user','user@gmail.com','{bcrypt}$2a$10$fEb9KwsgjXwjIaHksKb1BuzUV2JvqEtSTEV3KRb9.S0dJO68qJrq.',1,1);

ALTER TABLE prime_user AUTO_INCREMENT=4;

# prime_permission data
INSERT INTO prime_permission (permission_id, permission_name, description, is_active)
VALUES (1,'oauth_client_details','Operator with OAUTH_CLIENT_DETAILS table', true);

INSERT INTO prime_permission (permission_id, permission_name, description, is_active)
VALUES (2,'role','Operator with ROLE table', true);

INSERT INTO prime_permission (permission_id, permission_name, description, is_active)
VALUES (3,'permission','Operator with PERMISSION table', true);

INSERT INTO prime_permission (permission_id, permission_name, description, is_active)
VALUES (4,'role_permission','Operator with ROLE_PERMISSION table', true);

INSERT INTO prime_permission (permission_id, permission_name, description, is_active)
VALUES (5,'user','Operator with USER table', true);

INSERT INTO prime_permission (permission_id, permission_name, description, is_active)
VALUES (6,'user_detail','Operator with USER_DETAIL table', true);

INSERT INTO prime_permission (permission_id, permission_name, description, is_active)
VALUES (7,'token','Operator with TOKEN table', true);

ALTER TABLE prime_permission AUTO_INCREMENT=8;

#Set role admin full permission
INSERT INTO prime_role_permission(role_id, permission_id, read_flag, insert_flag, update_flag, delete_flag, is_active)
VALUES(1,1,true,true,true,true,true);
INSERT INTO prime_role_permission(role_id, permission_id, read_flag, insert_flag, update_flag, delete_flag, is_active)
VALUES(1,2,true,true,true,true,true);
INSERT INTO prime_role_permission(role_id, permission_id, read_flag, insert_flag, update_flag, delete_flag, is_active)
VALUES(1,3,true,true,true,true,true);
INSERT INTO prime_role_permission(role_id, permission_id, read_flag, insert_flag, update_flag, delete_flag, is_active)
VALUES(1,4,true,true,true,true,true);
INSERT INTO prime_role_permission(role_id, permission_id, read_flag, insert_flag, update_flag, delete_flag, is_active)
VALUES(1,5,true,true,true,true,true);
INSERT INTO prime_role_permission(role_id, permission_id, read_flag, insert_flag, update_flag, delete_flag, is_active)
VALUES(1,6,true,true,true,true,true);
INSERT INTO prime_role_permission(role_id, permission_id, read_flag, insert_flag, update_flag, delete_flag, is_active)
VALUES(1,7,true,true,true,true,true);

#Set role manager full permission - Table role_permission(4) only read, user(5), user_detail(6), token(7)
INSERT INTO prime_role_permission(role_id, permission_id, read_flag, insert_flag, update_flag, delete_flag, is_active)
VALUES(2,4,true,false,false,false,true);
INSERT INTO prime_role_permission(role_id, permission_id, read_flag, insert_flag, update_flag, delete_flag, is_active)
VALUES(2,5,true,true,true,true,true);
INSERT INTO prime_role_permission(role_id, permission_id, read_flag, insert_flag, update_flag, delete_flag, is_active)
VALUES(2,6,true,true,true,true,true);
INSERT INTO prime_role_permission(role_id, permission_id, read_flag, insert_flag, update_flag, delete_flag, is_active)
VALUES(2,7,true,true,true,true,true);

#Set role user full permission - Table user(5), user_detail(6)
INSERT INTO prime_role_permission(role_id, permission_id, read_flag, insert_flag, update_flag, delete_flag, is_active)
VALUES(3,5,true,false,true,false,true);
INSERT INTO prime_role_permission(role_id, permission_id, read_flag, insert_flag, update_flag, delete_flag, is_active)
VALUES(3,6,true,false,true,false,true);


