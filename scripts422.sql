CREATE TABLE IF NOT EXISTS owner_car(
    id integer NOT NULL,
    full_name character varying(50) NOT NULL COLLATE pg_catalog."default",
    age integer NOT NULL default 16,
    driving_license boolean not null default true,
    car_id integer,
    CONSTRAINT owner_car_pk PRIMARY KEY (id),
    CONSTRAINT check_age CHECK (age >= 16),
    CONSTRAINT unique_full_name UNIQUE (full_name)
);

CREATE TABLE IF NOT EXISTS car(
    id integer NOT NULL,
    brand_car character varying(30) COLLATE pg_catalog."default" not null,
    model character varying(20) COLLATE pg_catalog."default" not null,
    cost integer not null,
    CONSTRAINT car_pkey PRIMARY KEY (id),
    CONSTRAINT unigque_brand_car UNIQUE (brand_car, model)
);

ALTER TABLE owner_car ADD FOREIGN KEY (car_id) REFERENCES car;

/*
select * from owner_car;
id  full_name               age driving_license car_id
------------------------------------------------------
1	"Петр Иванов"	        18	    true	    1
2	"Кирил Петров"	        25	    true	    2
3	"Василий Кравцов"	    32	    true	    3
4	"Валентина Иванова"	    40	    true	    1
5	"Антонина Константинова" 52	    true	    2
7	"Светлана Ивлева"	    40	    false
8	"Валентин Константинов"	27	    false

select * from car;
id  brand_car    model      cost
-----------------------------------
1	"ВАЗ"	    "2108"	    500000
2	"LADA"	    "Granta"	200000
3	"Acura"	    "RDX 1"	    150000

*/