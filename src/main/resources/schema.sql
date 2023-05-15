DROP TABLE IF exists usuarios;
DROP TABLE IF exists autoridades;

CREATE TABLE usuarios(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(45) NOT NULL,
    pass VARCHAR(45) NOT NULL,
    activo CHAR(1) NOT NULL,
    creacion DATE NULL
);

CREATE TABLE autoridades(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(45) NOT NULL,
    autoridad VARCHAR(45) NOT NULL,
    creacion DATE NULL
);