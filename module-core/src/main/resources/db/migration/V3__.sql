ALTER TABLE channel
    ALTER COLUMN channel_type TYPE VARCHAR(255) USING (channel_type::VARCHAR(255));
