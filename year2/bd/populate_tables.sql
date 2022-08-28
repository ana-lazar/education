CREATE PROCEDURE Populeaza
AS
BEGIN
	INSERT INTO Adrese (ID, Strada, Oras, Tara) VALUES 
				(400610, 'Nasaud', 'Cluj-Napoca', 'Romania'), 
				(400012, 'Victor Babes', 'Aiud', 'Romania'), 
				(403009, 'Croitorilor', 'Timisoara', 'Romania'), 
				(500100, 'Al. V. Voievod', 'Bucuresti', 'Romania'), 
				(75001, 'Rue de Rivoli', 'Paris', 'Franta'),
				(10028, '5th Ave', 'New York', 'SUA'),
				(28014, 'Ruiz de Alarcón', 'Madrid', 'Spania'),
				(400000, 'Piata Unirii', 'Cluj-Napoca', 'Romania'),
				(010063, 'Calea Victoriei', 'Bucuresti', 'Romania');

	INSERT INTO Muzee (ID, Nume, Website, ID_Adresa) VALUES 
				(1, 'Louvre', 'https://www.louvre.fr/en', 75001),
				(2, 'MET', 'https://www.metmuseum.org/', 10028),
				(3, 'Prado', 'https://www.museodelprado.es/en', 28014),
				(4, 'Muzeul de Arta Cluj-Napoca', 'https://www.macluj.ro/', 400000),
				(5, 'Muzeul National de Arta al Romaniei', 'https://www.mnar.arts.ro/', 010063);

	INSERT INTO Persoane (ID, Nume, Prenume, ID_Adresa) VALUES
				(101, 'Lazar', 'Ana', 400610),
				(102, 'Popescu', 'Maria', 400012),
				(103, 'Grigore', 'Andrei', 403009),
				(104, 'Grigore', 'Ioana', 403009),
				(105, 'Panacotta', 'Mihai', 500100),
				(201, 'Vinci', 'Leonardo da', NULL),
				(202, 'Goya', 'Francisco de', NULL),
				(203, 'Grigorescu', 'Nicolae', NULL),
				(301, 'Pop', 'Oana', 500100),
				(302, 'Castravete', 'Marius', 010063);

	INSERT INTO Tranzactii (ID, ID_Persoana, ID_Muzeu, Data_Tranzactie, Suma) VALUES
				(111, 101, 4,'2020/05/10', 20),
				(222, 102, 1, '2019/04/09', 20),
				(333, 103, 3, '2020/06/25', 20),
				(444, 104, 2, '2020/09/10', 20),
				(555, 105, 5, '2020/04/27', 20),
				(667, 101, 4, '2020/05/11', 20),
				(777, 301, 5, '2020/08/09', 20),
				(888, 302, 3, '2019/11/15', 20),
				(999, 102, 4, '2019/12/09', 20);

	INSERT INTO Ghizi (ID_Persoana, ID_Departament) VALUES
				(301, 1),
				(302, 3),
				(301, 2),
				(302, 1);

	INSERT INTO Departamente (ID, Nume, ID_Muzeu) VALUES
				(1, 'Sculptura', 1),
				(2, 'Pictura', 1),
				(3, 'Sculptura', 2),
				(4, 'Sculptura', 3),
				(5, 'Sculptura', 4);

	INSERT INTO Artisti (ID, Data_Nasterii, Data_Decesului, Sectiune, ID_Persoana) VALUES
				(99, '1452/04/14', '1519/05/02', 'Sculptura', 201),
				(100, '1452/04/14', '1519/05/02', 'Pictura', 201),
				(101, '1746/03/30', '1828/04/16', 'Pictura', 202),
				(102, '1746/03/30', '1828/04/16', 'Gravuri', 202),
				(103, '1838/05/15', '1907/07/21', 'Pictura', 203);

	INSERT INTO Expozitii (ID, Titlu, Data_Inceput, Data_Final, ID_Artist, ID_Muzeu) VALUES
				(1, 'Dezastrele razboiului', '2020/10/10', '2020/10/20', 102, 5),
				(2, 'Dezastrele razboiului', '2019/12/10', '2020/12/25', 102, 3),
				(3, 'Vindecari', '2020/08/10', '2020/08/12', 103, 4);

	INSERT INTO Donatii (ID_Expozitie, ID_Persoana, Suma) VALUES
				(1, 101, 1000),
				(2, 102, 9000),
				(3, 103, 900),
				(1, 103, 12000),
				(2, 105, 100);

	INSERT INTO Evenimente (ID, Titlu, Descriere, Data, ID_Departament) VALUES
				(11, 'Strangere de fonduri', 'desc', '2020/11/15', 1),
				(22, 'Spectacol de teatru', 'desc', '2020/02/17', 3),
				(33, 'Gala', 'desc', '2020/08/01', 4),
				(44, 'Gala', 'desc', '2020/05/22', 1),
				(55, 'Strangere de fonduri', 'desc', '2020/07/21', 2);

	INSERT INTO Opere (ID, Titlu, ID_Artist, ID_Departament, ID_Donator) VALUES
				(1, 'Mona Lisa', 100, 2, 101),
				(2, 'Cainele', 101, 3, 101),
				(3, 'Maja', 101, 3, 102),
				(4, '3 mai', 101, 2, 105),
				(5, 'Car cu boi', 103, 5, 103),
				(6, 'Dorobant', 103, 4, 104),
				(7, 'Pastor', 103, 5, 103);

	INSERT INTO Opere_La_Evenimente (ID_Eveniment, ID_Opera) VALUES
				(1, 11),
				(1, 44),
				(2, 22),
				(3, 22),
				(4, 22),
				(5, 33),
				(6, 33),
				(6, 22),
				(7, 33),
				(7, 55);

END
GO
