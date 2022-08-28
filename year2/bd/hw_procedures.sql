-- Tema 3 Laborator
-- Proceduri
-- Lazar Ana-Patricia


USE BDMuzeeDeArta;


-- Modificarea tipului unei coloane
-- Schimba tipul atributului Data din Tabela Evenimente in tipul VARCHAR, daca nu este deja
GO 
ALTER PROCEDURE change_type
AS 
BEGIN 
	IF exists(SELECT DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Evenimente' AND COLUMN_NAME = 'Data' AND DATA_TYPE != 'varchar')
	BEGIN
		ALTER TABLE Evenimente
		ALTER COLUMN Data VARCHAR(20);
		PRINT 'Am schimbat tipul atributului Data din tabelul Evenimente in VARCHAR(20).';
	END
END


-- Schimba inapoi tipul atributului Data din Tabela Evenimente in tipul DATE, daca acesta e VARCHAR initial
GO 
ALTER PROCEDURE undo_change_type
AS 
BEGIN 
	IF exists(SELECT DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Evenimente' AND COLUMN_NAME = 'Data' AND DATA_TYPE = 'varchar')
	BEGIN
		ALTER TABLE Evenimente
		ALTER COLUMN Data DATE;
		PRINT 'Am schimbat tipul atributului Data din tabelul Evenimente in DATE.';
	END
END 

EXEC change_type;
EXEC undo_change_type;


---------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Adaugarea unei constrangeri DEFAULT
-- Adauga o constrangere default pentru campul Suma din Donatii, daca nu exista deja
GO 
ALTER PROCEDURE add_default_constraint
AS 
BEGIN
	IF not exists(SELECT COLUMN_DEFAULT FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Donatii' AND COLUMN_NAME = 'Suma' AND COLUMN_DEFAULT != 'NULL')
	BEGIN
		ALTER TABLE Donatii
		ADD CONSTRAINT c_suma DEFAULT 0 FOR Suma;
		PRINT 'Am adaugat o constrangere de tipul DEFAULT pentru atributul Suma din tabelul Donatii.';
	END
END 

-- Sterge constrangerea default c_suma pentru campul Suma din tabelul Donatii, daca acesta exista
GO
ALTER PROCEDURE undo_default_constraint
AS 
BEGIN
	IF exists(SELECT COLUMN_DEFAULT FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Donatii' AND COLUMN_NAME = 'Suma' AND COLUMN_DEFAULT != 'NULL')
	BEGIN
		ALTER TABLE Donatii
		DROP CONSTRAINT c_suma;
		PRINT 'Am sters constrangerea de tipul DEFAULT pentru atributul Suma din tabelul Donatii.';
	END
END 

EXEC add_default_constraint;
EXEC undo_default_constraint;


--------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Creare / Stergere tabela
-- Creaza o tabela Facturi, daca aceasta nu exista deja
GO 
ALTER PROCEDURE create_tabela
AS 
BEGIN
	IF not exists(SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'Facturi')
	BEGIN
		PRINT 'Am creat tabela Facturi.';
		CREATE TABLE Facturi
		(
			ID SMALLINT,
			Tip VARCHAR(30),
			ID_Tranzactie SMALLINT,
			PRIMARY KEY (ID)
		);
	END
END 

-- Sterge tabela Facturi, daca aceasta exista
GO
ALTER PROCEDURE delete_tabela
AS 
BEGIN 
	IF exists(SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'Facturi')
	BEGIN
		PRINT 'Am sters tabela Facturi';
		DROP TABLE Facturi;
	END
END 

EXEC create_tabela;
EXEC delete_tabela;


---------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Adauga / Sterge un camp nou
-- Adauga campul Data_Nasterii in tabela Persoane
GO 
ALTER PROCEDURE add_field
AS 
BEGIN
	IF not exists(SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Persoane' AND COLUMN_NAME = 'Data_Nasterii')
	BEGIN
		ALTER TABLE Persoane
		ADD Data_Nasterii DATE
		PRINT 'Am adaugat campul Data_Nasterii in tabela Persoane.';
	END
END 

-- Sterge campul Data_Nasterii, daca acesta exista
GO
ALTER PROCEDURE delete_field
AS 
BEGIN 
	IF exists(SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Persoane' AND COLUMN_NAME = 'Data_Nasterii')
	BEGIN
		ALTER TABLE Persoane
		DROP COLUMN Data_nasterii
		PRINT 'Am sters campul Data_Nasterii in tabela Persoane.';
	END
END 

EXEC add_field;
EXEC delete_field;


---------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Creare / Stergere constrangere de cheie straina
-- Creaza costrangere cheie straina intre campurile ID_Departament din Expozitie si ID din Departament
GO 
ALTER PROCEDURE create_foreign_key
AS
BEGIN
	IF not exists(SELECT CONSTRAINT_TYPE, CONSTRAINT_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE TABLE_NAME = 'Facturi' AND CONSTRAINT_TYPE = 'foreign key' AND CONSTRAINT_NAME = 'c_tranz')
	BEGIN
		ALTER TABLE Facturi
		ADD CONSTRAINT c_tranz FOREIGN KEY (ID_Tranzactie) REFERENCES Tranzactii(ID);
		PRINT 'Am adaugat o constrangere cheie straina pe tabela Facturi.';
	END
END

-- Sterge campul Data_Nasterii, daca acesta exista
GO
ALTER PROCEDURE delete_foreign_key
AS
BEGIN
	IF exists(SELECT CONSTRAINT_TYPE, CONSTRAINT_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE TABLE_NAME = 'Facturi' AND CONSTRAINT_TYPE = 'foreign key' AND CONSTRAINT_NAME = 'c_tranz')
	BEGIN
		ALTER TABLE Facturi
		DROP CONSTRAINT c_tranz;
		PRINT 'Am sters o constrangere cheie straina pe tabela Facturi.';
	END
END

EXEC create_foreign_key;
EXEC delete_foreign_key;


--------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Versiune 
CREATE TABLE Versiune
(
	ID TINYINT PRIMARY KEY
);

INSERT INTO Versiune (ID)
VALUES (0);


-- Incrementeaza cu 1 versiune baza de date
GO
ALTER PROCEDURE increment_by_1
AS
BEGIN
	DECLARE @versiune TINYINT = (SELECT ID FROM Versiune);
	IF (@versiune = 0)
	BEGIN
		EXEC change_type;
	END
	ELSE IF (@versiune = 1)
		BEGIN
			EXEC add_default_constraint;
		END
		ELSE IF (@versiune = 2)
			BEGIN
				EXEC create_tabela;
			END
			ELSE IF (@versiune = 3)
				BEGIN
					EXEC add_field;
				END
				ELSE IF (@versiune = 4)
					BEGIN
						EXEC create_foreign_key;
					END
	DECLARE @newv TINYINT = @versiune + 1;
	UPDATE Versiune SET ID = @newv WHERE ID = @versiune;
END


-- Decrementeaza cu 1 versiune baza de date
GO
ALTER PROCEDURE decrement_by_1
AS
BEGIN
	DECLARE @versiune TINYINT = (SELECT ID FROM Versiune);
	IF (@versiune = 1)
	BEGIN
		EXEC undo_change_type;
	END
	ELSE IF (@versiune = 2)
		BEGIN
			EXEC undo_default_constraint;
		END
		ELSE IF (@versiune = 3)
			BEGIN
				EXEC delete_tabela;
			END
			ELSE IF (@versiune = 4)
				BEGIN
					EXEC delete_field;
				END
				ELSE IF (@versiune = 5)
					BEGIN
						EXEC delete_foreign_key;
					END
	DECLARE @newv TINYINT = @versiune - 1;
	UPDATE Versiune SET ID = @newv WHERE ID = @versiune;
END


-- Actualizeaza versiunea bazei de date
GO
ALTER PROCEDURE change_version (@id SMALLINT)
AS
BEGIN
	DECLARE @versiune TINYINT = (SELECT ID FROM Versiune);
	WHILE (@versiune != @id)
	BEGIN
		IF (@versiune < @id) 
		BEGIN
			EXEC increment_by_1 ;
			SET @versiune = @versiune + 1;
			PRINT 'versiune ' + @versiune;
		END
		ELSE
		BEGIN
			EXEC decrement_by_1;
			SET @versiune = @versiune - 1;
			PRINT 'versiune ' + @versiune;
		END
	END
END

EXEC change_version 0;

SELECT * FROM Versiune;