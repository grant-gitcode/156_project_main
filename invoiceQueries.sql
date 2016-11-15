-- 1. A query to retrieve the major fields for every person.

SELECT * FROM Person;

-- 2. A query to add an email to a specific person. 
INSERT INTO Email (personId, emailAddress)
VALUES ('2', 'shane.ps360@gmail.com');
-- 3. A query to change the address of a theater in a movie ticket record. 

UPDATE Products AS sub JOIN Invoice AS I
ON sub.invoiceID = I.invoiceID 
SET sub.movieAddress="123 Change St." 
WHERE sub.subType="M" AND I.invoiceCode="INV001";

-- 4. A query (or series of queries) to remove a given movie ticket record. 
DELETE P FROM Products AS P JOIN Invoice AS I
ON P.invoiceID = I.invoiceID
WHERE subType = "M" AND I.invoiceCode="INV003";

-- 5. A query to get all the products in a particular invoice. 

SELECT * FROM Products AS P JOIN Invoice AS I
ON P.invoiceID = I.invoiceID
WHERE I.invoiceCode="INV003";

-- 6. A query to get all the invoices of a particular customer. 

SELECT * FROM Invoice AS I JOIN Customer AS C
ON I.customerID = C.customerID 
WHERE C.customerCode="C002";

-- 7. A query that “adds” a particular product to a particular invoice. 

INSERT INTO Products(productName,cost,units,invoiceID) 
VALUES ("New Product",100.00,35,
(SELECT I.invoiceID FROM Invoice AS I WHERE I.invoiceCode="INV006"));

-- 8. A query to find the total of all per-unit costs of all movie-tickets. 

SELECT SUM(cost*units) AS Perunitcost FROM Products WHERE subType="M";

-- 9. A query to find the total number of movie-tickets sold on a particular date. 

SELECT SUM(P.units) AS ticketCount
FROM Products AS P JOIN Invoice AS I 
ON P.invoiceID = I.invoiceID 
WHERE I.date='2016-10-16' AND P.subType="M";

-- 10. A query to find the total number of invoices for every salesperson. 
SELECT P.personCode, COUNT(I.invoiceID) AS invoiceTotal
FROM Person AS P JOIN Customer AS C JOIN Invoice AS I 
ON P.personID = C.personID AND C.customerID = I.customerID
GROUP BY P.personCode;
-- 11. A query to find the total number of invoices for a particular movie ticket.

SELECT COUNT(I.invoiceID) AS invoiceCount 
FROM Invoice AS I JOIN Products AS P
ON P.invoiceID = I.invoiceID
WHERE P.productName="The Shiny" AND P.movieDateTime='2016-10-21 13:10:00';
 
/* 12. A query to find the total revenue generated (excluding fees and taxes) on a particular date from 
all movie-tickets (hint: you can take an aggregate of a mathematical expression). 
*/
SELECT SUM(P.units * P.cost) As totalMovieRevenue
From Products As P JOIN Invoice AS I
ON P.invoiceID = I.invoiceID
WHERE I.date='2016-10-16' AND P.subType = "M";
/*13. A query to find the total quantities sold (excluding fees and taxes) of each category of services 
(parking-passes and refreshments) in all the existing invoices. 
*/

SELECT P.subType, SUM(P.units * P.cost) AS totalSold 
FROM Products AS P JOIN Invoice AS I 
ON P.invoiceID = I.invoiceID
WHERE P.subType="P" OR P.subType="R"
GROUP BY P.subType;

/*14. A query to detect invalid data in invoices as follows.  In a single invoice, a particular ticket 
should only appear once (since any number of units can be consolidated to a single record).  Write a 
query to find any invoice that includes multiple instances of the same ticket. 
*/
SELECT I.invoiceCode, P.productCode AS duplicateProductCode, P.subType, 
P.productName AS duplicateProductName, SUM(P.units) AS totalUnits, COUNT(P.invoiceID) AS duplicateCount
FROM Invoice AS I JOIN Products AS P 
ON I.invoiceID=P.invoiceID
WHERE P.subType="M" OR P.subType="S"
GROUP BY I.invoiceCode, P.productCode
HAVING COUNT(P.productsID) > 1;

/* 15. Write a query to detect a possible conflict of interest as follows.  No distinction is made in this 
system between a person who is the primary contact of a customer and a person who is also a sales person.  
Write a query to find any and all invoices where the salesperson is the same as the primary contact of the 
invoice’s customer.  */

SELECT I.invoiceCode,C.customerCode,P.personCode 
FROM Invoice AS I JOIN Customer AS C JOIN Person AS P
ON I.customerID = C.customerID AND C.personID = P.personID
WHERE I.personID = C.personID;

-- Should NOT show the 5th invoice, since nearly all our data is filled with conflicts of interests! --