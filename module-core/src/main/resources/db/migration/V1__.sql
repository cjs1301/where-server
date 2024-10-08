CREATE TABLE channel
(
    channel_id        UUID NOT NULL,
    channel_type      VARCHAR(31),
    created_at        TIMESTAMP WITH TIME ZONE,
    updated_at        TIMESTAMP WITH TIME ZONE,
    last_message      VARCHAR(255),
    last_message_time TIMESTAMP WITHOUT TIME ZONE,
    name              VARCHAR(255),
    CONSTRAINT pk_channel PRIMARY KEY (channel_id)
);

CREATE TABLE channel_membership
(
    membership_id UUID NOT NULL,
    connection_id VARCHAR(255),
    channel_id    UUID,
    member_id     BIGINT,
    joined_at     TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_channel_membership PRIMARY KEY (membership_id)
);

CREATE TABLE follow_member
(
    follower_id  BIGINT NOT NULL,
    following_id BIGINT NOT NULL,
    CONSTRAINT pk_follow_member PRIMARY KEY (follower_id, following_id)
);

CREATE TABLE location
(
    location_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE,
    updated_at  TIMESTAMP WITH TIME ZONE,
    route       GEOMETRY(LineString, 4326),
    channel_id  UUID,
    member_id   BIGINT,
    CONSTRAINT pk_location PRIMARY KEY (location_id)
);

CREATE TABLE member
(
    member_id                    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name                         VARCHAR(50),
    is_enabled                   BOOLEAN,
    phone_number                 VARCHAR(255),
    profile_image                VARCHAR(255),
    is_registered                BOOLEAN                                 NOT NULL,
    is_contact_list_synchronized BOOLEAN                                 NOT NULL,
    role                         VARCHAR(255),
    CONSTRAINT pk_member PRIMARY KEY (member_id)
);

CREATE TABLE message
(
    message_id UUID NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE,
    updated_at TIMESTAMP WITH TIME ZONE,
    message    VARCHAR(255),
    channel_id UUID,
    member_id  BIGINT,
    CONSTRAINT pk_message PRIMARY KEY (message_id)
);

ALTER TABLE channel_membership
    ADD CONSTRAINT uc_f1adca98adeb0b091c0244ef7 UNIQUE (channel_id, member_id);

ALTER TABLE member
    ADD CONSTRAINT uc_member_phone_number UNIQUE (phone_number);

CREATE INDEX idx_member_phone_number ON member (phone_number);

ALTER TABLE channel_membership
    ADD CONSTRAINT FK_CHANNEL_MEMBERSHIP_ON_CHANNEL FOREIGN KEY (channel_id) REFERENCES channel (channel_id);

ALTER TABLE channel_membership
    ADD CONSTRAINT FK_CHANNEL_MEMBERSHIP_ON_MEMBER FOREIGN KEY (member_id) REFERENCES member (member_id);

ALTER TABLE follow_member
    ADD CONSTRAINT FK_FOLLOW_MEMBER_ON_FOLLOWER FOREIGN KEY (follower_id) REFERENCES member (member_id);

ALTER TABLE follow_member
    ADD CONSTRAINT FK_FOLLOW_MEMBER_ON_FOLLOWING FOREIGN KEY (following_id) REFERENCES member (member_id);

ALTER TABLE location
    ADD CONSTRAINT FK_LOCATION_ON_CHANNEL FOREIGN KEY (channel_id) REFERENCES channel (channel_id);

ALTER TABLE location
    ADD CONSTRAINT FK_LOCATION_ON_MEMBER FOREIGN KEY (member_id) REFERENCES member (member_id);

ALTER TABLE message
    ADD CONSTRAINT FK_MESSAGE_ON_CHANNEL FOREIGN KEY (channel_id) REFERENCES channel (channel_id);

ALTER TABLE message
    ADD CONSTRAINT FK_MESSAGE_ON_MEMBER FOREIGN KEY (member_id) REFERENCES member (member_id);
