--<ScriptOptions statementTerminator=";"/>

ALTER TABLE drugtreatment DROP CONSTRAINT fk_drugtreatment_id;

ALTER TABLE treatment DROP CONSTRAINT fk_treatment_patient_fk;

ALTER TABLE surgerytreatment DROP CONSTRAINT fk_surgerytreatment_id;

ALTER TABLE radiologytreatment_dates DROP CONSTRAINT fk_radiologytreatment_dates_radiologytreatment_fk;

ALTER TABLE radiologytreatment DROP CONSTRAINT fk_radiologytreatment_id;

ALTER TABLE treatment DROP CONSTRAINT fk_treatment_provider_fk;

ALTER TABLE patient DROP CONSTRAINT patient_pkey;

ALTER TABLE treatment DROP CONSTRAINT treatment_pkey;

ALTER TABLE radiologytreatment DROP CONSTRAINT radiologytreatment_pkey;

ALTER TABLE drugtreatment DROP CONSTRAINT drugtreatment_pkey;

ALTER TABLE sequence DROP CONSTRAINT sequence_pkey;

ALTER TABLE message DROP CONSTRAINT message_pkey;

ALTER TABLE surgerytreatment DROP CONSTRAINT surgerytreatment_pkey;

ALTER TABLE provider DROP CONSTRAINT provider_pkey;

DROP INDEX sequence_pkey;

DROP INDEX provider_pkey;

DROP INDEX drugtreatment_pkey;

DROP INDEX treatment_pkey;

DROP INDEX message_pkey;

DROP INDEX patient_pkey;

DROP INDEX surgerytreatment_pkey;

DROP INDEX radiologytreatment_pkey;

DROP TABLE provider;

DROP TABLE radiologytreatment;

DROP TABLE radiologytreatment_dates;

DROP TABLE patient;

DROP TABLE message;

DROP TABLE treatment;

DROP TABLE drugtreatment;

DROP TABLE surgerytreatment;

DROP TABLE sequence;

