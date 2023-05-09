--
-- PostgreSQL database dump
--

-- Dumped from database version 10.13
-- Dumped by pg_dump version 10.14

-- Started on 2021-03-06 14:17:40

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE telemedicine;
--
-- TOC entry 4458 (class 1262 OID 31094)
-- Name: telemedicine; Type: DATABASE; Schema: -; Owner: sysadmin
--

CREATE DATABASE telemedicine WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_US.UTF-8' LC_CTYPE = 'en_US.UTF-8';


ALTER DATABASE telemedicine OWNER TO sysadmin;

\connect telemedicine

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 9 (class 2615 OID 31096)
-- Name: appointment; Type: SCHEMA; Schema: -; Owner: sysadmin
--

CREATE SCHEMA appointment;


ALTER SCHEMA appointment OWNER TO sysadmin;

--
-- TOC entry 4 (class 2615 OID 33870)
-- Name: audit; Type: SCHEMA; Schema: -; Owner: sysadmin
--

CREATE SCHEMA audit;


ALTER SCHEMA audit OWNER TO sysadmin;

--
-- TOC entry 12 (class 2615 OID 31099)
-- Name: master; Type: SCHEMA; Schema: -; Owner: sysadmin
--

CREATE SCHEMA master;


ALTER SCHEMA master OWNER TO sysadmin;

--
-- TOC entry 6 (class 2615 OID 31365)
-- Name: payment; Type: SCHEMA; Schema: -; Owner: sysadmin
--

CREATE SCHEMA payment;


ALTER SCHEMA payment OWNER TO sysadmin;

--
-- TOC entry 13 (class 2615 OID 31174)
-- Name: registration; Type: SCHEMA; Schema: -; Owner: sysadmin
--

CREATE SCHEMA registration;


ALTER SCHEMA registration OWNER TO sysadmin;

--
-- TOC entry 3 (class 2615 OID 31157)
-- Name: usrmgmt; Type: SCHEMA; Schema: -; Owner: sysadmin
--

CREATE SCHEMA usrmgmt;


ALTER SCHEMA usrmgmt OWNER TO sysadmin;

--
-- TOC entry 1 (class 3079 OID 13794)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 4461 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 300 (class 1259 OID 35867)
-- Name: appt_dr_scrb_assign_dtls; Type: TABLE; Schema: appointment; Owner: sysadmin
--

CREATE TABLE appointment.appt_dr_scrb_assign_dtls (
    adsad_id_pk integer NOT NULL,
    adsad_dr_user_id_fk character varying(50) NOT NULL,
    adsad_scribe_user_id_fk character varying(50) NOT NULL,
    adsad_date date NOT NULL,
    adsad_created_by character varying(255) NOT NULL,
    adsad_created_tmstmp timestamp without time zone NOT NULL,
    adsad_isactive boolean NOT NULL,
    adsad_modified_by character varying(255),
    adsad_modified_tmstmp timestamp without time zone,
    adsad_opti_version integer
);


ALTER TABLE appointment.appt_dr_scrb_assign_dtls OWNER TO sysadmin;

--
-- TOC entry 299 (class 1259 OID 35865)
-- Name: appt_dr_scrb_assign_dtls_adsad_id_pk_seq; Type: SEQUENCE; Schema: appointment; Owner: sysadmin
--

CREATE SEQUENCE appointment.appt_dr_scrb_assign_dtls_adsad_id_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE appointment.appt_dr_scrb_assign_dtls_adsad_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4462 (class 0 OID 0)
-- Dependencies: 299
-- Name: appt_dr_scrb_assign_dtls_adsad_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: appointment; Owner: sysadmin
--

ALTER SEQUENCE appointment.appt_dr_scrb_assign_dtls_adsad_id_pk_seq OWNED BY appointment.appt_dr_scrb_assign_dtls.adsad_id_pk;


--
-- TOC entry 213 (class 1259 OID 31440)
-- Name: appt_dtls; Type: TABLE; Schema: appointment; Owner: sysadmin
--

CREATE TABLE appointment.appt_dtls (
    ad_id_pk integer NOT NULL,
    ad_appt_id character varying,
    ad_dr_user_id_fk character varying(50) NOT NULL,
    ad_pt_user_id_fk character varying(50) NOT NULL,
    ad_appt_slot_fk character varying(11) NOT NULL,
    ad_appt_date_fk date NOT NULL,
    ad_appt_booked_for character varying(100) DEFAULT 'Y'::character varying,
    ad_appt_status character varying(1) DEFAULT 'S'::character varying,
    ad_pmt_trans_id_fk character varying(50),
    ap_cancel_reason character varying(250),
    ap_cancel_date timestamp without time zone,
    ad_created_tmstmp timestamp without time zone,
    ad_modified_by character varying(250),
    ad_modified_tmstmp timestamp without time zone,
    ad_opti_version integer,
    ad_created_by character varying,
    ad_scrb_user_id character varying(50),
    ad_patient_email character(100),
    ad_patient_mobile_no character(10),
    ad_isbooked boolean
);


ALTER TABLE appointment.appt_dtls OWNER TO sysadmin;

--
-- TOC entry 212 (class 1259 OID 31438)
-- Name: appt_dtls_ad_id_pk_seq; Type: SEQUENCE; Schema: appointment; Owner: sysadmin
--

CREATE SEQUENCE appointment.appt_dtls_ad_id_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE appointment.appt_dtls_ad_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4463 (class 0 OID 0)
-- Dependencies: 212
-- Name: appt_dtls_ad_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: appointment; Owner: sysadmin
--

ALTER SEQUENCE appointment.appt_dtls_ad_id_pk_seq OWNED BY appointment.appt_dtls.ad_id_pk;


--
-- TOC entry 293 (class 1259 OID 35493)
-- Name: appt_seq; Type: TABLE; Schema: appointment; Owner: sysadmin
--

CREATE TABLE appointment.appt_seq (
    aas_id_pk integer NOT NULL,
    aas_seq bigint NOT NULL,
    aas_opti_version bigint NOT NULL
);


ALTER TABLE appointment.appt_seq OWNER TO sysadmin;

--
-- TOC entry 292 (class 1259 OID 35491)
-- Name: appt_seq_aas_id_pk_seq; Type: SEQUENCE; Schema: appointment; Owner: sysadmin
--

CREATE SEQUENCE appointment.appt_seq_aas_id_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE appointment.appt_seq_aas_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4464 (class 0 OID 0)
-- Dependencies: 292
-- Name: appt_seq_aas_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: appointment; Owner: sysadmin
--

ALTER SEQUENCE appointment.appt_seq_aas_id_pk_seq OWNED BY appointment.appt_seq.aas_id_pk;


--
-- TOC entry 286 (class 1259 OID 34628)
-- Name: appt_ul_rprt_dtls; Type: TABLE; Schema: appointment; Owner: sysadmin
--

CREATE TABLE appointment.appt_ul_rprt_dtls (
    aurd_id_pk integer NOT NULL,
    aurd_opti_version character varying(255),
    aurd_ul_other_rpt_type character varying(50),
    aurd_ul_report_path character varying(250) NOT NULL,
    aurd_ul_report_name character varying(100) NOT NULL,
    aurd_ul_report_type character varying(50) NOT NULL,
    aurd_ul_note character varying(250),
    aurd_appt_id_fk character varying(255) NOT NULL
);


ALTER TABLE appointment.appt_ul_rprt_dtls OWNER TO sysadmin;

--
-- TOC entry 282 (class 1259 OID 34590)
-- Name: audit_consultation_dtls; Type: TABLE; Schema: appointment; Owner: sysadmin
--

CREATE TABLE appointment.audit_consultation_dtls (
    ct_id_pk integer NOT NULL,
    ct_advice character varying(255),
    ct_appointment_status character varying(255),
    ct_appt_id character varying(255),
    ct_chief_complaints character varying(255),
    ct_created_by character varying(255),
    ct_created_tmstmp timestamp without time zone,
    ct_diagnosis character varying(255),
    ct_doctor_id character varying(255),
    ct_medication character varying(255),
    ct_modified_by character varying(255),
    ct_modified_tmstmp timestamp without time zone,
    ct_patient_id character varying(255),
    ct_prescription_path character varying(255),
    ct_scribe_id character varying(255),
    ddt_opti_version bigint
);


ALTER TABLE appointment.audit_consultation_dtls OWNER TO sysadmin;

--
-- TOC entry 281 (class 1259 OID 34588)
-- Name: audit_consultation_dtls_ct_id_pk_seq; Type: SEQUENCE; Schema: appointment; Owner: sysadmin
--

CREATE SEQUENCE appointment.audit_consultation_dtls_ct_id_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE appointment.audit_consultation_dtls_ct_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4465 (class 0 OID 0)
-- Dependencies: 281
-- Name: audit_consultation_dtls_ct_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: appointment; Owner: sysadmin
--

ALTER SEQUENCE appointment.audit_consultation_dtls_ct_id_pk_seq OWNED BY appointment.audit_consultation_dtls.ct_id_pk;


--
-- TOC entry 244 (class 1259 OID 33368)
-- Name: consult_adv_dtls; Type: TABLE; Schema: appointment; Owner: sysadmin
--

CREATE TABLE appointment.consult_adv_dtls (
    cad_id_pk integer NOT NULL,
    cad_advice character varying(50) NOT NULL,
    cad_appt_id_fk character varying(50) NOT NULL,
    cad_dr_user_id_fk character varying(50) NOT NULL,
    cad_pt_user_id_fk character varying(50) NOT NULL,
    cad_note character varying(250),
    cad_created_by character varying(50),
    cad_created_tmstmp timestamp without time zone,
    cad_opti_version integer
);


ALTER TABLE appointment.consult_adv_dtls OWNER TO sysadmin;

--
-- TOC entry 243 (class 1259 OID 33366)
-- Name: consult_adv_dtls_cad_id_pk_seq; Type: SEQUENCE; Schema: appointment; Owner: sysadmin
--

CREATE SEQUENCE appointment.consult_adv_dtls_cad_id_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE appointment.consult_adv_dtls_cad_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4466 (class 0 OID 0)
-- Dependencies: 243
-- Name: consult_adv_dtls_cad_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: appointment; Owner: sysadmin
--

ALTER SEQUENCE appointment.consult_adv_dtls_cad_id_pk_seq OWNED BY appointment.consult_adv_dtls.cad_id_pk;


--
-- TOC entry 215 (class 1259 OID 31536)
-- Name: consult_cc_dtls; Type: TABLE; Schema: appointment; Owner: sysadmin
--

CREATE TABLE appointment.consult_cc_dtls (
    cccd_id_pk integer NOT NULL,
    cccd_appt_id_fk character varying(50) NOT NULL,
    cccd_dr_user_id_fk character varying(50) NOT NULL,
    cccd_pt_user_id_fk character varying(50) NOT NULL,
    cccd_symptom character varying(50) NOT NULL,
    cccd_symp_severity character varying(50) NOT NULL,
    cccd_symp_duration character varying(50) NOT NULL,
    cccd_symp_note character varying(250),
    cccd_created_by character varying(50) NOT NULL,
    cccd_created_tmstmp timestamp without time zone NOT NULL,
    cccd_opti_version integer
);


ALTER TABLE appointment.consult_cc_dtls OWNER TO sysadmin;

--
-- TOC entry 214 (class 1259 OID 31534)
-- Name: consult_cc_dtls_cccd_id_pk_seq; Type: SEQUENCE; Schema: appointment; Owner: sysadmin
--

CREATE SEQUENCE appointment.consult_cc_dtls_cccd_id_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE appointment.consult_cc_dtls_cccd_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4467 (class 0 OID 0)
-- Dependencies: 214
-- Name: consult_cc_dtls_cccd_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: appointment; Owner: sysadmin
--

ALTER SEQUENCE appointment.consult_cc_dtls_cccd_id_pk_seq OWNED BY appointment.consult_cc_dtls.cccd_id_pk;


--
-- TOC entry 238 (class 1259 OID 33050)
-- Name: consult_diag_dtls; Type: TABLE; Schema: appointment; Owner: sysadmin
--

CREATE TABLE appointment.consult_diag_dtls (
    cdd_id_pk integer NOT NULL,
    cdd_appt_id_fk character varying(50) NOT NULL,
    cdd_dr_user_id_fk character varying(50) NOT NULL,
    cdd_pt_user_id_fk character varying(50) NOT NULL,
    cdd_diagnosis character varying(50) NOT NULL,
    cdd_created_by character varying(50) NOT NULL,
    cdd_created_tmstmp timestamp without time zone NOT NULL,
    cdd_opti_version integer
);


ALTER TABLE appointment.consult_diag_dtls OWNER TO sysadmin;

--
-- TOC entry 237 (class 1259 OID 33048)
-- Name: consult_diag_dtls_cdd_id_pk_seq; Type: SEQUENCE; Schema: appointment; Owner: sysadmin
--

CREATE SEQUENCE appointment.consult_diag_dtls_cdd_id_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE appointment.consult_diag_dtls_cdd_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4468 (class 0 OID 0)
-- Dependencies: 237
-- Name: consult_diag_dtls_cdd_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: appointment; Owner: sysadmin
--

ALTER SEQUENCE appointment.consult_diag_dtls_cdd_id_pk_seq OWNED BY appointment.consult_diag_dtls.cdd_id_pk;


--
-- TOC entry 239 (class 1259 OID 33291)
-- Name: consult_inves_dtls; Type: TABLE; Schema: appointment; Owner: sysadmin
--

CREATE TABLE appointment.consult_inves_dtls (
    cid_id_pk integer NOT NULL,
    cid_allergies_history character varying(255),
    cid_appt_ul_report_name character varying(255) NOT NULL,
    cid_appt_ul_report_type character varying(255),
    cid_created_by character varying(255),
    cid_created_tmstmp timestamp without time zone,
    cid_inves_note character varying(255),
    cid_opti_version integer,
    cid_diastolic integer,
    cid_family_history character varying(255),
    cid_hb double precision,
    cid_height integer,
    cid_ofc integer,
    cid_pefr integer,
    cid_physical_exam character varying(255),
    cid_pulse integer,
    cid_respiration_rate integer,
    cid_spo2_level character varying(255),
    cid_systolic integer,
    cid_weight integer,
    cid_appt_id_fk character varying(255) NOT NULL,
    cid_dr_user_id_fk character varying(255) NOT NULL,
    cid_pt_user_id_fk character varying(255) NOT NULL
);


ALTER TABLE appointment.consult_inves_dtls OWNER TO sysadmin;

--
-- TOC entry 217 (class 1259 OID 31823)
-- Name: consult_mdcn_dtls; Type: TABLE; Schema: appointment; Owner: sysadmin
--

CREATE TABLE appointment.consult_mdcn_dtls (
    cmd_id_pk integer NOT NULL,
    cmd_appt_id_fk character varying NOT NULL,
    cmd_dr_user_id_fk character varying NOT NULL,
    cmd_pt_user_id_fk character varying NOT NULL,
    cmd_medicine_type character varying NOT NULL,
    cmd_medicine_name character varying NOT NULL,
    cmd_medicine_unit character varying,
    cmd_medicine_dose_dtls character varying NOT NULL,
    cmd_created_by character varying NOT NULL,
    cmd_created_tmstmp timestamp without time zone,
    cmd_opti_version integer
);


ALTER TABLE appointment.consult_mdcn_dtls OWNER TO sysadmin;

--
-- TOC entry 216 (class 1259 OID 31821)
-- Name: consult_mdcn_dtls_cmd_id_pk_seq; Type: SEQUENCE; Schema: appointment; Owner: sysadmin
--

CREATE SEQUENCE appointment.consult_mdcn_dtls_cmd_id_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE appointment.consult_mdcn_dtls_cmd_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4469 (class 0 OID 0)
-- Dependencies: 216
-- Name: consult_mdcn_dtls_cmd_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: appointment; Owner: sysadmin
--

ALTER SEQUENCE appointment.consult_mdcn_dtls_cmd_id_pk_seq OWNED BY appointment.consult_mdcn_dtls.cmd_id_pk;


--
-- TOC entry 276 (class 1259 OID 34483)
-- Name: consult_priscp_dtls; Type: TABLE; Schema: appointment; Owner: sysadmin
--

CREATE TABLE appointment.consult_priscp_dtls (
    cpd_id_pk integer NOT NULL,
    cpd_appt_id_fk character varying(50) NOT NULL,
    cpd_dr_user_id_fk character varying(50) NOT NULL,
    cpd_pt_user_id_fk character varying(50) NOT NULL,
    cpd_dr_tmplt_name character varying(50),
    cpd_priscp_path character varying(250) NOT NULL,
    cpd_is_priscp_verify character varying(1) DEFAULT 'N'::character varying,
    cpd_created_by character varying(50),
    cpd_created_tmst timestamp without time zone,
    cpd_verified_by character varying(50),
    cpd_verified_tmstmp timestamp without time zone,
    cpd_opti_version integer
);


ALTER TABLE appointment.consult_priscp_dtls OWNER TO sysadmin;

--
-- TOC entry 275 (class 1259 OID 34481)
-- Name: consult_priscp_dtls_cpd_id_pk_seq; Type: SEQUENCE; Schema: appointment; Owner: sysadmin
--

CREATE SEQUENCE appointment.consult_priscp_dtls_cpd_id_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE appointment.consult_priscp_dtls_cpd_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4470 (class 0 OID 0)
-- Dependencies: 275
-- Name: consult_priscp_dtls_cpd_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: appointment; Owner: sysadmin
--

ALTER SEQUENCE appointment.consult_priscp_dtls_cpd_id_pk_seq OWNED BY appointment.consult_priscp_dtls.cpd_id_pk;


--
-- TOC entry 280 (class 1259 OID 34554)
-- Name: consultation_dtls; Type: TABLE; Schema: appointment; Owner: sysadmin
--

CREATE TABLE appointment.consultation_dtls (
    ct_id_pk integer NOT NULL,
    ct_appt_id character varying(50) NOT NULL,
    ct_doctor_id character varying(50) NOT NULL,
    ct_scribe_id character varying(50),
    ct_patient_id character varying(50) NOT NULL,
    ct_chief_complaints text,
    ct_diagnosis text,
    ct_medication text,
    ct_advice text,
    ct_prescription_path character varying(250),
    ct_appointment_status character varying(50),
    ct_created_by character varying(50),
    ct_created_tmstmp timestamp without time zone,
    ct_modified_by character varying(50),
    ct_modified_tmstmp timestamp without time zone,
    ddt_opti_version bigint DEFAULT 0
);


ALTER TABLE appointment.consultation_dtls OWNER TO sysadmin;

--
-- TOC entry 279 (class 1259 OID 34552)
-- Name: consultation_dtls_ct_id_pk_seq; Type: SEQUENCE; Schema: appointment; Owner: sysadmin
--

CREATE SEQUENCE appointment.consultation_dtls_ct_id_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE appointment.consultation_dtls_ct_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4471 (class 0 OID 0)
-- Dependencies: 279
-- Name: consultation_dtls_ct_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: appointment; Owner: sysadmin
--

ALTER SEQUENCE appointment.consultation_dtls_ct_id_pk_seq OWNED BY appointment.consultation_dtls.ct_id_pk;


--
-- TOC entry 302 (class 1259 OID 36343)
-- Name: dr_slot_dtls; Type: TABLE; Schema: appointment; Owner: sysadmin
--

CREATE TABLE appointment.dr_slot_dtls (
    dsd_id_pk integer NOT NULL,
    dsd_consul_fee integer NOT NULL,
    dsd_created_by character varying(255) NOT NULL,
    dsd_created_tmstmp timestamp without time zone NOT NULL,
    dsd_isactive boolean NOT NULL,
    dsd_modified_by character varying(255),
    dsd_modified_tmstmp timestamp without time zone,
    dsd_opti_version integer,
    dsd_slot character varying(255) NOT NULL,
    dsd_slot_date date NOT NULL,
    dsd_dr_user_id_fk character varying(255) NOT NULL
);


ALTER TABLE appointment.dr_slot_dtls OWNER TO sysadmin;

--
-- TOC entry 301 (class 1259 OID 36341)
-- Name: dr_slot_dtls_dsd_id_pk_seq; Type: SEQUENCE; Schema: appointment; Owner: sysadmin
--

CREATE SEQUENCE appointment.dr_slot_dtls_dsd_id_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE appointment.dr_slot_dtls_dsd_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4472 (class 0 OID 0)
-- Dependencies: 301
-- Name: dr_slot_dtls_dsd_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: appointment; Owner: sysadmin
--

ALTER SEQUENCE appointment.dr_slot_dtls_dsd_id_pk_seq OWNED BY appointment.dr_slot_dtls.dsd_id_pk;


--
-- TOC entry 304 (class 1259 OID 36551)
-- Name: dr_slot_mstr; Type: TABLE; Schema: appointment; Owner: sysadmin
--

CREATE TABLE appointment.dr_slot_mstr (
    dsm_id_pk integer NOT NULL,
    dsm_slot_duration integer NOT NULL,
    dsm_rep_for_month integer NOT NULL,
    dsm_from_month integer NOT NULL,
    dsm_to_month integer NOT NULL,
    dsm_mon boolean NOT NULL,
    dsm_tue boolean NOT NULL,
    dsm_wed boolean NOT NULL,
    dsm_thu boolean NOT NULL,
    dsm_fri boolean NOT NULL,
    dsm_sat boolean NOT NULL,
    dsm_sun boolean NOT NULL,
    dsm_auto_rep boolean NOT NULL,
    dsm_dr_user_id_fk character varying(255) NOT NULL,
    dsm_created_by character varying(255),
    dsm_created_tmstmp timestamp without time zone NOT NULL,
    dsm_modified_by character varying(255),
    dsm_modified_tmstmp timestamp without time zone,
    dsm_isactive boolean NOT NULL,
    dsm_opti_version integer,
    dsm_mon_time character varying(500),
    dsm_tue_time character varying(500),
    dsm_wed_time character varying(500),
    dsm_thu_time character varying(500),
    dsm_fri_time character varying(500),
    dsm_sat_time character varying(500),
    dsm_sun_time character varying(500)
);


ALTER TABLE appointment.dr_slot_mstr OWNER TO sysadmin;

--
-- TOC entry 303 (class 1259 OID 36549)
-- Name: dr_slot_mstr_dsm_id_pk_seq; Type: SEQUENCE; Schema: appointment; Owner: sysadmin
--

CREATE SEQUENCE appointment.dr_slot_mstr_dsm_id_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE appointment.dr_slot_mstr_dsm_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4473 (class 0 OID 0)
-- Dependencies: 303
-- Name: dr_slot_mstr_dsm_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: appointment; Owner: sysadmin
--

ALTER SEQUENCE appointment.dr_slot_mstr_dsm_id_pk_seq OWNED BY appointment.dr_slot_mstr.dsm_id_pk;


--
-- TOC entry 308 (class 1259 OID 36624)
-- Name: holiday_dtls; Type: TABLE; Schema: appointment; Owner: sysadmin
--

CREATE TABLE appointment.holiday_dtls (
    hd_id_pk integer NOT NULL,
    hd_holiday_date date NOT NULL,
    hd_dr_user_id_fk character varying(255) NOT NULL,
    hd_created_by character varying(255) NOT NULL,
    hd_created_tmstmp timestamp without time zone NOT NULL,
    hd_modified_by character varying(255),
    hd_modified_tmstmp timestamp without time zone,
    hd_isactive boolean NOT NULL,
    hd_opti_version integer,
    hd_holiday_reason character varying(500)
);


ALTER TABLE appointment.holiday_dtls OWNER TO sysadmin;

--
-- TOC entry 307 (class 1259 OID 36622)
-- Name: holiday_dtls_hd_id_pk_seq; Type: SEQUENCE; Schema: appointment; Owner: sysadmin
--

CREATE SEQUENCE appointment.holiday_dtls_hd_id_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE appointment.holiday_dtls_hd_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4474 (class 0 OID 0)
-- Dependencies: 307
-- Name: holiday_dtls_hd_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: appointment; Owner: sysadmin
--

ALTER SEQUENCE appointment.holiday_dtls_hd_id_pk_seq OWNED BY appointment.holiday_dtls.hd_id_pk;


--
-- TOC entry 297 (class 1259 OID 35515)
-- Name: prescription_template_dtls; Type: TABLE; Schema: appointment; Owner: sysadmin
--

CREATE TABLE appointment.prescription_template_dtls (
    ptd_id_pk integer NOT NULL,
    ptd_doctor_id character varying(50) NOT NULL,
    ptd_prescription_template_path character varying(500) NOT NULL,
    ptd_created_by character varying(50),
    ptd_created_tmstmp timestamp without time zone,
    ptd_modified_by character varying(50),
    ptd_modified_tmstmp timestamp without time zone,
    ptd_opti_version bigint DEFAULT 0
);


ALTER TABLE appointment.prescription_template_dtls OWNER TO sysadmin;

--
-- TOC entry 296 (class 1259 OID 35513)
-- Name: prescription_template_dtls_ptd_id_pk_seq; Type: SEQUENCE; Schema: appointment; Owner: sysadmin
--

CREATE SEQUENCE appointment.prescription_template_dtls_ptd_id_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE appointment.prescription_template_dtls_ptd_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4475 (class 0 OID 0)
-- Dependencies: 296
-- Name: prescription_template_dtls_ptd_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: appointment; Owner: sysadmin
--

ALTER SEQUENCE appointment.prescription_template_dtls_ptd_id_pk_seq OWNED BY appointment.prescription_template_dtls.ptd_id_pk;


--
-- TOC entry 266 (class 1259 OID 34266)
-- Name: appt_dtls_aud; Type: TABLE; Schema: audit; Owner: sysadmin
--

CREATE TABLE audit.appt_dtls_aud (
    id integer NOT NULL,
    ad_id_pk integer NOT NULL,
    ad_appt_id character varying,
    ad_dr_user_id_fk character varying(50) NOT NULL,
    ad_pt_user_id_fk character varying(50) NOT NULL,
    ad_appt_slot_fk character varying(11) NOT NULL,
    ad_appt_date_fk date NOT NULL,
    ad_appt_booked_for character varying(100),
    ad_appt_status character varying(1),
    ad_pmt_trans_id_fk character varying(50),
    ap_cancel_reason character varying(250),
    ap_cancel_date timestamp without time zone,
    ad_created_tmstmp timestamp without time zone,
    ad_modified_by character varying(250),
    ad_modified_tmstmp timestamp without time zone,
    ad_opti_version character varying,
    user_id character varying(50),
    "timestamp" timestamp without time zone,
    ad_created_by character varying,
    ad_scrb_user_id character varying(255),
    ad_isbooked boolean
);


ALTER TABLE audit.appt_dtls_aud OWNER TO sysadmin;

--
-- TOC entry 265 (class 1259 OID 34264)
-- Name: appt_dtls_aud_ad_id_pk_seq; Type: SEQUENCE; Schema: audit; Owner: sysadmin
--

CREATE SEQUENCE audit.appt_dtls_aud_ad_id_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE audit.appt_dtls_aud_ad_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4476 (class 0 OID 0)
-- Dependencies: 265
-- Name: appt_dtls_aud_ad_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: audit; Owner: sysadmin
--

ALTER SEQUENCE audit.appt_dtls_aud_ad_id_pk_seq OWNED BY audit.appt_dtls_aud.ad_id_pk;


--
-- TOC entry 264 (class 1259 OID 34262)
-- Name: appt_dtls_aud_id_seq; Type: SEQUENCE; Schema: audit; Owner: sysadmin
--

CREATE SEQUENCE audit.appt_dtls_aud_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE audit.appt_dtls_aud_id_seq OWNER TO sysadmin;

--
-- TOC entry 4477 (class 0 OID 0)
-- Dependencies: 264
-- Name: appt_dtls_aud_id_seq; Type: SEQUENCE OWNED BY; Schema: audit; Owner: sysadmin
--

ALTER SEQUENCE audit.appt_dtls_aud_id_seq OWNED BY audit.appt_dtls_aud.id;


--
-- TOC entry 259 (class 1259 OID 34232)
-- Name: aud_consult_adv_dtls; Type: TABLE; Schema: audit; Owner: sysadmin
--

CREATE TABLE audit.aud_consult_adv_dtls (
    cad_id_pk integer NOT NULL,
    cad_advice character varying(50) NOT NULL,
    cad_appt_id character varying(50) NOT NULL,
    cad_dr_user_id character varying(50) NOT NULL,
    cad_pt_user_id character varying(50) NOT NULL,
    cad_note character varying(250),
    cad_created_by character varying(50),
    cad_created_tmstmp timestamp without time zone,
    cad_opti_version integer,
    userid character varying(50) NOT NULL
);


ALTER TABLE audit.aud_consult_adv_dtls OWNER TO sysadmin;

--
-- TOC entry 258 (class 1259 OID 34230)
-- Name: aud_consult_adv_dtls_cad_id_pk_seq; Type: SEQUENCE; Schema: audit; Owner: sysadmin
--

CREATE SEQUENCE audit.aud_consult_adv_dtls_cad_id_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE audit.aud_consult_adv_dtls_cad_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4478 (class 0 OID 0)
-- Dependencies: 258
-- Name: aud_consult_adv_dtls_cad_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: audit; Owner: sysadmin
--

ALTER SEQUENCE audit.aud_consult_adv_dtls_cad_id_pk_seq OWNED BY audit.aud_consult_adv_dtls.cad_id_pk;


--
-- TOC entry 261 (class 1259 OID 34243)
-- Name: aud_consult_cc_dtls; Type: TABLE; Schema: audit; Owner: sysadmin
--

CREATE TABLE audit.aud_consult_cc_dtls (
    cccd_audit_id_pk bigint NOT NULL,
    cccd_appt_id_fk character varying(255) NOT NULL,
    cccd_dr_user_id_fk character varying(255) NOT NULL,
    cccd_pt_user_id_fk character varying(255) NOT NULL,
    cccd_created_by character varying(50) NOT NULL,
    cccd_created_tmstmp timestamp without time zone NOT NULL,
    cccd_id_pk bigint,
    cccd_opti_version integer,
    cccd_symp_duration character varying(50) NOT NULL,
    cccd_symp_note character varying(250),
    cccd_symp_severity character varying(50) NOT NULL,
    cccd_symptom character varying(50) NOT NULL,
    "timestamp" timestamp without time zone NOT NULL,
    user_id character varying(255)
);


ALTER TABLE audit.aud_consult_cc_dtls OWNER TO sysadmin;

--
-- TOC entry 260 (class 1259 OID 34241)
-- Name: aud_consult_cc_dtls_cccd_audit_id_pk_seq; Type: SEQUENCE; Schema: audit; Owner: sysadmin
--

CREATE SEQUENCE audit.aud_consult_cc_dtls_cccd_audit_id_pk_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE audit.aud_consult_cc_dtls_cccd_audit_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4479 (class 0 OID 0)
-- Dependencies: 260
-- Name: aud_consult_cc_dtls_cccd_audit_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: audit; Owner: sysadmin
--

ALTER SEQUENCE audit.aud_consult_cc_dtls_cccd_audit_id_pk_seq OWNED BY audit.aud_consult_cc_dtls.cccd_audit_id_pk;


--
-- TOC entry 268 (class 1259 OID 34278)
-- Name: aud_consult_diag_dtls; Type: TABLE; Schema: audit; Owner: sysadmin
--

CREATE TABLE audit.aud_consult_diag_dtls (
    aud_id_pk bigint NOT NULL,
    cdd_appt_id_fk character varying(255),
    cdd_created_by character varying(255),
    cdd_created_tmstmp timestamp without time zone,
    cdd_id_pk bigint,
    cdd_opti_version bigint,
    cdd_diagnosis character varying(255),
    cdd_dr_user_id_fk character varying(255),
    cdd_pt_user_id_fk character varying(255),
    "timestamp" timestamp without time zone NOT NULL,
    user_id character varying(255)
);


ALTER TABLE audit.aud_consult_diag_dtls OWNER TO sysadmin;

--
-- TOC entry 267 (class 1259 OID 34276)
-- Name: aud_consult_diag_dtls_aud_id_pk_seq; Type: SEQUENCE; Schema: audit; Owner: sysadmin
--

CREATE SEQUENCE audit.aud_consult_diag_dtls_aud_id_pk_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE audit.aud_consult_diag_dtls_aud_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4480 (class 0 OID 0)
-- Dependencies: 267
-- Name: aud_consult_diag_dtls_aud_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: audit; Owner: sysadmin
--

ALTER SEQUENCE audit.aud_consult_diag_dtls_aud_id_pk_seq OWNED BY audit.aud_consult_diag_dtls.aud_id_pk;


--
-- TOC entry 255 (class 1259 OID 34110)
-- Name: aud_consult_inves_dtls; Type: TABLE; Schema: audit; Owner: sysadmin
--

CREATE TABLE audit.aud_consult_inves_dtls (
    cid_id_pk integer NOT NULL,
    cid_allergies_history character varying(255),
    cid_appt_ul_report_name character varying(255) NOT NULL,
    cid_appt_ul_report_type character varying(255),
    cid_created_by character varying(255),
    cid_created_tmstmp timestamp without time zone,
    cid_inves_note character varying(255),
    cid_opti_version integer,
    cid_diastolic integer,
    cid_family_history character varying(255),
    cid_hb double precision,
    cid_height integer,
    cid_ofc integer,
    cid_pefr integer,
    cid_physical_exam character varying(255),
    cid_pulse integer,
    cid_respiration_rate integer,
    cid_spo2_level character varying(255),
    cid_systolic integer,
    "timestamp" timestamp without time zone,
    user_id character varying(255),
    cid_weight integer,
    cid_appt_id_fk character varying(255) NOT NULL,
    cid_dr_user_id_fk character varying(255) NOT NULL,
    cid_pt_user_id_fk character varying(255) NOT NULL
);


ALTER TABLE audit.aud_consult_inves_dtls OWNER TO sysadmin;

--
-- TOC entry 277 (class 1259 OID 34512)
-- Name: aud_consult_priscp_dtls; Type: TABLE; Schema: audit; Owner: sysadmin
--

CREATE TABLE audit.aud_consult_priscp_dtls (
    cpd_audit_id_pk bigint NOT NULL,
    cpd_appt_id_fk character varying(255) NOT NULL,
    cpd_created_by character varying(255),
    cpd_created_tmst timestamp without time zone,
    cpd_dr_tmplt_name character varying(255),
    cpd_dr_user_id_fk character varying(255) NOT NULL,
    cpd_id_pk bigint,
    cpd_is_priscp_verify character varying(1) DEFAULT 'N'::character varying,
    cpd_opti_version integer,
    cpd_priscp_path character varying(255),
    cpd_pt_user_id_fk character varying(255) NOT NULL,
    cpd_verified_by character varying(255),
    cpd_verified_tmstmp timestamp without time zone,
    "timestamp" timestamp without time zone,
    user_id character varying(255)
);


ALTER TABLE audit.aud_consult_priscp_dtls OWNER TO sysadmin;

--
-- TOC entry 254 (class 1259 OID 34079)
-- Name: aud_dr_reg_dtls; Type: TABLE; Schema: audit; Owner: sysadmin
--

CREATE TABLE audit.aud_dr_reg_dtls (
    drd_id_pk integer NOT NULL,
    drd_dr_name character varying(50) NOT NULL,
    drd_user_id character varying(50) NOT NULL,
    drd_password character varying(50) NOT NULL,
    drd_mobile_no character varying(10) NOT NULL,
    drd_email character varying(100),
    drd_smc_number character varying(50) NOT NULL,
    drd_mci_number character varying(50) NOT NULL,
    drd_specialiazation character varying(100) NOT NULL,
    drd_consul_fee integer DEFAULT 0,
    drd_isverified character(1) DEFAULT 'Y'::bpchar NOT NULL,
    drd_is_reg_by_ipan character(1) DEFAULT 'V'::bpchar NOT NULL,
    drd_otp_refid_fk integer DEFAULT 0,
    drd_verified_lvl1_by character varying(50),
    drd_verified_lvl1_tmstmp timestamp without time zone,
    drd_verified_lvl2_by character varying(50),
    drd_verified_lvl2_tmstmp timestamp without time zone,
    drd_modified_by character varying(50),
    drd_modified_tmstmp timestamp without time zone,
    drd_opti_version bigint DEFAULT 0,
    "timestamp" timestamp without time zone,
    user_id character varying NOT NULL
);


ALTER TABLE audit.aud_dr_reg_dtls OWNER TO sysadmin;

--
-- TOC entry 253 (class 1259 OID 34077)
-- Name: aud_dr_reg_dtls_drd_id_pk_seq; Type: SEQUENCE; Schema: audit; Owner: sysadmin
--

CREATE SEQUENCE audit.aud_dr_reg_dtls_drd_id_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE audit.aud_dr_reg_dtls_drd_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4481 (class 0 OID 0)
-- Dependencies: 253
-- Name: aud_dr_reg_dtls_drd_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: audit; Owner: sysadmin
--

ALTER SEQUENCE audit.aud_dr_reg_dtls_drd_id_pk_seq OWNED BY audit.aud_dr_reg_dtls.drd_id_pk;


--
-- TOC entry 249 (class 1259 OID 33971)
-- Name: aud_dr_slot_dtls; Type: TABLE; Schema: audit; Owner: sysadmin
--

CREATE TABLE audit.aud_dr_slot_dtls (
    dsd_id_pk integer NOT NULL,
    dsd_dr_user_id_fk character varying(50) NOT NULL,
    dsd_slot character varying(11) NOT NULL,
    dsd_slot_date date NOT NULL,
    dsd_consul_fee integer NOT NULL,
    dsd_created_by character varying(50),
    dsd_created_tmstmp timestamp without time zone,
    dsd_modified_by character varying(50),
    dsd_modified_tmstmp timestamp without time zone,
    dsd_opti_version integer,
    dsd_isactive boolean NOT NULL,
    "timestamp" timestamp without time zone NOT NULL,
    user_id character varying NOT NULL
);


ALTER TABLE audit.aud_dr_slot_dtls OWNER TO sysadmin;

--
-- TOC entry 248 (class 1259 OID 33969)
-- Name: aud_dr_slot_dtls_dsd_id_pk_seq; Type: SEQUENCE; Schema: audit; Owner: sysadmin
--

CREATE SEQUENCE audit.aud_dr_slot_dtls_dsd_id_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE audit.aud_dr_slot_dtls_dsd_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4482 (class 0 OID 0)
-- Dependencies: 248
-- Name: aud_dr_slot_dtls_dsd_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: audit; Owner: sysadmin
--

ALTER SEQUENCE audit.aud_dr_slot_dtls_dsd_id_pk_seq OWNED BY audit.aud_dr_slot_dtls.dsd_id_pk;


--
-- TOC entry 306 (class 1259 OID 36567)
-- Name: aud_dr_slot_mstr; Type: TABLE; Schema: audit; Owner: sysadmin
--

CREATE TABLE audit.aud_dr_slot_mstr (
    adsm_id_pk integer NOT NULL,
    dsm_id_pk integer NOT NULL,
    dsm_slot_duration integer NOT NULL,
    dsm_rep_for_month integer NOT NULL,
    dsm_from_month integer NOT NULL,
    dsm_to_month integer NOT NULL,
    dsm_mon boolean DEFAULT false,
    dsm_tue boolean DEFAULT false,
    dsm_wed boolean DEFAULT false,
    dsm_thu boolean DEFAULT false,
    dsm_fri boolean DEFAULT false,
    dsm_sat boolean DEFAULT false,
    dsm_sun boolean DEFAULT false,
    dsm_auto_rep boolean NOT NULL,
    dsm_dr_user_id_fk character varying(255) NOT NULL,
    dsm_created_by character varying(255) NOT NULL,
    dsm_created_tmstmp timestamp without time zone NOT NULL,
    dsm_modified_by character varying(255),
    dsm_modified_tmstmp timestamp without time zone,
    dsm_isactive boolean NOT NULL,
    dsm_opti_version integer,
    "timestamp" timestamp without time zone NOT NULL,
    user_id character varying NOT NULL
);


ALTER TABLE audit.aud_dr_slot_mstr OWNER TO sysadmin;

--
-- TOC entry 305 (class 1259 OID 36565)
-- Name: aud_dr_slot_mstr_adsm_id_pk_seq; Type: SEQUENCE; Schema: audit; Owner: sysadmin
--

CREATE SEQUENCE audit.aud_dr_slot_mstr_adsm_id_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE audit.aud_dr_slot_mstr_adsm_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4483 (class 0 OID 0)
-- Dependencies: 305
-- Name: aud_dr_slot_mstr_adsm_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: audit; Owner: sysadmin
--

ALTER SEQUENCE audit.aud_dr_slot_mstr_adsm_id_pk_seq OWNED BY audit.aud_dr_slot_mstr.adsm_id_pk;


--
-- TOC entry 310 (class 1259 OID 37066)
-- Name: aud_func_mstr; Type: TABLE; Schema: audit; Owner: sysadmin
--

CREATE TABLE audit.aud_func_mstr (
    aud_id_pk bigint NOT NULL,
    cr_by character varying(50),
    cr_dtimes timestamp without time zone,
    del_dtimes timestamp without time zone,
    is_active boolean NOT NULL,
    is_deleted boolean NOT NULL,
    upd_by character varying(50),
    upd_dtimes timestamp without time zone,
    fm_func integer NOT NULL,
    fm_func_name_pk character varying(100),
    "timestamp" timestamp without time zone,
    user_id character varying(255)
);


ALTER TABLE audit.aud_func_mstr OWNER TO sysadmin;

--
-- TOC entry 309 (class 1259 OID 37064)
-- Name: aud_func_mstr_aud_id_pk_seq; Type: SEQUENCE; Schema: audit; Owner: sysadmin
--

CREATE SEQUENCE audit.aud_func_mstr_aud_id_pk_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE audit.aud_func_mstr_aud_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4484 (class 0 OID 0)
-- Dependencies: 309
-- Name: aud_func_mstr_aud_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: audit; Owner: sysadmin
--

ALTER SEQUENCE audit.aud_func_mstr_aud_id_pk_seq OWNED BY audit.aud_func_mstr.aud_id_pk;


--
-- TOC entry 251 (class 1259 OID 33983)
-- Name: aud_mstr_tbl; Type: TABLE; Schema: audit; Owner: sysadmin
--

CREATE TABLE audit.aud_mstr_tbl (
    mt_master_pk integer NOT NULL,
    mt_master_name character varying(100) NOT NULL,
    mt_master_value character varying(250) NOT NULL,
    mt_master_unit character varying(10),
    mt_isactive_flg character(1) DEFAULT 'Y'::bpchar NOT NULL,
    mt_created_by character varying(50) NOT NULL,
    mt_created_tmstmp timestamp without time zone NOT NULL,
    mt_opti_version integer,
    "timestamp" timestamp without time zone NOT NULL,
    user_id character varying NOT NULL
);


ALTER TABLE audit.aud_mstr_tbl OWNER TO sysadmin;

--
-- TOC entry 250 (class 1259 OID 33981)
-- Name: aud_mstr_tbl_mt_master_pk_seq; Type: SEQUENCE; Schema: audit; Owner: sysadmin
--

CREATE SEQUENCE audit.aud_mstr_tbl_mt_master_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE audit.aud_mstr_tbl_mt_master_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4485 (class 0 OID 0)
-- Dependencies: 250
-- Name: aud_mstr_tbl_mt_master_pk_seq; Type: SEQUENCE OWNED BY; Schema: audit; Owner: sysadmin
--

ALTER SEQUENCE audit.aud_mstr_tbl_mt_master_pk_seq OWNED BY audit.aud_mstr_tbl.mt_master_pk;


--
-- TOC entry 278 (class 1259 OID 34522)
-- Name: aud_pt_lifesty_dtls; Type: TABLE; Schema: audit; Owner: sysadmin
--

CREATE TABLE audit.aud_pt_lifesty_dtls (
    plsd_id_pk integer NOT NULL,
    "timestamp" timestamp without time zone,
    user_id character varying(255),
    plsd_lfsty_type character varying(50),
    plsd_lfsty_type_value character varying(50),
    plsd_opti_version bigint,
    plsd_pt_user_id_fk character varying(50) NOT NULL
);


ALTER TABLE audit.aud_pt_lifesty_dtls OWNER TO sysadmin;

--
-- TOC entry 270 (class 1259 OID 34318)
-- Name: aud_pt_medi_dtls; Type: TABLE; Schema: audit; Owner: sysadmin
--

CREATE TABLE audit.aud_pt_medi_dtls (
    pmd_id_pk integer NOT NULL,
    "timestamp" timestamp without time zone,
    user_id character varying(255),
    pmd_medical_type character varying(50),
    pmd_medical_type_value character varying(50),
    pmd_opti_version bigint,
    pmd_pt_user_id_fk character varying(50) NOT NULL
);


ALTER TABLE audit.aud_pt_medi_dtls OWNER TO sysadmin;

--
-- TOC entry 271 (class 1259 OID 34323)
-- Name: aud_pt_reg_dtls; Type: TABLE; Schema: audit; Owner: sysadmin
--

CREATE TABLE audit.aud_pt_reg_dtls (
    prd_id_pk integer NOT NULL,
    prd_address1 character varying(50),
    prd_address2 character varying(50),
    prd_address3 character varying(50),
    "timestamp" timestamp without time zone,
    user_id character varying(255),
    prd_blood_grp character varying(5),
    prd_created_tmstmp timestamp without time zone,
    prd_dob timestamp without time zone,
    prd_emerg_contact_no character varying(12),
    prd_gender character varying(10),
    prd_height numeric(6,0),
    prd_is_reg_by_ipan character(1) NOT NULL,
    prd_isactive character(1) NOT NULL,
    prd_modified_tmstmp timestamp without time zone,
    prd_opti_version bigint,
    prd_pincode integer,
    prd_isprofile_compl_flg character(1) NOT NULL,
    prd_email character varying(50) NOT NULL,
    prd_pt_name character varying(100) NOT NULL,
    prd_mobile_no bigint NOT NULL,
    prd_password character varying(100) NOT NULL,
    prd_user_id character varying(50) NOT NULL,
    prd_weight numeric(6,0)
);


ALTER TABLE audit.aud_pt_reg_dtls OWNER TO sysadmin;

--
-- TOC entry 273 (class 1259 OID 34342)
-- Name: aud_role_function_dtls; Type: TABLE; Schema: audit; Owner: sysadmin
--

CREATE TABLE audit.aud_role_function_dtls (
    aud_id_pk bigint NOT NULL,
    cr_by character varying(50),
    cr_dtimes timestamp without time zone,
    del_dtimes timestamp without time zone,
    is_active boolean NOT NULL,
    is_deleted boolean NOT NULL,
    upd_by character varying(50),
    upd_dtimes timestamp without time zone,
    rfd_rolefunc_pk integer NOT NULL,
    rfd_function_fk character varying(255) NOT NULL,
    rfd_function_uri character varying(255) NOT NULL,
    rfd_role_name_fk character varying(255) NOT NULL,
    "timestamp" timestamp without time zone,
    user_id character varying(255)
);


ALTER TABLE audit.aud_role_function_dtls OWNER TO sysadmin;

--
-- TOC entry 272 (class 1259 OID 34340)
-- Name: aud_role_function_dtls_aud_id_pk_seq; Type: SEQUENCE; Schema: audit; Owner: sysadmin
--

CREATE SEQUENCE audit.aud_role_function_dtls_aud_id_pk_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE audit.aud_role_function_dtls_aud_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4486 (class 0 OID 0)
-- Dependencies: 272
-- Name: aud_role_function_dtls_aud_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: audit; Owner: sysadmin
--

ALTER SEQUENCE audit.aud_role_function_dtls_aud_id_pk_seq OWNED BY audit.aud_role_function_dtls.aud_id_pk;


--
-- TOC entry 288 (class 1259 OID 35391)
-- Name: aud_role_mstr; Type: TABLE; Schema: audit; Owner: sysadmin
--

CREATE TABLE audit.aud_role_mstr (
    aud_id_pk bigint NOT NULL,
    cr_by character varying(50),
    cr_dtimes timestamp without time zone,
    del_dtimes timestamp without time zone,
    is_active boolean NOT NULL,
    is_deleted boolean NOT NULL,
    upd_by character varying(50),
    upd_dtimes timestamp without time zone,
    rm_role integer NOT NULL,
    rm_role_name_pk character varying(100),
    "timestamp" timestamp without time zone,
    user_id character varying(255)
);


ALTER TABLE audit.aud_role_mstr OWNER TO sysadmin;

--
-- TOC entry 287 (class 1259 OID 35389)
-- Name: aud_role_mstr_aud_id_pk_seq; Type: SEQUENCE; Schema: audit; Owner: sysadmin
--

CREATE SEQUENCE audit.aud_role_mstr_aud_id_pk_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE audit.aud_role_mstr_aud_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4487 (class 0 OID 0)
-- Dependencies: 287
-- Name: aud_role_mstr_aud_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: audit; Owner: sysadmin
--

ALTER SEQUENCE audit.aud_role_mstr_aud_id_pk_seq OWNED BY audit.aud_role_mstr.aud_id_pk;


--
-- TOC entry 247 (class 1259 OID 33871)
-- Name: aud_scrb_reg_dtls; Type: TABLE; Schema: audit; Owner: sysadmin
--

CREATE TABLE audit.aud_scrb_reg_dtls (
    srd_id_pk integer NOT NULL,
    srd_scrb_name character varying(100) NOT NULL,
    srd_user_id character varying(50) NOT NULL,
    srd_password character varying(100) NOT NULL,
    srd_mobile_no bigint NOT NULL,
    srd_email character varying(50) NOT NULL,
    srd_dr_user_id_fk character varying(50) NOT NULL,
    srd_address1 character varying(100),
    srd_address2 character varying(100),
    srd_address3 character varying(100),
    srd_address4 character varying(100),
    srd_isactive character varying(1) NOT NULL,
    srd_created_by character varying,
    srd_created_tmstmp timestamp without time zone,
    srd_modified_by character varying,
    srd_modified_tmstmp timestamp without time zone,
    srd_opti_version character varying,
    srd_photo_path character varying,
    "timestamp" timestamp without time zone NOT NULL,
    user_id character varying NOT NULL
);


ALTER TABLE audit.aud_scrb_reg_dtls OWNER TO sysadmin;

--
-- TOC entry 257 (class 1259 OID 34199)
-- Name: aud_usr_dtls; Type: TABLE; Schema: audit; Owner: sysadmin
--

CREATE TABLE audit.aud_usr_dtls (
    aud_id_pk bigint NOT NULL,
    ud_user_id_pk bigint,
    "timestamp" timestamp without time zone,
    user_id character varying(255),
    ud_created_by character varying(255),
    ud_created_tmstmp timestamp without time zone,
    ud_de_reg_reason character varying(255),
    ud_email character varying(100),
    ud_fail_attempt_count bigint,
    ud_fail_attempt_tmstmp timestamp without time zone,
    ud_isactive_flg boolean,
    ud_islock_flg boolean,
    ud_logged_in_flg boolean,
    ud_ischange_pwd boolean,
    ud_mci_number character varying(100),
    ud_mobile_no bigint,
    ud_modified_by character varying(255),
    ud_modified_tmstmp timestamp without time zone,
    ud_password character varying(256),
    ud_pwd_expiry_tmstmp timestamp without time zone,
    ud_role_id_fk character varying(50),
    ud_session_id character varying(255),
    ud_smc_number character varying(100),
    ud_user_full_name character varying(100),
    ud_user_id character varying(50),
    ud_user_type character varying(50),
    ud_opti_version bigint
);


ALTER TABLE audit.aud_usr_dtls OWNER TO sysadmin;

--
-- TOC entry 256 (class 1259 OID 34197)
-- Name: aud_usr_dtls_aud_id_pk_seq; Type: SEQUENCE; Schema: audit; Owner: sysadmin
--

CREATE SEQUENCE audit.aud_usr_dtls_aud_id_pk_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE audit.aud_usr_dtls_aud_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4488 (class 0 OID 0)
-- Dependencies: 256
-- Name: aud_usr_dtls_aud_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: audit; Owner: sysadmin
--

ALTER SEQUENCE audit.aud_usr_dtls_aud_id_pk_seq OWNED BY audit.aud_usr_dtls.aud_id_pk;


--
-- TOC entry 284 (class 1259 OID 34601)
-- Name: audit_consultation_dtls; Type: TABLE; Schema: audit; Owner: sysadmin
--

CREATE TABLE audit.audit_consultation_dtls (
    ct_id_pk integer NOT NULL,
    ct_advice character varying(255),
    ct_appointment_status character varying(255),
    ct_appt_id character varying(255),
    ct_chief_complaints character varying(255),
    ct_created_by character varying(255),
    ct_created_tmstmp timestamp without time zone,
    ct_diagnosis character varying(255),
    ct_doctor_id character varying(255),
    ct_medication character varying(255),
    ct_modified_by character varying(255),
    ct_modified_tmstmp timestamp without time zone,
    ct_patient_id character varying(255),
    ct_prescription_path character varying(255),
    ct_scribe_id character varying(255),
    ddt_opti_version bigint
);


ALTER TABLE audit.audit_consultation_dtls OWNER TO sysadmin;

--
-- TOC entry 283 (class 1259 OID 34599)
-- Name: audit_consultation_dtls_ct_id_pk_seq; Type: SEQUENCE; Schema: audit; Owner: sysadmin
--

CREATE SEQUENCE audit.audit_consultation_dtls_ct_id_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE audit.audit_consultation_dtls_ct_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4489 (class 0 OID 0)
-- Dependencies: 283
-- Name: audit_consultation_dtls_ct_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: audit; Owner: sysadmin
--

ALTER SEQUENCE audit.audit_consultation_dtls_ct_id_pk_seq OWNED BY audit.audit_consultation_dtls.ct_id_pk;


--
-- TOC entry 252 (class 1259 OID 34044)
-- Name: consult_mdcn_dtls_aud; Type: TABLE; Schema: audit; Owner: sysadmin
--

CREATE TABLE audit.consult_mdcn_dtls_aud (
    cmd_id_pk integer NOT NULL,
    cmd_appt_id_fk character varying NOT NULL,
    cmd_dr_user_id_fk character varying NOT NULL,
    cmd_pt_user_id_fk character varying NOT NULL,
    cmd_medicine_type character varying NOT NULL,
    cmd_medicine_name character varying NOT NULL,
    cmd_medicine_unit character varying,
    cmd_medicine_dose_dtls character varying NOT NULL,
    cmd_created_by character varying NOT NULL,
    cmd_created_tmstmp timestamp without time zone,
    cmd_opti_version integer,
    user_id character varying NOT NULL,
    "timestamp" timestamp without time zone
);


ALTER TABLE audit.consult_mdcn_dtls_aud OWNER TO sysadmin;

--
-- TOC entry 285 (class 1259 OID 34610)
-- Name: consultation_dtls_audit; Type: TABLE; Schema: audit; Owner: sysadmin
--

CREATE TABLE audit.consultation_dtls_audit (
    ct_id_pk integer NOT NULL,
    ct_appt_id character varying(50) NOT NULL,
    ct_doctor_id character varying(50) NOT NULL,
    ct_scribe_id character varying(50),
    ct_patient_id character varying(50) NOT NULL,
    ct_chief_complaints text,
    ct_diagnosis text,
    ct_medication text,
    ct_advice text,
    ct_prescription_path character varying(250),
    ct_appointment_status character varying(50) DEFAULT 'Pending'::character varying,
    ct_created_by character varying(50),
    ct_created_tmstmp timestamp without time zone,
    ct_modified_by character varying(50),
    ct_modified_tmstmp timestamp without time zone,
    ddt_opti_version bigint DEFAULT 0,
    user_id character varying(50) NOT NULL,
    "timestamp" timestamp without time zone
);


ALTER TABLE audit.consultation_dtls_audit OWNER TO sysadmin;

--
-- TOC entry 269 (class 1259 OID 34314)
-- Name: pt_rev_dtls_aud; Type: TABLE; Schema: audit; Owner: sysadmin
--

CREATE TABLE audit.pt_rev_dtls_aud (
    prd_id_pk integer NOT NULL,
    prd_dr_user_id_fk character varying(50) NOT NULL,
    prd_pt_user_id_fk character varying(50) NOT NULL,
    prd_review character varying(50) NOT NULL,
    prd_review_date date NOT NULL,
    prd_created_by character varying(50),
    prd_created_tmstmp timestamp without time zone,
    prd_opti_version character varying(50),
    prd_rating integer NOT NULL,
    user_id character varying(50) NOT NULL,
    "timestamp" timestamp without time zone
);


ALTER TABLE audit.pt_rev_dtls_aud OWNER TO sysadmin;

--
-- TOC entry 263 (class 1259 OID 34254)
-- Name: udt_mstr_tbl; Type: TABLE; Schema: audit; Owner: sysadmin
--

CREATE TABLE audit.udt_mstr_tbl (
    mt_master_pk integer NOT NULL,
    mt_created_by character varying(255),
    mt_created_tmstmp timestamp without time zone,
    mt_isactive_flg character varying(255),
    mt_master_name character varying(255),
    mt_master_unit character varying(255),
    mt_master_value character varying(255),
    mt_opti_version integer,
    "timestamp" timestamp without time zone NOT NULL,
    user_id character varying(255)
);


ALTER TABLE audit.udt_mstr_tbl OWNER TO sysadmin;

--
-- TOC entry 262 (class 1259 OID 34252)
-- Name: udt_mstr_tbl_mt_master_pk_seq; Type: SEQUENCE; Schema: audit; Owner: sysadmin
--

ALTER TABLE audit.udt_mstr_tbl ALTER COLUMN mt_master_pk ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME audit.udt_mstr_tbl_mt_master_pk_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 314 (class 1259 OID 37473)
-- Name: city_mstr; Type: TABLE; Schema: master; Owner: sysadmin
--

CREATE TABLE master.city_mstr (
    cm_city_pk integer NOT NULL,
    cm_city_name character varying NOT NULL,
    cm_state_name_fk character varying NOT NULL,
    cm_cntry_name_fk character varying NOT NULL,
    cm_isactive_flg character varying DEFAULT 'Y'::bpchar NOT NULL,
    cm_created_by character varying,
    cm_created_tmstmp timestamp without time zone,
    cm_opti_version integer
);


ALTER TABLE master.city_mstr OWNER TO sysadmin;

--
-- TOC entry 313 (class 1259 OID 37471)
-- Name: city_mstr_cm_city_pk_seq; Type: SEQUENCE; Schema: master; Owner: sysadmin
--

CREATE SEQUENCE master.city_mstr_cm_city_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE master.city_mstr_cm_city_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4490 (class 0 OID 0)
-- Dependencies: 313
-- Name: city_mstr_cm_city_pk_seq; Type: SEQUENCE OWNED BY; Schema: master; Owner: sysadmin
--

ALTER SEQUENCE master.city_mstr_cm_city_pk_seq OWNED BY master.city_mstr.cm_city_pk;


--
-- TOC entry 232 (class 1259 OID 32474)
-- Name: cntry_mstr; Type: TABLE; Schema: master; Owner: sysadmin
--

CREATE TABLE master.cntry_mstr (
    cm_cntry_pk integer NOT NULL,
    cm_cntry_name character varying NOT NULL,
    cm_isactive_flg character varying DEFAULT 'Y'::bpchar NOT NULL,
    cm_created_by character varying,
    cm_created_tmstmp timestamp without time zone,
    cm_opti_version integer
);


ALTER TABLE master.cntry_mstr OWNER TO sysadmin;

--
-- TOC entry 231 (class 1259 OID 32472)
-- Name: cntry_mstr_cm_cntry_pk_seq; Type: SEQUENCE; Schema: master; Owner: sysadmin
--

CREATE SEQUENCE master.cntry_mstr_cm_cntry_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE master.cntry_mstr_cm_cntry_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4491 (class 0 OID 0)
-- Dependencies: 231
-- Name: cntry_mstr_cm_cntry_pk_seq; Type: SEQUENCE OWNED BY; Schema: master; Owner: sysadmin
--

ALTER SEQUENCE master.cntry_mstr_cm_cntry_pk_seq OWNED BY master.cntry_mstr.cm_cntry_pk;


--
-- TOC entry 294 (class 1259 OID 35503)
-- Name: email_sms_template_dtls_estd_template_id_seq; Type: SEQUENCE; Schema: master; Owner: sysadmin
--

CREATE SEQUENCE master.email_sms_template_dtls_estd_template_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE master.email_sms_template_dtls_estd_template_id_seq OWNER TO sysadmin;

--
-- TOC entry 295 (class 1259 OID 35505)
-- Name: email_sms_template_dtls; Type: TABLE; Schema: master; Owner: sysadmin
--

CREATE TABLE master.email_sms_template_dtls (
    estd_template_id integer DEFAULT nextval('master.email_sms_template_dtls_estd_template_id_seq'::regclass) NOT NULL,
    estd_created_by character varying(255),
    estd_created_tmstmp timestamp without time zone,
    estd_isactive_flg character(1) NOT NULL,
    estd_modified_by character varying(255),
    estd_modified_tmstmp timestamp without time zone,
    estd_email_sms_content text NOT NULL,
    estd_subject character varying(100),
    estd_name character varying(100) NOT NULL,
    estd_temp_type character varying(100) NOT NULL
);


ALTER TABLE master.email_sms_template_dtls OWNER TO sysadmin;

--
-- TOC entry 228 (class 1259 OID 32288)
-- Name: mstr_tbl; Type: TABLE; Schema: master; Owner: sysadmin
--

CREATE TABLE master.mstr_tbl (
    mt_master_pk integer NOT NULL,
    mt_master_name character varying(100) NOT NULL,
    mt_master_value character varying(250) NOT NULL,
    mt_master_unit character varying(10),
    mt_isactive_flg character(1) DEFAULT 'Y'::bpchar NOT NULL,
    mt_created_by character varying(50) NOT NULL,
    mt_created_tmstmp timestamp without time zone NOT NULL,
    mt_opti_version integer
);


ALTER TABLE master.mstr_tbl OWNER TO sysadmin;

--
-- TOC entry 227 (class 1259 OID 32286)
-- Name: mstr_tbl_mt_master_pk_seq; Type: SEQUENCE; Schema: master; Owner: sysadmin
--

CREATE SEQUENCE master.mstr_tbl_mt_master_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE master.mstr_tbl_mt_master_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4492 (class 0 OID 0)
-- Dependencies: 227
-- Name: mstr_tbl_mt_master_pk_seq; Type: SEQUENCE OWNED BY; Schema: master; Owner: sysadmin
--

ALTER SEQUENCE master.mstr_tbl_mt_master_pk_seq OWNED BY master.mstr_tbl.mt_master_pk;


--
-- TOC entry 312 (class 1259 OID 37132)
-- Name: state_mstr; Type: TABLE; Schema: master; Owner: sysadmin
--

CREATE TABLE master.state_mstr (
    sm_state_pk integer NOT NULL,
    sm_state_name character varying NOT NULL,
    sm_cntry_name_fk character varying NOT NULL,
    sm_isactive_flg character varying DEFAULT 'Y'::bpchar NOT NULL,
    sm_created_by character varying,
    sm_created_tmstmp timestamp without time zone,
    sm_opti_version integer
);


ALTER TABLE master.state_mstr OWNER TO sysadmin;

--
-- TOC entry 311 (class 1259 OID 37130)
-- Name: state_mstr_sm_state_pk_seq; Type: SEQUENCE; Schema: master; Owner: sysadmin
--

CREATE SEQUENCE master.state_mstr_sm_state_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE master.state_mstr_sm_state_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4493 (class 0 OID 0)
-- Dependencies: 311
-- Name: state_mstr_sm_state_pk_seq; Type: SEQUENCE OWNED BY; Schema: master; Owner: sysadmin
--

ALTER SEQUENCE master.state_mstr_sm_state_pk_seq OWNED BY master.state_mstr.sm_state_pk;


--
-- TOC entry 211 (class 1259 OID 31428)
-- Name: pmt_dtls; Type: TABLE; Schema: payment; Owner: sysadmin
--

CREATE TABLE payment.pmt_dtls (
    pd_id_pk integer NOT NULL,
    pd_pmt_trans_id character varying(50) NOT NULL,
    pd_pmt_mode character varying(50) NOT NULL,
    pd_amount integer NOT NULL,
    pd_bank_ref_trans_id character varying(50) NOT NULL,
    pd_pmt_date date NOT NULL,
    pd_pmt_status character varying(1) NOT NULL,
    pd_refund_amount integer,
    pd_refund_date date,
    pd_card_type character varying(10) NOT NULL,
    pd_card_no integer NOT NULL,
    pd_name_on_card character varying(50) NOT NULL,
    pd_card_validity character varying(10) NOT NULL,
    prd_opti_version integer
);


ALTER TABLE payment.pmt_dtls OWNER TO sysadmin;

--
-- TOC entry 210 (class 1259 OID 31426)
-- Name: pmt_dtls_pd_id_pk_seq; Type: SEQUENCE; Schema: payment; Owner: sysadmin
--

CREATE SEQUENCE payment.pmt_dtls_pd_id_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE payment.pmt_dtls_pd_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4494 (class 0 OID 0)
-- Dependencies: 210
-- Name: pmt_dtls_pd_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: payment; Owner: sysadmin
--

ALTER SEQUENCE payment.pmt_dtls_pd_id_pk_seq OWNED BY payment.pmt_dtls.pd_id_pk;


--
-- TOC entry 241 (class 1259 OID 33318)
-- Name: consult_adv_dtls; Type: TABLE; Schema: public; Owner: sysadmin
--

CREATE TABLE public.consult_adv_dtls (
    cad_id_pk integer NOT NULL,
    cad_advice character varying(50) NOT NULL,
    cad_created_by character varying(50),
    cad_created_tmstmp timestamp without time zone,
    cad_note character varying(250),
    cad_opti_version character varying(255),
    cad_appt_id_fk character varying(255) NOT NULL,
    cad_dr_user_id_fk character varying(255) NOT NULL,
    cad_pt_user_id_fk character varying(255) NOT NULL
);


ALTER TABLE public.consult_adv_dtls OWNER TO sysadmin;

--
-- TOC entry 240 (class 1259 OID 33316)
-- Name: consult_adv_dtls_cad_id_pk_seq; Type: SEQUENCE; Schema: public; Owner: sysadmin
--

CREATE SEQUENCE public.consult_adv_dtls_cad_id_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.consult_adv_dtls_cad_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4495 (class 0 OID 0)
-- Dependencies: 240
-- Name: consult_adv_dtls_cad_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sysadmin
--

ALTER SEQUENCE public.consult_adv_dtls_cad_id_pk_seq OWNED BY public.consult_adv_dtls.cad_id_pk;


--
-- TOC entry 222 (class 1259 OID 31912)
-- Name: dr_reg_dtls; Type: TABLE; Schema: registration; Owner: sysadmin
--

CREATE TABLE registration.dr_reg_dtls (
    drd_id_pk integer NOT NULL,
    drd_dr_name character varying(50) NOT NULL,
    drd_user_id character varying(50) NOT NULL,
    drd_password character(256) NOT NULL,
    drd_mobile_no character varying(10) NOT NULL,
    drd_email character varying(100),
    drd_smc_number character varying(50) NOT NULL,
    drd_mci_number character varying(50) NOT NULL,
    drd_specialiazation character varying(100) NOT NULL,
    drd_consul_fee integer DEFAULT 0,
    drd_isverified character(1) DEFAULT 'Y'::bpchar NOT NULL,
    drd_is_reg_by_ipan character(1) DEFAULT 'V'::bpchar NOT NULL,
    drd_otp_refid_fk integer DEFAULT 0,
    drd_verified_lvl1_by character varying(50),
    drd_verified_lvl1_tmstmp timestamp without time zone,
    drd_verified_lvl2_by character varying(50),
    drd_verified_lvl2_tmstmp timestamp without time zone,
    drd_modified_by character varying(50),
    drd_modified_tmstmp timestamp without time zone,
    drd_opti_version bigint DEFAULT 0,
    drd_isactive character varying(10),
    drd_photo_path character varying(500),
    drd_city character varying(50) NOT NULL,
    drd_state character varying(50) NOT NULL,
    drd_address3 character varying(100),
    drd_address2 character varying(100),
    drd_address1 character varying(100) NOT NULL,
    drd_gender character(15) NOT NULL,
    drd_rej_reason character varying(150)
);


ALTER TABLE registration.dr_reg_dtls OWNER TO sysadmin;

--
-- TOC entry 221 (class 1259 OID 31910)
-- Name: dr_reg_dtls_drd_id_pk_seq; Type: SEQUENCE; Schema: registration; Owner: sysadmin
--

CREATE SEQUENCE registration.dr_reg_dtls_drd_id_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE registration.dr_reg_dtls_drd_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4496 (class 0 OID 0)
-- Dependencies: 221
-- Name: dr_reg_dtls_drd_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: registration; Owner: sysadmin
--

ALTER SEQUENCE registration.dr_reg_dtls_drd_id_pk_seq OWNED BY registration.dr_reg_dtls.drd_id_pk;


--
-- TOC entry 291 (class 1259 OID 35439)
-- Name: dr_reg_dtls_test; Type: TABLE; Schema: public; Owner: sysadmin
--

CREATE TABLE public.dr_reg_dtls_test (
    drd_id_pk integer DEFAULT nextval('registration.dr_reg_dtls_drd_id_pk_seq'::regclass) NOT NULL,
    drd_dr_name character varying(50) NOT NULL,
    drd_user_id character varying(50) NOT NULL,
    drd_password character varying(50) NOT NULL,
    drd_mobile_no character varying(10) NOT NULL,
    drd_email character varying(100),
    drd_smc_number character varying(50) NOT NULL,
    drd_mci_number character varying(50) NOT NULL,
    drd_specialiazation character varying(100) NOT NULL,
    drd_consul_fee integer DEFAULT 0,
    drd_isverified character(1) DEFAULT 'Y'::bpchar NOT NULL,
    drd_is_reg_by_ipan character(1) DEFAULT 'V'::bpchar NOT NULL,
    drd_otp_refid_fk integer DEFAULT 0,
    drd_verified_lvl1_by character varying(50),
    drd_verified_lvl1_tmstmp timestamp without time zone,
    drd_verified_lvl2_by character varying(50),
    drd_verified_lvl2_tmstmp timestamp without time zone,
    drd_modified_by character varying(50),
    drd_modified_tmstmp timestamp without time zone,
    drd_opti_version bigint DEFAULT 0,
    drd_isactive character varying(10),
    drd_photo_path character varying(500),
    drd_city character varying(50) NOT NULL,
    drd_state character varying(50) NOT NULL,
    drd_address3 character varying(100),
    drd_address2 character varying(100),
    drd_address1 character varying(100) NOT NULL,
    drd_gender character varying(10) NOT NULL,
    drd_rej_reason character varying(150)
);


ALTER TABLE public.dr_reg_dtls_test OWNER TO sysadmin;

--
-- TOC entry 220 (class 1259 OID 31906)
-- Name: employee; Type: TABLE; Schema: public; Owner: sysadmin
--

CREATE TABLE public.employee (
    id integer,
    rollno integer,
    salary integer
);


ALTER TABLE public.employee OWNER TO sysadmin;

--
-- TOC entry 225 (class 1259 OID 32251)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: sysadmin
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO sysadmin;

--
-- TOC entry 274 (class 1259 OID 34479)
-- Name: native; Type: SEQUENCE; Schema: public; Owner: sysadmin
--

CREATE SEQUENCE public.native
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.native OWNER TO sysadmin;

--
-- TOC entry 219 (class 1259 OID 31903)
-- Name: person; Type: TABLE; Schema: public; Owner: sysadmin
--

CREATE TABLE public.person (
    id integer,
    rollno integer,
    salary integer,
    isactive boolean
);


ALTER TABLE public.person OWNER TO sysadmin;

--
-- TOC entry 234 (class 1259 OID 32824)
-- Name: usr_otp_email_verify_dtls; Type: TABLE; Schema: public; Owner: sysadmin
--

CREATE TABLE public.usr_otp_email_verify_dtls (
    uvd_id_pk integer NOT NULL,
    uvd_email_otp character varying(255),
    uvd_email_otp_sent_tmstmp timestamp without time zone,
    uvd_email_verify_tmstmp timestamp without time zone,
    uvd_is_email_otp_verified character varying(255),
    uvd_is_sms_otp_verified character varying(255),
    uvd_opti_version integer,
    uvd_otp_expired_tmstmp timestamp without time zone,
    uvd_otp_for character varying(255),
    uvd_otp_generate_attempts integer,
    uvd_otp_incorrect_attempts integer,
    uvd_otp_sent_tmstmp timestamp without time zone,
    uvd_otp_type character varying(255),
    uvd_sms_otp character varying(255),
    uvd_sms_verify_tmstmp timestamp without time zone,
    uvd_user_id_fk character varying(255)
);


ALTER TABLE public.usr_otp_email_verify_dtls OWNER TO sysadmin;

--
-- TOC entry 226 (class 1259 OID 32262)
-- Name: usrmgmt_usr_otp_email_verify_dtls; Type: TABLE; Schema: public; Owner: sysadmin
--

CREATE TABLE public.usrmgmt_usr_otp_email_verify_dtls (
    uvd_id integer NOT NULL,
    uvd_email_otp character varying(255),
    uvd_email_otp_sent_tmstmp timestamp without time zone,
    uvd_is_otp_verified character(1),
    uvd_opti_version integer,
    uvd_otp_expired_tmstmp timestamp without time zone,
    uvd_otp_for character varying(255),
    uvd_otp_generate_attempts integer,
    uvd_otp_incorrect_attempts integer,
    uvd_otp_sent_tmstmp timestamp without time zone,
    uvd_otp_verified_tmstmp timestamp without time zone,
    uvd_sms_otp character varying(255),
    uvd_user_id_fk character varying(255),
    uvd_usr_verify_tmstmp timestamp without time zone
);


ALTER TABLE public.usrmgmt_usr_otp_email_verify_dtls OWNER TO sysadmin;

--
-- TOC entry 224 (class 1259 OID 32175)
-- Name: dr_docs_dtls; Type: TABLE; Schema: registration; Owner: sysadmin
--

CREATE TABLE registration.dr_docs_dtls (
    ddt_doc_id_pk integer NOT NULL,
    ddt_dr_user_id_fk character varying(50) NOT NULL,
    ddt_docs_name character varying(50) NOT NULL,
    ddt_docs_path character varying(500) NOT NULL,
    ddt_docs_type character varying(50),
    ddt_docs_remark character varying(250),
    ddt_created_by character varying(50),
    ddt_created_tmstmp timestamp without time zone,
    ddt_modified_by character varying(50),
    ddt_modified_tmstmp timestamp without time zone,
    ddt_opti_version bigint DEFAULT 0
);


ALTER TABLE registration.dr_docs_dtls OWNER TO sysadmin;

--
-- TOC entry 223 (class 1259 OID 32173)
-- Name: dr_docs_dtls_ddt_doc_id_pk_seq; Type: SEQUENCE; Schema: registration; Owner: sysadmin
--

CREATE SEQUENCE registration.dr_docs_dtls_ddt_doc_id_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE registration.dr_docs_dtls_ddt_doc_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4497 (class 0 OID 0)
-- Dependencies: 223
-- Name: dr_docs_dtls_ddt_doc_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: registration; Owner: sysadmin
--

ALTER SEQUENCE registration.dr_docs_dtls_ddt_doc_id_pk_seq OWNED BY registration.dr_docs_dtls.ddt_doc_id_pk;


--
-- TOC entry 206 (class 1259 OID 31273)
-- Name: dr_mstr_dtls; Type: TABLE; Schema: registration; Owner: sysadmin
--

CREATE TABLE registration.dr_mstr_dtls (
    dmd_id_pk integer NOT NULL,
    dmd_dr_name character varying(50) NOT NULL,
    dmd_user_id character varying(50) NOT NULL,
    dmd_password character(256) NOT NULL,
    dmd_mobile_no bigint NOT NULL,
    dmd_email character varying(50),
    dmd_smc_number character varying(50) NOT NULL,
    dmd_mci_number character varying(50) NOT NULL,
    dmd_specialiazation character varying(100) NOT NULL,
    dmd_consul_fee integer,
    dmd_is_reg_by_ipan character varying(1) DEFAULT 'V'::character varying NOT NULL,
    dmd_modified_by character varying(50),
    dmd_modified_tmstmp timestamp without time zone,
    dmd_opti_version character varying(50),
    dmd_isverified boolean NOT NULL,
    dmd_gender character(15) NOT NULL,
    dmd_photo_path character varying(500),
    dmd_state character(50) NOT NULL,
    dmd_address3 character(100),
    dmd_address2 character(100),
    dmd_address1 character(100) NOT NULL,
    dmd_city character(50) NOT NULL,
    srd_dr_user_id_fk character varying(255),
    dmd_isactive_flg boolean
);


ALTER TABLE registration.dr_mstr_dtls OWNER TO sysadmin;

--
-- TOC entry 205 (class 1259 OID 31271)
-- Name: dr_mstr_dtls_dmd_id_pk_seq; Type: SEQUENCE; Schema: registration; Owner: sysadmin
--

CREATE SEQUENCE registration.dr_mstr_dtls_dmd_id_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE registration.dr_mstr_dtls_dmd_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4498 (class 0 OID 0)
-- Dependencies: 205
-- Name: dr_mstr_dtls_dmd_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: registration; Owner: sysadmin
--

ALTER SEQUENCE registration.dr_mstr_dtls_dmd_id_pk_seq OWNED BY registration.dr_mstr_dtls.dmd_id_pk;


--
-- TOC entry 320 (class 1259 OID 37693)
-- Name: dr_mstr_dtls_solr_demo; Type: TABLE; Schema: registration; Owner: sysadmin
--

CREATE TABLE registration.dr_mstr_dtls_solr_demo (
    dmd_id_pk integer NOT NULL,
    dmd_dr_name character varying(25),
    dmd_user_id character varying(25),
    dmd_password character varying(25),
    dmd_mobile_no character varying(25),
    dmd_email character varying(25),
    dmd_smc_number character varying(25),
    dmd_mci_number character varying(25),
    dmd_specialiazation character varying(25),
    dmd_consul_fee character varying(25),
    dmd_is_reg_by_ipan character varying(2),
    dmd_modified_by character varying(25),
    dmd_modified_tmstmp character varying(25),
    dmd_opti_version character varying(2),
    dmd_isverified character varying(25),
    dmd_gender character varying(25),
    dmd_photo_path character varying(256),
    dmd_state character varying(25),
    dmd_address3 character varying(256),
    dmd_address2 character varying(256),
    dmd_address1 character varying(256),
    dmd_city character varying(25),
    srd_dr_user_id_fk character varying(25),
    dmd_isactive_flg character varying(2)
);


ALTER TABLE registration.dr_mstr_dtls_solr_demo OWNER TO sysadmin;

--
-- TOC entry 319 (class 1259 OID 37691)
-- Name: dr_mstr_dtls_solr_demo_dmd_id_pk_seq; Type: SEQUENCE; Schema: registration; Owner: sysadmin
--

CREATE SEQUENCE registration.dr_mstr_dtls_solr_demo_dmd_id_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE registration.dr_mstr_dtls_solr_demo_dmd_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4499 (class 0 OID 0)
-- Dependencies: 319
-- Name: dr_mstr_dtls_solr_demo_dmd_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: registration; Owner: sysadmin
--

ALTER SEQUENCE registration.dr_mstr_dtls_solr_demo_dmd_id_pk_seq OWNED BY registration.dr_mstr_dtls_solr_demo.dmd_id_pk;


--
-- TOC entry 318 (class 1259 OID 37674)
-- Name: dr_mstr_dtls_solrdemo; Type: TABLE; Schema: registration; Owner: sysadmin
--

CREATE TABLE registration.dr_mstr_dtls_solrdemo (
    dmd_id_pk integer,
    dmd_dr_name character varying(50),
    dmd_user_id character varying(50),
    dmd_password character(256),
    dmd_mobile_no bigint,
    dmd_email character varying(50),
    dmd_smc_number character varying(50),
    dmd_mci_number character varying(50),
    dmd_specialiazation character varying(100),
    dmd_consul_fee integer,
    dmd_is_reg_by_ipan character varying(1),
    dmd_modified_by character varying(50),
    dmd_modified_tmstmp timestamp without time zone,
    dmd_opti_version character varying(50),
    dmd_isverified boolean,
    dmd_gender character(15),
    dmd_photo_path character varying(500),
    dmd_state character(50),
    dmd_address3 character(100),
    dmd_address2 character(100),
    dmd_address1 character(100),
    dmd_city character(50),
    srd_dr_user_id_fk character varying(255),
    dmd_isactive_flg boolean
);


ALTER TABLE registration.dr_mstr_dtls_solrdemo OWNER TO sysadmin;

--
-- TOC entry 298 (class 1259 OID 35546)
-- Name: poc_master_doctor; Type: TABLE; Schema: registration; Owner: sysadmin
--

CREATE TABLE registration.poc_master_doctor (
    id bigint NOT NULL,
    consul_fee integer,
    doctor_name character varying(255) NOT NULL,
    email character varying(255),
    gender character varying(255) NOT NULL,
    is_reg_by_ipan character varying(1) DEFAULT 'V'::character varying NOT NULL,
    is_verified boolean NOT NULL,
    mci_number character varying(255) NOT NULL,
    mobile bigint NOT NULL,
    modified_by character varying(255),
    modified_tmstmp timestamp without time zone,
    password character varying(255) NOT NULL,
    smc_number character varying(255) NOT NULL,
    specialiazation character varying(255) NOT NULL,
    user_id character varying(255) NOT NULL
);


ALTER TABLE registration.poc_master_doctor OWNER TO sysadmin;

--
-- TOC entry 204 (class 1259 OID 31223)
-- Name: pt_lifesty_dtls; Type: TABLE; Schema: registration; Owner: sysadmin
--

CREATE TABLE registration.pt_lifesty_dtls (
    plsd_pt_user_id_fk character varying(50) NOT NULL,
    plsd_lfsty_type character varying(50),
    plsd_lfsty_type_value character varying(50),
    plsd_id_pk integer NOT NULL,
    plsd_opti_version character varying
);


ALTER TABLE registration.pt_lifesty_dtls OWNER TO sysadmin;

--
-- TOC entry 4500 (class 0 OID 0)
-- Dependencies: 204
-- Name: TABLE pt_lifesty_dtls; Type: COMMENT; Schema: registration; Owner: sysadmin
--

COMMENT ON TABLE registration.pt_lifesty_dtls IS 'patient lifestyle details';


--
-- TOC entry 203 (class 1259 OID 31210)
-- Name: pt_medi_dtls; Type: TABLE; Schema: registration; Owner: sysadmin
--

CREATE TABLE registration.pt_medi_dtls (
    pmd_id_pk integer NOT NULL,
    pmd_medical_type character varying(50),
    pmd_medical_type_value character varying(50),
    pmd_opti_version character varying,
    pmd_pt_user_id_fk character varying(50) NOT NULL
);


ALTER TABLE registration.pt_medi_dtls OWNER TO sysadmin;

--
-- TOC entry 4501 (class 0 OID 0)
-- Dependencies: 203
-- Name: TABLE pt_medi_dtls; Type: COMMENT; Schema: registration; Owner: sysadmin
--

COMMENT ON TABLE registration.pt_medi_dtls IS 'patient medical details
';


--
-- TOC entry 202 (class 1259 OID 31198)
-- Name: pt_reg_dtls; Type: TABLE; Schema: registration; Owner: sysadmin
--

CREATE TABLE registration.pt_reg_dtls (
    prd_weight numeric(6,0),
    prd_user_id character varying(50) NOT NULL,
    prd_pt_name character varying(100) NOT NULL,
    prd_pincode integer,
    prd_password character varying(100) NOT NULL,
    prd_modified_tmstmp timestamp without time zone,
    prd_mobile_no character varying(10) NOT NULL,
    prd_isprofile_compl_flg character varying(1) NOT NULL,
    prd_isactive character varying(1) NOT NULL,
    prd_is_reg_by_ipan character varying(1) NOT NULL,
    prd_id_pk integer NOT NULL,
    prd_height numeric(6,0),
    prd_gender character(15),
    "prd_emerg_contact_no." integer,
    prd_email character varying(50),
    prd_dob date,
    prd_created_tmstmp timestamp without time zone,
    prd_blood_grp character varying(5),
    prd_address3 character varying(50),
    prd_address2 character varying(50),
    prd_address1 character varying(50),
    prd_addres3 character varying(255),
    prd_emerg_contact_no character varying(255),
    prd_city_name_fk character(50),
    prd_state_name_fk character(50),
    prd_cntry_name_fk character(50),
    prd_photo_path character varying(255),
    prd_opti_version integer
);


ALTER TABLE registration.pt_reg_dtls OWNER TO sysadmin;

--
-- TOC entry 4502 (class 0 OID 0)
-- Dependencies: 202
-- Name: TABLE pt_reg_dtls; Type: COMMENT; Schema: registration; Owner: sysadmin
--

COMMENT ON TABLE registration.pt_reg_dtls IS 'patient registration details';


--
-- TOC entry 207 (class 1259 OID 31313)
-- Name: scrb_reg_dtls; Type: TABLE; Schema: registration; Owner: sysadmin
--

CREATE TABLE registration.scrb_reg_dtls (
    srd_id_pk integer NOT NULL,
    srd_scrb_name character varying(100) NOT NULL,
    srd_user_id character varying(50) NOT NULL,
    srd_password character varying(100) NOT NULL,
    srd_mobile_no bigint NOT NULL,
    srd_email character varying(50) NOT NULL,
    srd_dr_user_id_fk character varying(50) NOT NULL,
    srd_address1 character varying(100),
    srd_address2 character varying(100),
    srd_address3 character varying(100),
    srd_address4 character varying(100),
    srd_isactive character varying(1) NOT NULL,
    srd_created_by character varying(50),
    srd_created_tmstmp timestamp without time zone,
    srd_modified_by character varying(50),
    srd_modified_tmstmp timestamp without time zone,
    srd_opti_version character varying,
    srd_photo_path character varying,
    srd_is_default_scribe character varying(1),
    srd_gender character(15),
    srd_state character(50) NOT NULL,
    srd_city character(50) NOT NULL
);


ALTER TABLE registration.scrb_reg_dtls OWNER TO sysadmin;

--
-- TOC entry 235 (class 1259 OID 32882)
-- Name: func_mstr; Type: TABLE; Schema: usrmgmt; Owner: sysadmin
--

CREATE TABLE usrmgmt.func_mstr (
    fm_func_name_pk character varying(100) NOT NULL,
    cr_by character varying(50),
    cr_dtimes timestamp without time zone,
    del_dtimes timestamp without time zone,
    is_active boolean NOT NULL,
    is_deleted boolean NOT NULL,
    upd_by character varying(50),
    upd_dtimes timestamp without time zone,
    fm_func integer NOT NULL
);


ALTER TABLE usrmgmt.func_mstr OWNER TO sysadmin;

--
-- TOC entry 242 (class 1259 OID 33344)
-- Name: oauth_token; Type: TABLE; Schema: usrmgmt; Owner: sysadmin
--

CREATE TABLE usrmgmt.oauth_token (
    user_id character varying NOT NULL,
    auth_token character varying,
    expiration_time timestamp without time zone,
    is_active boolean,
    cr_by character varying,
    created_time timestamp without time zone,
    upd_by character varying,
    upd_time timestamp without time zone
);


ALTER TABLE usrmgmt.oauth_token OWNER TO sysadmin;

--
-- TOC entry 209 (class 1259 OID 31342)
-- Name: pt_rev_dtls; Type: TABLE; Schema: usrmgmt; Owner: sysadmin
--

CREATE TABLE usrmgmt.pt_rev_dtls (
    prd_id_pk integer NOT NULL,
    prd_dr_user_id_fk character varying(50) NOT NULL,
    prd_pt_user_id_fk character varying(50) NOT NULL,
    prd_review character varying(50),
    prd_review_date date NOT NULL,
    prd_created_by character varying(50),
    prd_created_tmstmp timestamp without time zone,
    prd_opti_version character varying(50),
    prd_rating integer
);


ALTER TABLE usrmgmt.pt_rev_dtls OWNER TO sysadmin;

--
-- TOC entry 208 (class 1259 OID 31340)
-- Name: pt_rev_dtls_prd_id_pk_seq; Type: SEQUENCE; Schema: usrmgmt; Owner: sysadmin
--

CREATE SEQUENCE usrmgmt.pt_rev_dtls_prd_id_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE usrmgmt.pt_rev_dtls_prd_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4503 (class 0 OID 0)
-- Dependencies: 208
-- Name: pt_rev_dtls_prd_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: usrmgmt; Owner: sysadmin
--

ALTER SEQUENCE usrmgmt.pt_rev_dtls_prd_id_pk_seq OWNED BY usrmgmt.pt_rev_dtls.prd_id_pk;


--
-- TOC entry 316 (class 1259 OID 37594)
-- Name: role_func_mapping; Type: TABLE; Schema: usrmgmt; Owner: sysadmin
--

CREATE TABLE usrmgmt.role_func_mapping (
    rfm_id_pk integer NOT NULL,
    rfm_role_name character varying(50),
    rfm_menu_name character varying(50),
    rfm_function_name character varying(50),
    rfm_route character varying(50),
    rfm_cr_by character varying(50),
    rfm_cr_dtimes timestamp without time zone,
    rfm_is_active boolean NOT NULL,
    rfm_is_deleted boolean NOT NULL,
    rfm_upd_by character varying(50),
    rfm_upd_dtimes timestamp without time zone,
    rfm_submenu_name character varying
);


ALTER TABLE usrmgmt.role_func_mapping OWNER TO sysadmin;

--
-- TOC entry 315 (class 1259 OID 37592)
-- Name: role_func_mapping_rfm_id_pk_seq; Type: SEQUENCE; Schema: usrmgmt; Owner: sysadmin
--

CREATE SEQUENCE usrmgmt.role_func_mapping_rfm_id_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE usrmgmt.role_func_mapping_rfm_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4504 (class 0 OID 0)
-- Dependencies: 315
-- Name: role_func_mapping_rfm_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: usrmgmt; Owner: sysadmin
--

ALTER SEQUENCE usrmgmt.role_func_mapping_rfm_id_pk_seq OWNED BY usrmgmt.role_func_mapping.rfm_id_pk;


--
-- TOC entry 246 (class 1259 OID 33436)
-- Name: role_function_dtls; Type: TABLE; Schema: usrmgmt; Owner: sysadmin
--

CREATE TABLE usrmgmt.role_function_dtls (
    rfd_rolefunc_pk integer NOT NULL,
    cr_by character varying(50),
    cr_dtimes timestamp without time zone,
    del_dtimes timestamp without time zone,
    is_active boolean NOT NULL,
    is_deleted boolean NOT NULL,
    upd_by character varying(50),
    upd_dtimes timestamp without time zone,
    rfd_function_fk character varying(255) NOT NULL,
    rfd_role_name_fk character varying(255) NOT NULL,
    rfd_function_uri character varying(255)
);


ALTER TABLE usrmgmt.role_function_dtls OWNER TO sysadmin;

--
-- TOC entry 245 (class 1259 OID 33434)
-- Name: role_function_dtls_rfd_rolefunc_pk_seq; Type: SEQUENCE; Schema: usrmgmt; Owner: sysadmin
--

CREATE SEQUENCE usrmgmt.role_function_dtls_rfd_rolefunc_pk_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE usrmgmt.role_function_dtls_rfd_rolefunc_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4505 (class 0 OID 0)
-- Dependencies: 245
-- Name: role_function_dtls_rfd_rolefunc_pk_seq; Type: SEQUENCE OWNED BY; Schema: usrmgmt; Owner: sysadmin
--

ALTER SEQUENCE usrmgmt.role_function_dtls_rfd_rolefunc_pk_seq OWNED BY usrmgmt.role_function_dtls.rfd_rolefunc_pk;


--
-- TOC entry 236 (class 1259 OID 32895)
-- Name: role_mstr; Type: TABLE; Schema: usrmgmt; Owner: sysadmin
--

CREATE TABLE usrmgmt.role_mstr (
    rm_role_name_pk character varying(100) NOT NULL,
    cr_by character varying(50),
    cr_dtimes timestamp without time zone,
    del_dtimes timestamp without time zone,
    is_active boolean NOT NULL,
    is_deleted boolean NOT NULL,
    upd_by character varying(50),
    upd_dtimes timestamp without time zone,
    rm_role integer NOT NULL
);


ALTER TABLE usrmgmt.role_mstr OWNER TO sysadmin;

--
-- TOC entry 290 (class 1259 OID 35410)
-- Name: usr_dtls; Type: TABLE; Schema: usrmgmt; Owner: sysadmin
--

CREATE TABLE usrmgmt.usr_dtls (
    ud_user_id_pk bigint NOT NULL,
    ud_created_by character varying(255),
    ud_created_tmstmp timestamp without time zone,
    ud_de_reg_reason character varying(255),
    ud_email character varying(100),
    ud_fail_attempt_count bigint,
    ud_fail_attempt_tmstmp timestamp without time zone,
    ud_isactive_flg boolean,
    ud_islock_flg boolean,
    ud_logged_in_flg boolean,
    ud_ischange_pwd boolean,
    ud_mci_number character varying(100),
    ud_mobile_no bigint NOT NULL,
    ud_modified_by character varying(255),
    ud_modified_tmstmp timestamp without time zone,
    ud_password character varying(256) NOT NULL,
    ud_pwd_expiry_tmstmp timestamp without time zone,
    ud_session_id character varying(255),
    ud_smc_number character varying(100),
    ud_user_full_name character varying(100) NOT NULL,
    ud_user_id character varying(50) NOT NULL,
    ud_user_type character varying(50) NOT NULL,
    ud_opti_version bigint,
    ud_role_id_fk character varying(100) NOT NULL
);


ALTER TABLE usrmgmt.usr_dtls OWNER TO sysadmin;

--
-- TOC entry 230 (class 1259 OID 32447)
-- Name: usr_dtls_his; Type: TABLE; Schema: usrmgmt; Owner: sysadmin
--

CREATE TABLE usrmgmt.usr_dtls_his (
    id integer NOT NULL,
    ud_created_by character varying(255),
    ud_created_tmstmp timestamp without time zone,
    ud_password character varying(255),
    ud_user_id character varying(255) NOT NULL
);


ALTER TABLE usrmgmt.usr_dtls_his OWNER TO sysadmin;

--
-- TOC entry 229 (class 1259 OID 32445)
-- Name: usr_dtls_his_id_seq; Type: SEQUENCE; Schema: usrmgmt; Owner: sysadmin
--

CREATE SEQUENCE usrmgmt.usr_dtls_his_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE usrmgmt.usr_dtls_his_id_seq OWNER TO sysadmin;

--
-- TOC entry 4506 (class 0 OID 0)
-- Dependencies: 229
-- Name: usr_dtls_his_id_seq; Type: SEQUENCE OWNED BY; Schema: usrmgmt; Owner: sysadmin
--

ALTER SEQUENCE usrmgmt.usr_dtls_his_id_seq OWNED BY usrmgmt.usr_dtls_his.id;


--
-- TOC entry 289 (class 1259 OID 35408)
-- Name: usr_dtls_ud_user_id_pk_seq; Type: SEQUENCE; Schema: usrmgmt; Owner: sysadmin
--

CREATE SEQUENCE usrmgmt.usr_dtls_ud_user_id_pk_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE usrmgmt.usr_dtls_ud_user_id_pk_seq OWNER TO sysadmin;

--
-- TOC entry 4507 (class 0 OID 0)
-- Dependencies: 289
-- Name: usr_dtls_ud_user_id_pk_seq; Type: SEQUENCE OWNED BY; Schema: usrmgmt; Owner: sysadmin
--

ALTER SEQUENCE usrmgmt.usr_dtls_ud_user_id_pk_seq OWNED BY usrmgmt.usr_dtls.ud_user_id_pk;


--
-- TOC entry 218 (class 1259 OID 31888)
-- Name: usr_otp_email_verify_dtls_id_seq; Type: SEQUENCE; Schema: usrmgmt; Owner: sysadmin
--

CREATE SEQUENCE usrmgmt.usr_otp_email_verify_dtls_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE usrmgmt.usr_otp_email_verify_dtls_id_seq OWNER TO sysadmin;

--
-- TOC entry 233 (class 1259 OID 32812)
-- Name: usr_otp_email_verify_dtls; Type: TABLE; Schema: usrmgmt; Owner: sysadmin
--

CREATE TABLE usrmgmt.usr_otp_email_verify_dtls (
    uvd_id_pk integer DEFAULT nextval('usrmgmt.usr_otp_email_verify_dtls_id_seq'::regclass) NOT NULL,
    uvd_sms_otp character varying(250),
    uvd_otp_sent_tmstmp timestamp without time zone,
    uvd_email_otp character varying(250),
    uvd_email_otp_sent_tmstmp timestamp without time zone,
    uvd_email_verify_tmstmp timestamp without time zone,
    uvd_otp_for character varying(10) NOT NULL,
    uvd_otp_type character varying(10) NOT NULL,
    uvd_is_sms_otp_verified character(1) DEFAULT 'N'::bpchar,
    uvd_sms_verify_tmstmp timestamp without time zone,
    uvd_otp_expired_tmstmp timestamp without time zone,
    uvd_otp_incorrect_attempts integer,
    uvd_otp_generate_attempts integer,
    uvd_opti_version integer DEFAULT 0,
    uvd_user_id_fk character varying(50) NOT NULL,
    uvd_is_email_otp_verified character(1) DEFAULT 'N'::bpchar NOT NULL,
    uvd_created_tmstmp timestamp without time zone NOT NULL
);


ALTER TABLE usrmgmt.usr_otp_email_verify_dtls OWNER TO sysadmin;

--
-- TOC entry 317 (class 1259 OID 37639)
-- Name: vcauth_token; Type: TABLE; Schema: usrmgmt; Owner: sysadmin
--

CREATE TABLE usrmgmt.vcauth_token (
    user_id character varying NOT NULL,
    user_role character varying,
    appt_id character varying NOT NULL,
    auth_token character varying,
    is_active boolean,
    created_by character varying,
    created_time timestamp without time zone,
    updated_by character varying,
    updated_time timestamp without time zone,
    expire_time timestamp without time zone
);


ALTER TABLE usrmgmt.vcauth_token OWNER TO sysadmin;

--
-- TOC entry 4045 (class 2604 OID 35870)
-- Name: appt_dr_scrb_assign_dtls adsad_id_pk; Type: DEFAULT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.appt_dr_scrb_assign_dtls ALTER COLUMN adsad_id_pk SET DEFAULT nextval('appointment.appt_dr_scrb_assign_dtls_adsad_id_pk_seq'::regclass);


--
-- TOC entry 3981 (class 2604 OID 31443)
-- Name: appt_dtls ad_id_pk; Type: DEFAULT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.appt_dtls ALTER COLUMN ad_id_pk SET DEFAULT nextval('appointment.appt_dtls_ad_id_pk_seq'::regclass);


--
-- TOC entry 4040 (class 2604 OID 35496)
-- Name: appt_seq aas_id_pk; Type: DEFAULT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.appt_seq ALTER COLUMN aas_id_pk SET DEFAULT nextval('appointment.appt_seq_aas_id_pk_seq'::regclass);


--
-- TOC entry 4028 (class 2604 OID 34593)
-- Name: audit_consultation_dtls ct_id_pk; Type: DEFAULT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.audit_consultation_dtls ALTER COLUMN ct_id_pk SET DEFAULT nextval('appointment.audit_consultation_dtls_ct_id_pk_seq'::regclass);


--
-- TOC entry 4005 (class 2604 OID 33371)
-- Name: consult_adv_dtls cad_id_pk; Type: DEFAULT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_adv_dtls ALTER COLUMN cad_id_pk SET DEFAULT nextval('appointment.consult_adv_dtls_cad_id_pk_seq'::regclass);


--
-- TOC entry 3984 (class 2604 OID 31539)
-- Name: consult_cc_dtls cccd_id_pk; Type: DEFAULT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_cc_dtls ALTER COLUMN cccd_id_pk SET DEFAULT nextval('appointment.consult_cc_dtls_cccd_id_pk_seq'::regclass);


--
-- TOC entry 4003 (class 2604 OID 33053)
-- Name: consult_diag_dtls cdd_id_pk; Type: DEFAULT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_diag_dtls ALTER COLUMN cdd_id_pk SET DEFAULT nextval('appointment.consult_diag_dtls_cdd_id_pk_seq'::regclass);


--
-- TOC entry 3985 (class 2604 OID 31826)
-- Name: consult_mdcn_dtls cmd_id_pk; Type: DEFAULT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_mdcn_dtls ALTER COLUMN cmd_id_pk SET DEFAULT nextval('appointment.consult_mdcn_dtls_cmd_id_pk_seq'::regclass);


--
-- TOC entry 4023 (class 2604 OID 34486)
-- Name: consult_priscp_dtls cpd_id_pk; Type: DEFAULT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_priscp_dtls ALTER COLUMN cpd_id_pk SET DEFAULT nextval('appointment.consult_priscp_dtls_cpd_id_pk_seq'::regclass);


--
-- TOC entry 4026 (class 2604 OID 34557)
-- Name: consultation_dtls ct_id_pk; Type: DEFAULT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consultation_dtls ALTER COLUMN ct_id_pk SET DEFAULT nextval('appointment.consultation_dtls_ct_id_pk_seq'::regclass);


--
-- TOC entry 4046 (class 2604 OID 36346)
-- Name: dr_slot_dtls dsd_id_pk; Type: DEFAULT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.dr_slot_dtls ALTER COLUMN dsd_id_pk SET DEFAULT nextval('appointment.dr_slot_dtls_dsd_id_pk_seq'::regclass);


--
-- TOC entry 4047 (class 2604 OID 36554)
-- Name: dr_slot_mstr dsm_id_pk; Type: DEFAULT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.dr_slot_mstr ALTER COLUMN dsm_id_pk SET DEFAULT nextval('appointment.dr_slot_mstr_dsm_id_pk_seq'::regclass);


--
-- TOC entry 4056 (class 2604 OID 36627)
-- Name: holiday_dtls hd_id_pk; Type: DEFAULT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.holiday_dtls ALTER COLUMN hd_id_pk SET DEFAULT nextval('appointment.holiday_dtls_hd_id_pk_seq'::regclass);


--
-- TOC entry 4042 (class 2604 OID 35518)
-- Name: prescription_template_dtls ptd_id_pk; Type: DEFAULT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.prescription_template_dtls ALTER COLUMN ptd_id_pk SET DEFAULT nextval('appointment.prescription_template_dtls_ptd_id_pk_seq'::regclass);


--
-- TOC entry 4019 (class 2604 OID 34269)
-- Name: appt_dtls_aud id; Type: DEFAULT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.appt_dtls_aud ALTER COLUMN id SET DEFAULT nextval('audit.appt_dtls_aud_id_seq'::regclass);


--
-- TOC entry 4020 (class 2604 OID 34270)
-- Name: appt_dtls_aud ad_id_pk; Type: DEFAULT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.appt_dtls_aud ALTER COLUMN ad_id_pk SET DEFAULT nextval('audit.appt_dtls_aud_ad_id_pk_seq'::regclass);


--
-- TOC entry 4017 (class 2604 OID 34235)
-- Name: aud_consult_adv_dtls cad_id_pk; Type: DEFAULT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_consult_adv_dtls ALTER COLUMN cad_id_pk SET DEFAULT nextval('audit.aud_consult_adv_dtls_cad_id_pk_seq'::regclass);


--
-- TOC entry 4018 (class 2604 OID 34246)
-- Name: aud_consult_cc_dtls cccd_audit_id_pk; Type: DEFAULT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_consult_cc_dtls ALTER COLUMN cccd_audit_id_pk SET DEFAULT nextval('audit.aud_consult_cc_dtls_cccd_audit_id_pk_seq'::regclass);


--
-- TOC entry 4021 (class 2604 OID 34281)
-- Name: aud_consult_diag_dtls aud_id_pk; Type: DEFAULT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_consult_diag_dtls ALTER COLUMN aud_id_pk SET DEFAULT nextval('audit.aud_consult_diag_dtls_aud_id_pk_seq'::regclass);


--
-- TOC entry 4010 (class 2604 OID 34082)
-- Name: aud_dr_reg_dtls drd_id_pk; Type: DEFAULT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_dr_reg_dtls ALTER COLUMN drd_id_pk SET DEFAULT nextval('audit.aud_dr_reg_dtls_drd_id_pk_seq'::regclass);


--
-- TOC entry 4007 (class 2604 OID 33974)
-- Name: aud_dr_slot_dtls dsd_id_pk; Type: DEFAULT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_dr_slot_dtls ALTER COLUMN dsd_id_pk SET DEFAULT nextval('audit.aud_dr_slot_dtls_dsd_id_pk_seq'::regclass);


--
-- TOC entry 4048 (class 2604 OID 36570)
-- Name: aud_dr_slot_mstr adsm_id_pk; Type: DEFAULT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_dr_slot_mstr ALTER COLUMN adsm_id_pk SET DEFAULT nextval('audit.aud_dr_slot_mstr_adsm_id_pk_seq'::regclass);


--
-- TOC entry 4057 (class 2604 OID 37069)
-- Name: aud_func_mstr aud_id_pk; Type: DEFAULT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_func_mstr ALTER COLUMN aud_id_pk SET DEFAULT nextval('audit.aud_func_mstr_aud_id_pk_seq'::regclass);


--
-- TOC entry 4008 (class 2604 OID 33986)
-- Name: aud_mstr_tbl mt_master_pk; Type: DEFAULT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_mstr_tbl ALTER COLUMN mt_master_pk SET DEFAULT nextval('audit.aud_mstr_tbl_mt_master_pk_seq'::regclass);


--
-- TOC entry 4022 (class 2604 OID 34345)
-- Name: aud_role_function_dtls aud_id_pk; Type: DEFAULT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_role_function_dtls ALTER COLUMN aud_id_pk SET DEFAULT nextval('audit.aud_role_function_dtls_aud_id_pk_seq'::regclass);


--
-- TOC entry 4032 (class 2604 OID 35394)
-- Name: aud_role_mstr aud_id_pk; Type: DEFAULT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_role_mstr ALTER COLUMN aud_id_pk SET DEFAULT nextval('audit.aud_role_mstr_aud_id_pk_seq'::regclass);


--
-- TOC entry 4016 (class 2604 OID 34202)
-- Name: aud_usr_dtls aud_id_pk; Type: DEFAULT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_usr_dtls ALTER COLUMN aud_id_pk SET DEFAULT nextval('audit.aud_usr_dtls_aud_id_pk_seq'::regclass);


--
-- TOC entry 4029 (class 2604 OID 34604)
-- Name: audit_consultation_dtls ct_id_pk; Type: DEFAULT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.audit_consultation_dtls ALTER COLUMN ct_id_pk SET DEFAULT nextval('audit.audit_consultation_dtls_ct_id_pk_seq'::regclass);


--
-- TOC entry 4060 (class 2604 OID 37476)
-- Name: city_mstr cm_city_pk; Type: DEFAULT; Schema: master; Owner: sysadmin
--

ALTER TABLE ONLY master.city_mstr ALTER COLUMN cm_city_pk SET DEFAULT nextval('master.city_mstr_cm_city_pk_seq'::regclass);


--
-- TOC entry 3998 (class 2604 OID 32477)
-- Name: cntry_mstr cm_cntry_pk; Type: DEFAULT; Schema: master; Owner: sysadmin
--

ALTER TABLE ONLY master.cntry_mstr ALTER COLUMN cm_cntry_pk SET DEFAULT nextval('master.cntry_mstr_cm_cntry_pk_seq'::regclass);


--
-- TOC entry 3994 (class 2604 OID 32291)
-- Name: mstr_tbl mt_master_pk; Type: DEFAULT; Schema: master; Owner: sysadmin
--

ALTER TABLE ONLY master.mstr_tbl ALTER COLUMN mt_master_pk SET DEFAULT nextval('master.mstr_tbl_mt_master_pk_seq'::regclass);


--
-- TOC entry 4058 (class 2604 OID 37135)
-- Name: state_mstr sm_state_pk; Type: DEFAULT; Schema: master; Owner: sysadmin
--

ALTER TABLE ONLY master.state_mstr ALTER COLUMN sm_state_pk SET DEFAULT nextval('master.state_mstr_sm_state_pk_seq'::regclass);


--
-- TOC entry 3980 (class 2604 OID 31431)
-- Name: pmt_dtls pd_id_pk; Type: DEFAULT; Schema: payment; Owner: sysadmin
--

ALTER TABLE ONLY payment.pmt_dtls ALTER COLUMN pd_id_pk SET DEFAULT nextval('payment.pmt_dtls_pd_id_pk_seq'::regclass);


--
-- TOC entry 4004 (class 2604 OID 33321)
-- Name: consult_adv_dtls cad_id_pk; Type: DEFAULT; Schema: public; Owner: sysadmin
--

ALTER TABLE ONLY public.consult_adv_dtls ALTER COLUMN cad_id_pk SET DEFAULT nextval('public.consult_adv_dtls_cad_id_pk_seq'::regclass);


--
-- TOC entry 3992 (class 2604 OID 32178)
-- Name: dr_docs_dtls ddt_doc_id_pk; Type: DEFAULT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.dr_docs_dtls ALTER COLUMN ddt_doc_id_pk SET DEFAULT nextval('registration.dr_docs_dtls_ddt_doc_id_pk_seq'::regclass);


--
-- TOC entry 3977 (class 2604 OID 31276)
-- Name: dr_mstr_dtls dmd_id_pk; Type: DEFAULT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.dr_mstr_dtls ALTER COLUMN dmd_id_pk SET DEFAULT nextval('registration.dr_mstr_dtls_dmd_id_pk_seq'::regclass);


--
-- TOC entry 4063 (class 2604 OID 37696)
-- Name: dr_mstr_dtls_solr_demo dmd_id_pk; Type: DEFAULT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.dr_mstr_dtls_solr_demo ALTER COLUMN dmd_id_pk SET DEFAULT nextval('registration.dr_mstr_dtls_solr_demo_dmd_id_pk_seq'::regclass);


--
-- TOC entry 3986 (class 2604 OID 31915)
-- Name: dr_reg_dtls drd_id_pk; Type: DEFAULT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.dr_reg_dtls ALTER COLUMN drd_id_pk SET DEFAULT nextval('registration.dr_reg_dtls_drd_id_pk_seq'::regclass);


--
-- TOC entry 3979 (class 2604 OID 31345)
-- Name: pt_rev_dtls prd_id_pk; Type: DEFAULT; Schema: usrmgmt; Owner: sysadmin
--

ALTER TABLE ONLY usrmgmt.pt_rev_dtls ALTER COLUMN prd_id_pk SET DEFAULT nextval('usrmgmt.pt_rev_dtls_prd_id_pk_seq'::regclass);


--
-- TOC entry 4062 (class 2604 OID 37597)
-- Name: role_func_mapping rfm_id_pk; Type: DEFAULT; Schema: usrmgmt; Owner: sysadmin
--

ALTER TABLE ONLY usrmgmt.role_func_mapping ALTER COLUMN rfm_id_pk SET DEFAULT nextval('usrmgmt.role_func_mapping_rfm_id_pk_seq'::regclass);


--
-- TOC entry 4006 (class 2604 OID 33439)
-- Name: role_function_dtls rfd_rolefunc_pk; Type: DEFAULT; Schema: usrmgmt; Owner: sysadmin
--

ALTER TABLE ONLY usrmgmt.role_function_dtls ALTER COLUMN rfd_rolefunc_pk SET DEFAULT nextval('usrmgmt.role_function_dtls_rfd_rolefunc_pk_seq'::regclass);


--
-- TOC entry 4033 (class 2604 OID 35413)
-- Name: usr_dtls ud_user_id_pk; Type: DEFAULT; Schema: usrmgmt; Owner: sysadmin
--

ALTER TABLE ONLY usrmgmt.usr_dtls ALTER COLUMN ud_user_id_pk SET DEFAULT nextval('usrmgmt.usr_dtls_ud_user_id_pk_seq'::regclass);


--
-- TOC entry 3996 (class 2604 OID 32450)
-- Name: usr_dtls_his id; Type: DEFAULT; Schema: usrmgmt; Owner: sysadmin
--

ALTER TABLE ONLY usrmgmt.usr_dtls_his ALTER COLUMN id SET DEFAULT nextval('usrmgmt.usr_dtls_his_id_seq'::regclass);


--
-- TOC entry 4169 (class 2606 OID 33491)
-- Name: consult_adv_dtls ak_consult_adv_dtls_unique_constraints; Type: CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_adv_dtls
    ADD CONSTRAINT ak_consult_adv_dtls_unique_constraints UNIQUE (cad_appt_id_fk, cad_advice, cad_dr_user_id_fk, cad_pt_user_id_fk);


--
-- TOC entry 4117 (class 2606 OID 33242)
-- Name: consult_cc_dtls ak_consult_cc_dtls_unique_constraints; Type: CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_cc_dtls
    ADD CONSTRAINT ak_consult_cc_dtls_unique_constraints UNIQUE (cccd_appt_id_fk, cccd_dr_user_id_fk, cccd_pt_user_id_fk, cccd_symptom);


--
-- TOC entry 4159 (class 2606 OID 33057)
-- Name: consult_diag_dtls ak_consult_diag_dtls_unique_constraints; Type: CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_diag_dtls
    ADD CONSTRAINT ak_consult_diag_dtls_unique_constraints UNIQUE (cdd_appt_id_fk, cdd_dr_user_id_fk, cdd_pt_user_id_fk, cdd_diagnosis, cdd_created_by, cdd_created_tmstmp);


--
-- TOC entry 4121 (class 2606 OID 33563)
-- Name: consult_mdcn_dtls ak_consult_mdcn_dtls_unique_constraints; Type: CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_mdcn_dtls
    ADD CONSTRAINT ak_consult_mdcn_dtls_unique_constraints UNIQUE (cmd_appt_id_fk, cmd_dr_user_id_fk, cmd_pt_user_id_fk, cmd_medicine_type, cmd_medicine_name);


--
-- TOC entry 4253 (class 2606 OID 35875)
-- Name: appt_dr_scrb_assign_dtls appt_dr_scrb_assign_dtls_pkey; Type: CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.appt_dr_scrb_assign_dtls
    ADD CONSTRAINT appt_dr_scrb_assign_dtls_pkey PRIMARY KEY (adsad_id_pk);


--
-- TOC entry 4107 (class 2606 OID 32789)
-- Name: appt_dtls appt_dtls_ad_pmt_trans_id_fk; Type: CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.appt_dtls
    ADD CONSTRAINT appt_dtls_ad_pmt_trans_id_fk UNIQUE (ad_pmt_trans_id_fk);


--
-- TOC entry 4109 (class 2606 OID 31450)
-- Name: appt_dtls appt_dtls_pkey; Type: CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.appt_dtls
    ADD CONSTRAINT appt_dtls_pkey PRIMARY KEY (ad_id_pk);


--
-- TOC entry 4227 (class 2606 OID 34635)
-- Name: appt_ul_rprt_dtls appt_ul_rprt_dtls_pkey; Type: CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.appt_ul_rprt_dtls
    ADD CONSTRAINT appt_ul_rprt_dtls_pkey PRIMARY KEY (aurd_id_pk);


--
-- TOC entry 4223 (class 2606 OID 34598)
-- Name: audit_consultation_dtls audit_consultation_dtls_pkey; Type: CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.audit_consultation_dtls
    ADD CONSTRAINT audit_consultation_dtls_pkey PRIMARY KEY (ct_id_pk);


--
-- TOC entry 4171 (class 2606 OID 33376)
-- Name: consult_adv_dtls consult_adv_dtls_pkey; Type: CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_adv_dtls
    ADD CONSTRAINT consult_adv_dtls_pkey PRIMARY KEY (cad_id_pk);


--
-- TOC entry 4119 (class 2606 OID 31544)
-- Name: consult_cc_dtls consult_cc_dtls_pkey; Type: CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_cc_dtls
    ADD CONSTRAINT consult_cc_dtls_pkey PRIMARY KEY (cccd_id_pk);


--
-- TOC entry 4161 (class 2606 OID 33055)
-- Name: consult_diag_dtls consult_diag_dtls_pkey; Type: CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_diag_dtls
    ADD CONSTRAINT consult_diag_dtls_pkey PRIMARY KEY (cdd_id_pk);


--
-- TOC entry 4163 (class 2606 OID 33298)
-- Name: consult_inves_dtls consult_inves_dtls_pkey; Type: CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_inves_dtls
    ADD CONSTRAINT consult_inves_dtls_pkey PRIMARY KEY (cid_id_pk);


--
-- TOC entry 4123 (class 2606 OID 31831)
-- Name: consult_mdcn_dtls consult_mdcn_dtls_pkey; Type: CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_mdcn_dtls
    ADD CONSTRAINT consult_mdcn_dtls_pkey PRIMARY KEY (cmd_id_pk);


--
-- TOC entry 4211 (class 2606 OID 34492)
-- Name: consult_priscp_dtls consult_priscp_dtls_pkey; Type: CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_priscp_dtls
    ADD CONSTRAINT consult_priscp_dtls_pkey PRIMARY KEY (cpd_id_pk);


--
-- TOC entry 4221 (class 2606 OID 34564)
-- Name: consultation_dtls consultation_dtls_pkey; Type: CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consultation_dtls
    ADD CONSTRAINT consultation_dtls_pkey PRIMARY KEY (ct_id_pk);


--
-- TOC entry 4213 (class 2606 OID 34494)
-- Name: consult_priscp_dtls cpd_unique_constraint; Type: CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_priscp_dtls
    ADD CONSTRAINT cpd_unique_constraint UNIQUE (cpd_appt_id_fk, cpd_dr_user_id_fk, cpd_pt_user_id_fk, cpd_dr_tmplt_name);


--
-- TOC entry 4257 (class 2606 OID 36351)
-- Name: dr_slot_dtls dr_slot_dtls_pkey; Type: CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.dr_slot_dtls
    ADD CONSTRAINT dr_slot_dtls_pkey PRIMARY KEY (dsd_id_pk);


--
-- TOC entry 4261 (class 2606 OID 36559)
-- Name: dr_slot_mstr dr_slot_mstr_pkey; Type: CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.dr_slot_mstr
    ADD CONSTRAINT dr_slot_mstr_pkey PRIMARY KEY (dsm_id_pk);


--
-- TOC entry 4265 (class 2606 OID 36632)
-- Name: holiday_dtls holiday_dtls_pkey; Type: CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.holiday_dtls
    ADD CONSTRAINT holiday_dtls_pkey PRIMARY KEY (hd_id_pk);


--
-- TOC entry 4245 (class 2606 OID 35524)
-- Name: prescription_template_dtls prescription_template_dtls_pkey; Type: CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.prescription_template_dtls
    ADD CONSTRAINT prescription_template_dtls_pkey PRIMARY KEY (ptd_id_pk);


--
-- TOC entry 4215 (class 2606 OID 34511)
-- Name: consult_priscp_dtls uk4k5fr8vflj28h20p5b23ccd0u; Type: CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_priscp_dtls
    ADD CONSTRAINT uk4k5fr8vflj28h20p5b23ccd0u UNIQUE (cpd_appt_id_fk, cpd_dr_user_id_fk, cpd_pt_user_id_fk, cpd_dr_tmplt_name);


--
-- TOC entry 4111 (class 2606 OID 32945)
-- Name: appt_dtls uk79uigwmdcyq4cfiiyx9dtmuof; Type: CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.appt_dtls
    ADD CONSTRAINT uk79uigwmdcyq4cfiiyx9dtmuof UNIQUE (ad_dr_user_id_fk, ad_pt_user_id_fk, ad_appt_slot_fk, ad_appt_date_fk, ad_pmt_trans_id_fk);


--
-- TOC entry 4267 (class 2606 OID 36634)
-- Name: holiday_dtls uk_; Type: CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.holiday_dtls
    ADD CONSTRAINT uk_ UNIQUE (hd_dr_user_id_fk, hd_holiday_date, hd_isactive);


--
-- TOC entry 4113 (class 2606 OID 32943)
-- Name: appt_dtls uk_damt47iq4baxwt08olf8wxuay; Type: CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.appt_dtls
    ADD CONSTRAINT uk_damt47iq4baxwt08olf8wxuay UNIQUE (ad_appt_id);


--
-- TOC entry 4165 (class 2606 OID 33300)
-- Name: consult_inves_dtls ukgfc17ejyagcynporpxi3d28rj; Type: CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_inves_dtls
    ADD CONSTRAINT ukgfc17ejyagcynporpxi3d28rj UNIQUE (cid_dr_user_id_fk, cid_pt_user_id_fk, cid_appt_id_fk, cid_appt_ul_report_name, cid_appt_ul_report_type);


--
-- TOC entry 4259 (class 2606 OID 36353)
-- Name: dr_slot_dtls ukob33futv5xnvl5cwqhxqwcpls; Type: CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.dr_slot_dtls
    ADD CONSTRAINT ukob33futv5xnvl5cwqhxqwcpls UNIQUE (dsd_dr_user_id_fk, dsd_slot, dsd_slot_date, dsd_isactive);


--
-- TOC entry 4115 (class 2606 OID 35532)
-- Name: appt_dtls ukp9ujsbmj79n6ond25t3sf12f7; Type: CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.appt_dtls
    ADD CONSTRAINT ukp9ujsbmj79n6ond25t3sf12f7 UNIQUE (ad_dr_user_id_fk, ad_appt_slot_fk, ad_appt_date_fk, ad_isbooked);


--
-- TOC entry 4255 (class 2606 OID 35877)
-- Name: appt_dr_scrb_assign_dtls unique_appt_dr_scrb_assign; Type: CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.appt_dr_scrb_assign_dtls
    ADD CONSTRAINT unique_appt_dr_scrb_assign UNIQUE (adsad_dr_user_id_fk, adsad_date, adsad_isactive);


--
-- TOC entry 4179 (class 2606 OID 34053)
-- Name: consult_mdcn_dtls_aud ak_consult_mdcn_dtls_unique_constraints; Type: CONSTRAINT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.consult_mdcn_dtls_aud
    ADD CONSTRAINT ak_consult_mdcn_dtls_unique_constraints UNIQUE (cmd_appt_id_fk, cmd_dr_user_id_fk, cmd_pt_user_id_fk, cmd_medicine_type, cmd_medicine_name);


--
-- TOC entry 4201 (class 2606 OID 34275)
-- Name: appt_dtls_aud appt_dtls_aud_pkey; Type: CONSTRAINT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.appt_dtls_aud
    ADD CONSTRAINT appt_dtls_aud_pkey PRIMARY KEY (id);


--
-- TOC entry 4195 (class 2606 OID 34240)
-- Name: aud_consult_adv_dtls aud_consult_adv_dtls_pkey; Type: CONSTRAINT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_consult_adv_dtls
    ADD CONSTRAINT aud_consult_adv_dtls_pkey PRIMARY KEY (cad_id_pk);


--
-- TOC entry 4197 (class 2606 OID 34251)
-- Name: aud_consult_cc_dtls aud_consult_cc_dtls_pkey; Type: CONSTRAINT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_consult_cc_dtls
    ADD CONSTRAINT aud_consult_cc_dtls_pkey PRIMARY KEY (cccd_audit_id_pk);


--
-- TOC entry 4203 (class 2606 OID 34286)
-- Name: aud_consult_diag_dtls aud_consult_diag_dtls_pkey; Type: CONSTRAINT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_consult_diag_dtls
    ADD CONSTRAINT aud_consult_diag_dtls_pkey PRIMARY KEY (aud_id_pk);


--
-- TOC entry 4189 (class 2606 OID 34117)
-- Name: aud_consult_inves_dtls aud_consult_inves_dtls_pkey; Type: CONSTRAINT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_consult_inves_dtls
    ADD CONSTRAINT aud_consult_inves_dtls_pkey PRIMARY KEY (cid_id_pk);


--
-- TOC entry 4217 (class 2606 OID 34520)
-- Name: aud_consult_priscp_dtls aud_consult_priscp_dtls_pkey; Type: CONSTRAINT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_consult_priscp_dtls
    ADD CONSTRAINT aud_consult_priscp_dtls_pkey PRIMARY KEY (cpd_audit_id_pk);


--
-- TOC entry 4183 (class 2606 OID 34094)
-- Name: aud_dr_reg_dtls aud_dr_reg_dtls_drd_mobile_no_key; Type: CONSTRAINT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_dr_reg_dtls
    ADD CONSTRAINT aud_dr_reg_dtls_drd_mobile_no_key UNIQUE (drd_mobile_no);


--
-- TOC entry 4185 (class 2606 OID 34096)
-- Name: aud_dr_reg_dtls aud_dr_reg_dtls_drd_user_id_key; Type: CONSTRAINT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_dr_reg_dtls
    ADD CONSTRAINT aud_dr_reg_dtls_drd_user_id_key UNIQUE (drd_user_id);


--
-- TOC entry 4187 (class 2606 OID 34092)
-- Name: aud_dr_reg_dtls aud_dr_reg_dtls_pkey; Type: CONSTRAINT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_dr_reg_dtls
    ADD CONSTRAINT aud_dr_reg_dtls_pkey PRIMARY KEY (drd_id_pk);


--
-- TOC entry 4175 (class 2606 OID 33979)
-- Name: aud_dr_slot_dtls aud_dr_slot_dtls_pkey; Type: CONSTRAINT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_dr_slot_dtls
    ADD CONSTRAINT aud_dr_slot_dtls_pkey PRIMARY KEY (dsd_id_pk);


--
-- TOC entry 4263 (class 2606 OID 36582)
-- Name: aud_dr_slot_mstr aud_dr_slot_mstr_pkey; Type: CONSTRAINT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_dr_slot_mstr
    ADD CONSTRAINT aud_dr_slot_mstr_pkey PRIMARY KEY (adsm_id_pk);


--
-- TOC entry 4269 (class 2606 OID 37071)
-- Name: aud_func_mstr aud_func_mstr_pkey; Type: CONSTRAINT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_func_mstr
    ADD CONSTRAINT aud_func_mstr_pkey PRIMARY KEY (aud_id_pk);


--
-- TOC entry 4177 (class 2606 OID 33992)
-- Name: aud_mstr_tbl aud_mstr_tbl_pkey; Type: CONSTRAINT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_mstr_tbl
    ADD CONSTRAINT aud_mstr_tbl_pkey PRIMARY KEY (mt_master_pk);


--
-- TOC entry 4219 (class 2606 OID 34526)
-- Name: aud_pt_lifesty_dtls aud_pt_lifesty_dtls_pkey; Type: CONSTRAINT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_pt_lifesty_dtls
    ADD CONSTRAINT aud_pt_lifesty_dtls_pkey PRIMARY KEY (plsd_id_pk);


--
-- TOC entry 4205 (class 2606 OID 34322)
-- Name: aud_pt_medi_dtls aud_pt_medi_dtls_pkey; Type: CONSTRAINT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_pt_medi_dtls
    ADD CONSTRAINT aud_pt_medi_dtls_pkey PRIMARY KEY (pmd_id_pk);


--
-- TOC entry 4207 (class 2606 OID 34330)
-- Name: aud_pt_reg_dtls aud_pt_reg_dtls_pkey; Type: CONSTRAINT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_pt_reg_dtls
    ADD CONSTRAINT aud_pt_reg_dtls_pkey PRIMARY KEY (prd_id_pk);


--
-- TOC entry 4209 (class 2606 OID 34350)
-- Name: aud_role_function_dtls aud_role_function_dtls_pkey; Type: CONSTRAINT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_role_function_dtls
    ADD CONSTRAINT aud_role_function_dtls_pkey PRIMARY KEY (aud_id_pk);


--
-- TOC entry 4229 (class 2606 OID 35396)
-- Name: aud_role_mstr aud_role_mstr_pkey; Type: CONSTRAINT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_role_mstr
    ADD CONSTRAINT aud_role_mstr_pkey PRIMARY KEY (aud_id_pk);


--
-- TOC entry 4193 (class 2606 OID 34207)
-- Name: aud_usr_dtls aud_usr_dtls_pkey; Type: CONSTRAINT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_usr_dtls
    ADD CONSTRAINT aud_usr_dtls_pkey PRIMARY KEY (aud_id_pk);


--
-- TOC entry 4225 (class 2606 OID 34609)
-- Name: audit_consultation_dtls audit_consultation_dtls_pkey; Type: CONSTRAINT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.audit_consultation_dtls
    ADD CONSTRAINT audit_consultation_dtls_pkey PRIMARY KEY (ct_id_pk);


--
-- TOC entry 4181 (class 2606 OID 34051)
-- Name: consult_mdcn_dtls_aud consult_mdcn_dtls_pkey; Type: CONSTRAINT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.consult_mdcn_dtls_aud
    ADD CONSTRAINT consult_mdcn_dtls_pkey PRIMARY KEY (cmd_id_pk);


--
-- TOC entry 4199 (class 2606 OID 34261)
-- Name: udt_mstr_tbl udt_mstr_tbl_pkey; Type: CONSTRAINT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.udt_mstr_tbl
    ADD CONSTRAINT udt_mstr_tbl_pkey PRIMARY KEY (mt_master_pk);


--
-- TOC entry 4191 (class 2606 OID 34128)
-- Name: aud_consult_inves_dtls uk6saiwqjqj4qksg208h2k0lg6c; Type: CONSTRAINT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_consult_inves_dtls
    ADD CONSTRAINT uk6saiwqjqj4qksg208h2k0lg6c UNIQUE (cid_dr_user_id_fk, cid_pt_user_id_fk, cid_appt_id_fk, cid_appt_ul_report_name, cid_appt_ul_report_type);


--
-- TOC entry 4277 (class 2606 OID 37482)
-- Name: city_mstr city_mstr_pk; Type: CONSTRAINT; Schema: master; Owner: sysadmin
--

ALTER TABLE ONLY master.city_mstr
    ADD CONSTRAINT city_mstr_pk PRIMARY KEY (cm_city_pk, cm_city_name, cm_state_name_fk, cm_cntry_name_fk);


--
-- TOC entry 4279 (class 2606 OID 37484)
-- Name: city_mstr city_mstr_unk; Type: CONSTRAINT; Schema: master; Owner: sysadmin
--

ALTER TABLE ONLY master.city_mstr
    ADD CONSTRAINT city_mstr_unk UNIQUE (cm_city_name, cm_state_name_fk, cm_cntry_name_fk, cm_isactive_flg);


--
-- TOC entry 4145 (class 2606 OID 33770)
-- Name: cntry_mstr cntry_mstr_cm_cntry_name_key; Type: CONSTRAINT; Schema: master; Owner: sysadmin
--

ALTER TABLE ONLY master.cntry_mstr
    ADD CONSTRAINT cntry_mstr_cm_cntry_name_key UNIQUE (cm_cntry_name);


--
-- TOC entry 4147 (class 2606 OID 32483)
-- Name: cntry_mstr cntry_mstr_pkey; Type: CONSTRAINT; Schema: master; Owner: sysadmin
--

ALTER TABLE ONLY master.cntry_mstr
    ADD CONSTRAINT cntry_mstr_pkey PRIMARY KEY (cm_cntry_pk);


--
-- TOC entry 4139 (class 2606 OID 32296)
-- Name: mstr_tbl mstr_tbl_mt_master_name_mt_master_value_mt_master_unit_key; Type: CONSTRAINT; Schema: master; Owner: sysadmin
--

ALTER TABLE ONLY master.mstr_tbl
    ADD CONSTRAINT mstr_tbl_mt_master_name_mt_master_value_mt_master_unit_key UNIQUE (mt_master_name, mt_master_value, mt_master_unit);


--
-- TOC entry 4141 (class 2606 OID 32294)
-- Name: mstr_tbl mstr_tbl_pkey; Type: CONSTRAINT; Schema: master; Owner: sysadmin
--

ALTER TABLE ONLY master.mstr_tbl
    ADD CONSTRAINT mstr_tbl_pkey PRIMARY KEY (mt_master_pk);


--
-- TOC entry 4271 (class 2606 OID 37141)
-- Name: state_mstr state_mstr_pk; Type: CONSTRAINT; Schema: master; Owner: sysadmin
--

ALTER TABLE ONLY master.state_mstr
    ADD CONSTRAINT state_mstr_pk PRIMARY KEY (sm_state_pk, sm_state_name, sm_cntry_name_fk);


--
-- TOC entry 4273 (class 2606 OID 37143)
-- Name: state_mstr state_mstr_sm_state_name_key; Type: CONSTRAINT; Schema: master; Owner: sysadmin
--

ALTER TABLE ONLY master.state_mstr
    ADD CONSTRAINT state_mstr_sm_state_name_key UNIQUE (sm_state_name, sm_cntry_name_fk, sm_isactive_flg);


--
-- TOC entry 4275 (class 2606 OID 37496)
-- Name: state_mstr uk_ecvu7chx79k84s9iyx4b44qhq; Type: CONSTRAINT; Schema: master; Owner: sysadmin
--

ALTER TABLE ONLY master.state_mstr
    ADD CONSTRAINT uk_ecvu7chx79k84s9iyx4b44qhq UNIQUE (sm_state_name);


--
-- TOC entry 4149 (class 2606 OID 33768)
-- Name: cntry_mstr uk_m6wfq9xb0r4c1ph4tv2yue4af; Type: CONSTRAINT; Schema: master; Owner: sysadmin
--

ALTER TABLE ONLY master.cntry_mstr
    ADD CONSTRAINT uk_m6wfq9xb0r4c1ph4tv2yue4af UNIQUE (cm_cntry_name);


--
-- TOC entry 4099 (class 2606 OID 31437)
-- Name: pmt_dtls pmt_dtls_pd_bank_ref_trans_id_key; Type: CONSTRAINT; Schema: payment; Owner: sysadmin
--

ALTER TABLE ONLY payment.pmt_dtls
    ADD CONSTRAINT pmt_dtls_pd_bank_ref_trans_id_key UNIQUE (pd_bank_ref_trans_id);


--
-- TOC entry 4101 (class 2606 OID 31435)
-- Name: pmt_dtls pmt_dtls_pd_pmt_trans_id_key; Type: CONSTRAINT; Schema: payment; Owner: sysadmin
--

ALTER TABLE ONLY payment.pmt_dtls
    ADD CONSTRAINT pmt_dtls_pd_pmt_trans_id_key UNIQUE (pd_pmt_trans_id);


--
-- TOC entry 4103 (class 2606 OID 31433)
-- Name: pmt_dtls pmt_dtls_pkey; Type: CONSTRAINT; Schema: payment; Owner: sysadmin
--

ALTER TABLE ONLY payment.pmt_dtls
    ADD CONSTRAINT pmt_dtls_pkey PRIMARY KEY (pd_id_pk);


--
-- TOC entry 4105 (class 2606 OID 32953)
-- Name: pmt_dtls uk_6ff6gacc4vthx8wryq975l3el; Type: CONSTRAINT; Schema: payment; Owner: sysadmin
--

ALTER TABLE ONLY payment.pmt_dtls
    ADD CONSTRAINT uk_6ff6gacc4vthx8wryq975l3el UNIQUE (pd_pmt_trans_id);


--
-- TOC entry 4167 (class 2606 OID 33326)
-- Name: consult_adv_dtls consult_adv_dtls_pkey; Type: CONSTRAINT; Schema: public; Owner: sysadmin
--

ALTER TABLE ONLY public.consult_adv_dtls
    ADD CONSTRAINT consult_adv_dtls_pkey PRIMARY KEY (cad_id_pk);


--
-- TOC entry 4237 (class 2606 OID 35454)
-- Name: dr_reg_dtls_test dr_reg_dtls_drd_email; Type: CONSTRAINT; Schema: public; Owner: sysadmin
--

ALTER TABLE ONLY public.dr_reg_dtls_test
    ADD CONSTRAINT dr_reg_dtls_drd_email UNIQUE (drd_email);


--
-- TOC entry 4239 (class 2606 OID 35456)
-- Name: dr_reg_dtls_test dr_reg_dtls_drd_mobile_no_key; Type: CONSTRAINT; Schema: public; Owner: sysadmin
--

ALTER TABLE ONLY public.dr_reg_dtls_test
    ADD CONSTRAINT dr_reg_dtls_drd_mobile_no_key UNIQUE (drd_mobile_no);


--
-- TOC entry 4241 (class 2606 OID 35458)
-- Name: dr_reg_dtls_test dr_reg_dtls_drd_user_id_key; Type: CONSTRAINT; Schema: public; Owner: sysadmin
--

ALTER TABLE ONLY public.dr_reg_dtls_test
    ADD CONSTRAINT dr_reg_dtls_drd_user_id_key UNIQUE (drd_user_id);


--
-- TOC entry 4243 (class 2606 OID 35452)
-- Name: dr_reg_dtls_test dr_reg_dtls_pkey; Type: CONSTRAINT; Schema: public; Owner: sysadmin
--

ALTER TABLE ONLY public.dr_reg_dtls_test
    ADD CONSTRAINT dr_reg_dtls_pkey PRIMARY KEY (drd_id_pk);


--
-- TOC entry 4153 (class 2606 OID 32831)
-- Name: usr_otp_email_verify_dtls usr_otp_email_verify_dtls_pkey; Type: CONSTRAINT; Schema: public; Owner: sysadmin
--

ALTER TABLE ONLY public.usr_otp_email_verify_dtls
    ADD CONSTRAINT usr_otp_email_verify_dtls_pkey PRIMARY KEY (uvd_id_pk);


--
-- TOC entry 4137 (class 2606 OID 32269)
-- Name: usrmgmt_usr_otp_email_verify_dtls usrmgmt_usr_otp_email_verify_dtls_pkey; Type: CONSTRAINT; Schema: public; Owner: sysadmin
--

ALTER TABLE ONLY public.usrmgmt_usr_otp_email_verify_dtls
    ADD CONSTRAINT usrmgmt_usr_otp_email_verify_dtls_pkey PRIMARY KEY (uvd_id);


--
-- TOC entry 4135 (class 2606 OID 32184)
-- Name: dr_docs_dtls dr_docs_dtls_pkey; Type: CONSTRAINT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.dr_docs_dtls
    ADD CONSTRAINT dr_docs_dtls_pkey PRIMARY KEY (ddt_doc_id_pk);


--
-- TOC entry 4079 (class 2606 OID 32051)
-- Name: dr_mstr_dtls dr_mstr_dtls_dmd_mobile_no_key; Type: CONSTRAINT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.dr_mstr_dtls
    ADD CONSTRAINT dr_mstr_dtls_dmd_mobile_no_key UNIQUE (dmd_mobile_no);


--
-- TOC entry 4081 (class 2606 OID 31284)
-- Name: dr_mstr_dtls dr_mstr_dtls_dmd_user_id_key; Type: CONSTRAINT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.dr_mstr_dtls
    ADD CONSTRAINT dr_mstr_dtls_dmd_user_id_key UNIQUE (dmd_user_id);


--
-- TOC entry 4083 (class 2606 OID 31282)
-- Name: dr_mstr_dtls dr_mstr_dtls_pkey; Type: CONSTRAINT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.dr_mstr_dtls
    ADD CONSTRAINT dr_mstr_dtls_pkey PRIMARY KEY (dmd_id_pk);


--
-- TOC entry 4283 (class 2606 OID 37701)
-- Name: dr_mstr_dtls_solr_demo dr_mstr_dtls_solr_demo_pkey; Type: CONSTRAINT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.dr_mstr_dtls_solr_demo
    ADD CONSTRAINT dr_mstr_dtls_solr_demo_pkey PRIMARY KEY (dmd_id_pk);


--
-- TOC entry 4125 (class 2606 OID 35464)
-- Name: dr_reg_dtls dr_reg_dtls_drd_email_key; Type: CONSTRAINT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.dr_reg_dtls
    ADD CONSTRAINT dr_reg_dtls_drd_email_key UNIQUE (drd_email);


--
-- TOC entry 4127 (class 2606 OID 31929)
-- Name: dr_reg_dtls dr_reg_dtls_drd_mobile_no_key; Type: CONSTRAINT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.dr_reg_dtls
    ADD CONSTRAINT dr_reg_dtls_drd_mobile_no_key UNIQUE (drd_mobile_no);


--
-- TOC entry 4129 (class 2606 OID 31927)
-- Name: dr_reg_dtls dr_reg_dtls_drd_user_id_key; Type: CONSTRAINT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.dr_reg_dtls
    ADD CONSTRAINT dr_reg_dtls_drd_user_id_key UNIQUE (drd_user_id);


--
-- TOC entry 4131 (class 2606 OID 31925)
-- Name: dr_reg_dtls dr_reg_dtls_pkey; Type: CONSTRAINT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.dr_reg_dtls
    ADD CONSTRAINT dr_reg_dtls_pkey PRIMARY KEY (drd_id_pk);


--
-- TOC entry 4077 (class 2606 OID 31230)
-- Name: pt_lifesty_dtls plsd_id_pk; Type: CONSTRAINT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.pt_lifesty_dtls
    ADD CONSTRAINT plsd_id_pk PRIMARY KEY (plsd_id_pk);


--
-- TOC entry 4075 (class 2606 OID 31217)
-- Name: pt_medi_dtls pmd_id_pk; Type: CONSTRAINT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.pt_medi_dtls
    ADD CONSTRAINT pmd_id_pk PRIMARY KEY (pmd_id_pk);


--
-- TOC entry 4247 (class 2606 OID 35554)
-- Name: poc_master_doctor poc_master_doctor_pkey; Type: CONSTRAINT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.poc_master_doctor
    ADD CONSTRAINT poc_master_doctor_pkey PRIMARY KEY (id);


--
-- TOC entry 4065 (class 2606 OID 31209)
-- Name: pt_reg_dtls prd_mobile_no; Type: CONSTRAINT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.pt_reg_dtls
    ADD CONSTRAINT prd_mobile_no UNIQUE (prd_mobile_no);


--
-- TOC entry 4067 (class 2606 OID 31207)
-- Name: pt_reg_dtls prd_user_id; Type: CONSTRAINT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.pt_reg_dtls
    ADD CONSTRAINT prd_user_id UNIQUE (prd_user_id);


--
-- TOC entry 4069 (class 2606 OID 31205)
-- Name: pt_reg_dtls pt_reg_dtls_pkey; Type: CONSTRAINT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.pt_reg_dtls
    ADD CONSTRAINT pt_reg_dtls_pkey PRIMARY KEY (prd_id_pk);


--
-- TOC entry 4087 (class 2606 OID 31317)
-- Name: scrb_reg_dtls scrb_reg_dtls_pkey; Type: CONSTRAINT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.scrb_reg_dtls
    ADD CONSTRAINT scrb_reg_dtls_pkey PRIMARY KEY (srd_id_pk);


--
-- TOC entry 4089 (class 2606 OID 32301)
-- Name: scrb_reg_dtls srd_mobile_no; Type: CONSTRAINT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.scrb_reg_dtls
    ADD CONSTRAINT srd_mobile_no UNIQUE (srd_mobile_no);


--
-- TOC entry 4091 (class 2606 OID 33877)
-- Name: scrb_reg_dtls srd_user_id; Type: CONSTRAINT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.scrb_reg_dtls
    ADD CONSTRAINT srd_user_id UNIQUE (srd_user_id);


--
-- TOC entry 4249 (class 2606 OID 35558)
-- Name: poc_master_doctor uk_3aegj9846n306fse3621w0ryb; Type: CONSTRAINT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.poc_master_doctor
    ADD CONSTRAINT uk_3aegj9846n306fse3621w0ryb UNIQUE (user_id);


--
-- TOC entry 4133 (class 2606 OID 35466)
-- Name: dr_reg_dtls uk_7wj2pt2m25gbg4qpj0hyvjth8; Type: CONSTRAINT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.dr_reg_dtls
    ADD CONSTRAINT uk_7wj2pt2m25gbg4qpj0hyvjth8 UNIQUE (drd_user_id);


--
-- TOC entry 4251 (class 2606 OID 35556)
-- Name: poc_master_doctor uk_h1ghgsj1w9vm8qmxaaedqng5q; Type: CONSTRAINT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.poc_master_doctor
    ADD CONSTRAINT uk_h1ghgsj1w9vm8qmxaaedqng5q UNIQUE (mobile);


--
-- TOC entry 4071 (class 2606 OID 32957)
-- Name: pt_reg_dtls uk_ll94xhgjeh3rj2ucuf4o6uexk; Type: CONSTRAINT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.pt_reg_dtls
    ADD CONSTRAINT uk_ll94xhgjeh3rj2ucuf4o6uexk UNIQUE (prd_user_id);


--
-- TOC entry 4085 (class 2606 OID 32955)
-- Name: dr_mstr_dtls uk_od1t9vwx7bbqaudalc77msbsr; Type: CONSTRAINT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.dr_mstr_dtls
    ADD CONSTRAINT uk_od1t9vwx7bbqaudalc77msbsr UNIQUE (dmd_user_id);


--
-- TOC entry 4073 (class 2606 OID 32941)
-- Name: pt_reg_dtls ukebxohagoaweu127m7n1jp0g9b; Type: CONSTRAINT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.pt_reg_dtls
    ADD CONSTRAINT ukebxohagoaweu127m7n1jp0g9b UNIQUE (prd_mobile_no, prd_user_id);


--
-- TOC entry 4093 (class 2606 OID 31360)
-- Name: pt_rev_dtls ak_pt_rev_dtls_userid; Type: CONSTRAINT; Schema: usrmgmt; Owner: sysadmin
--

ALTER TABLE ONLY usrmgmt.pt_rev_dtls
    ADD CONSTRAINT ak_pt_rev_dtls_userid UNIQUE (prd_dr_user_id_fk, prd_pt_user_id_fk);


--
-- TOC entry 4155 (class 2606 OID 32886)
-- Name: func_mstr func_mstr_pkey; Type: CONSTRAINT; Schema: usrmgmt; Owner: sysadmin
--

ALTER TABLE ONLY usrmgmt.func_mstr
    ADD CONSTRAINT func_mstr_pkey PRIMARY KEY (fm_func_name_pk);


--
-- TOC entry 4095 (class 2606 OID 31348)
-- Name: pt_rev_dtls pt_rev_dtls_pkey; Type: CONSTRAINT; Schema: usrmgmt; Owner: sysadmin
--

ALTER TABLE ONLY usrmgmt.pt_rev_dtls
    ADD CONSTRAINT pt_rev_dtls_pkey PRIMARY KEY (prd_id_pk);


--
-- TOC entry 4281 (class 2606 OID 37599)
-- Name: role_func_mapping role_func_mapping_pkey; Type: CONSTRAINT; Schema: usrmgmt; Owner: sysadmin
--

ALTER TABLE ONLY usrmgmt.role_func_mapping
    ADD CONSTRAINT role_func_mapping_pkey PRIMARY KEY (rfm_id_pk);


--
-- TOC entry 4173 (class 2606 OID 33444)
-- Name: role_function_dtls role_function_dtls_pkey; Type: CONSTRAINT; Schema: usrmgmt; Owner: sysadmin
--

ALTER TABLE ONLY usrmgmt.role_function_dtls
    ADD CONSTRAINT role_function_dtls_pkey PRIMARY KEY (rfd_rolefunc_pk);


--
-- TOC entry 4157 (class 2606 OID 32899)
-- Name: role_mstr role_mstr_pkey; Type: CONSTRAINT; Schema: usrmgmt; Owner: sysadmin
--

ALTER TABLE ONLY usrmgmt.role_mstr
    ADD CONSTRAINT role_mstr_pkey PRIMARY KEY (rm_role_name_pk);


--
-- TOC entry 4231 (class 2606 OID 35420)
-- Name: usr_dtls uk_c3hh8vldfwajjfc79u30nv83; Type: CONSTRAINT; Schema: usrmgmt; Owner: sysadmin
--

ALTER TABLE ONLY usrmgmt.usr_dtls
    ADD CONSTRAINT uk_c3hh8vldfwajjfc79u30nv83 UNIQUE (ud_email);


--
-- TOC entry 4233 (class 2606 OID 35422)
-- Name: usr_dtls uk_jrhm5p2owut0y0j9nvxyxjqun; Type: CONSTRAINT; Schema: usrmgmt; Owner: sysadmin
--

ALTER TABLE ONLY usrmgmt.usr_dtls
    ADD CONSTRAINT uk_jrhm5p2owut0y0j9nvxyxjqun UNIQUE (ud_mobile_no);


--
-- TOC entry 4097 (class 2606 OID 32959)
-- Name: pt_rev_dtls uki59u7dr13ks8y625100aqvok3; Type: CONSTRAINT; Schema: usrmgmt; Owner: sysadmin
--

ALTER TABLE ONLY usrmgmt.pt_rev_dtls
    ADD CONSTRAINT uki59u7dr13ks8y625100aqvok3 UNIQUE (prd_dr_user_id_fk, prd_pt_user_id_fk);


--
-- TOC entry 4143 (class 2606 OID 32455)
-- Name: usr_dtls_his usr_dtls_his_pkey; Type: CONSTRAINT; Schema: usrmgmt; Owner: sysadmin
--

ALTER TABLE ONLY usrmgmt.usr_dtls_his
    ADD CONSTRAINT usr_dtls_his_pkey PRIMARY KEY (id);


--
-- TOC entry 4235 (class 2606 OID 35418)
-- Name: usr_dtls usr_dtls_pkey; Type: CONSTRAINT; Schema: usrmgmt; Owner: sysadmin
--

ALTER TABLE ONLY usrmgmt.usr_dtls
    ADD CONSTRAINT usr_dtls_pkey PRIMARY KEY (ud_user_id_pk);


--
-- TOC entry 4151 (class 2606 OID 32823)
-- Name: usr_otp_email_verify_dtls uvd_id_pk; Type: CONSTRAINT; Schema: usrmgmt; Owner: sysadmin
--

ALTER TABLE ONLY usrmgmt.usr_otp_email_verify_dtls
    ADD CONSTRAINT uvd_id_pk PRIMARY KEY (uvd_id_pk);


--
-- TOC entry 4289 (class 2606 OID 31453)
-- Name: appt_dtls appt_dtls_ad_dr_user_id_fk_fkey; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.appt_dtls
    ADD CONSTRAINT appt_dtls_ad_dr_user_id_fk_fkey FOREIGN KEY (ad_dr_user_id_fk) REFERENCES registration.dr_mstr_dtls(dmd_user_id);


--
-- TOC entry 4291 (class 2606 OID 31463)
-- Name: appt_dtls appt_dtls_ad_pmt_trans_id_fk_fkey; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.appt_dtls
    ADD CONSTRAINT appt_dtls_ad_pmt_trans_id_fk_fkey FOREIGN KEY (ad_pmt_trans_id_fk) REFERENCES payment.pmt_dtls(pd_pmt_trans_id);


--
-- TOC entry 4290 (class 2606 OID 31458)
-- Name: appt_dtls appt_dtls_ad_pt_user_id_fk_fkey; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.appt_dtls
    ADD CONSTRAINT appt_dtls_ad_pt_user_id_fk_fkey FOREIGN KEY (ad_pt_user_id_fk) REFERENCES registration.pt_reg_dtls(prd_user_id);


--
-- TOC entry 4294 (class 2606 OID 33213)
-- Name: consult_cc_dtls cccd_appt_id_fk; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_cc_dtls
    ADD CONSTRAINT cccd_appt_id_fk FOREIGN KEY (cccd_appt_id_fk) REFERENCES appointment.appt_dtls(ad_appt_id);


--
-- TOC entry 4293 (class 2606 OID 33027)
-- Name: consult_cc_dtls cccd_dr_user_id_fk; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_cc_dtls
    ADD CONSTRAINT cccd_dr_user_id_fk FOREIGN KEY (cccd_dr_user_id_fk) REFERENCES registration.dr_reg_dtls(drd_user_id);


--
-- TOC entry 4295 (class 2606 OID 33228)
-- Name: consult_cc_dtls cccd_pt_user_id_fk; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_cc_dtls
    ADD CONSTRAINT cccd_pt_user_id_fk FOREIGN KEY (cccd_pt_user_id_fk) REFERENCES registration.pt_reg_dtls(prd_user_id);


--
-- TOC entry 4299 (class 2606 OID 33068)
-- Name: consult_diag_dtls consult_diag_dtls_cdd_appt_id_fk_fkey; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_diag_dtls
    ADD CONSTRAINT consult_diag_dtls_cdd_appt_id_fk_fkey FOREIGN KEY (cdd_appt_id_fk) REFERENCES appointment.appt_dtls(ad_appt_id);


--
-- TOC entry 4298 (class 2606 OID 33063)
-- Name: consult_diag_dtls consult_diag_dtls_cdd_dr_user_id_fk_fkey; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_diag_dtls
    ADD CONSTRAINT consult_diag_dtls_cdd_dr_user_id_fk_fkey FOREIGN KEY (cdd_dr_user_id_fk) REFERENCES registration.dr_reg_dtls(drd_user_id);


--
-- TOC entry 4297 (class 2606 OID 33058)
-- Name: consult_diag_dtls consult_diag_dtls_cdd_pt_user_id_fk_fkey; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_diag_dtls
    ADD CONSTRAINT consult_diag_dtls_cdd_pt_user_id_fk_fkey FOREIGN KEY (cdd_pt_user_id_fk) REFERENCES registration.pt_reg_dtls(prd_user_id);


--
-- TOC entry 4314 (class 2606 OID 34495)
-- Name: consult_priscp_dtls cpd_appt_id_fk; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_priscp_dtls
    ADD CONSTRAINT cpd_appt_id_fk FOREIGN KEY (cpd_appt_id_fk) REFERENCES appointment.appt_dtls(ad_appt_id);


--
-- TOC entry 4315 (class 2606 OID 34500)
-- Name: consult_priscp_dtls cpd_dr_user_id_fk; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_priscp_dtls
    ADD CONSTRAINT cpd_dr_user_id_fk FOREIGN KEY (cpd_dr_user_id_fk) REFERENCES registration.dr_mstr_dtls(dmd_user_id);


--
-- TOC entry 4316 (class 2606 OID 34505)
-- Name: consult_priscp_dtls cpd_pt_user_id_fk; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_priscp_dtls
    ADD CONSTRAINT cpd_pt_user_id_fk FOREIGN KEY (cpd_pt_user_id_fk) REFERENCES registration.pt_reg_dtls(prd_user_id);


--
-- TOC entry 4325 (class 2606 OID 36354)
-- Name: dr_slot_dtls fk8oaysmi662xnm1bne4g724roj; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.dr_slot_dtls
    ADD CONSTRAINT fk8oaysmi662xnm1bne4g724roj FOREIGN KEY (dsd_dr_user_id_fk) REFERENCES registration.dr_mstr_dtls(dmd_user_id);


--
-- TOC entry 4307 (class 2606 OID 33457)
-- Name: consult_adv_dtls fk_cad_appt_id; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_adv_dtls
    ADD CONSTRAINT fk_cad_appt_id FOREIGN KEY (cad_appt_id_fk) REFERENCES appointment.appt_dtls(ad_appt_id);


--
-- TOC entry 4309 (class 2606 OID 33477)
-- Name: consult_adv_dtls fk_cad_dr_user_id; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_adv_dtls
    ADD CONSTRAINT fk_cad_dr_user_id FOREIGN KEY (cad_dr_user_id_fk) REFERENCES registration.dr_mstr_dtls(dmd_user_id);


--
-- TOC entry 4310 (class 2606 OID 33492)
-- Name: consult_adv_dtls fk_cad_pt_user_id; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_adv_dtls
    ADD CONSTRAINT fk_cad_pt_user_id FOREIGN KEY (cad_pt_user_id_fk) REFERENCES registration.pt_reg_dtls(prd_user_id);


--
-- TOC entry 4317 (class 2606 OID 34565)
-- Name: consultation_dtls fk_ct_appt_id; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consultation_dtls
    ADD CONSTRAINT fk_ct_appt_id FOREIGN KEY (ct_appt_id) REFERENCES appointment.appt_dtls(ad_appt_id);


--
-- TOC entry 4318 (class 2606 OID 34570)
-- Name: consultation_dtls fk_ct_doctor_id; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consultation_dtls
    ADD CONSTRAINT fk_ct_doctor_id FOREIGN KEY (ct_doctor_id) REFERENCES registration.dr_mstr_dtls(dmd_user_id);


--
-- TOC entry 4319 (class 2606 OID 34575)
-- Name: consultation_dtls fk_ct_patient_id; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consultation_dtls
    ADD CONSTRAINT fk_ct_patient_id FOREIGN KEY (ct_patient_id) REFERENCES registration.pt_reg_dtls(prd_user_id);


--
-- TOC entry 4323 (class 2606 OID 35878)
-- Name: appt_dr_scrb_assign_dtls fk_dr_appt_dr_scrb_assign; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.appt_dr_scrb_assign_dtls
    ADD CONSTRAINT fk_dr_appt_dr_scrb_assign FOREIGN KEY (adsad_dr_user_id_fk) REFERENCES registration.dr_mstr_dtls(dmd_user_id);


--
-- TOC entry 4328 (class 2606 OID 36635)
-- Name: holiday_dtls fk_dr_mstr_dtls; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.holiday_dtls
    ADD CONSTRAINT fk_dr_mstr_dtls FOREIGN KEY (hd_dr_user_id_fk) REFERENCES registration.dr_mstr_dtls(dmd_user_id);


--
-- TOC entry 4326 (class 2606 OID 36560)
-- Name: dr_slot_mstr fk_dr_slot_mstr; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.dr_slot_mstr
    ADD CONSTRAINT fk_dr_slot_mstr FOREIGN KEY (dsm_dr_user_id_fk) REFERENCES registration.dr_mstr_dtls(dmd_user_id);


--
-- TOC entry 4322 (class 2606 OID 35525)
-- Name: prescription_template_dtls fk_ptd_doctor_id; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.prescription_template_dtls
    ADD CONSTRAINT fk_ptd_doctor_id FOREIGN KEY (ptd_doctor_id) REFERENCES registration.dr_mstr_dtls(dmd_user_id);


--
-- TOC entry 4324 (class 2606 OID 35883)
-- Name: appt_dr_scrb_assign_dtls fk_scribe_appt_dr_scrb_assign; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.appt_dr_scrb_assign_dtls
    ADD CONSTRAINT fk_scribe_appt_dr_scrb_assign FOREIGN KEY (adsad_scribe_user_id_fk) REFERENCES registration.scrb_reg_dtls(srd_user_id);


--
-- TOC entry 4301 (class 2606 OID 33301)
-- Name: consult_inves_dtls fkdcx5e92v1djaff6329f9mgxvl; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_inves_dtls
    ADD CONSTRAINT fkdcx5e92v1djaff6329f9mgxvl FOREIGN KEY (cid_appt_id_fk) REFERENCES appointment.appt_dtls(ad_appt_id);


--
-- TOC entry 4308 (class 2606 OID 33472)
-- Name: consult_adv_dtls fke9xgyo4ch3484d3q15pjb8iuv; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_adv_dtls
    ADD CONSTRAINT fke9xgyo4ch3484d3q15pjb8iuv FOREIGN KEY (cad_dr_user_id_fk) REFERENCES registration.dr_reg_dtls(drd_user_id);


--
-- TOC entry 4302 (class 2606 OID 33306)
-- Name: consult_inves_dtls fkg2wat6ucuroqoq3qde3mak12j; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_inves_dtls
    ADD CONSTRAINT fkg2wat6ucuroqoq3qde3mak12j FOREIGN KEY (cid_dr_user_id_fk) REFERENCES registration.dr_mstr_dtls(dmd_user_id);


--
-- TOC entry 4300 (class 2606 OID 33286)
-- Name: consult_diag_dtls fki0jfa6ka6jhqmjvgdim7ir2ts; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_diag_dtls
    ADD CONSTRAINT fki0jfa6ka6jhqmjvgdim7ir2ts FOREIGN KEY (cdd_dr_user_id_fk) REFERENCES registration.dr_mstr_dtls(dmd_user_id);


--
-- TOC entry 4303 (class 2606 OID 33311)
-- Name: consult_inves_dtls fkjjviwbfshtiqx82t0bhh52oiq; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_inves_dtls
    ADD CONSTRAINT fkjjviwbfshtiqx82t0bhh52oiq FOREIGN KEY (cid_pt_user_id_fk) REFERENCES registration.pt_reg_dtls(prd_user_id);


--
-- TOC entry 4320 (class 2606 OID 34636)
-- Name: appt_ul_rprt_dtls fkqhgxhptaca0fbx8dh0ifl8dyj; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.appt_ul_rprt_dtls
    ADD CONSTRAINT fkqhgxhptaca0fbx8dh0ifl8dyj FOREIGN KEY (aurd_appt_id_fk) REFERENCES appointment.appt_dtls(ad_appt_id);


--
-- TOC entry 4292 (class 2606 OID 33022)
-- Name: consult_cc_dtls fkrq9j4ewbjbc6h9a632jawjbe8; Type: FK CONSTRAINT; Schema: appointment; Owner: sysadmin
--

ALTER TABLE ONLY appointment.consult_cc_dtls
    ADD CONSTRAINT fkrq9j4ewbjbc6h9a632jawjbe8 FOREIGN KEY (cccd_dr_user_id_fk) REFERENCES registration.dr_mstr_dtls(dmd_user_id);


--
-- TOC entry 4311 (class 2606 OID 34131)
-- Name: aud_consult_inves_dtls fk86oqyh68q65yejm7ingvsommq; Type: FK CONSTRAINT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_consult_inves_dtls
    ADD CONSTRAINT fk86oqyh68q65yejm7ingvsommq FOREIGN KEY (cid_appt_id_fk) REFERENCES appointment.appt_dtls(ad_appt_id);


--
-- TOC entry 4327 (class 2606 OID 36583)
-- Name: aud_dr_slot_mstr fk_aud_dr_slot_mstr; Type: FK CONSTRAINT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_dr_slot_mstr
    ADD CONSTRAINT fk_aud_dr_slot_mstr FOREIGN KEY (dsm_dr_user_id_fk) REFERENCES registration.dr_mstr_dtls(dmd_user_id);


--
-- TOC entry 4313 (class 2606 OID 34141)
-- Name: aud_consult_inves_dtls fkhrv1hsthcq49d0dterq3hdb95; Type: FK CONSTRAINT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_consult_inves_dtls
    ADD CONSTRAINT fkhrv1hsthcq49d0dterq3hdb95 FOREIGN KEY (cid_pt_user_id_fk) REFERENCES registration.pt_reg_dtls(prd_user_id);


--
-- TOC entry 4312 (class 2606 OID 34136)
-- Name: aud_consult_inves_dtls fkji4rfs7tntpoxhiataja9f6fa; Type: FK CONSTRAINT; Schema: audit; Owner: sysadmin
--

ALTER TABLE ONLY audit.aud_consult_inves_dtls
    ADD CONSTRAINT fkji4rfs7tntpoxhiataja9f6fa FOREIGN KEY (cid_dr_user_id_fk) REFERENCES registration.dr_mstr_dtls(dmd_user_id);


--
-- TOC entry 4329 (class 2606 OID 37144)
-- Name: state_mstr cntry_fk; Type: FK CONSTRAINT; Schema: master; Owner: sysadmin
--

ALTER TABLE ONLY master.state_mstr
    ADD CONSTRAINT cntry_fk FOREIGN KEY (sm_cntry_name_fk) REFERENCES master.cntry_mstr(cm_cntry_name);


--
-- TOC entry 4330 (class 2606 OID 37485)
-- Name: city_mstr cntry_fk; Type: FK CONSTRAINT; Schema: master; Owner: sysadmin
--

ALTER TABLE ONLY master.city_mstr
    ADD CONSTRAINT cntry_fk FOREIGN KEY (cm_cntry_name_fk) REFERENCES master.cntry_mstr(cm_cntry_name);


--
-- TOC entry 4331 (class 2606 OID 37497)
-- Name: city_mstr fkodv55epj2p8n5ss3373tgtoki; Type: FK CONSTRAINT; Schema: master; Owner: sysadmin
--

ALTER TABLE ONLY master.city_mstr
    ADD CONSTRAINT fkodv55epj2p8n5ss3373tgtoki FOREIGN KEY (cm_state_name_fk) REFERENCES master.state_mstr(sm_state_name);


--
-- TOC entry 4306 (class 2606 OID 33337)
-- Name: consult_adv_dtls fk56tca0e5vr4esvaijoybl6qjc; Type: FK CONSTRAINT; Schema: public; Owner: sysadmin
--

ALTER TABLE ONLY public.consult_adv_dtls
    ADD CONSTRAINT fk56tca0e5vr4esvaijoybl6qjc FOREIGN KEY (cad_pt_user_id_fk) REFERENCES registration.pt_reg_dtls(prd_user_id);


--
-- TOC entry 4305 (class 2606 OID 33332)
-- Name: consult_adv_dtls fke9xgyo4ch3484d3q15pjb8iuv; Type: FK CONSTRAINT; Schema: public; Owner: sysadmin
--

ALTER TABLE ONLY public.consult_adv_dtls
    ADD CONSTRAINT fke9xgyo4ch3484d3q15pjb8iuv FOREIGN KEY (cad_dr_user_id_fk) REFERENCES registration.dr_reg_dtls(drd_user_id);


--
-- TOC entry 4304 (class 2606 OID 33327)
-- Name: consult_adv_dtls fks7erecmoumppfo1opfm9b81dv; Type: FK CONSTRAINT; Schema: public; Owner: sysadmin
--

ALTER TABLE ONLY public.consult_adv_dtls
    ADD CONSTRAINT fks7erecmoumppfo1opfm9b81dv FOREIGN KEY (cad_appt_id_fk) REFERENCES appointment.appt_dtls(ad_appt_id);


--
-- TOC entry 4296 (class 2606 OID 32185)
-- Name: dr_docs_dtls fk_ddt_dr_user_id; Type: FK CONSTRAINT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.dr_docs_dtls
    ADD CONSTRAINT fk_ddt_dr_user_id FOREIGN KEY (ddt_dr_user_id_fk) REFERENCES registration.dr_reg_dtls(drd_user_id);


--
-- TOC entry 4285 (class 2606 OID 31231)
-- Name: pt_lifesty_dtls plsd_pt_user_id_fk; Type: FK CONSTRAINT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.pt_lifesty_dtls
    ADD CONSTRAINT plsd_pt_user_id_fk FOREIGN KEY (plsd_pt_user_id_fk) REFERENCES registration.pt_reg_dtls(prd_user_id);


--
-- TOC entry 4284 (class 2606 OID 31218)
-- Name: pt_medi_dtls pmd_pt_user_id_fk; Type: FK CONSTRAINT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.pt_medi_dtls
    ADD CONSTRAINT pmd_pt_user_id_fk FOREIGN KEY (pmd_pt_user_id_fk) REFERENCES registration.pt_reg_dtls(prd_user_id);


--
-- TOC entry 4286 (class 2606 OID 31528)
-- Name: scrb_reg_dtls srd_dr_user_id_fk; Type: FK CONSTRAINT; Schema: registration; Owner: sysadmin
--

ALTER TABLE ONLY registration.scrb_reg_dtls
    ADD CONSTRAINT srd_dr_user_id_fk FOREIGN KEY (srd_dr_user_id_fk) REFERENCES registration.dr_mstr_dtls(dmd_user_id);


--
-- TOC entry 4321 (class 2606 OID 35423)
-- Name: usr_dtls fkr5g4jfoh1a9msvwlkaj802jme; Type: FK CONSTRAINT; Schema: usrmgmt; Owner: sysadmin
--

ALTER TABLE ONLY usrmgmt.usr_dtls
    ADD CONSTRAINT fkr5g4jfoh1a9msvwlkaj802jme FOREIGN KEY (ud_role_id_fk) REFERENCES usrmgmt.role_mstr(rm_role_name_pk);


--
-- TOC entry 4287 (class 2606 OID 31349)
-- Name: pt_rev_dtls pt_rev_dtls_prd_dr_user_id_fk_fkey; Type: FK CONSTRAINT; Schema: usrmgmt; Owner: sysadmin
--

ALTER TABLE ONLY usrmgmt.pt_rev_dtls
    ADD CONSTRAINT pt_rev_dtls_prd_dr_user_id_fk_fkey FOREIGN KEY (prd_dr_user_id_fk) REFERENCES registration.dr_mstr_dtls(dmd_user_id);


--
-- TOC entry 4288 (class 2606 OID 31354)
-- Name: pt_rev_dtls pt_rev_dtls_prd_pt_user_id_fk_fkey; Type: FK CONSTRAINT; Schema: usrmgmt; Owner: sysadmin
--

ALTER TABLE ONLY usrmgmt.pt_rev_dtls
    ADD CONSTRAINT pt_rev_dtls_prd_pt_user_id_fk_fkey FOREIGN KEY (prd_pt_user_id_fk) REFERENCES registration.pt_reg_dtls(prd_user_id);


--
-- TOC entry 4460 (class 0 OID 0)
-- Dependencies: 10
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2021-03-06 14:17:52

--
-- PostgreSQL database dump complete
--

