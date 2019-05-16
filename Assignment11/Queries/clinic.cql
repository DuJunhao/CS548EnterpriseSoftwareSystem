CREATE TYPE IF NOT EXISTS cs548.drugtreatment(drug varchar,dosage float);
CREATE TYPE IF NOT EXISTS cs548.surgery(form varchar);
CREATE TYPE IF NOT EXISTS cs548.radiology(dates List<date>);

create table IF NOT EXISTS patient (record_id uuid , patientid int primary key,patient_name varchar, dateofbirth date);
create table IF NOT EXISTS provider(record_id uuid , npi int primary key,provider_name varchar,specialization varchar);
create table IF NOT EXISTS patient_treatment(record_id uuid ,startedtime date, startedyear int,diagonsis varchar,patientid int, primary key(patientid,startedyear),
	drugtreatmenttype FROZEN<drugtreatment>,surgerytype FROZEN<surgery>,radiologytype List<FROZEN<radiology>>)
	WITH CLUSTERING ORDER BY (startedyear DESC);
create table IF NOT EXISTS provider_treatment(record_id uuid ,startedtime date, startedyear int,diagonsis varchar,npi int,primary key(npi,startedyear),
	drugtreatmenttype FROZEN<drugtreatment>,surgerytype FROZEN<surgery>,radiologytype List<FROZEN<radiology>>)
	WITH CLUSTERING ORDER BY (startedyear DESC);


INSERT INTO patient (patientid, patient_name, dateofbirth)
                VALUES (12345678, 'John Smith', '1995-06-06');
INSERT INTO patient (patientid, patient_name, dateofbirth)
                VALUES (23456781, 'Alice Bomb', '1996-07-07');
INSERT INTO provider (npi, provider_name, specialization)
                VALUES (87654321, 'Bob Fuck', 'radiology');
INSERT INTO patient_treatment (startedtime, startedyear, diagonsis,patientid,drugtreatmenttype)
                VALUES ('2017-06-06', 2017, 'varicella',12345678,{drug:'panacea',dosage:14.0}); 
                
INSERT INTO provider_treatment (startedtime, startedyear, diagonsis,npi,drugtreatmenttype)
                VALUES ('2017-06-06', 2017, 'varicella',87654321,{drug:'panacea',dosage:14.0}); 


              