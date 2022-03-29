-----------------------------Se deve modificar la tabla para dejar autoincremenal el ID---------------------------------------------------
ALTER TABLE "CUSTOMERS" ALTER COLUMN "ID" INT AUTO_INCREMENT;

-----------------------------Data de prueba---------------------------------------------------------------------------------------------

INSERT INTO customers (id, first_name, last_name, company) VALUES ('1', 'Steve', 'Rogers', 'Marvel');
INSERT INTO customers (id, first_name, last_name, company) VALUES ('2', 'Homer', 'Simpson', 'Simpsons');
INSERT INTO customers (id, first_name, last_name, company) VALUES ('3', 'Steve', 'Jobs', 'Apple');