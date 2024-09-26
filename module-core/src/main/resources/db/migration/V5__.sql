ALTER TABLE message
    ADD is_read BOOLEAN;

ALTER TABLE message
    ALTER COLUMN is_read SET NOT NULL;
