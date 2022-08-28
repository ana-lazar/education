-- Lazar Ana-Patricia
-- grupa 224
-- laborator 5


USE BDMuzeeDeArta;

-- Am ales tabelele: Persoane, Adrese, Muzee, Tranzactii

-- Valideaza un string, se asigura ca nu e null
GO
CREATE OR ALTER FUNCTION ValidateString (@String NVARCHAR(100))
RETURNS INT
AS
BEGIN
	IF (@String = N'')
		RETURN 0;
	RETURN 1;
END;

GO
PRINT dbo.ValidateString(N'a');

-- ADRESE

-- Validari de date
-- Valideaza ID-ul adresei, returneaza numarul de aparitii ale ID-ului respectiv in inregistrarile din tabela
GO
CREATE OR ALTER FUNCTION ValidateIDAdrese (@ID INT)
RETURNS INT
AS
BEGIN
	DECLARE @count INT = 0;
	SELECT @count = COUNT(*) FROM Adrese
	GROUP BY ID
	HAVING ID = @ID;
	RETURN @count;
END;

GO
PRINT dbo.ValidateIDAdrese(1);
SELECT * FROM Adrese;

-- Operatii CRUD
-- Adaugarea unei noi adrese in baza de date
GO
CREATE OR ALTER PROCEDURE CreateAdrese (@ID INT, @Strada NVARCHAR(100), @Oras NVARCHAR(100), @Tara NVARCHAR(100))
AS
BEGIN
	SET NOCOUNT ON;

	IF (dbo.ValidateString(@Strada) + dbo.ValidateString(@Oras) + dbo.ValidateString(@Tara) != 3)
	BEGIN
		PRINT 'Nu toate campurile din adresa sunt diferite de vid';
		RETURN;
	END;
	IF dbo.ValidateIDAdrese(@ID) = 1
	BEGIN
		PRINT 'Adresa existenta deja in baza de date';
		RETURN;
	END;

	INSERT INTO Adrese (ID, Strada, Oras, Tara) VALUES (@ID, @Strada, @Oras, @Tara);

	PRINT 'Adresa adaugata cu succes in baza de date';
END;

EXEC CreateAdrese 1, N'Nicolae Draganu', N'Cluj-Napoca', N'Romania';
SELECT * FROM Adrese;

-- Citirea unei adrese din baza de date
GO
CREATE OR ALTER PROCEDURE ReadAdrese (@ID INT)
AS
BEGIN
	IF dbo.ValidateIDAdrese(@ID) = 0
	BEGIN
		PRINT 'Adresa inexistenta in baza de date';
		RETURN;
	END;

	SELECT * 
	FROM Adrese
	WHERE ID = @ID;
END;

EXEC ReadAdrese 1;

-- Modificarea unei adrese din baza de date
GO
CREATE OR ALTER PROCEDURE UpdateAdrese (@ID INT, @Strada NVARCHAR(100), @Oras NVARCHAR(100), @Tara NVARCHAR(100))
AS
BEGIN
	SET NOCOUNT ON;

	IF (dbo.ValidateString(@Strada) + dbo.ValidateString(@Oras) + dbo.ValidateString(@Tara) != 3)
	BEGIN
		PRINT 'Nu toate campurile din adresa sunt diferite de vid';
		RETURN;
	END;
	IF dbo.ValidateIDAdrese(@ID) = 0
	BEGIN
		PRINT 'Adresa inexistenta in baza de date';
		RETURN;
	END;

	UPDATE Adrese
	SET Strada = @Strada, Oras = @Oras, Tara = @Tara
	WHERE ID = @ID;

	PRINT 'Adresa modificata cu succes in baza de date';
END;

EXEC UpdateAdrese 1, N'Nasaud', N'Cluj-Napoca', N'Romania';
SELECT * FROM Adrese;

-- Stergerea unei adrese din baza de date
GO
CREATE OR ALTER PROCEDURE DeleteAdrese (@ID INT)
AS
BEGIN
	SET NOCOUNT ON;

	IF dbo.ValidateIDAdrese(@ID) = 0
	BEGIN
		PRINT 'Adresa inexistenta in baza de date';
		RETURN;
	END;
	
	DELETE FROM Adrese
	WHERE ID = @ID;

	PRINT 'Adresa stearsa cu succes in baza de date';
END;

EXEC DeleteAdrese 1;
SELECT * FROM Adrese;

------------------------------------------------------------------------------------------------------------------------------------------------

-- MUZEE

-- Validari de date
-- Valideaza ID-ul adresei, returneaza numarul de aparitii ale ID-ului respectiv in inregistrarile din tabela
GO
CREATE OR ALTER FUNCTION ValidateIDMuzee (@ID INT)
RETURNS INT
AS
BEGIN
	DECLARE @count INT = 0;
	SELECT @count = COUNT(*) FROM Muzee
	GROUP BY ID
	HAVING ID = @ID;
	RETURN @count;
END;

GO
PRINT dbo.ValidateIDMuzee(1);
SELECT * FROM Muzee;

-- Operatii CRUD
-- Adaugarea unui nou muzeu in baza de date
GO
CREATE OR ALTER PROCEDURE CreateMuzee (@ID INT, @Nume NVARCHAR(100), @Website NVARCHAR(100), @ID_Adresa INT)
AS
BEGIN
	SET NOCOUNT ON;

	IF (dbo.ValidateString(@Nume) + dbo.ValidateString(@Website) != 2)
	BEGIN
		PRINT 'Nu toate campurile din muzeu sunt diferite de vid';
		RETURN;
	END;
	IF dbo.ValidateIDAdrese(@ID_Adresa) = 0
	BEGIN
		PRINT 'Adresa muzeului este inexistenta in baza de date';
		RETURN;
	END;
	IF dbo.ValidateIDMuzee(@ID) = 1
	BEGIN
		PRINT 'Muzeul exista deja in baza de date';
		RETURN;
	END;

	INSERT INTO Muzee (ID, Nume, Website, ID_Adresa) VALUES (@ID, @Nume, @Website, @ID_Adresa);

	PRINT 'Muzeu adaugat cu succes in baza de date';
END;

EXEC CreateMuzee 1, N'Muzeu de Arta', N'www.cluj.ro', 1;
SELECT * FROM Muzee;
SELECT * FROM Adrese;

-- Citirea unui muzeu din baza de date
GO
CREATE OR ALTER PROCEDURE ReadMuzee (@ID INT)
AS
BEGIN
	IF dbo.ValidateIDMuzee(@ID) = 0
	BEGIN
		PRINT 'Muzeul nu exista in baza de date';
		RETURN;
	END;

	SELECT * 
	FROM Muzee
	WHERE ID = @ID;
END;

EXEC ReadMuzee 2;

-- Modificarea unui muzeu din baza de date
GO
CREATE OR ALTER PROCEDURE UpdateMuzee (@ID INT, @Nume NVARCHAR(100), @Website NVARCHAR(100), @ID_Adresa INT)
AS
BEGIN
	SET NOCOUNT ON;

	IF (dbo.ValidateString(@Nume) + dbo.ValidateString(@Website) != 2)
	BEGIN
		PRINT 'Nu toate campurile din muzeu sunt diferite de vid';
		RETURN;
	END;
	IF dbo.ValidateIDAdrese(@ID_Adresa) = 0
	BEGIN
		PRINT 'Adresa muzeului este inexistenta in baza de date';
		RETURN;
	END;
	IF dbo.ValidateIDMuzee(@ID) = 0
	BEGIN
		PRINT 'Muzeul nu exista in baza de date';
		RETURN;
	END;

	UPDATE Muzee
	SET Nume = @Nume, Website = @Website, ID_Adresa = @ID_Adresa
	WHERE ID = @ID;

	PRINT 'Muzeu modificat cu succes in baza de date';
END;

EXEC UpdateMuzee 1, N'Muzeu de Arta', N'www.muzeu.ro', 1;
SELECT * FROM Muzee;

-- Stergerea unui muzeu din baza de date
GO
CREATE OR ALTER PROCEDURE DeleteMuzee (@ID INT)
AS
BEGIN
	SET NOCOUNT ON;

	IF dbo.ValidateIDMuzee(@ID) = 0
	BEGIN
		PRINT 'Muzeu inexistent in baza de date';
		RETURN;
	END;
	
	DELETE FROM Muzee
	WHERE ID = @ID;

	PRINT 'Muzeu sters cu succes din baza de date';
END;

EXEC DeleteMuzee 1;
SELECT * FROM Muzee;


-----------------------------------------------------------------------------------------------------------------------------------------

-- PERSOANE

-- Validari de date
-- Valideaza ID-ul persoanei, returneaza numarul de aparitii ale ID-ului respectiv in inregistrarile din tabela
GO
CREATE OR ALTER FUNCTION ValidateIDPersoane (@ID INT)
RETURNS INT
AS
BEGIN
	DECLARE @count INT = 0;
	SELECT @count = COUNT(*) FROM Persoane
	GROUP BY ID
	HAVING ID = @ID;
	RETURN @count;
END;

GO
PRINT dbo.ValidateIDPersoane(1);
SELECT * FROM Persoane;

-- Operatii CRUD
-- Adaugarea unei noi persoane in baza de date
GO
CREATE OR ALTER PROCEDURE CreatePersoane (@ID INT, @Nume NVARCHAR(100), @Prenume NVARCHAR(100), @ID_Adresa INT, @Data_Nasterii DATE)
AS
BEGIN
	SET NOCOUNT ON;

	IF (dbo.ValidateString(@Nume) + dbo.ValidateString(@Prenume) != 2)
	BEGIN
		PRINT 'Nu toate campurile sunt diferite de vid';
		RETURN;
	END;
	IF dbo.ValidateIDAdrese(@ID_Adresa) = 0
	BEGIN
		PRINT 'Adresa persoanei este inexistenta in baza de date';
		RETURN;
	END;
	IF dbo.ValidateIDPersoane(@ID) = 1
	BEGIN
		PRINT 'Persoana exista deja in baza de date';
		RETURN;
	END;

	INSERT INTO Persoane (ID, Nume, Prenume, ID_Adresa, Data_Nasterii) VALUES (@ID, @Nume, @Prenume, @ID_Adresa, @Data_Nasterii);

	PRINT 'Persoana adaugata cu succes in baza de date';
END;

EXEC CreatePersoane 1, N'Ana', N'Lazar', 1, '2020/05/10';
SELECT * FROM Persoane;
SELECT * FROM Adrese;

-- Citirea unei persoane din baza de date
GO
CREATE OR ALTER PROCEDURE ReadPersoane (@ID INT)
AS
BEGIN
	IF dbo.ValidateIDPersoane(@ID) = 0
	BEGIN
		PRINT 'Persoana nu exista in baza de date';
		RETURN;
	END;

	SELECT * 
	FROM Persoane
	WHERE ID = @ID;
END;

EXEC ReadPersoane 1;

-- Modificarea unei persoane din baza de date
GO
CREATE OR ALTER PROCEDURE UpdatePersoane (@ID INT, @Nume NVARCHAR(100), @Prenume NVARCHAR(100), @ID_Adresa INT, @Data_Nasterii DATE)
AS
BEGIN
	SET NOCOUNT ON;

	IF (dbo.ValidateString(@Nume) + dbo.ValidateString(@Prenume) != 2)
	BEGIN
		PRINT 'Nu toate campurile sunt diferite de vid';
		RETURN;
	END;
	IF dbo.ValidateIDAdrese(@ID_Adresa) = 0
	BEGIN
		PRINT 'Adresa persoanei este inexistenta in baza de date';
		RETURN;
	END;
	IF dbo.ValidateIDPersoane(@ID) = 0
	BEGIN
		PRINT 'Persoana nu exista in baza de date';
		RETURN;
	END;

	UPDATE Persoane
	SET Nume = @Nume, Prenume = @Prenume, ID_Adresa = @ID_Adresa, @Data_Nasterii = @Data_Nasterii
	WHERE ID = @ID;

	PRINT 'Persoana modificata cu succes in baza de date';
END;

EXEC UpdatePersoane 1, N'Maria', N'Lazar', 1, '2020/05/10';
SELECT * FROM Persoane;

-- Stergerea unei persoane din baza de date
GO
CREATE OR ALTER PROCEDURE DeletePersoane (@ID INT)
AS
BEGIN
	SET NOCOUNT ON;

	IF dbo.ValidateIDPersoane(@ID) = 0
	BEGIN
		PRINT 'Persoana inexistenta in baza de date';
		RETURN;
	END;
	
	DELETE FROM Persoane
	WHERE ID = @ID;

	PRINT 'Persoana stearsa cu succes din baza de date';
END;

EXEC DeletePersoane 1;
SELECT * FROM Persoane;


-----------------------------------------------------------------------------------------------------------------------------------------

-- TRANZACTII

-- Validari de date
-- Valideaza ID-ul tranzactiei, returneaza numarul de aparitii ale ID-ului respectiv in inregistrarile din tabela
GO
CREATE OR ALTER FUNCTION ValidateIDTranzactii (@ID INT)
RETURNS INT
AS
BEGIN
	DECLARE @count INT = 0;
	SELECT @count = COUNT(*) FROM Tranzactii
	GROUP BY ID
	HAVING ID = @ID;
	RETURN @count;
END;

GO
PRINT dbo.ValidateIDTranzactii(1);
SELECT * FROM Tranzactii;

-- Valideaza ID-ul facturii, returneaza numarul de aparitii ale ID-ului respectiv in inregistrarile din tabela
GO
CREATE OR ALTER FUNCTION ValidateIDFacturi (@ID INT)
RETURNS INT
AS
BEGIN
	DECLARE @count INT = 0;
	SELECT @count = COUNT(*) FROM Facturi
	GROUP BY ID
	HAVING ID = @ID;
	RETURN @count;
END;

GO
PRINT dbo.ValidateIDFacturi(1);
SELECT * FROM Facturi;

-- Operatii CRUD
-- Adaugarea unei noi tranzactii in baza de date
GO
CREATE OR ALTER PROCEDURE CreateTranzactie (@ID INT, @ID_Persoana INT, @ID_Muzeu INT, @ID_Factura INT, @Data_Tranzactie DATE)
AS
BEGIN
	SET NOCOUNT ON;

	IF dbo.ValidateIDPersoane(@ID_Persoana) = 0
	BEGIN
		PRINT 'Persoana este inexistenta in baza de date';
		RETURN;
	END;
	IF dbo.ValidateIDFacturi(@ID_Factura) = 0
	BEGIN
		PRINT 'Factura este inexistenta in baza de date';
		RETURN;
	END;
	IF dbo.ValidateIDMuzee(@ID_Muzeu) = 0
	BEGIN
		PRINT 'Muzeul este inexistent in baza de date';
		RETURN;
	END;
	IF dbo.ValidateIDTranzactii(@ID) = 1
	BEGIN
		PRINT 'Tranzactia exista deja in baza de date';
		RETURN;
	END;

	INSERT INTO Tranzactii(ID, ID_Persoana, ID_Muzeu, ID_Factura, Data_Tranzactie) VALUES (@ID, @ID_Persoana, @ID_Muzeu, @ID_Factura, @Data_Tranzactie);

	PRINT 'Tranzactie adaugata cu succes in baza de date';
END;

INSERT INTO Facturi (ID, Tip, Suma) VALUES (1, N'IDK', 100);

EXEC CreateTranzactie 1, 1, 1, 1, '2020/05/10';
EXEC CreateTranzactie 2, 2, 2, 1, '2020/05/10';
EXEC CreateTranzactie 3, 3, 3, 1, '2020/05/10';
EXEC CreateTranzactie 4, 4, 4, 1, '2020/05/10';
EXEC CreateTranzactie 5, 5, 5, 1, '2020/05/10';
SELECT * FROM Tranzactii;

-- Citirea unei tranzactii din baza de date
GO
CREATE OR ALTER PROCEDURE ReadTranzactie (@ID INT)
AS
BEGIN
	IF dbo.ValidateIDTranzactii(@ID) = 0
	BEGIN
		PRINT 'Tranzactia nu exista in baza de date';
		RETURN;
	END;

	SELECT * 
	FROM Tranzactii
	WHERE ID = @ID;
END;

EXEC ReadTranzactie 1;

-- Modificarea unei tranzactii din baza de date
GO
CREATE OR ALTER PROCEDURE UpdateTranzactie (@ID INT, @ID_Persoana INT, @ID_Muzeu INT, @ID_Factura INT, @Data_Tranzactie DATE)
AS
BEGIN
	SET NOCOUNT ON;

	IF dbo.ValidateIDPersoane(@ID_Persoana) = 0
	BEGIN
		PRINT 'Persoana este inexistenta in baza de date';
		RETURN;
	END;
	IF dbo.ValidateIDFacturi(@ID_Factura) = 0
	BEGIN
		PRINT 'Factura este inexistenta in baza de date';
		RETURN;
	END;
	IF dbo.ValidateIDMuzee(@ID_Muzeu) = 0
	BEGIN
		PRINT 'Muzeul este inexistent in baza de date';
		RETURN;
	END;
	IF dbo.ValidateIDTranzactii(@ID) = 0
	BEGIN
		PRINT 'Tranzactia nu exista in baza de date';
		RETURN;
	END;

	UPDATE Tranzactii
	SET ID_Persoana = @ID_Persoana, ID_Muzeu = @ID_Muzeu, ID_Factura = @ID_Factura, Data_Tranzactie = @Data_Tranzactie
	WHERE ID = @ID;

	PRINT 'Tranzactia modificata cu succes in baza de date';
END;

EXEC UpdateTranzactie 1, 1, 1, 1, '2020/05/10';
SELECT * FROM Tranzactii;

-- Stergerea unei tranzactii din baza de date
GO
CREATE OR ALTER PROCEDURE DeleteTranzactie (@ID INT)
AS
BEGIN
	SET NOCOUNT ON;

	IF dbo.ValidateIDTranzactii(@ID) = 0
	BEGIN
		PRINT 'Tranzactie inexistenta in baza de date';
		RETURN;
	END;
	
	DELETE FROM Tranzactii
	WHERE ID = @ID;

	PRINT 'Tranzactie stearsa cu succes din baza de date';
END;

EXEC DeleteTranzactie 1;
SELECT * FROM Tranzactii;
