CREATE TABLE IF NOT EXISTS recommendations
(
    id                  uuid primary key,

    recommendation_name text,
    conclusion          text,
    recommendation      text,
    chart_type           text,
    date_created        timestamp,
    date_updated        timestamp
);