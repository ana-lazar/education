-- Lazar Ana-Patricia
-- grupa 224
-- laborator 4


USE BDMuzeeDeArta;

-- Test 1

-- View_Facturi
-- Afiseaza toate facturile cu suma peste 10
GO
CREATE VIEW view_Facturi
AS
SELECT * 
FROM Facturi
WHERE Suma > 10;


-- Tabel: Facturi
-- Insereaza o inregistrare cu id-ul @id in tabelul Facturi
GO
ALTER PROCEDURE insert_Facturi(@no_rows INT)
AS
BEGIN
	SET NOCOUNT ON;

	DECLARE @id SMALLINT = 0, @inserted INT = 0;

	WHILE @inserted < @no_rows
	BEGIN
		SET @id = @id + 1;
		IF (not exists(SELECT TOP 1 ID FROM Facturi WHERE ID = @id))
		BEGIN
			INSERT INTO Facturi (ID, Tip, Suma) VALUES (@id, '', @id * 100);
			SET @inserted = @inserted + 1;
		END
	END
END


-- Test 2


-- View_Muzee
-- Afiseaza toate muzeele din Romania
GO
CREATE VIEW view_Muzee
AS
SELECT Nume
FROM Muzee M INNER JOIN Adrese A ON M.ID_Adresa = A.ID
WHERE A.Tara = 'Romania';


-- Tabel: Muzee
-- Insereaza o inregistrare cu id-ul @id in tabelul Muzee
GO
ALTER PROCEDURE insert_Muzee(@no_rows INT)
AS
BEGIN
	SET NOCOUNT ON;

	DECLARE @id SMALLINT = 0, @inserted INT = 0;

	IF (not exists(SELECT TOP 1 ID FROM Adrese WHERE ID = 1))
	BEGIN
		INSERT INTO Adrese (ID, Strada, Oras, Tara) VALUES (1, 'a', 'a', 'Romania');
	END

	WHILE @inserted < @no_rows
	BEGIN
		SET @id = @id + 1;
		IF (not exists(SELECT TOP 1 ID FROM Muzee WHERE ID = @id))
		BEGIN
			INSERT INTO Muzee (ID, Nume, Website, ID_Adresa) VALUES (@id, 'Muzeu de Arta', 'www', 1);
			SET @inserted = @inserted + 1;
		END
	END
END


-- Tabel: Departamente
-- Insereaza o inregistrare cu id-ul @id in tabelul Departamente
GO
ALTER PROCEDURE insert_Departamente(@no_rows INT)
AS
BEGIN
	SET NOCOUNT ON;

	DECLARE @id SMALLINT = 0, @inserted INT = 0;

	INSERT INTO Muzee (ID, Nume, Website, ID_Adresa) VALUES (-1, '', '', -1);

	WHILE @inserted < @no_rows
	BEGIN
		SET @id = @id + 1;
		IF (not exists(SELECT TOP 1 ID FROM Departamente WHERE ID = @id))
		BEGIN
			INSERT INTO Departamente (ID, Nume, ID_Muzeu) VALUES (@id, '', -1);
			SET @inserted = @inserted + 1;
		END
	END
END


-- Test 3


-- View_Donatii
-- Afiseaza toate donatiile de la o anumita persoana si suma totala donata
GO
CREATE VIEW view_Donatii
AS
SELECT P.Nume, SUM(Suma) AS Suma_Totala
FROM Donatii D INNER JOIN Persoane P ON P.ID = D.ID_Persoana
			   INNER JOIN Expozitii E ON D.ID_Expozitie = E.ID
GROUP BY P.Nume, Suma;


-- View_Expozitii
-- Afiseaza toate expozitiile unui singur artist
GO
CREATE VIEW view_Expozitii
AS
SELECT P.Nume, E.Titlu
FROM Artisti A INNER JOIN Persoane P ON P.ID = A.ID_Persoana
			   INNER JOIN Expozitii E ON E.ID_Artist = A.ID
WHERE P.ID = 1;


-- Tabel: Persoane
-- Insereaza o inregistrare cu id-ul @id in tabelul Persoane
GO
ALTER PROCEDURE insert_Persoane(@no_rows INT)
AS
BEGIN
	SET NOCOUNT ON;

	DECLARE @id SMALLINT = 0, @inserted INT = 0;

	IF (not exists(SELECT TOP 1 ID FROM Adrese WHERE ID = 1))
	BEGIN
		INSERT INTO Adrese (ID, Strada, Oras, Tara) VALUES (1, '', '', '');
	END

	WHILE @inserted < @no_rows
	BEGIN
		SET @id = @id + 1;
		IF (not exists(SELECT TOP 1 ID FROM Persoane WHERE ID = @id))
		BEGIN
			INSERT INTO Persoane (ID, Nume, Prenume, ID_Adresa, Data_Nasterii) VALUES (@id, 'Ana', 'M', 1, '2020/05/10');
			SET @inserted = @inserted + 1;
		END
	END
END


-- Tabel: Expozitii
-- Insereaza o inregistrare cu id-ul @id in tabelul Expozitii
GO
ALTER PROCEDURE insert_Expozitii(@no_rows INT)
AS
BEGIN
	SET NOCOUNT ON;

	DECLARE @id SMALLINT = 0, @inserted INT = 0;

	WHILE @inserted < @no_rows
	BEGIN
		SET @id = @id + 1;
		IF (not exists(SELECT TOP 1 ID FROM Expozitii WHERE ID = @id))
		BEGIN
			INSERT INTO Expozitii(ID, Titlu, Data_Inceput, Data_Final, ID_Artist) VALUES (@id, '', '2020/05/10', '2020/05/10', @id);
			SET @inserted = @inserted + 1;
		END
	END
END


-- Tabel: Donatii
-- Insereaza o inregistrare cu id-ul (@id, @id) in tabelul Donatii
GO
ALTER PROCEDURE insert_Donatii(@no_rows INT)
AS
BEGIN
	SET NOCOUNT ON;

	DECLARE @id SMALLINT = 0, @inserted INT = 0;

	WHILE @inserted < @no_rows
	BEGIN
		SET @id = @id + 1;
		IF (not exists(SELECT TOP 1 ID_Expozitie, ID_Persoana FROM Donatii WHERE ID_Expozitie = @id AND ID_Persoana = @id))
		BEGIN
			INSERT INTO Donatii(ID_Persoana, ID_Expozitie, Suma) VALUES (@id, @id, @id * 10);
			SET @inserted = @inserted + 1;
		END
	END
END


-- Tabel: Artisti
-- Insereaza o inregistrare cu id-ul @id in tabelul Artisti
GO
ALTER PROCEDURE insert_Artisti(@no_rows INT)
AS
BEGIN
	SET NOCOUNT ON;

	DECLARE @id SMALLINT = 0, @inserted INT = 0;

	WHILE @inserted < @no_rows
	BEGIN
		SET @id = @id + 1;
		IF (not exists(SELECT TOP 1 ID FROM Artisti WHERE ID = @id))
		BEGIN
			INSERT INTO Artisti(ID, ID_Persoana, Data_Nasterii, Data_Decesului, Sectiune) VALUES (@id, @id, '2020/05/10', '2020/05/10', '');
			SET @inserted = @inserted + 1;
		END
	END
END


-- Ruleaza toate testele

GO
ALTER PROCEDURE main
AS 
BEGIN
	SET NOCOUNT ON;

	DELETE FROM TestRuns;
	DBCC CHECKIDENT (TestRuns, RESEED, 0);

	DECLARE @id_test INT, @id_view INT, @id_test_run INT, @id_table INT, @no_rows INT, 
		    @test_name VARCHAR(20), @table_name VARCHAR(20), @view_name VARCHAR(20), 
			@SQL VARCHAR(100), @func VARCHAR(20), @desc VARCHAR(100), 
			@starts DATETIME;

	DECLARE cursor_tests CURSOR
		FOR SELECT TestID, Name FROM Tests
		ORDER BY TestID

	OPEN cursor_tests

	FETCH NEXT FROM cursor_tests INTO @id_test, @test_name

	WHILE @@FETCH_STATUS = 0
	BEGIN
		DECLARE cursor_tabele SCROLL CURSOR
			FOR SELECT TT.TableID, Name, NoOfRows
			FROM TestTables TT INNER JOIN Tables T ON TT.TableID = T.TableID
			WHERE TT.TestID = @id_test
			ORDER BY Position

		OPEN cursor_tabele

		PRINT('DELETE');

		SET @desc = 'Test: ' + CAST(@id_test AS VARCHAR(10));
		INSERT INTO TestRuns(Description, StartAt, EndAt) VALUES (@desc, GETDATE(), NULL);
		SET @id_test_run = @@IDENTITY;

		FETCH NEXT FROM cursor_tabele INTO @id_table, @table_name, @no_rows

		WHILE @@FETCH_STATUS = 0
		BEGIN
			PRINT('Test: ' + CAST(@id_test AS VARCHAR(10)) + '; Tabel: ' + @table_name);

			SET @SQL = 'DELETE FROM ' + @table_name;
			EXEC(@SQL);

			FETCH NEXT FROM cursor_tabele INTO @id_table, @table_name, @no_rows
		END

		PRINT('INSERT');

		FETCH PRIOR FROM cursor_tabele INTO @id_table, @table_name, @no_rows

		WHILE @@FETCH_STATUS = 0
		BEGIN
			PRINT('Test: ' + CAST(@id_test AS VARCHAR(10)) + '; Tabel: ' + @table_name);

			SET @starts = GETDATE();

			SET @func = 'insert_' + @table_name;
			EXEC @func @no_rows;

			INSERT INTO TestRunTables(TestRunID, TableID, StartAt, EndAt) VALUES (@id_test_run, @id_table, @starts, GETDATE());

			FETCH PRIOR FROM cursor_tabele INTO @id_table, @table_name, @no_rows
		END

		CLOSE cursor_tabele
		DEALLOCATE cursor_tabele

		DECLARE cursor_views CURSOR
			FOR SELECT TV.ViewID, Name
			FROM TestViews TV INNER JOIN Views V ON TV.ViewID = V.ViewID
			WHERE TV.TestID = @id_test

		OPEN cursor_views

		PRINT('VIEW');

		FETCH NEXT FROM cursor_views INTO @id_view, @view_name

		WHILE @@FETCH_STATUS = 0
		BEGIN
			PRINT('Test: ' + CAST(@id_test AS VARCHAR(10)) + '; View: ' + @view_name);

			SET @starts = GETDATE();

			SET @SQL = 'SELECT * FROM ' + @view_name;
			EXEC(@SQL);

			INSERT INTO TestRunViews(TestRunID, ViewID, StartAt, EndAt) VALUES (@id_test_run, @id_view, @starts, GETDATE());

			FETCH NEXT FROM cursor_views INTO @id_view, @view_name
		END

		CLOSE cursor_views
		DEALLOCATE cursor_views

		UPDATE TestRuns SET EndAt = GETDATE() WHERE TestRunID = @id_test_run;

		FETCH NEXT FROM cursor_tests INTO @id_test, @test_name
	END

	CLOSE cursor_tests
	DEALLOCATE cursor_tests
END


EXEC main;


SELECT *
FROM TestRuns;

SELECT *
FROM TestRunTables;

SELECT *
FROM TestRunViews;


