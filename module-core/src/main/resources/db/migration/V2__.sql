CREATE TABLE follow_member
(
    follower_id  BIGINT NOT NULL,
    following_id BIGINT NOT NULL,
    CONSTRAINT pk_follow_member PRIMARY KEY (follower_id, following_id)
);

ALTER TABLE member
    ADD is_contact_list_synchronized BOOLEAN;

ALTER TABLE member
    ALTER COLUMN is_contact_list_synchronized SET NOT NULL;

ALTER TABLE follow_member
    ADD CONSTRAINT FK_FOLLOW_MEMBER_ON_FOLLOWER FOREIGN KEY (follower_id) REFERENCES member (member_id);

ALTER TABLE follow_member
    ADD CONSTRAINT FK_FOLLOW_MEMBER_ON_FOLLOWING FOREIGN KEY (following_id) REFERENCES member (member_id);

ALTER TABLE member
    ALTER COLUMN is_app_installed SET NOT NULL;

ALTER TABLE member
    DROP COLUMN role;

ALTER TABLE member
    ADD role VARCHAR(255);
