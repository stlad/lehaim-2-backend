CREATE TABLE IF NOT EXISTS cytokine_chart_states
(
    id                uuid primary key,

    range_TNFa        text,
    range_IFNy        text,
    range_IL2         text,

    diagnosis_id      bigint,
    recommendation_Id uuid,

    FOREIGN KEY (diagnosis_id) REFERENCES diagnoses_catalog (id) ON DELETE CASCADE,
    FOREIGN KEY (recommendation_Id) REFERENCES recommendations (id) ON DELETE CASCADE
);