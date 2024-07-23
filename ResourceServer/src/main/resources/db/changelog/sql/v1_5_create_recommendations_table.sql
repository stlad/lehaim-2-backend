CREATE TABLE IF NOT EXISTS recommendations
(
    id                  bigserial primary key,

    range_min           double precision,
    range_max           double precision,

    recommendation_name text,
    conclusion          text,
    recommendation      text,
    diagnosis_id        int,
    first_param_id      bigint,
    second_param_id     bigint,

    date_created        timestamp,
    date_updated        timestamp,

    FOREIGN KEY (first_param_id) REFERENCES parameter_catalog (id) ON DELETE CASCADE,
    FOREIGN KEY (second_param_id) REFERENCES parameter_catalog (id) ON DELETE CASCADE,
    FOREIGN KEY (diagnosis_id) REFERENCES diagnoses_catalog (id) ON DELETE CASCADE
);