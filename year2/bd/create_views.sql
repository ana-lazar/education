USE BDMuzeeDeArta

-- View ce foloseste o singura tabela si o comanda SELECT
-- Afisarea tuturor adreselor

GO
CREATE VIEW View_Adrese
AS
SELECT ID, Strada, Oras, Tara
FROM Adrese

SELECT * FROM View_Adrese;

DROP VIEW View_Adrese;


-- View ce foloseste 2 tabele si o comanda SELECT
-- Afisarea tuturor artistilor, precum si a sectiunii din care fac parte

GO
CREATE VIEW View_Artisti
AS
SELECT A.ID, P.Nume, P.Prenume, A.Data_Nasterii, A.Data_Decesului, A.Sectiune
FROM Persoane P INNER JOIN Artisti A ON P.Id = A.ID_Persoana;

SELECT * FROM View_Artisti;

DROP VIEW View_Artisti;


-- View ce foloseste 2 tabele si o clauza GROUP BY
-- Afisarea numarului de donatori si suma totala stransa pentru fiecare expozitie din baza de date

GO
CREATE VIEW View_Donatori
AS
SELECT E.ID AS Expozitie, COUNT(D.ID_Expozitie) AS Numar_Donatori, SUM(D.Suma) AS Suma_Totala
FROM Donatii D INNER JOIN Expozitii E ON D.ID_Expozitie = E.ID
GROUP BY E.ID;

SELECT * FROM View_Donatori;

DROP VIEW View_Donatori;
