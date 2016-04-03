CREATE TABLE "user"
(
  id serial NOT NULL,
  login character varying(10),
  password character varying(10),
  role integer,
  want boolean,
  vk_page character varying(50),
  name character varying(20) NOT NULL,
  current_place_id character varying(50),
  active boolean,
  CONSTRAINT user_pkey PRIMARY KEY (id),
  CONSTRAINT user_unique UNIQUE (vk_page)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "user"
  OWNER TO postgres;

