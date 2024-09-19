ALTER TABLE member
    ADD is_registered BOOLEAN;

ALTER TABLE member
    ALTER COLUMN is_registered SET NOT NULL;

ALTER TABLE member
    DROP COLUMN is_app_installed;

CREATE INDEX idx_member_phone_number ON member (phone_number);
