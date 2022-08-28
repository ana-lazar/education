-- Tema 2 Laborator
-- Interogari
-- Lazar Ana-Patricia


-- Afisarea numarului de donatori si suma totala stransa pentru fiecare expozitie din baza de date
-- GROUP BY
SELECT E.ID AS Expozitie, COUNT(D.ID_Expozitie) AS Numar_Donatori, SUM(D.Suma) AS Suma_Totala
FROM Donatii D INNER JOIN Expozitii E ON D.ID_Expozitie = E.ID
GROUP BY E.ID;


-- Afisarea sumei totale de tranzactii dintr-o data (25 iunie 2020)
-- GROUP BY, WHERE
SELECT Data_Tranzactie AS Data, SUM(Suma) AS Suma_Totala
FROM Tranzactii
WHERE Data_Tranzactie = '2020-06-25'
GROUP BY Data_Tranzactie;


-- Afisarea tuturor tarilor cu Muzee din baza de date
-- WHERE
SELECT DISTINCT A.Tara
FROM Muzee M INNER JOIN Adrese A ON M.ID_Adresa = A.ID;


-- Afisarea tuturor operelor si numele unui un anumit artist
-- WHERE, foloseste 3 tabele
SELECT O.Titlu, P.Nume, P.Prenume
FROM Artisti A INNER JOIN Opere O ON O.ID_Artist = A.ID
			   INNER JOIN Persoane P ON A.ID_Persoana = P.ID
WHERE P.Nume = 'Grigorescu' AND P.Prenume = 'Nicolae';


-- Afisarea ghizilor de la dintr-un anumit muzeu, o singura data indiferent din cate departamente fac parte
-- WHERE, DISTINCT, foloseste 4 tabele, relatie m - n (persoane - departamente)
SELECT DISTINCT P.ID, P.Nume, P.Prenume, M.Nume AS Nume_Muzeu
FROM Ghizi G INNER JOIN Persoane P ON G.ID_Persoana = P.ID
			 INNER JOIN Departamente D ON G.ID_Departament = D.ID
									   INNER JOIN Muzee M ON D.ID_Muzeu = M.ID
WHERE M.ID = 1;


-- Afisarea tuturor expozitiilor ce vor tine mai mult de 10 zile, a artistului si a muzeelor unde vor avea loc
-- WHERE, foloseste 4 tabele
SELECT E.Titlu, P.Prenume, P.Nume, M.Nume, DATEDIFF(day, E.Data_Inceput, E.Data_Final) AS Numar_Zile
FROM Expozitii E INNER JOIN Muzee M ON E.ID_Muzeu = M.ID
				 INNER JOIN Artisti A ON E.ID_Artist = A.ID
									  INNER JOIN Persoane P ON A.ID_Persoana = P.ID
WHERE DATEDIFF(day, E.Data_Inceput, E.Data_Final) > 10;


-- Afisarea tuturor tarilor unde suma totala a tranzactiilor din muzeele de arta in Aprilie 2020 depaseste 100
-- GROUP BY, HAVING, foloseste 3 tabele
SELECT A.Tara, SUM(T.Suma) AS Suma_Totala
FROM Muzee M INNER JOIN Adrese A ON M.ID_Adresa = A.ID
			 INNER JOIN Tranzactii T ON T.ID_Muzeu = M.ID
GROUP BY A.Tara
HAVING SUM(T.Suma) > 100;


-- Afisarea tuturor operelor ce fac parte din mai mult de un eveniment
-- GROUP BY, HAVING, foloseste 3 tabele, relatie m - n (opere - evenimente)
SELECT O.Titlu, COUNT(O.ID) AS Numar_Evenimente
FROM Opere_La_Evenimente OE INNER JOIN Opere O ON OE.ID_Opera = O.ID
							INNER JOIN Evenimente E ON OE.ID_Eveniment = E.ID
GROUP BY O.Titlu
HAVING COUNT(O.ID) > 1;


-- Afisarea persoanelor care au donat unei expozitii mai mult de 1000 lei, a sumei, precum si a titlului expozitiei
-- WHERE, foloseste 3 tabele, relatie m - n (persoane - expozitii)
SELECT P.ID, P.Nume, P.Prenume, E.Titlu, D.Suma
FROM Donatii D INNER JOIN Persoane P ON P.ID = D.ID_Persoana
			   INNER JOIN Expozitii E ON E.ID = D.ID_Expozitie
WHERE D.Suma > 1000;


-- Afisarea tuturor evenimentelor de la un anumit muzeu (Louvre)
-- WHERE, foloseste 3 tabele
SELECT M.Nume, E.Titlu, E.Data
FROM Departamente D INNER JOIN Muzee M ON D.ID_Muzeu = M.ID
					INNER JOIN Evenimente E ON E.ID_Departament = D.ID
WHERE M.ID = 1;


-- Afisarea tuturor muzeelor din Romania din baza de date
-- WHERE, foloseste 2 tabele
-- SELECT M.Nume, A.Tara
-- FROM Muzee M INNER JOIN Adrese A ON M.ID_Adresa = A.ID
-- WHERE A.Tara = 'Romania';