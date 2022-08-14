CREATE TABLE IF NOT EXISTS oauth_client_details
(
    client_id               varchar(255) primary key,
    resource_ids            varchar(255),
    client_secret           varchar(255) not null,
    scope                   varchar(255),
    authorized_grant_types  varchar(255),
    web_server_redirect_uri varchar(255),
    authorities             varchar(255),
    access_token_validity   int,
    refresh_token_validity  int,
    additional_information  varchar(4000),
    autoapprove             varchar(255)
);

-- For service user service
CREATE TABLE IF NOT EXISTS prime_role
(
    role_id          int         not null auto_increment primary key,
    role_name        varchar(64) not null unique,
    create_timestamp timestamp,
    update_timestamp timestamp
);

CREATE TABLE IF NOT EXISTS prime_permission
(
    permission_id    int         not null auto_increment primary key,
    permission_name  varchar(64) not null unique,
    description      varchar(255),
    is_active        boolean     not null default true,
    create_timestamp timestamp,
    update_timestamp timestamp
);

CREATE TABLE IF NOT EXISTS prime_user
(
    user_id               int          not null auto_increment primary key,
    username              varchar(64)  not null unique,
    email                 varchar(128) not null unique,
    password              varchar(255) not null,
    last_password         text,
    failed_attempt        int,
    locked_time           timestamp,
    password_changed_time timestamp,
    user_status           int,
    role_id               int,
    create_timestamp      timestamp,
    update_timestamp      timestamp,
    foreign key (role_id) REFERENCES prime_role (role_id)
);

CREATE TABLE IF NOT EXISTS prime_user_detail
(
    user_id               int         not null primary key,
    first_name            varchar(64) not null,
    last_name             varchar(64) not null,
    gender                int         not null,
    dob                   varchar(32),
    address               varchar(255),
    created_operator      int,
    create_timestamp      timestamp,
    last_updated_operator int,
    update_timestamp      timestamp,
    foreign key (user_id) REFERENCES prime_user (user_id)
);

CREATE TABLE IF NOT EXISTS prime_role_permission
(
    role_id          int     not null,
    permission_id    int     not null,
    read_flag        boolean not null default true,
    write_flag       boolean not null default true,
    delete_flag      boolean not null default true,
    is_active        boolean not null default true,
    create_timestamp timestamp,
    update_timestamp timestamp,
    primary key (role_id, permission_id),
    foreign key (role_id) REFERENCES prime_role (role_id),
    foreign key (permission_id) REFERENCES prime_permission (permission_id)
);

CREATE TABLE IF NOT EXISTS prime_token
(
    token_id         int          not null auto_increment primary key,
    token            varchar(255) not null unique,
    expire_time      timestamp    not null,
    token_type       int,
    reference_id     int,
    create_timestamp timestamp,
    update_timestamp timestamp
);