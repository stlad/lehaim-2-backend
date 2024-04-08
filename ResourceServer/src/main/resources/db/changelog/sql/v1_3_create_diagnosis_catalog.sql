ALTER TABLE catalog RENAME TO parameter_catalog;

CREATE TABLE diagnoses_catalog
(
    id          serial primary key,
    code        varchar(10),
    description text
);

ALTER TABLE patient DROP COLUMN main_diagnosis;
ALTER TABLE patient DROP COLUMN other_diagnosis;
ALTER TABLE patient ADD COLUMN diagnosis_id int;
ALTER TABLE patient ADD COLUMN T varchar(10);
ALTER TABLE patient ADD COLUMN X varchar(10);
ALTER TABLE patient ADD COLUMN M varchar(10);
ALTER TABLE patient ADD FOREIGN KEY (diagnosis_id) references diagnoses_catalog(id) ON DELETE SET NULL ;