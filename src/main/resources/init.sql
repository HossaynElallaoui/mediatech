-- MediaTech Database Initialization Script
-- This script should be run to create default roles and an admin user

-- Insert default roles
INSERT INTO roles (name) VALUES ('USER');
INSERT INTO roles (name) VALUES ('ADMIN');

-- Note: To create users, you need to use the /api/auth/register endpoint
-- The password will be hashed by BCrypt
-- Example admin user (password: admin123):
-- You can use the register endpoint or manually insert:
-- INSERT INTO users (username, password) VALUES ('admin', '$2a$10$...');
-- Then link to ADMIN role in users_roles table
