set foreign_key_checks = 0;

drop table ProductsInvoice;
drop table Products;
drop table Invoice;
drop table Customer;
drop table Person;
drop table Address;
drop table Email;
select * from Person;
alter table Person drop column emailID;
alter table Person drop column addressID;
alter table Person drop foreign key Person_ibfk_1;
alter table Person drop foreign key Person_ibfk_2;
show create table Person;
show tables;
SELECT * FROM Products as P JOIN Invoice as I ON P.invoiceID = I.invoiceID;
SELECT * FROM Email;
SELECT * FROM Customer;
SELECT * FROM Invoice;
SELECT * FROM Products;
SELECT * FROM Address;
SELECT * FROM ProductsInvoice;
SELECT * FROM Products AS P JOIN ProductsInvoice AS PI JOIN Invoice AS I 
ON P.invoiceID = PI.invoiceID AND PI.productsID = P.productsID;
SELECT * FROM ProductsInvoice;
SELECT * FROM Products AS P JOIN ProductsInvoice AS PI ON P.productsID = PI.productsID;
SELECT * FROM 
Products AS P JOIN ProductsInvoice AS PI JOIN Invoice AS I
ON P.productsID = PI.productsID AND PI.invoiceID = I.invoiceID;
SELECT * FROM Products WHERE productsID=1;
SELECT * FROM Invoice;
SELECT * FROM Person;
SELECT * FROM ProductsInvoice;

SELECT COUNT(*) FROM Invoice;

SELECT * FROM Person where personID=5;
