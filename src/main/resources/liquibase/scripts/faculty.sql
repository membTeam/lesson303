-- liquibase formatted sql

-- changeset lesson403_faculty:2
CREATE INDEX faculty_name_and_color_index ON faculty (name, color);
