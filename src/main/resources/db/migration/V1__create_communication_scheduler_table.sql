CREATE SEQUENCE IF NOT EXISTS seq_communication_scheduler START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS communication_scheduler
(
    id                 BIGINT            NOT NULL DEFAULT nextval('seq_communication_scheduler') PRIMARY KEY,
    destination        VARCHAR(255)      NOT NULL,
    message            VARCHAR(5000)     NOT NULL,
    schedule_date      TIMESTAMP         NOT NULL,
    communication_type VARCHAR(50)       NOT NULL,
    status             VARCHAR(50)       NOT NULL
);
