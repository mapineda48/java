/*
 *https://dev.mysql.com/doc/refman/8.0/en/creating-database.html
 */
CREATE DATABASE IF NOT EXISTS sergio;
USE sergio;

/*
 *Create Tables
 */
DROP TABLE IF EXISTS product;

CREATE TABLE IF NOT EXISTS product(
   id INT NOT NULL AUTO_INCREMENT,
   code INT NOT NULL UNIQUE,
   full_name VARCHAR(100) NOT NULL,
   price FLOAT NOT NULL,
   quantity INT NOT NULL,
   PRIMARY KEY (id)
);

INSERT INTO
   product (code, full_name, price, quantity)
VALUES
   (1, 'Manzanas', 8000.0, 55),
   (2, 'Limones', 2300.0, 15),
   (3, 'Peras', 2500.0, 38),
   (4, 'Arandanos', 9300.0, 55),
   (5, 'Tomates', 2100.0, 42),
   (6, 'Fresas', 4100.0, 33),
   (7, 'Helado', 4500.0, 41),
   (8, 'Galletas', 500.0, 33),
   (9, 'Chocolates', 3500.0, 80),
   (10, 'Jamon', 17000.0, 10);


/*
 *Generate report
 */
DROP FUNCTION IF EXISTS generate_report;

DELIMITER $$
CREATE FUNCTION generate_report()
  RETURNS TEXT
  DETERMINISTIC
  LANGUAGE SQL
BEGIN
    DECLARE big TEXT;
    DECLARE little TEXT;
    DECLARE avg_res TEXT;
    DECLARE total TEXT;
    
    SELECT full_name INTO big FROM product ORDER BY price DESC LIMIT 1;

    SELECT full_name INTO little FROM product ORDER BY price ASC LIMIT 1;

    SELECT TRUNCATE(AVG(price), 2) INTO avg_res FROM product;

    SELECT TRUNCATE(sum(price * quantity), 2) INTO total FROM product;

    RETURN CONCAT(big,' ',little,' ',avg_res,' ',total);
END;
$$
DELIMITER ;