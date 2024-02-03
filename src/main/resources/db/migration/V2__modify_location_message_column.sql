ALTER TABLE location_message
    DROP COLUMN position;

ALTER TABLE location_message
    ADD position GEOMETRY(Point, 4326);
