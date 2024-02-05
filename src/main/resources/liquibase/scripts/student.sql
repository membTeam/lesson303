-- liquibase formatted sql

-- changeset lesson403_student:1
CREATE INDEX student_name_index ON student (name);
