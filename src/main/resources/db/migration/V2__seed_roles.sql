INSERT INTO role (id, name) VALUES (uuid_generate_v4(), 'ROLE_ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO role (id, name) VALUES (uuid_generate_v4(), 'ROLE_USER') ON CONFLICT DO NOTHING;
