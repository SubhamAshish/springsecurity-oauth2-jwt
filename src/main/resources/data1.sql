CREATE TABLE public.clientdetails
(
  appid character varying(255) NOT NULL,
  resourceids character varying(255),
  appsecret character varying(255),
  scope character varying(255),
  granttypes character varying(255),
  redirecturl character varying(255),
  authorities character varying(255),
  access_token_validity integer,
  refresh_token_validity integer,
  additionalinformation character varying(4096),
  autoapprovescopes character varying(255),
  CONSTRAINT clientdetails_pkey PRIMARY KEY (appid)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.clientdetails
  OWNER TO postgres;

  CREATE TABLE public.oauth_client_details
(
  client_id character varying(255) NOT NULL,
  resource_ids character varying(255),
  client_secret character varying(255),
  scope character varying(255),
  authorized_grant_types character varying(255),
  web_server_redirect_uri character varying(255),
  authorities character varying(255),
  access_token_validity integer,
  refresh_token_validity integer,
  additional_information character varying(4096),
  autoapprove character varying(255),
  CONSTRAINT oauth_client_details_pkey PRIMARY KEY (client_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.oauth_client_details
  OWNER TO postgres;
  
  create table if not exists oauth_access_token (
  token_id VARCHAR(255),
  token bytea,
  authentication_id VARCHAR(255) PRIMARY KEY,
  user_name VARCHAR(255),
  client_id VARCHAR(255),
  authentication bytea,
  refresh_token VARCHAR(255)
);
create table if not exists oauth_refresh_token (
  token_id VARCHAR(255),
  token bytea,
  authentication bytea
);