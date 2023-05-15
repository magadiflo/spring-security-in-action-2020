INSERT IGNORE INTO usuarios(usuario, pass, activo, creacion) VALUES('admin', '12345', 1, '2020-05-14');
INSERT IGNORE INTO usuarios(usuario, pass, activo, creacion) VALUES('martin', '12345', 1, '2020-05-14');

INSERT IGNORE INTO autoridades(usuario, autoridad, creacion) VALUES('admin', 'leer', '2020-05-14');
INSERT IGNORE INTO autoridades(usuario, autoridad, creacion) VALUES('admin', 'escribir', '2020-05-14');
INSERT IGNORE INTO autoridades(usuario, autoridad, creacion) VALUES('martin', 'leer', '2020-05-14');
