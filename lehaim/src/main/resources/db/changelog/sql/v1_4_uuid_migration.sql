
ALTER TABLE patient DROP COLUMN alive;


CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
ALTER TABLE patient ADD COLUMN uuid uuid NOT NULL UNIQUE DEFAULT uuid_generate_v4();

ALTER TABLE oncological_tests ADD COLUMN patient_uuid uuid DEFAULT NULL;
ALTER TABLE oncological_tests ADD FOREIGN KEY (patient_uuid) REFERENCES patient (uuid);

UPDATE oncological_tests
SET patient_uuid = p.uuid
FROM oncological_tests ot
INNER JOIN patient p on ot.patient_id = p.id;


ALTER TABLE oncological_tests DROP COLUMN patient_id;
ALTER TABLE oncological_tests RENAME COLUMN patient_uuid to patient_id;

ALTER TABLE patient DROP CONSTRAINT patient_pkey;
ALTER TABLE patient ADD PRIMARY KEY (uuid);
ALTER TABLE patient DROP COLUMN id;
ALTER TABLE patient RENAME COLUMN uuid to id;
