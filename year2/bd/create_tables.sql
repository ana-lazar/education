CREATE PROCEDURE Creaza
AS
BEGIN
	-- Creare tabel Adrese
CREATE TABLE Adrese
(
	ID INT,
	Strada NVARCHAR(100),
	Oras NVARCHAR(100) NOT NULL,
	Tara NVARCHAR(100),
	PRIMARY KEY (ID),
);

-- Creare tabel Muzee
CREATE TABLE Muzee
(
	ID SMALLINT,
	Nume NVARCHAR(100) NOT NULL,
	Website NVARCHAR(100),
	ID_Adresa INT,
	PRIMARY KEY (ID),
	FOREIGN KEY (ID_Adresa) REFERENCES Adrese(ID)
);

-- Creare tabel Departamente
CREATE TABLE Departamente
(
	ID SMALLINT,
	Nume NVARCHAR(100),
	ID_Muzeu SMALLINT,
	PRIMARY KEY (ID),
	FOREIGN KEY (ID_Muzeu) REFERENCES Muzee(ID)
);

-- Creare tabel Persoane
CREATE TABLE Persoane
(
	ID SMALLINT,
	Nume NVARCHAR(100) NOT NULL,
	Prenume NVARCHAR(100) NOT NULL,
	ID_Adresa INT,
	PRIMARY KEY (ID),
	FOREIGN KEY (ID_Adresa) REFERENCES Adrese(ID)
);

-- Creare tabel Tranzactii
CREATE TABLE Tranzactii
(
	ID SMALLINT,
	ID_Persoana SMALLINT,
	ID_Muzeu SMALLINT,
	ID_Factura SMALLINT,
	Data_Tranzactie DATE,
	Suma INT NOT NULL,
	PRIMARY KEY (ID),
	FOREIGN KEY (ID_Persoana) REFERENCES Persoane(ID),
	FOREIGN KEY (ID_Muzeu) REFERENCES Muzee(ID),
	FOREIGN KEY (ID_Factura) REFERENCES Facturi(ID)
);

-- Creare tabel Artisti
CREATE TABLE Artisti
(
	ID SMALLINT,
	ID_Persoana SMALLINT,
	Data_Nasterii DATE,
	Data_Decesului DATE,
	Sectiune NVARCHAR(20),
	FOREIGN KEY (ID_Persoana) REFERENCES Persoane(ID),
	PRIMARY KEY (ID)
);

-- Creare tabel Opere
CREATE TABLE Opere
(
	ID SMALLINT,
	Titlu NVARCHAR(100) NOT NULL,
	ID_Artist SMALLINT,
	ID_Departament SMALLINT,
	ID_Donator SMALLINT,
	PRIMARY KEY (ID),
	FOREIGN KEY (ID_Departament) REFERENCES Departamente(ID),
	FOREIGN KEY (ID_Artist) REFERENCES Artisti(ID),
	FOREIGN KEY (ID_Donator) REFERENCES Persoane(ID)
);

-- Creare tabel Evenimente
CREATE TABLE Evenimente
(
	ID SMALLINT,
	Titlu NVARCHAR(100) NOT NULL,
	Descriere NVARCHAR(100),
	Data DATE,
	ID_Departament SMALLINT,
	PRIMARY KEY (ID),
	FOREIGN KEY (ID_Departament) REFERENCES Departamente(ID)
);

-- Creare tabel Opere_La_Evenimente
CREATE TABLE Opere_La_Evenimente
(
	ID_Opera SMALLINT,
	ID_Eveniment SMALLINT,
	PRIMARY KEY (ID_Opera, ID_Eveniment),
	FOREIGN KEY (ID_Eveniment) REFERENCES Evenimente(ID),
	FOREIGN KEY (ID_Opera) REFERENCES Opere(ID)
);

-- Creare tabel Expozitii
CREATE TABLE Expozitii
(
	ID SMALLINT,
	Titlu NVARCHAR(100) NOT NULL,
	Data_Inceput DATE,
	Data_Final DATE,
	ID_Artist SMALLINT,
	ID_Muzeu SMALLINT,
	PRIMARY KEY (ID),
	FOREIGN KEY (ID_Artist) REFERENCES Artisti(ID),
	FOREIGN KEY (ID_Muzeu) REFERENCES Muzee(ID)
);

-- Creare tabel Ghizi
CREATE TABLE Ghizi
(
	ID_Persoana SMALLINT,
	ID_Departament SMALLINT,
	PRIMARY KEY (ID_Persoana, ID_Departament),
	FOREIGN KEY (ID_Persoana) REFERENCES Persoane(ID),
	FOREIGN KEY (ID_Departament) REFERENCES Departamente(ID)
);

-- Creare tabel Donatii
CREATE TABLE Donatii
(
	ID_Persoana SMALLINT,
	ID_Expozitie SMALLINT,
	Suma INT,
	PRIMARY KEY (ID_Persoana, ID_Expozitie),
	FOREIGN KEY (ID_Persoana) REFERENCES Persoane(ID),
	FOREIGN KEY (ID_Expozitie) REFERENCES Expozitii(ID)
);

-- Creare tabel Facturi
CREATE TABLE Facturi
(
	ID SMALLINT,
	Tip VARCHAR(30),
	Suma INT,
	PRIMARY KEY (ID)
);

END
GO

drop table Facturi;