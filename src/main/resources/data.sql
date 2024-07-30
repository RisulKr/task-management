INSERT INTO roles (id, role_name) VALUES (1, 'USER')
ON CONFLICT (id) DO NOTHING;

INSERT INTO roles (id, role_name) VALUES (2, 'ADMIN')
ON CONFLICT (id) DO NOTHING;



