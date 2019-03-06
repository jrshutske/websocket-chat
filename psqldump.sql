--
-- PostgreSQL database dump
--

-- Dumped from database version 11.2
-- Dumped by pg_dump version 11.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: jackshutske
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO jackshutske;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: rooms; Type: TABLE; Schema: public; Owner: jackshutske
--

CREATE TABLE public.rooms (
    id integer NOT NULL,
    creator integer,
    roomname character varying
);


ALTER TABLE public.rooms OWNER TO jackshutske;

--
-- Name: ticks; Type: TABLE; Schema: public; Owner: jackshutske
--

CREATE TABLE public.ticks (
    tick timestamp without time zone
);


ALTER TABLE public.ticks OWNER TO jackshutske;

--
-- Name: users; Type: TABLE; Schema: public; Owner: jackshutske
--

CREATE TABLE public.users (
    id integer NOT NULL,
    email character varying(255),
    firstname character varying(255),
    lastname character varying(255),
    password character varying(255),
    username character varying(255)
);


ALTER TABLE public.users OWNER TO jackshutske;

--
-- Name: users_rooms; Type: TABLE; Schema: public; Owner: jackshutske
--

CREATE TABLE public.users_rooms (
    room_id integer,
    user_id integer
);


ALTER TABLE public.users_rooms OWNER TO jackshutske;

--
-- Data for Name: rooms; Type: TABLE DATA; Schema: public; Owner: jackshutske
--

COPY public.rooms (id, creator, roomname) FROM stdin;
51	1	asfasd
52	2	fasdfasdf
53	2	fasdfasdf
54	2	fasdfasdf
56	2	fasdfasdf
57	2	fasdfasdf
58	1	asdfasd
59	1	asdfasd
60	1	asdfasd
61	1	asdfasd
62	1	asdfasd
63	1	asdfasd
64	1	asdfasd
55	2	fasdfasdf
\.


--
-- Data for Name: ticks; Type: TABLE DATA; Schema: public; Owner: jackshutske
--

COPY public.ticks (tick) FROM stdin;
2019-02-23 22:05:28.842061
2019-02-23 22:05:31.486356
2019-02-24 12:27:02.571842
2019-02-25 00:12:43.936424
2019-02-25 00:12:46.261181
2019-02-25 00:16:25.329563
2019-02-25 00:25:52.305748
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: jackshutske
--

COPY public.users (id, email, firstname, lastname, password, username) FROM stdin;
32	jiffshutske@gmail.com	jiff	ekstuhs	abc12345	jiffshutske
33	jiffshutske@gmail.com	jiff	ekstuhs	abc12345	jiffshutske
34	jiffshutske@gmail.com	jiff	ekstuhs	abc12345	jiffshutske
35	jiffshutske@gmail.com	jiff	ekstuhs	abc12345	jiffshutske
36	jiffshutske@gmail.com	jiff	ekstuhs	abc12345	jiffshutske
37	jiffshutske@gmail.com	jiff	ekstuhs	abc12345	jiffshutske
38	jiffshutske@gmail.com	jiff	ekstuhs	abc12345	jiffshutske
39	jiffshutske@gmail.com	jiff	ekstuhs	abc12345	jiffshutske
40	jiffshutske@gmail.com	jiff	ekstuhs	abc12345	jiffshutske
41	jiffshutske@gmail.com	jiff	ekstuhs	abc12345	jiffshutske
42	jiffshutske@gmail.com	jiff	ekstuhs	abc12345	jiffshutske
43	jiffshutske@gmail.com	jiff	ekstuhs	abc12345	jiffshutske
44	jiffshutske@gmail.com	jiff	ekstuhs	abc12345	jiffshutske
45	jiffshutske@gmail.com	jiff	ekstuhs	abc12345	jiffshutske
1	jeffjohnson@gmail.com	jack	shutske	abc123	billyjohnson
2	jackshutske2@gmail.com	jack	shutske	abc123	johnshutske
65	sdfasdf	sadfsa	dfsadf	fasdf	ldaksfjsadf
\.


--
-- Data for Name: users_rooms; Type: TABLE DATA; Schema: public; Owner: jackshutske
--

COPY public.users_rooms (room_id, user_id) FROM stdin;
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: jackshutske
--

SELECT pg_catalog.setval('public.hibernate_sequence', 65, true);


--
-- Name: rooms rooms_pkey; Type: CONSTRAINT; Schema: public; Owner: jackshutske
--

ALTER TABLE ONLY public.rooms
    ADD CONSTRAINT rooms_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: jackshutske
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: rooms rooms_creator_fkey; Type: FK CONSTRAINT; Schema: public; Owner: jackshutske
--

ALTER TABLE ONLY public.rooms
    ADD CONSTRAINT rooms_creator_fkey FOREIGN KEY (creator) REFERENCES public.users(id);


--
-- Name: users_rooms users_rooms_room_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: jackshutske
--

ALTER TABLE ONLY public.users_rooms
    ADD CONSTRAINT users_rooms_room_id_fkey FOREIGN KEY (room_id) REFERENCES public.rooms(id);


--
-- Name: users_rooms users_rooms_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: jackshutske
--

ALTER TABLE ONLY public.users_rooms
    ADD CONSTRAINT users_rooms_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- PostgreSQL database dump complete
--

