--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3
-- Dumped by pg_dump version 16.3

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: account; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.account (
    number bigint NOT NULL,
    created_at timestamp(6) with time zone NOT NULL,
    modified_at timestamp(6) with time zone,
    balance numeric(38,2) NOT NULL,
    customer_id character varying(13) NOT NULL,
    state boolean NOT NULL,
    type character varying(255) NOT NULL,
    CONSTRAINT account_type_check CHECK (((type)::text = ANY ((ARRAY['AHORROS'::character varying, 'CORRIENTE'::character varying])::text[])))
);


ALTER TABLE public.account OWNER TO admin;

--
-- Name: account_number_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.account_number_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.account_number_seq OWNER TO admin;

--
-- Name: account_number_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.account_number_seq OWNED BY public.account.number;


--
-- Name: customer; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.customer (
    password character varying(255) NOT NULL,
    state boolean NOT NULL,
    customer_id character varying(13) NOT NULL
);


ALTER TABLE public.customer OWNER TO admin;

--
-- Name: person; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.person (
    person_id character varying(13) NOT NULL,
    created_at timestamp(6) with time zone NOT NULL,
    modified_at timestamp(6) with time zone,
    address character varying(255),
    age smallint,
    gender character(1) NOT NULL,
    name character varying(255) NOT NULL,
    phone character varying(20),
    CONSTRAINT person_gender_check CHECK ((gender = ANY (ARRAY['M'::bpchar, 'F'::bpchar, 'O'::bpchar])))
);


ALTER TABLE public.person OWNER TO admin;

--
-- Name: transaction; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.transaction (
    transaction_id character varying(255) NOT NULL,
    created_at timestamp(6) with time zone NOT NULL,
    modified_at timestamp(6) with time zone,
    balance numeric(38,2) NOT NULL,
    previous_balance numeric(38,2) NOT NULL,
    transaction_type smallint NOT NULL,
    value numeric(38,2) NOT NULL,
    account_number bigint NOT NULL,
    CONSTRAINT transaction_transaction_type_check CHECK (((transaction_type >= 0) AND (transaction_type <= 1)))
);


ALTER TABLE public.transaction OWNER TO admin;

--
-- Name: account number; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.account ALTER COLUMN number SET DEFAULT nextval('public.account_number_seq'::regclass);


--
-- Data for Name: account; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.account (number, created_at, modified_at, balance, customer_id, state, type) FROM stdin;
3	2024-06-23 08:07:19.891686+00	2024-06-23 08:07:19.901504+00	1000.00	0956257491	t	CORRIENTE
2	2024-06-23 08:06:49.021658+00	2024-06-23 08:10:59.73327+00	700.00	0956257492	t	CORRIENTE
1	2024-06-23 08:06:19.441623+00	2024-06-23 08:11:49.313649+00	2125.00	0956257491	t	AHORROS
\.


--
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.customer (password, state, customer_id) FROM stdin;
$2a$12$PJQBPqVA99EKonkJW3mcpu8.ajH2gzqROoZaShU214u32AdiMKX7y	t	0956257491
$2a$12$wVDVupwC.CHScEvKW4Y5Tuu8sBT.tEn8wNif8mjBwHC3od8Qo3exq	t	0956257492
$2a$12$2UWWgfYc4DH5m7X8N9X7LesCcDPOjsOP8HyeL8up6qdj6kbaESp7S	t	0956257493
$2a$12$6LgT17whtD0UNXQ0Mtm1vOEuUziTTZx/8M.DvsanjTu9r.QCrk166	t	0956257497
\.


--
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.person (person_id, created_at, modified_at, address, age, gender, name, phone) FROM stdin;
0956257493	2024-06-23 07:54:19.161382+00	2024-06-23 07:54:19.495002+00	13 junio y Equinoccial	30	M	Juan Osorio	098874587
0956257491	2024-06-23 07:52:35.171616+00	2024-06-23 07:54:56.877785+00	Otavalo sn y principal	25	M	Jose Lema	098254785
0956257492	2024-06-23 07:53:26.153923+00	2024-06-23 07:55:16.604654+00	Amazonas y NNUU	30	F	Marianela Montalvo	097548965
0956257497	2024-06-23 08:04:21.08182+00	2024-06-23 08:04:21.402365+00	Toscana	25	M	Jeanpier Mendoza	\N
\.


--
-- Data for Name: transaction; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.transaction (transaction_id, created_at, modified_at, balance, previous_balance, transaction_type, value, account_number) FROM stdin;
ef642cec-d1b9-40d7-aaa9-f0fe70c441cc	2024-06-23 08:10:13.5936+00	2024-06-23 08:10:13.600803+00	1425.00	2000.00	0	-575.00	1
90bb2709-94ef-4300-9bc3-a77f208f7df1	2024-06-23 08:10:59.731236+00	2024-06-23 08:10:59.732087+00	700.00	100.00	1	600.00	2
503e3bdc-ce35-476f-8722-2b6a91281f4e	2024-06-23 08:11:42.295125+00	2024-06-23 08:11:42.295724+00	2025.00	1425.00	1	600.00	1
43301341-80b3-4083-b5fd-2207e9bf7bcf	2024-06-23 08:11:45.702774+00	2024-06-23 08:11:45.703394+00	2425.00	2025.00	1	400.00	1
1c889a5d-49b6-4976-84ee-8bb49fd0f9e2	2024-06-23 08:11:49.309251+00	2024-06-23 08:11:49.310547+00	2125.00	2425.00	0	-300.00	1
\.


--
-- Name: account_number_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.account_number_seq', 3, true);


--
-- Name: account account_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_pkey PRIMARY KEY (number);


--
-- Name: customer customer_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (customer_id);


--
-- Name: person person_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (person_id);


--
-- Name: transaction transaction_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT transaction_pkey PRIMARY KEY (transaction_id);


--
-- Name: account fk_customer; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.account
    ADD CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES public.customer(customer_id) ON DELETE CASCADE;


--
-- Name: transaction fka80kblc0ww9p9xjei8luheqlk; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT fka80kblc0ww9p9xjei8luheqlk FOREIGN KEY (account_number) REFERENCES public.account(number);


--
-- Name: customer fkqw58igk7s2kunxvf7ns62ktu2; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT fkqw58igk7s2kunxvf7ns62ktu2 FOREIGN KEY (customer_id) REFERENCES public.person(person_id);


--
-- PostgreSQL database dump complete
--

