CREATE TABLE recommendations(
    id serial primary key,

    range_min double precision,
    range_max double precision,

    name text,
    conclusion text,
    recommendation text,
-- КОД ДИАГНОЗА???
    first_param_id bigint,
    second_param_id bigint,

    date_created timestamp,
    date_updated timestamp,

    FOREIGN KEY (first_param_id) REFERENCES parameter_catalog(id) ON DELETE CASCADE,
    FOREIGN KEY (second_param_id) REFERENCES parameter_catalog(id) ON DELETE CASCADE,
)