CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE role (
  id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  name varchar(50) NOT NULL UNIQUE
);

CREATE TABLE "user" (
  id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  username varchar(50) NOT NULL UNIQUE,
  password_hash varchar(255) NOT NULL,
  role_id uuid REFERENCES role(id)
);

CREATE TABLE member (
  id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  first_name varchar(100) NOT NULL,
  last_name varchar(100) NOT NULL,
  date_of_birth date NOT NULL,
  email varchar(255) NOT NULL UNIQUE,
  created_at timestamp NOT NULL DEFAULT now(),
  updated_at timestamp NOT NULL DEFAULT now()
);
