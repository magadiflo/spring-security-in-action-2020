INSERT IGNORE INTO users(username, password, enabled) VALUES('admin', '12345', 1);
INSERT IGNORE INTO users(username, password, enabled) VALUES('martin', '12345', 1);

INSERT IGNORE INTO authorities(username, authority) VALUES('admin', 'read');
INSERT IGNORE INTO authorities(username, authority) VALUES('admin', 'write');
INSERT IGNORE INTO authorities(username, authority) VALUES('martin', 'read');