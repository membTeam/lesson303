
CREATE TABLE IF NOT EXISTS public.student
(
    id bigint NOT NULL,
    age integer NOT NULL default 20,
    "name" character varying(100) NOT NULL COLLATE pg_catalog."default",
    faculty_id bigint,
    CONSTRAINT student_pkey PRIMARY KEY (id),
	CONSTRAINT check_age CHECK (age >= 16),
	CONSTRAINT unique_name_student UNIQUE ("name"),
    CONSTRAINT student_faculty_foreingkey FOREIGN KEY (faculty_id)
        REFERENCES public.faculty (id) MATCH SIMPLE     -- (простоеСовпадение)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

-- *******************************

CREATE TABLE IF NOT EXISTS public.facultyTemp
(
    id bigint NOT NULL,
    "name" character varying(100) COLLATE pg_catalog."default",
	color character varying(50) COLLATE pg_catalog."default",
    CONSTRAINT faculty_pkey PRIMARY KEY (id),
	CONSTRAINT unique_name_color UNIQUE ("name", color)
)
