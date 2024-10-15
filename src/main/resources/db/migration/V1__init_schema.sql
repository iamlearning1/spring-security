CREATE TABLE authorities
(
    username     VARCHAR(255) NOT NULL,
    authority    VARCHAR(255),
    authority_id VARCHAR(255),
    CONSTRAINT pk_authorities PRIMARY KEY (username)
);

CREATE TABLE users
(
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255),
    enabled  BOOLEAN      NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (username)
);

ALTER TABLE authorities
    ADD CONSTRAINT FK_AUTHORITIES_ON_AUTHORITY FOREIGN KEY (authority_id) REFERENCES users (username);
