CREATE TABLE IF NOT EXISTS catalog(
    id bigserial primary key,
    ref_min double precision,
    ref_max double precision,
    name text,
    additional_name text,
    research_type text,
    unit text,

    UNIQUE(name, additional_name)
);

CREATE TABLE IF NOT EXISTS patient(
    id bigserial primary key,
    name text,
    lastname text,
    patronymic text,
    gender text,

    birthdate date,
    deathdate date,
    alive boolean,

    info text,
    main_diagnosis text,
    other_diagnosis text,
    chemotherapy_comments text,
    diagnosis_comments text,
    operation_comments text
);

CREATE TABLE IF NOT EXISTS oncological_tests(
    id bigserial primary key,
    test_date date,
    patient_id bigint,
    FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS parameter_result(
    id bigserial primary key,
    value double precision,
    catalog_id bigint,
    test_id bigint,

    FOREIGN KEY (catalog_id) REFERENCES catalog(id) ON DELETE CASCADE ,
    FOREIGN KEY (test_id) REFERENCES oncological_tests(id) ON DELETE CASCADE
);
