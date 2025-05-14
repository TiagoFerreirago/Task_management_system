INSERT INTO users (
    user_name, full_name, password,
    account_non_expired, account_non_locked,
    credentials_non_expired, enabled
) VALUES
('leandro', 'Leandro Costa', '$2a$16$9qr2tv0HmXbHBsx.TZFjfux742wCZM32a8Wi6iBqwIqaizlHPuxHS', TRUE, TRUE, TRUE, TRUE),
('flavio', 'Flavio Costa', '$2a$16$h4yDQCYTy62R6xrtFDWONeMH3Lim4WQuU/aj8hxW.dJJoeyvtEkhK', TRUE, TRUE, TRUE, TRUE),
('rebeca', 'Rebeca Souza', '{pbkdf2}ab0081f8581209af9e138e1d335e443e316b452f341fd3aeb937ff396ff33d06a271605382049d43', TRUE, TRUE, TRUE, TRUE);
