INSERT INTO usrmgmt.role_function_dtls
(cr_by, cr_dtimes, del_dtimes, is_active, is_deleted, upd_by, upd_dtimes, rfd_function_fk, rfd_role_name_fk, rfd_function_uri)
VALUES( NULL, '2021-01-20 11:43:56.718', NULL, true, false, NULL, NULL, 'getMappedDrListByPatientId', 'doctor', '/doctor-registration/DoctorRegistration/v1/getMappedDrListByPatientId');


INSERT INTO usrmgmt.role_function_dtls
(cr_by, cr_dtimes, del_dtimes, is_active, is_deleted, upd_by, upd_dtimes, rfd_function_fk, rfd_role_name_fk, rfd_function_uri)
VALUES( NULL, '2021-01-20 11:45:25.957', NULL, true, false, NULL, NULL, 'getMappedPatientListByDrId', 'doctor', '/patient/v1/patientModification/getMappedPatientListByDrId');


INSERT INTO usrmgmt.role_function_dtls
(cr_by, cr_dtimes, del_dtimes, is_active, is_deleted, upd_by, upd_dtimes, rfd_function_fk, rfd_role_name_fk, rfd_function_uri)
VALUES(NULL, '2021-01-20 11:45:25.957', NULL, true, false, NULL, NULL, 'getMappedPatientListByDrId', 'scribe', '/patient/v1/patientModification/getMappedPatientListByDrId');


INSERT INTO usrmgmt.role_function_dtls
(cr_by, cr_dtimes, del_dtimes, is_active, is_deleted, upd_by, upd_dtimes, rfd_function_fk, rfd_role_name_fk, rfd_function_uri)
VALUES( NULL, '2021-01-20 11:45:25.957', NULL, true, false, NULL, NULL, 'unMappedPatientOrDrById', 'patient', '/patient/v1/patientModification/unMappedPatientOrDrById');
INSERT INTO usrmgmt.role_function_dtls
(cr_by, cr_dtimes, del_dtimes, is_active, is_deleted, upd_by, upd_dtimes, rfd_function_fk, rfd_role_name_fk, rfd_function_uri)
VALUES(NULL, '2021-01-20 11:45:25.957', NULL, true, false, NULL, NULL, 'unMappedPatientOrDrById', 'doctor', '/patient/v1/patientModification/unMappedPatientOrDrById');
INSERT INTO usrmgmt.role_function_dtls
(cr_by, cr_dtimes, del_dtimes, is_active, is_deleted, upd_by, upd_dtimes, rfd_function_fk, rfd_role_name_fk, rfd_function_uri)
VALUES(NULL, '2021-01-20 11:45:25.957', NULL, true, false, NULL, NULL, 'unMappedPatientOrDrById', 'scribe', '/patient/v1/patientModification/unMappedPatientOrDrById');



INSERT INTO usrmgmt.usrmgmt.role_function_dtls
(cr_by, cr_dtimes, del_dtimes, is_active, is_deleted, upd_by, upd_dtimes, rfd_function_fk, rfd_role_name_fk, rfd_function_uri)
VALUES(NULL, '2021-01-20 11:45:25.957', NULL, true, false, NULL, NULL, 'getSlotCreatedMonth', 'doctor', '/slotManagement/v1/getSlotCreatedMonth');
INSERT INTO usrmgmt.usrmgmt.role_function_dtls
(cr_by, cr_dtimes, del_dtimes, is_active, is_deleted, upd_by, upd_dtimes, rfd_function_fk, rfd_role_name_fk, rfd_function_uri)
VALUES(NULL, '2021-01-20 11:45:25.957', NULL, true, false, NULL, NULL, 'getSlotCreatedMonth', 'scribe', '/slotManagement/v1/getSlotCreatedMonth');


INSERT INTO usrmgmt.usrmgmt.role_function_dtls
(cr_by, cr_dtimes, del_dtimes, is_active, is_deleted, upd_by, upd_dtimes, rfd_function_fk, rfd_role_name_fk, rfd_function_uri)
VALUES(NULL, '2021-01-20 11:45:25.957', NULL, true, false, NULL, NULL, 'getApptHistory', 'doctor', '/slotManagement/v1/getApptHistory');
INSERT INTO usrmgmt.usrmgmt.role_function_dtls
(cr_by, cr_dtimes, del_dtimes, is_active, is_deleted, upd_by, upd_dtimes, rfd_function_fk, rfd_role_name_fk, rfd_function_uri)
VALUES(NULL, '2021-01-20 11:45:25.957', NULL, true, false, NULL, NULL, 'getApptHistory', 'scribe', '/slotManagement/v1/getApptHistory');



-----------------------++++++++++++++++++++Sayali Scripts-----------------

-- Table: registration.recept_reg_dtls

-- DROP TABLE registration.recept_reg_dtls;

CREATE TABLE registration.recept_reg_dtls
(
    rrd_id_pk serial NOT NULL,
    rrd_recept_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    rrd_user_id character varying(50) COLLATE pg_catalog."default" NOT NULL,
    rrd_password character varying(100) COLLATE pg_catalog."default" NOT NULL,
    rrd_mobile_no bigint NOT NULL,
    rrd_email character varying(50) COLLATE pg_catalog."default",
    rrd_dr_user_id_fk character varying(50) COLLATE pg_catalog."default" NOT NULL,
    rrd_address1 character varying(100) COLLATE pg_catalog."default",
    rrd_address2 character varying(100) COLLATE pg_catalog."default",
    rrd_address3 character varying(100) COLLATE pg_catalog."default",
    rrd_address4 character varying(100) COLLATE pg_catalog."default",
    rrd_isactive character varying(1) COLLATE pg_catalog."default" NOT NULL,
    rrd_created_by character varying(50) COLLATE pg_catalog."default",
    rrd_created_tmstmp timestamp without time zone,
    rrd_modified_by character varying(50) COLLATE pg_catalog."default",
    rrd_modified_tmstmp timestamp without time zone,
    rrd_opti_version character varying COLLATE pg_catalog."default",
    rrd_photo_path character varying COLLATE pg_catalog."default",
    rrd_is_default_recept character varying(1) COLLATE pg_catalog."default",
    rrd_gender character(15) COLLATE pg_catalog."default",
    rrd_state character(50) COLLATE pg_catalog."default" NOT NULL,
    rrd_city character(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT recept_reg_dtls_pkey PRIMARY KEY (rrd_id_pk),
    CONSTRAINT rrd_mobile_no UNIQUE (rrd_mobile_no),
    CONSTRAINT rrd_user_id UNIQUE (rrd_user_id),
    CONSTRAINT rrd_dr_user_id_fk FOREIGN KEY (rrd_dr_user_id_fk)
        REFERENCES registration.dr_mstr_dtls (dmd_user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE registration.scrb_reg_dtls
    OWNER to sysadmin;
	

--------------------------Roles added to rolemaster table------------------------
INSERT INTO usrmgmt.role_mstr(
                rm_role_name_pk, cr_by, cr_dtimes, del_dtimes, is_active, is_deleted, upd_by, upd_dtimes, rm_role)
                VALUES ('RECEPTIONIST', 'ADMIN','2020-12-04 17:35:27.169' , null, 'true', 'false', 'ADMIN', '2020-12-04 17:35:27.169', ((select max(rm_role) from usrmgmt.role_mstr) +1));

INSERT INTO usrmgmt.role_mstr(
                rm_role_name_pk, cr_by, cr_dtimes, del_dtimes, is_active, is_deleted, upd_by, upd_dtimes, rm_role)
                VALUES ('CALLCENTRE', 'ADMIN','2020-12-04 17:35:27.169' , null, 'true', 'false', 'ADMIN', '2020-12-04 17:35:27.169', ((select max(rm_role) from usrmgmt.role_mstr) +1));

				

------Added receptionist role and call centre-----------

INSERT INTO registration.recept_reg_dtls(rrd_id_pk, rrd_recept_name, rrd_user_id, rrd_password, rrd_mobile_no, rrd_email, rrd_dr_user_id_fk, rrd_address1, rrd_address2, rrd_address3, rrd_address4, rrd_isactive, rrd_created_by, rrd_created_tmstmp, rrd_modified_by, rrd_modified_tmstmp, rrd_opti_version, rrd_photo_path, rrd_is_default_recept, rrd_gender, rrd_state, rrd_city) VALUES 	((select max(rrd_id_pk) from registration.recept_reg_dtls)+1 ,	'RECEPTIONIST ONE',	'RECEPTIONIST1',	'password@1234',	'7720901600',	'recptone1@gmail.com',	'MANISHBENDRE',	'xyzz','' , '', '', 'Y', 	'MANISHBENDRE', '2021-04-12 06:14:25.436','', '2021-04-12 06:14:25.436', null, null, 'Y', 'Male', 'MAHARASHTRA', 'Mumbai');	
INSERT INTO registration.recept_reg_dtls(rrd_id_pk, rrd_recept_name, rrd_user_id, rrd_password, rrd_mobile_no, rrd_email, rrd_dr_user_id_fk, rrd_address1, rrd_address2, rrd_address3, rrd_address4, rrd_isactive, rrd_created_by, rrd_created_tmstmp, rrd_modified_by, rrd_modified_tmstmp, rrd_opti_version, rrd_photo_path, rrd_is_default_recept, rrd_gender, rrd_state, rrd_city) VALUES 	((select max(rrd_id_pk) from registration.recept_reg_dtls)+1 ,	'RECEPTIONIST TWO',	'RECEPTIONIST2',	'password@1234'	,'7720901601',	'recpttwo2@gmail.com',	'DHARMESHP',	'xyzz','' , '', '', 'Y', 	'DHARMESHP','2021-04-12 06:14:25.436','', '2021-04-12 06:14:25.436', null, null, 'Y', 'Male', 'MAHARASHTRA', 'Mumbai');	
INSERT INTO registration.recept_reg_dtls(rrd_id_pk, rrd_recept_name, rrd_user_id, rrd_password, rrd_mobile_no, rrd_email, rrd_dr_user_id_fk, rrd_address1, rrd_address2, rrd_address3, rrd_address4, rrd_isactive, rrd_created_by, rrd_created_tmstmp, rrd_modified_by, rrd_modified_tmstmp, rrd_opti_version, rrd_photo_path, rrd_is_default_recept, rrd_gender, rrd_state, rrd_city) VALUES 	((select max(rrd_id_pk) from registration.recept_reg_dtls)+1 ,	'RECEPTIONIST THREE',	'RECEPTIONIST3',	'password@1234',	'7720901602',	'recptone3@gmail.com',	'VEDANTPATIL',	'xyzz','' , '', '', 'Y', 	'VEDANTPATIL', '2021-04-12 06:14:25.436','', '2021-04-12 06:14:25.436', null, null, 'Y', 'Male', 'MAHARASHTRA', 'Mumbai');	
INSERT INTO registration.recept_reg_dtls(rrd_id_pk, rrd_recept_name, rrd_user_id, rrd_password, rrd_mobile_no, rrd_email, rrd_dr_user_id_fk, rrd_address1, rrd_address2, rrd_address3, rrd_address4, rrd_isactive, rrd_created_by, rrd_created_tmstmp, rrd_modified_by, rrd_modified_tmstmp, rrd_opti_version, rrd_photo_path, rrd_is_default_recept, rrd_gender, rrd_state, rrd_city) VALUES 	((select max(rrd_id_pk) from registration.recept_reg_dtls)+1 ,	'RECEPTIONIST FOUR',	'RECEPTIONIST4',	'password@1234',	'7720901603',	'recpttwo4@gmail.com',	'VIVEKKINGE',	'xyzz','' , '', '', 'Y', 	'VIVEKKINGE', '2021-04-12 06:14:25.436','', '2021-04-12 06:14:25.436', null, null, 'Y', 'Male', 'MAHARASHTRA', 'Mumbai');	
INSERT INTO registration.recept_reg_dtls(rrd_id_pk, rrd_recept_name, rrd_user_id, rrd_password, rrd_mobile_no, rrd_email, rrd_dr_user_id_fk, rrd_address1, rrd_address2, rrd_address3, rrd_address4, rrd_isactive, rrd_created_by, rrd_created_tmstmp, rrd_modified_by, rrd_modified_tmstmp, rrd_opti_version, rrd_photo_path, rrd_is_default_recept, rrd_gender, rrd_state, rrd_city) VALUES 	((select max(rrd_id_pk) from registration.recept_reg_dtls)+1 ,	'RECEPTIONIST FIVE',	'RECEPTIONIST5',	'password@1234',	'7720901604',	'recptone5@gmail.com',	'NEHAD1234',	'xyzz','' , '', '', 'Y', 	'NEHAD1234','2021-04-12 06:14:25.436','', '2021-04-12 06:14:25.436', null, null, 'Y', 'Male', 'MAHARASHTRA', 'Mumbai');	
INSERT INTO registration.recept_reg_dtls(rrd_id_pk, rrd_recept_name, rrd_user_id, rrd_password, rrd_mobile_no, rrd_email, rrd_dr_user_id_fk, rrd_address1, rrd_address2, rrd_address3, rrd_address4, rrd_isactive, rrd_created_by, rrd_created_tmstmp, rrd_modified_by, rrd_modified_tmstmp, rrd_opti_version, rrd_photo_path, rrd_is_default_recept, rrd_gender, rrd_state, rrd_city) VALUES 	((select max(rrd_id_pk) from registration.recept_reg_dtls)+1 ,	'RECEPTIONIST SIX',	'RECEPTIONIST6',	'password@1234'	,'7720901605',	'recpttwo6@gmail.com',	'ANANDIM123',	'xyzz','' , '', '', 'Y', 	'ANANDIM123','2021-04-12 06:14:25.436','', '2021-04-12 06:14:25.436', null, null, 'Y', 'Male', 'MAHARASHTRA', 'Mumbai');	
INSERT INTO registration.recept_reg_dtls(rrd_id_pk, rrd_recept_name, rrd_user_id, rrd_password, rrd_mobile_no, rrd_email, rrd_dr_user_id_fk, rrd_address1, rrd_address2, rrd_address3, rrd_address4, rrd_isactive, rrd_created_by, rrd_created_tmstmp, rrd_modified_by, rrd_modified_tmstmp, rrd_opti_version, rrd_photo_path, rrd_is_default_recept, rrd_gender, rrd_state, rrd_city) VALUES 	((select max(rrd_id_pk) from registration.recept_reg_dtls)+1 ,	'RECEPTIONIST SEVEN',	'RECEPTIONIST7',	'password@1234'	,'7720901606',	'recptone7@gmail.com',	'HITENDRAS123',	'xyzz','' , '', '', 'Y', 	'HITENDRAS123',	'2021-04-12 06:14:25.436','', '2021-04-12 06:14:25.436', null, null, 'Y', 'Male', 'MAHARASHTRA', 'Mumbai');	
INSERT INTO registration.recept_reg_dtls(rrd_id_pk, rrd_recept_name, rrd_user_id, rrd_password, rrd_mobile_no, rrd_email, rrd_dr_user_id_fk, rrd_address1, rrd_address2, rrd_address3, rrd_address4, rrd_isactive, rrd_created_by, rrd_created_tmstmp, rrd_modified_by, rrd_modified_tmstmp, rrd_opti_version, rrd_photo_path, rrd_is_default_recept, rrd_gender, rrd_state, rrd_city) VALUES 	((select max(rrd_id_pk) from registration.recept_reg_dtls)+1 ,	'RECEPTIONIST EIGHT',	'RECEPTIONIST8',	'password@1234'	,'7720901607',	'recpttwo8@gmail.com',	'SULOCHANA123',	'xyzz','' , '', '', 'Y', 	'SULOCHANA123',	'2021-04-12 06:14:25.436','', '2021-04-12 06:14:25.436', null, null, 'Y', 'Male', 'MAHARASHTRA', 'Mumbai');	
INSERT INTO registration.recept_reg_dtls(rrd_id_pk, rrd_recept_name, rrd_user_id, rrd_password, rrd_mobile_no, rrd_email, rrd_dr_user_id_fk, rrd_address1, rrd_address2, rrd_address3, rrd_address4, rrd_isactive, rrd_created_by, rrd_created_tmstmp, rrd_modified_by, rrd_modified_tmstmp, rrd_opti_version, rrd_photo_path, rrd_is_default_recept, rrd_gender, rrd_state, rrd_city) VALUES 	((select max(rrd_id_pk) from registration.recept_reg_dtls)+1 ,	'RECEPTIONIST NIGHT',	'RECEPTIONIST9',	'password@1234',	'7720901608',	'recptone9@gmail.com',	'IMRAN123',	'xyzz','' , '', '', 'Y', 	'IMRAN123',	'2021-04-12 06:14:25.436','', '2021-04-12 06:14:25.436', null, null, 'Y', 'Male', 'MAHARASHTRA', 'Mumbai');	
INSERT INTO registration.recept_reg_dtls(rrd_id_pk, rrd_recept_name, rrd_user_id, rrd_password, rrd_mobile_no, rrd_email, rrd_dr_user_id_fk, rrd_address1, rrd_address2, rrd_address3, rrd_address4, rrd_isactive, rrd_created_by, rrd_created_tmstmp, rrd_modified_by, rrd_modified_tmstmp, rrd_opti_version, rrd_photo_path, rrd_is_default_recept, rrd_gender, rrd_state, rrd_city) VALUES 	((select max(rrd_id_pk) from registration.recept_reg_dtls)+1 ,	'RECEPTIONIST TEN',	'RECEPTIONIST10',	'password@1234',	'7720901609',	'recpttwo10@gmail.com',	'VAISHALID123',	'xyzz','' , '', '', 'Y', 	'VAISHALID123',	'2021-04-12 06:14:25.436','', '2021-04-12 06:14:25.436', null, null, 'Y', 'Male', 'MAHARASHTRA', 'Mumbai');	
INSERT INTO registration.recept_reg_dtls(rrd_id_pk, rrd_recept_name, rrd_user_id, rrd_password, rrd_mobile_no, rrd_email, rrd_dr_user_id_fk, rrd_address1, rrd_address2, rrd_address3, rrd_address4, rrd_isactive, rrd_created_by, rrd_created_tmstmp, rrd_modified_by, rrd_modified_tmstmp, rrd_opti_version, rrd_photo_path, rrd_is_default_recept, rrd_gender, rrd_state, rrd_city) VALUES 	((select max(rrd_id_pk) from registration.recept_reg_dtls)+1 ,	'RECEPTIONIST ELEVEN',	'RECEPTIONIST11',	'password@1234'	,'7720901610',	'recptone11@gmail.com',	'IMRAN000',	'xyzz','' , '', '', 'Y', 	'IMRAN000',	'2021-04-12 06:14:25.436','', '2021-04-12 06:14:25.436', null, null, 'Y', 'Male', 'MAHARASHTRA', 'Mumbai');	
INSERT INTO registration.recept_reg_dtls(rrd_id_pk, rrd_recept_name, rrd_user_id, rrd_password, rrd_mobile_no, rrd_email, rrd_dr_user_id_fk, rrd_address1, rrd_address2, rrd_address3, rrd_address4, rrd_isactive, rrd_created_by, rrd_created_tmstmp, rrd_modified_by, rrd_modified_tmstmp, rrd_opti_version, rrd_photo_path, rrd_is_default_recept, rrd_gender, rrd_state, rrd_city) VALUES 	((select max(rrd_id_pk) from registration.recept_reg_dtls)+1 ,	'RECEPTIONIST TWELVE',	'RECEPTIONIST12',	'password@1234'	,'7720901611',	'recpttwo12@gmail.com',	'REVATIS1',	'xyzz','' , '', '', 'Y', 	'REVATIS1',	'2021-04-12 06:14:25.436','', '2021-04-12 06:14:25.436', null, null, 'Y', 'Male', 'MAHARASHTRA', 'Mumbai');	
INSERT INTO registration.recept_reg_dtls(rrd_id_pk, rrd_recept_name, rrd_user_id, rrd_password, rrd_mobile_no, rrd_email, rrd_dr_user_id_fk, rrd_address1, rrd_address2, rrd_address3, rrd_address4, rrd_isactive, rrd_created_by, rrd_created_tmstmp, rrd_modified_by, rrd_modified_tmstmp, rrd_opti_version, rrd_photo_path, rrd_is_default_recept, rrd_gender, rrd_state, rrd_city) VALUES 	((select max(rrd_id_pk) from registration.recept_reg_dtls)+1 ,	'RECEPTIONIST THIREETEEN',	'RECEPTIONIST13',	'password@1234'	,'7720901612',	'recptone13@gmail.com',	'SWARUPP123',	'xyzz','' , '', '', 'Y', 	'SWARUPP123', '2021-04-12 06:14:25.436','', '2021-04-12 06:14:25.436', null, null, 'Y', 'Male', 'MAHARASHTRA', 'Mumbai');	
INSERT INTO registration.recept_reg_dtls(rrd_id_pk, rrd_recept_name, rrd_user_id, rrd_password, rrd_mobile_no, rrd_email, rrd_dr_user_id_fk, rrd_address1, rrd_address2, rrd_address3, rrd_address4, rrd_isactive, rrd_created_by, rrd_created_tmstmp, rrd_modified_by, rrd_modified_tmstmp, rrd_opti_version, rrd_photo_path, rrd_is_default_recept, rrd_gender, rrd_state, rrd_city) VALUES 	((select max(rrd_id_pk) from registration.recept_reg_dtls)+1 ,	'RECEPTIONIST FOURTEEN',	'RECEPTIONIST14',	'password@1234',	'7720901613',	'recpttwo14@gmail.com',	'JASHIN11',	'xyzz','' , '', '', 'Y', 	'JASHIN11','2021-04-12 06:14:25.436','', '2021-04-12 06:14:25.436', null, null, 'Y', 'Male', 'MAHARASHTRA', 'Mumbai');	
INSERT INTO registration.recept_reg_dtls(rrd_id_pk, rrd_recept_name, rrd_user_id, rrd_password, rrd_mobile_no, rrd_email, rrd_dr_user_id_fk, rrd_address1, rrd_address2, rrd_address3, rrd_address4, rrd_isactive, rrd_created_by, rrd_created_tmstmp, rrd_modified_by, rrd_modified_tmstmp, rrd_opti_version, rrd_photo_path, rrd_is_default_recept, rrd_gender, rrd_state, rrd_city) VALUES 	((select max(rrd_id_pk) from registration.recept_reg_dtls)+1 ,	'RECEPTIONIST FIFTEEN ',	'RECEPTIONIST15',	'password@1234'	,'7720901614',	'recptone15@gmail.com',	'DHANUS123',	'xyzz','' , '', '', 'Y', 	'DHANUS123', '2021-04-12 06:14:25.436','', '2021-04-12 06:14:25.436', null, null, 'Y', 'Male', 'MAHARASHTRA', 'Mumbai');	
INSERT INTO registration.recept_reg_dtls(rrd_id_pk, rrd_recept_name, rrd_user_id, rrd_password, rrd_mobile_no, rrd_email, rrd_dr_user_id_fk, rrd_address1, rrd_address2, rrd_address3, rrd_address4, rrd_isactive, rrd_created_by, rrd_created_tmstmp, rrd_modified_by, rrd_modified_tmstmp, rrd_opti_version, rrd_photo_path, rrd_is_default_recept, rrd_gender, rrd_state, rrd_city) VALUES 	((select max(rrd_id_pk) from registration.recept_reg_dtls)+1 ,	'RECEPTIONIST  SIXTEEN',	'RECEPTIONIST16',	'password@1234'	,'7720901615',	'recpttwo16@gmail.com',	'HRISHIKESH1',	'xyzz','' , '', '', 'Y', 	'HRISHIKESH1', '2021-04-12 06:14:25.436','', '2021-04-12 06:14:25.436', null, null, 'Y', 'Male', 'MAHARASHTRA', 'Mumbai');	
INSERT INTO registration.recept_reg_dtls(rrd_id_pk, rrd_recept_name, rrd_user_id, rrd_password, rrd_mobile_no, rrd_email, rrd_dr_user_id_fk, rrd_address1, rrd_address2, rrd_address3, rrd_address4, rrd_isactive, rrd_created_by, rrd_created_tmstmp, rrd_modified_by, rrd_modified_tmstmp, rrd_opti_version, rrd_photo_path, rrd_is_default_recept, rrd_gender, rrd_state, rrd_city) VALUES 	((select max(rrd_id_pk) from registration.recept_reg_dtls)+1 ,	'RECEPTIONIST SEVENTEEN',	'RECEPTIONIST17',	'password@1234'	,'7720901616',	'recptone17@gmail.com',	'SHUBHAMP123',	'xyzz','' , '', '', 'Y', 	'SHUBHAMP123',	'2021-04-12 06:14:25.436','', '2021-04-12 06:14:25.436', null, null, 'Y', 'Male', 'MAHARASHTRA', 'Mumbai');	
	 

INSERT INTO usrmgmt.usr_dtls (ud_user_id_pk, ud_created_by, ud_created_tmstmp, ud_de_reg_reason, ud_email, ud_fail_attempt_count, ud_fail_attempt_tmstmp, ud_isactive_flg, ud_islock_flg, ud_logged_in_flg, ud_ischange_pwd, ud_mci_number, ud_mobile_no, ud_modified_by, ud_modified_tmstmp, ud_password, ud_pwd_expiry_tmstmp, ud_session_id, ud_smc_number, ud_user_full_name, ud_user_id, ud_user_type, ud_opti_version, ud_role_id_fk) VALUES	((select max(ud_user_id_pk) from usrmgmt.usr_dtls)+1,	'ADMIN', now(), NULL, 	'recptone1@gmail.com',	0, now(), true, false, true, true, NULL,	7720901600	, 'ADMIN', now(), '4F3DC14DE27AD3B13D2DF28E3BB59299BACF55BF2AA5E95462C2399A6357F5E0', now(), NULL, NULL,	 'I AM RECEPTNIST',	'RECEPTIONIST1','RECEPTIONIST', 0, 'RECEPTIONIST');		
INSERT INTO usrmgmt.usr_dtls (ud_user_id_pk, ud_created_by, ud_created_tmstmp, ud_de_reg_reason, ud_email, ud_fail_attempt_count, ud_fail_attempt_tmstmp, ud_isactive_flg, ud_islock_flg, ud_logged_in_flg, ud_ischange_pwd, ud_mci_number, ud_mobile_no, ud_modified_by, ud_modified_tmstmp, ud_password, ud_pwd_expiry_tmstmp, ud_session_id, ud_smc_number, ud_user_full_name, ud_user_id, ud_user_type, ud_opti_version, ud_role_id_fk) VALUES	((select max(ud_user_id_pk) from usrmgmt.usr_dtls)+1,	'ADMIN', now(), NULL, 	'recpttwo2@gmail.com',	0, now(), true, false, true, true, NULL,	7720901601	, 'ADMIN', now(), '4F3DC14DE27AD3B13D2DF28E3BB59299BACF55BF2AA5E95462C2399A6357F5E0', now(), NULL, NULL,	 'I AM RECEPTNIST',	'RECEPTIONIST2', 'RECEPTIONIST', 0, 'RECEPTIONIST');		
INSERT INTO usrmgmt.usr_dtls (ud_user_id_pk, ud_created_by, ud_created_tmstmp, ud_de_reg_reason, ud_email, ud_fail_attempt_count, ud_fail_attempt_tmstmp, ud_isactive_flg, ud_islock_flg, ud_logged_in_flg, ud_ischange_pwd, ud_mci_number, ud_mobile_no, ud_modified_by, ud_modified_tmstmp, ud_password, ud_pwd_expiry_tmstmp, ud_session_id, ud_smc_number, ud_user_full_name, ud_user_id, ud_user_type, ud_opti_version, ud_role_id_fk) VALUES	((select max(ud_user_id_pk) from usrmgmt.usr_dtls)+1,	'ADMIN', now(), NULL, 	'recptone3@gmail.com',	0, now(), true, false, true, true, NULL,	7720901602	, 'ADMIN', now(), '4F3DC14DE27AD3B13D2DF28E3BB59299BACF55BF2AA5E95462C2399A6357F5E0', now(), NULL, NULL,	 'I AM RECEPTNIST',	'RECEPTIONIST3', 'RECEPTIONIST', 0, 'RECEPTIONIST');		
INSERT INTO usrmgmt.usr_dtls (ud_user_id_pk, ud_created_by, ud_created_tmstmp, ud_de_reg_reason, ud_email, ud_fail_attempt_count, ud_fail_attempt_tmstmp, ud_isactive_flg, ud_islock_flg, ud_logged_in_flg, ud_ischange_pwd, ud_mci_number, ud_mobile_no, ud_modified_by, ud_modified_tmstmp, ud_password, ud_pwd_expiry_tmstmp, ud_session_id, ud_smc_number, ud_user_full_name, ud_user_id, ud_user_type, ud_opti_version, ud_role_id_fk) VALUES	((select max(ud_user_id_pk) from usrmgmt.usr_dtls)+1,	'ADMIN', now(), NULL, 	'recpttwo4@gmail.com',	0, now(), true, false, true, true, NULL,	7720901603	, 'ADMIN', now(), '4F3DC14DE27AD3B13D2DF28E3BB59299BACF55BF2AA5E95462C2399A6357F5E0', now(), NULL, NULL,	 'I AM RECEPTNIST',	'RECEPTIONIST4', 'RECEPTIONIST', 0, 'RECEPTIONIST');		
INSERT INTO usrmgmt.usr_dtls (ud_user_id_pk, ud_created_by, ud_created_tmstmp, ud_de_reg_reason, ud_email, ud_fail_attempt_count, ud_fail_attempt_tmstmp, ud_isactive_flg, ud_islock_flg, ud_logged_in_flg, ud_ischange_pwd, ud_mci_number, ud_mobile_no, ud_modified_by, ud_modified_tmstmp, ud_password, ud_pwd_expiry_tmstmp, ud_session_id, ud_smc_number, ud_user_full_name, ud_user_id, ud_user_type, ud_opti_version, ud_role_id_fk) VALUES	((select max(ud_user_id_pk) from usrmgmt.usr_dtls)+1,	'ADMIN', now(), NULL, 	'recptone5@gmail.com',	0, now(), true, false, true, true, NULL,	7720901604	, 'ADMIN', now(), '4F3DC14DE27AD3B13D2DF28E3BB59299BACF55BF2AA5E95462C2399A6357F5E0', now(), NULL, NULL,	 'I AM RECEPTNIST',	'RECEPTIONIST5', 'RECEPTIONIST', 0, 'RECEPTIONIST');		
INSERT INTO usrmgmt.usr_dtls (ud_user_id_pk, ud_created_by, ud_created_tmstmp, ud_de_reg_reason, ud_email, ud_fail_attempt_count, ud_fail_attempt_tmstmp, ud_isactive_flg, ud_islock_flg, ud_logged_in_flg, ud_ischange_pwd, ud_mci_number, ud_mobile_no, ud_modified_by, ud_modified_tmstmp, ud_password, ud_pwd_expiry_tmstmp, ud_session_id, ud_smc_number, ud_user_full_name, ud_user_id, ud_user_type, ud_opti_version, ud_role_id_fk) VALUES	((select max(ud_user_id_pk) from usrmgmt.usr_dtls)+1,	'ADMIN', now(), NULL, 	'recpttwo6@gmail.com',	0, now(), true, false, true, true, NULL,	7720901605	, 'ADMIN', now(), '4F3DC14DE27AD3B13D2DF28E3BB59299BACF55BF2AA5E95462C2399A6357F5E0', now(), NULL, NULL,	 'I AM RECEPTNIST',	'RECEPTIONIST6', 'RECEPTIONIST', 0, 'RECEPTIONIST');		
INSERT INTO usrmgmt.usr_dtls (ud_user_id_pk, ud_created_by, ud_created_tmstmp, ud_de_reg_reason, ud_email, ud_fail_attempt_count, ud_fail_attempt_tmstmp, ud_isactive_flg, ud_islock_flg, ud_logged_in_flg, ud_ischange_pwd, ud_mci_number, ud_mobile_no, ud_modified_by, ud_modified_tmstmp, ud_password, ud_pwd_expiry_tmstmp, ud_session_id, ud_smc_number, ud_user_full_name, ud_user_id, ud_user_type, ud_opti_version, ud_role_id_fk) VALUES	((select max(ud_user_id_pk) from usrmgmt.usr_dtls)+1,	'ADMIN', now(), NULL, 	'recptone7@gmail.com',	0, now(), true, false, true, true, NULL,	7720901606	, 'ADMIN', now(), '4F3DC14DE27AD3B13D2DF28E3BB59299BACF55BF2AA5E95462C2399A6357F5E0', now(), NULL, NULL,	 'I AM RECEPTNIST',	'RECEPTIONIST7', 'RECEPTIONIST', 0, 'RECEPTIONIST');		
INSERT INTO usrmgmt.usr_dtls (ud_user_id_pk, ud_created_by, ud_created_tmstmp, ud_de_reg_reason, ud_email, ud_fail_attempt_count, ud_fail_attempt_tmstmp, ud_isactive_flg, ud_islock_flg, ud_logged_in_flg, ud_ischange_pwd, ud_mci_number, ud_mobile_no, ud_modified_by, ud_modified_tmstmp, ud_password, ud_pwd_expiry_tmstmp, ud_session_id, ud_smc_number, ud_user_full_name, ud_user_id, ud_user_type, ud_opti_version, ud_role_id_fk) VALUES	((select max(ud_user_id_pk) from usrmgmt.usr_dtls)+1,	'ADMIN', now(), NULL, 	'recpttwo8@gmail.com',	0, now(), true, false, true, true, NULL,	7720901607	, 'ADMIN', now(), '4F3DC14DE27AD3B13D2DF28E3BB59299BACF55BF2AA5E95462C2399A6357F5E0', now(), NULL, NULL,	 'I AM RECEPTNIST',	'RECEPTIONIST8', 'RECEPTIONIST', 0, 'RECEPTIONIST');		
INSERT INTO usrmgmt.usr_dtls (ud_user_id_pk, ud_created_by, ud_created_tmstmp, ud_de_reg_reason, ud_email, ud_fail_attempt_count, ud_fail_attempt_tmstmp, ud_isactive_flg, ud_islock_flg, ud_logged_in_flg, ud_ischange_pwd, ud_mci_number, ud_mobile_no, ud_modified_by, ud_modified_tmstmp, ud_password, ud_pwd_expiry_tmstmp, ud_session_id, ud_smc_number, ud_user_full_name, ud_user_id, ud_user_type, ud_opti_version, ud_role_id_fk) VALUES	((select max(ud_user_id_pk) from usrmgmt.usr_dtls)+1,	'ADMIN', now(), NULL, 	'recptone9@gmail.com',	0, now(), true, false, true, true, NULL,	7720901608	, 'ADMIN', now(), '4F3DC14DE27AD3B13D2DF28E3BB59299BACF55BF2AA5E95462C2399A6357F5E0', now(), NULL, NULL,	 'I AM RECEPTNIST',	'RECEPTIONIST9', 'RECEPTIONIST', 0, 'RECEPTIONIST');		
INSERT INTO usrmgmt.usr_dtls (ud_user_id_pk, ud_created_by, ud_created_tmstmp, ud_de_reg_reason, ud_email, ud_fail_attempt_count, ud_fail_attempt_tmstmp, ud_isactive_flg, ud_islock_flg, ud_logged_in_flg, ud_ischange_pwd, ud_mci_number, ud_mobile_no, ud_modified_by, ud_modified_tmstmp, ud_password, ud_pwd_expiry_tmstmp, ud_session_id, ud_smc_number, ud_user_full_name, ud_user_id, ud_user_type, ud_opti_version, ud_role_id_fk) VALUES	((select max(ud_user_id_pk) from usrmgmt.usr_dtls)+1,	'ADMIN', now(), NULL, 	'recpttwo10@gmail.com',	0, now(), true, false, true, true, NULL,	7720901609	, 'ADMIN', now(), '4F3DC14DE27AD3B13D2DF28E3BB59299BACF55BF2AA5E95462C2399A6357F5E0', now(), NULL, NULL,	 'I AM RECEPTNIST',	'RECEPTIONIST10', 'RECEPTIONIST', 0, 'RECEPTIONIST');		
INSERT INTO usrmgmt.usr_dtls (ud_user_id_pk, ud_created_by, ud_created_tmstmp, ud_de_reg_reason, ud_email, ud_fail_attempt_count, ud_fail_attempt_tmstmp, ud_isactive_flg, ud_islock_flg, ud_logged_in_flg, ud_ischange_pwd, ud_mci_number, ud_mobile_no, ud_modified_by, ud_modified_tmstmp, ud_password, ud_pwd_expiry_tmstmp, ud_session_id, ud_smc_number, ud_user_full_name, ud_user_id, ud_user_type, ud_opti_version, ud_role_id_fk) VALUES	((select max(ud_user_id_pk) from usrmgmt.usr_dtls)+1,	'ADMIN', now(), NULL, 	'recptone11@gmail.com',	0, now(), true, false, true, true, NULL,	7720901610	, 'ADMIN', now(), '4F3DC14DE27AD3B13D2DF28E3BB59299BACF55BF2AA5E95462C2399A6357F5E0', now(), NULL, NULL,	 'I AM RECEPTNIST',	'RECEPTIONIST11', 'RECEPTIONIST', 0, 'RECEPTIONIST');		
INSERT INTO usrmgmt.usr_dtls (ud_user_id_pk, ud_created_by, ud_created_tmstmp, ud_de_reg_reason, ud_email, ud_fail_attempt_count, ud_fail_attempt_tmstmp, ud_isactive_flg, ud_islock_flg, ud_logged_in_flg, ud_ischange_pwd, ud_mci_number, ud_mobile_no, ud_modified_by, ud_modified_tmstmp, ud_password, ud_pwd_expiry_tmstmp, ud_session_id, ud_smc_number, ud_user_full_name, ud_user_id, ud_user_type, ud_opti_version, ud_role_id_fk) VALUES	((select max(ud_user_id_pk) from usrmgmt.usr_dtls)+1,	'ADMIN', now(), NULL, 	'recpttwo12@gmail.com',	0, now(), true, false, true, true, NULL,	7720901611	, 'ADMIN', now(), '4F3DC14DE27AD3B13D2DF28E3BB59299BACF55BF2AA5E95462C2399A6357F5E0', now(), NULL, NULL,	 'I AM RECEPTNIST',	'RECEPTIONIST12', 'RECEPTIONIST', 0, 'RECEPTIONIST');		
INSERT INTO usrmgmt.usr_dtls (ud_user_id_pk, ud_created_by, ud_created_tmstmp, ud_de_reg_reason, ud_email, ud_fail_attempt_count, ud_fail_attempt_tmstmp, ud_isactive_flg, ud_islock_flg, ud_logged_in_flg, ud_ischange_pwd, ud_mci_number, ud_mobile_no, ud_modified_by, ud_modified_tmstmp, ud_password, ud_pwd_expiry_tmstmp, ud_session_id, ud_smc_number, ud_user_full_name, ud_user_id, ud_user_type, ud_opti_version, ud_role_id_fk) VALUES	((select max(ud_user_id_pk) from usrmgmt.usr_dtls)+1,	'ADMIN', now(), NULL, 	'recptone13@gmail.com',	0, now(), true, false, true, true, NULL,	7720901612	, 'ADMIN', now(), '4F3DC14DE27AD3B13D2DF28E3BB59299BACF55BF2AA5E95462C2399A6357F5E0', now(), NULL, NULL,	 'I AM RECEPTNIST',	'RECEPTIONIST13', 'RECEPTIONIST', 0, 'RECEPTIONIST');		
INSERT INTO usrmgmt.usr_dtls (ud_user_id_pk, ud_created_by, ud_created_tmstmp, ud_de_reg_reason, ud_email, ud_fail_attempt_count, ud_fail_attempt_tmstmp, ud_isactive_flg, ud_islock_flg, ud_logged_in_flg, ud_ischange_pwd, ud_mci_number, ud_mobile_no, ud_modified_by, ud_modified_tmstmp, ud_password, ud_pwd_expiry_tmstmp, ud_session_id, ud_smc_number, ud_user_full_name, ud_user_id, ud_user_type, ud_opti_version, ud_role_id_fk) VALUES	((select max(ud_user_id_pk) from usrmgmt.usr_dtls)+1,	'ADMIN', now(), NULL, 	'recpttwo14@gmail.com',	0, now(), true, false, true, true, NULL,	7720901613	, 'ADMIN', now(), '4F3DC14DE27AD3B13D2DF28E3BB59299BACF55BF2AA5E95462C2399A6357F5E0', now(), NULL, NULL,	 'I AM RECEPTNIST',	'RECEPTIONIST14', 'RECEPTIONIST', 0, 'RECEPTIONIST');		
INSERT INTO usrmgmt.usr_dtls (ud_user_id_pk, ud_created_by, ud_created_tmstmp, ud_de_reg_reason, ud_email, ud_fail_attempt_count, ud_fail_attempt_tmstmp, ud_isactive_flg, ud_islock_flg, ud_logged_in_flg, ud_ischange_pwd, ud_mci_number, ud_mobile_no, ud_modified_by, ud_modified_tmstmp, ud_password, ud_pwd_expiry_tmstmp, ud_session_id, ud_smc_number, ud_user_full_name, ud_user_id, ud_user_type, ud_opti_version, ud_role_id_fk) VALUES	((select max(ud_user_id_pk) from usrmgmt.usr_dtls)+1,	'ADMIN', now(), NULL, 	'recptone15@gmail.com',	0, now(), true, false, true, true, NULL,	7720901614	, 'ADMIN', now(), '4F3DC14DE27AD3B13D2DF28E3BB59299BACF55BF2AA5E95462C2399A6357F5E0', now(), NULL, NULL,	 'I AM RECEPTNIST',	'RECEPTIONIST15', 'RECEPTIONIST', 0, 'RECEPTIONIST');		
INSERT INTO usrmgmt.usr_dtls (ud_user_id_pk, ud_created_by, ud_created_tmstmp, ud_de_reg_reason, ud_email, ud_fail_attempt_count, ud_fail_attempt_tmstmp, ud_isactive_flg, ud_islock_flg, ud_logged_in_flg, ud_ischange_pwd, ud_mci_number, ud_mobile_no, ud_modified_by, ud_modified_tmstmp, ud_password, ud_pwd_expiry_tmstmp, ud_session_id, ud_smc_number, ud_user_full_name, ud_user_id, ud_user_type, ud_opti_version, ud_role_id_fk) VALUES	((select max(ud_user_id_pk) from usrmgmt.usr_dtls)+1,	'ADMIN', now(), NULL, 	'recpttwo16@gmail.com',	0, now(), true, false, true, true, NULL,	7720901615	, 'ADMIN', now(), '4F3DC14DE27AD3B13D2DF28E3BB59299BACF55BF2AA5E95462C2399A6357F5E0', now(), NULL, NULL,	 'I AM RECEPTNIST',	'RECEPTIONIST16', 'RECEPTIONIST', 0, 'RECEPTIONIST');		
INSERT INTO usrmgmt.usr_dtls (ud_user_id_pk, ud_created_by, ud_created_tmstmp, ud_de_reg_reason, ud_email, ud_fail_attempt_count, ud_fail_attempt_tmstmp, ud_isactive_flg, ud_islock_flg, ud_logged_in_flg, ud_ischange_pwd, ud_mci_number, ud_mobile_no, ud_modified_by, ud_modified_tmstmp, ud_password, ud_pwd_expiry_tmstmp, ud_session_id, ud_smc_number, ud_user_full_name, ud_user_id, ud_user_type, ud_opti_version, ud_role_id_fk) VALUES	((select max(ud_user_id_pk) from usrmgmt.usr_dtls)+1,	'ADMIN', now(), NULL, 	'recptone17@gmail.com',	0, now(), true, false, true, true, NULL,	7720901616	, 'ADMIN', now(), '4F3DC14DE27AD3B13D2DF28E3BB59299BACF55BF2AA5E95462C2399A6357F5E0', now(), NULL, NULL,	 'I AM RECEPTNIST',	'RECEPTIONIST17', 'RECEPTIONIST', 0, 'RECEPTIONIST');		

	 --------------------Roles permission added--
	 
  
	  INSERT INTO usrmgmt.role_function_dtls(
	rfd_rolefunc_pk, cr_by, cr_dtimes, del_dtimes, is_active, is_deleted, upd_by, upd_dtimes, rfd_function_fk, rfd_role_name_fk, rfd_function_uri)
	VALUES ((select max(rfd_rolefunc_pk) from usrmgmt.role_function_dtls)+1 , null, '2020-11-05 18:56:04.612', null, true, false, null, null, 'FetchConsultationList','callcentre','/consultation/appointment/getConsultationListByDoctorID');
	
	
	
	
	 INSERT INTO usrmgmt.role_function_dtls(
	rfd_rolefunc_pk, cr_by, cr_dtimes, del_dtimes, is_active, is_deleted, upd_by, upd_dtimes, rfd_function_fk, rfd_role_name_fk, rfd_function_uri)
	VALUES ((select max(rfd_rolefunc_pk) from usrmgmt.role_function_dtls)+1 , null, '2020-11-05 18:56:04.612', null, true, false, null, null, 'FetchConsultationList','receptionist','/consultation/appointment/getConsultationListByDoctorID');
	
	
	
	
	-------------------save apppointment scripts---
	
	
	 INSERT INTO usrmgmt.role_function_dtls(
	rfd_rolefunc_pk, cr_by, cr_dtimes, del_dtimes, is_active, is_deleted, upd_by, upd_dtimes, rfd_function_fk, rfd_role_name_fk, rfd_function_uri)
	VALUES ((select max(rfd_rolefunc_pk) from usrmgmt.role_function_dtls)+1 , null, '2020-11-05 18:56:04.612', null, true, false, null, null, 'saveAppointment','receptionist','/book/v1/saveAppointmentDetails');
	
	 INSERT INTO usrmgmt.role_function_dtls(
	rfd_rolefunc_pk, cr_by, cr_dtimes, del_dtimes, is_active, is_deleted, upd_by, upd_dtimes, rfd_function_fk, rfd_role_name_fk, rfd_function_uri)
	VALUES ((select max(rfd_rolefunc_pk) from usrmgmt.role_function_dtls)+1 , null, '2020-11-05 18:56:04.612', null, true, false, null, null, 'saveAppointment','callcentre','/book/v1/saveAppointmentDetails');
	
	
	-----------------------regarding issue for getconsulationlistbydoctorid
	
	alter table appointment.appt_dtls add column ad_recpt_user_id character varying(50);

	
	----Foreign key constraints 
ALTER TABLE appointment.appt_dtls ADD 
CONSTRAINT ad_recpt_user_id_fk FOREIGN KEY (ad_recpt_user_id) 
REFERENCES registration.recept_reg_dtls(rrd_user_id) ON DELETE CASCADE ON UPDATE CASCADE;  
	
	
------DB scripts:- 
ALTER TABLE appointment.appt_dtls ADD COLUMN ad_appt_start_time character varying(10) NULL;
ALTER TABLE appointment.appt_dtls ADD COLUMN ad_appt_end_time character varying(10) NULL;
ALTER TABLE appointment.appt_dtls ADD COLUMN ad_appt_total_time character varying(10) NULL;

alter table registration.pt_reg_dtls alter prd_email drop not null;	
alter table audit.aud_pt_reg_dtls alter prd_email drop not null;
-----------------------------------------------------
UPDATE master.email_sms_template_dtls
SET estd_email_sms_content='Dear User, Your password has been changed successfully. Please find below system generated password for login @password. Regards, NSDL e-Gov'
WHERE estd_name='Change_Password_SMS';
--------------------------------------------------------
UPDATE master.email_sms_template_dtls
SET estd_email_sms_content='Dear User, To complete your registration please insert OTP received over SMS and EMAIL. Your otp is @password. Regards, NSDL e-Gov'
WHERE estd_name='Registration_Success_SMS';
---------------------------------------------------------
UPDATE master.email_sms_template_dtls
SET estd_email_sms_content='Dear User, Reset your password using system generated password-@password which is only valid for next @pwdExpireTime minutes. Regards, NSDL e-Gov'
WHERE estd_name='Forgot_Password_SMS';
----------------------------------------------------------
UPDATE master.email_sms_template_dtls
SET estd_email_sms_content='Dear User, Your de-registration request has been received and processed successfully in NSDL’s Telemedicine. Regards, NSDL e-Gov'
WHERE estd_name='Doctor_Deregister_SMS'
----------------------------------------------------------------------
UPDATE master.email_sms_template_dtls
SET estd_email_sms_content='Dear User, Your Payment for an amount of @amount has been accepted. Regards, NSDL e-Gov'
WHERE estd_name='Payment_Success_SMS'
----------------------------------------------------------------
UPDATE master.email_sms_template_dtls
SET estd_email_sms_content='Dear User, Your Payment for an amount of @amount has failed. Regards, NSDL e-Gov'
WHERE estd_name='Payment_Fail_SMS'
-------------------------------------------------------------------------
UPDATE master.email_sms_template_dtls
SET estd_email_sms_content='Dear User, Your appointment is booked with @doctorName at @appointmentDate, @appointmentTime. Regards, NSDL e-Gov' WHERE estd_name='Appointment_Dtls_Patient_SMS'
-------------------------------------------------------------------------
UPDATE master.email_sms_template_dtls
SET estd_email_sms_content='Dear Doctor, Your appointment is booked with @patientName at @appointmentDate, @appointmentTime. Regards, NSDL e-Gov' WHERE estd_name='Appointment_Dtls_Doctor_SMS';
--------------------------------------------------------------------------
UPDATE master.email_sms_template_dtls
SET estd_email_sms_content='Dear User, Your appointment is rescheduled with @doctorName. Revised time is @appointmentDate, @appointmentTime. Regards, NSDL e-Gov' WHERE estd_name='Reschedule_Dtls_Patient_SMS';
-----------------------------------------------------------------------
UPDATE master.email_sms_template_dtls
SET estd_email_sms_content='Dear User, Your registration request at NSDL’s Telemedicine is verified successfully. Regards, NSDL e-Gov' WHERE estd_name='Doctor_Verification_SMS';
--------------------------------------------------------------------
UPDATE master.email_sms_template_dtls
SET estd_email_sms_content='Dear Doctor, Your appointment is rescheduled with @patientName. Revised time is @appointmentDate, @appointmentTime. Regards, NSDL e-Gov' WHERE estd_name='Reschedule_Dtls_Doctor_SMS' ;
-----------------------------------------------------------------------
UPDATE master.email_sms_template_dtls
SET estd_email_sms_content='Dear Doctor, Appointment with @patientName is cancelled successfully. Rebook the appointment at the available time slots. Regards, NSDL e-Gov' WHERE estd_name='Appointment_Cancellation_Doctor_SMS' ;
-----------------------------------------------------------------------
UPDATE master.email_sms_template_dtls
SET estd_email_sms_content='Dear Doctor, Your consultation with @patientName is completed successfully. Regards, NSDL e-Gov' WHERE estd_name='Consultation_Success_Doctor_SMS' ;
----------------------------------------------------------------
UPDATE master.email_sms_template_dtls
SET estd_email_sms_content='Dear User, Your consultation with @doctorName is completed successfully. Regards, NSDL e-Gov' WHERE estd_name='Consultation_Success_Patient_SMS' ;
---------------------------------------------------------------------
UPDATE master.email_sms_template_dtls
SET estd_email_sms_content='Dear User, Appointment with @doctorName is cancelled successfully. Rebook the appointment at the available time slots. Regards, NSDL e-Gov' WHERE estd_name='Appointment_Cancellation_Patient_SMS' ;
--------------------------------------------------------------------------

---------------------------------------------------------------------
UPDATE master.email_sms_template_dtls
SET estd_email_sms_content='Dear User, Your registration request at NSDL’s Telemedicine is failed due to @rejectReason. Regards, NSDL e-Gov' WHERE estd_name='Doctor_Verification_Fail_SMS' ;
--------------------------------------------------------------------------

INSERT INTO master.email_sms_template_dtls
(estd_template_id, estd_created_by, estd_created_tmstmp, estd_isactive_flg,estd_email_sms_content, estd_subject, estd_name, estd_temp_type)
VALUES(nextval('master.email_sms_template_dtls_estd_template_id_seq'::regclass), 'Admin', now(), 'Y', 'Dear User, To complete your de-registration please insert OTP received over SMS and EMAIL. Your OTP is @password. Regards, NSDL e-Gov', 'NA', 'Doctor_Deregister_Initiations_SMS', 'SMS');

---------------------------------------------------------------------
INSERT INTO master.email_sms_template_dtls
(estd_template_id, estd_created_by, estd_created_tmstmp, estd_isactive_flg,estd_email_sms_content, estd_subject, estd_name, estd_temp_type)
VALUES(nextval('master.email_sms_template_dtls_estd_template_id_seq'::regclass), 'Admin', now(), 'Y', 'Dear User, To complete your de-registration please insert OTP received over SMS and EMAIL. Your OTP is @password. Regards, NSDL e-Gov', 'NA', 'Doctor_Deregister_Initiations_Email', 'Email');

-----------------------------------------------------------------------

INSERT INTO master.email_sms_template_dtls
(estd_template_id, estd_created_by, estd_created_tmstmp, estd_isactive_flg,estd_email_sms_content, estd_subject, estd_name, estd_temp_type)
VALUES(nextval('master.email_sms_template_dtls_estd_template_id_seq'::regclass), 'Admin', now(), 'Y', 'Dear Mr./Ms. @patientName,Your appointment will in 5 min.Regards, Team Telemedicine.', 'NA', 'Notify_Before_Appointment_Patient_SMS', 'SMS');






