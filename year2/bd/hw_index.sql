-- Lazar Ana-Patricia
-- grupa 224
-- laborator 5


USE BDMuzeeDeArta;

-- VIEWS

-- 
GO
CREATE OR ALTER VIEW View_Tranzactii
AS
SELECT DISTINCT P.Nume
FROM Tranzactii T INNER JOIN Persoane P ON T.ID_Persoana = P.ID
				  INNER JOIN Muzee M ON T.ID_Muzeu = M.ID
WHERE P.ID < 10 AND M.Nume = 'Muzeu de Arta';

GO
CREATE NONCLUSTERED INDEX IX_Tranzactie ON Tranzactii (ID_Persoana, ID_Muzeu ASC);
CREATE NONCLUSTERED INDEX IX_Persoana ON Persoane (ID, Nume ASC);
CREATE NONCLUSTERED INDEX IX_Muzeu ON Muzee (ID, Nume ASC);

DROP INDEX IX_Tranzactie ON Tranzactii;
DROP INDEX IX_Persoana ON Persoane;
DROP INDEX IX_Muzeu ON Muzee;

SELECT * FROM View_Tranzactii;


-- View Adrese
-- Afiseaza toate 
GO
CREATE OR ALTER VIEW View_Adrese
AS
SELECT DISTINCT P.Nume, P.Prenume
FROM Adrese A INNER JOIN Persoane P ON A.ID = P.ID_Adresa
			  INNER JOIN Muzee M ON A.ID = M.ID_Adresa
WHERE M.ID < 10 AND Tara = N'Romania';

GO
CREATE NONCLUSTERED INDEX IX_Adrese ON Adrese (ID, Tara ASC);
CREATE NONCLUSTERED INDEX IX_Persoana ON Persoane (ID_Adresa, Nume, Prenume ASC);
CREATE NONCLUSTERED INDEX IX_Muzeu ON Muzee (ID_Adresa ASC);

DROP INDEX IX_Adrese on Adrese;
DROP INDEX IX_Persoana ON Persoane;
DROP INDEX IX_Muzeu ON Muzee;

SELECT * FROM View_Adrese;



EXEC insert_Muzee 100;
EXEC insert_Persoane 100;
EXEC insert_Tranzactii 100;

delete from Muzee;
delete from Persoane;
delete from Tranzactii;

select * from Muzee;
select * from Persoane;
select * from Tranzactii;


CREATE NONCLUSTERED INDEX IX_TranzactieMuzeu ON Tranzactii (ID_Muzeu ASC);
CREATE NONCLUSTERED INDEX IX_TranzactiePersoana ON Tranzactii (ID_Persoana ASC);
CREATE NONCLUSTERED INDEX IX_PersoanaID ON Persoane (ID ASC);
CREATE NONCLUSTERED INDEX IX_PersoanaNume ON Persoane (Nume ASC);
CREATE NONCLUSTERED INDEX IX_MuzeuID ON Muzee (ID ASC);
CREATE NONCLUSTERED INDEX IX_MuzeuNume ON Muzee (Nume ASC);