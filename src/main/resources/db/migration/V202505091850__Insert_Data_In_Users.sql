INSERT INTO users (
    user_name, full_name, password,
    account_non_expired, account_non_locked,
    credentials_non_expired, enabled
) VALUES
('leandro', 'Leandro Costa', '$2a$16$9qr2tv0HmXbHBsx.TZFjfux742wCZM32a8Wi6iBqwIqaizlHPuxHS', TRUE, TRUE, TRUE, TRUE),
('flavio', 'Flavio Costa', '$2a$16$h4yDQCYTy62R6xrtFDWONeMH3Lim4WQuU/aj8hxW.dJJoeyvtEkhK', TRUE, TRUE, TRUE, TRUE),
('tiago', 'Tiago Ferreira', '518b2498311b4a5088960c5760fd16d5964fb9cf8d24279e9de1a820cbfce4fa1a36f8f1adab646c', TRUE, TRUE, TRUE, TRUE),
('rebeca', 'Rebeca Souza', 'd1fb294fa07dd30e0dbd50cb748795c41f50f22b1e82ecc1b124fb2cff816220c974ba16b3eda2e3', TRUE, TRUE, TRUE, TRUE);
