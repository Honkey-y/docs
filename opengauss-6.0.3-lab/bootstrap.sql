CREATE DATABASE og_lab WITH DBCOMPATIBILITY 'B' ENCODING 'UTF8';
\connect og_lab
CREATE USER lab_user WITH SYSADMIN PASSWORD :'lab_password';
SELECT set_native_password('lab_user', :'lab_password', '');
CREATE SCHEMA cz001 AUTHORIZATION lab_user;
CREATE TABLE cz001.packet_probe (
    id INTEGER PRIMARY KEY,
    value TEXT
);
ALTER TABLE cz001.packet_probe OWNER TO lab_user;
INSERT INTO cz001.packet_probe VALUES (1, 'created in openGauss B database');
