/*
Navicat SQL Server Data Transfer

Source Server         : WindowsSQL
Source Server Version : 120000
Source Host           : KADIRBWIN81\SQLEXPRESS:1433
Source Database       : jdepo
Source Schema         : dbo

Target Server Type    : SQL Server
Target Server Version : 120000
File Encoding         : 65001

Date: 2016-04-02 23:59:17
*/


-- ----------------------------
-- Table structure for commercialUsers
-- ----------------------------
DROP TABLE [dbo].[commercialUsers]
GO
CREATE TABLE [dbo].[commercialUsers] (
[id] int NOT NULL IDENTITY(1,1) ,
[ntlmName] varchar(64) NOT NULL DEFAULT (suser_sname()) ,
[userName] varchar(64) NULL ,
[userSurname] varchar(64) NULL ,
[tcKimlikNo] bigint NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[commercialUsers]', RESEED, 17)
GO

-- ----------------------------
-- Records of commercialUsers
-- ----------------------------
SET IDENTITY_INSERT [dbo].[commercialUsers] ON
GO
INSERT INTO [dbo].[commercialUsers] ([id], [ntlmName], [userName], [userSurname], [tcKimlikNo]) VALUES (N'17', N'KadirBWin81\Kadir', N'Bilinmiyor', N'Bilinmiyor', N'0')
GO
GO
SET IDENTITY_INSERT [dbo].[commercialUsers] OFF
GO

-- ----------------------------
-- Table structure for companyUsers
-- ----------------------------
DROP TABLE [dbo].[companyUsers]
GO
CREATE TABLE [dbo].[companyUsers] (
[userName] varchar(64) NULL ,
[userSurname] varchar(64) NULL ,
[tcIdentityNo] bigint NULL ,
[userEmail] varchar(64) NULL ,
[userPhone] varchar(64) NULL ,
[company] int NULL ,
[status] tinyint NULL ,
[id] int NOT NULL IDENTITY(1,1) 
)


GO
DBCC CHECKIDENT(N'[dbo].[companyUsers]', RESEED, 3)
GO

-- ----------------------------
-- Records of companyUsers
-- ----------------------------
SET IDENTITY_INSERT [dbo].[companyUsers] ON
GO
INSERT INTO [dbo].[companyUsers] ([userName], [userSurname], [tcIdentityNo], [userEmail], [userPhone], [company], [status], [id]) VALUES (N'MElike', N'Bakir', N'12131314151', N'melike.bakir@ozidas', N'05544826523', N'1', N'1', N'1')
GO
GO
INSERT INTO [dbo].[companyUsers] ([userName], [userSurname], [tcIdentityNo], [userEmail], [userPhone], [company], [status], [id]) VALUES (N'Aysun', N'Temel', N'12131517181', N'Aysun.temel@ozidas.com', N'05356826989', N'2', N'1', N'2')
GO
GO
INSERT INTO [dbo].[companyUsers] ([userName], [userSurname], [tcIdentityNo], [userEmail], [userPhone], [company], [status], [id]) VALUES (N'Kadir ', N'Basol', N'12556987451', N'Kadir.basol@hotmail.com', N'05446936356', N'3', N'1', N'3')
GO
GO
SET IDENTITY_INSERT [dbo].[companyUsers] OFF
GO

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE [dbo].[customer]
GO
CREATE TABLE [dbo].[customer] (
[id] int NOT NULL IDENTITY(1,1) ,
[name] varchar(64) NULL ,
[surname] varchar(64) NULL ,
[unvan] varchar(64) NULL ,
[type] tinyint NULL ,
[phoneNumber] varchar(32) NULL ,
[address] varchar(255) NULL ,
[tcKimlik] bigint NULL ,
[vergiKimlik] bigint NULL ,
[status] bigint NULL ,
[country] varchar(64) NULL ,
[province] varchar(64) NULL ,
[city] varchar(64) NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[customer]', RESEED, 3)
GO

-- ----------------------------
-- Records of customer
-- ----------------------------
SET IDENTITY_INSERT [dbo].[customer] ON
GO
INSERT INTO [dbo].[customer] ([id], [name], [surname], [unvan], [type], [phoneNumber], [address], [tcKimlik], [vergiKimlik], [status], [country], [province], [city]) VALUES (N'1', N'Ahmet', N'Sayin', N'Karadeniz A.s', N'2', N'05443383639', N'Zeytinburnu', N'49171875074', N'13212', N'1', N'Türkiye', N'Zeytinburnu', N'Istanbul')
GO
GO
INSERT INTO [dbo].[customer] ([id], [name], [surname], [unvan], [type], [phoneNumber], [address], [tcKimlik], [vergiKimlik], [status], [country], [province], [city]) VALUES (N'2', N'Mehmet', N'Ak', N'Sagdiclar', N'2', N'05338503214', N'Ahmet yesevi', N'47121562063', N'23658', N'1', N'Türkiye', N'Esenler', N'Istanbul')
GO
GO
INSERT INTO [dbo].[customer] ([id], [name], [surname], [unvan], [type], [phoneNumber], [address], [tcKimlik], [vergiKimlik], [status], [country], [province], [city]) VALUES (N'3', N'Hüseyin', N'Kaya', N'Ucanlar A.s', N'3', N'05382113285', N'Beylik Mah.', N'45612364582', N'23652', N'1', N'Türkiye', N'Beylikdüxü', N'Kocaeli')
GO
GO
SET IDENTITY_INSERT [dbo].[customer] OFF
GO

-- ----------------------------
-- Table structure for customerTypes
-- ----------------------------
DROP TABLE [dbo].[customerTypes]
GO
CREATE TABLE [dbo].[customerTypes] (
[id] int NOT NULL IDENTITY(1,1) ,
[customerType] int NULL ,
[info] varchar(64) NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[customerTypes]', RESEED, 5)
GO

-- ----------------------------
-- Records of customerTypes
-- ----------------------------
SET IDENTITY_INSERT [dbo].[customerTypes] ON
GO
INSERT INTO [dbo].[customerTypes] ([id], [customerType], [info]) VALUES (N'1', N'1', N'Sahis')
GO
GO
INSERT INTO [dbo].[customerTypes] ([id], [customerType], [info]) VALUES (N'2', N'2', N'Tedarikçi')
GO
GO
INSERT INTO [dbo].[customerTypes] ([id], [customerType], [info]) VALUES (N'3', N'3', N'Esnaf/Pazarci')
GO
GO
INSERT INTO [dbo].[customerTypes] ([id], [customerType], [info]) VALUES (N'4', N'4', N'Diger')
GO
GO
SET IDENTITY_INSERT [dbo].[customerTypes] OFF
GO

-- ----------------------------
-- Table structure for depots
-- ----------------------------
DROP TABLE [dbo].[depots]
GO
CREATE TABLE [dbo].[depots] (
[id] int NOT NULL IDENTITY(1,1) ,
[depot] int NULL ,
[depotName] varchar(64) NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[depots]', RESEED, 12)
GO

-- ----------------------------
-- Records of depots
-- ----------------------------
SET IDENTITY_INSERT [dbo].[depots] ON
GO
INSERT INTO [dbo].[depots] ([id], [depot], [depotName]) VALUES (N'1', N'1', N'-18 1A ')
GO
GO
INSERT INTO [dbo].[depots] ([id], [depot], [depotName]) VALUES (N'2', N'2', N'-18 1B')
GO
GO
INSERT INTO [dbo].[depots] ([id], [depot], [depotName]) VALUES (N'3', N'3', N'-18 2
')
GO
GO
INSERT INTO [dbo].[depots] ([id], [depot], [depotName]) VALUES (N'4', N'4', N'-18 3
')
GO
GO
INSERT INTO [dbo].[depots] ([id], [depot], [depotName]) VALUES (N'5', N'5', N'Sok Odasi 8
')
GO
GO
INSERT INTO [dbo].[depots] ([id], [depot], [depotName]) VALUES (N'8', N'8', N'Sok Odasi 11
')
GO
GO
INSERT INTO [dbo].[depots] ([id], [depot], [depotName]) VALUES (N'9', N'9', N'Sok Odasi 12
')
GO
GO
INSERT INTO [dbo].[depots] ([id], [depot], [depotName]) VALUES (N'10', N'10', N'Taze Muhafaza Çipura-Levrek
')
GO
GO
SET IDENTITY_INSERT [dbo].[depots] OFF
GO

-- ----------------------------
-- Table structure for process
-- ----------------------------
DROP TABLE [dbo].[process]
GO
CREATE TABLE [dbo].[process] (
[id] int NOT NULL IDENTITY(1,1) ,
[depot] int NOT NULL ,
[processType] tinyint NOT NULL ,
[company] int NOT NULL ,
[units] bigint NOT NULL ,
[description] text NULL ,
[unitsType] tinyint NOT NULL ,
[partNo] int NOT NULL ,
[owner] int NULL ,
[actionDate] datetime NULL ,
[product] int NULL ,
[companyUserId] int NULL ,
[price] bigint NULL ,
[priceType] tinyint NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[process]', RESEED, 57)
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'process', 
'COLUMN', N'id')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'Giriş ve Çıkışlar'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'process'
, @level2type = 'COLUMN', @level2name = N'id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'Giriş ve Çıkışlar'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'process'
, @level2type = 'COLUMN', @level2name = N'id'
GO

-- ----------------------------
-- Records of process
-- ----------------------------
SET IDENTITY_INSERT [dbo].[process] ON
GO
SET IDENTITY_INSERT [dbo].[process] OFF
GO

-- ----------------------------
-- Table structure for products
-- ----------------------------
DROP TABLE [dbo].[products]
GO
CREATE TABLE [dbo].[products] (
[id] int NOT NULL IDENTITY(1,1) ,
[productName] varchar(64) NULL ,
[unitType] tinyint NULL ,
[description] text NULL ,
[avgWeight] float(53) NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[products]', RESEED, 8)
GO

-- ----------------------------
-- Records of products
-- ----------------------------
SET IDENTITY_INSERT [dbo].[products] ON
GO
INSERT INTO [dbo].[products] ([id], [productName], [unitType], [description], [avgWeight]) VALUES (N'1', N'Hamsi', null, null, null)
GO
GO
INSERT INTO [dbo].[products] ([id], [productName], [unitType], [description], [avgWeight]) VALUES (N'2', N'Istavrit', null, null, null)
GO
GO
INSERT INTO [dbo].[products] ([id], [productName], [unitType], [description], [avgWeight]) VALUES (N'3', N'Levrek', null, null, null)
GO
GO
INSERT INTO [dbo].[products] ([id], [productName], [unitType], [description], [avgWeight]) VALUES (N'4', N'Somon', null, null, null)
GO
GO
INSERT INTO [dbo].[products] ([id], [productName], [unitType], [description], [avgWeight]) VALUES (N'5', N'Cipura', null, null, null)
GO
GO
INSERT INTO [dbo].[products] ([id], [productName], [unitType], [description], [avgWeight]) VALUES (N'6', N'Karides', null, null, null)
GO
GO
INSERT INTO [dbo].[products] ([id], [productName], [unitType], [description], [avgWeight]) VALUES (N'7', N'Sardalya', null, null, null)
GO
GO
INSERT INTO [dbo].[products] ([id], [productName], [unitType], [description], [avgWeight]) VALUES (N'8', N'Lüfer', null, null, null)
GO
GO
SET IDENTITY_INSERT [dbo].[products] OFF
GO

-- ----------------------------
-- Table structure for totalProducts
-- ----------------------------
DROP TABLE [dbo].[totalProducts]
GO
CREATE TABLE [dbo].[totalProducts] (
[company] int NOT NULL ,
[depot] int NOT NULL ,
[units] bigint NULL ,
[product] int NOT NULL ,
[partiNo] bigint NULL ,
[dateAdded] datetime NULL ,
[unitType] tinyint NULL ,
[id] int NOT NULL IDENTITY(1,1) 
)


GO
DBCC CHECKIDENT(N'[dbo].[totalProducts]', RESEED, 25)
GO

-- ----------------------------
-- Records of totalProducts
-- ----------------------------
SET IDENTITY_INSERT [dbo].[totalProducts] ON
GO
SET IDENTITY_INSERT [dbo].[totalProducts] OFF
GO

-- ----------------------------
-- Table structure for totalUnits
-- ----------------------------
DROP TABLE [dbo].[totalUnits]
GO
CREATE TABLE [dbo].[totalUnits] (
[company] int NOT NULL ,
[units] bigint NULL ,
[unitsType] int NULL ,
[id] int NOT NULL IDENTITY(1,1) 
)


GO
DBCC CHECKIDENT(N'[dbo].[totalUnits]', RESEED, 11)
GO

-- ----------------------------
-- Records of totalUnits
-- ----------------------------
SET IDENTITY_INSERT [dbo].[totalUnits] ON
GO
SET IDENTITY_INSERT [dbo].[totalUnits] OFF
GO

-- ----------------------------
-- Procedure structure for DepotManager_addCompany
-- ----------------------------
DROP PROCEDURE [dbo].[DepotManager_addCompany]
GO

CREATE PROCEDURE [dbo].[DepotManager_addCompany]
(
	@Name VARCHAR(64) ,
	@SurName VARCHAR(64) ,
	@Unvan VARCHAR(64) ,
	@Type TINYINT ,
	@PhoneNumber VARCHAR(32),
	@Address VARCHAR(255),
	@TcKimlik BIGINT,
	@VergiKimlik BIGINT ,
	@Success INT OUT
) 
AS
BEGIN
BEGIN TRY
	BEGIN TRANSACTION
  -- routine body goes here, e.g.
  -- SELECT 'Navicat for SQL Server'
  DECLARE @userId as INT;
	INSERT INTO customer(name,surname,unvan,type,phoneNumber,address,tcKimlik,vergiKimlik,status)
		VALUES ( @Name , @SurName , @Unvan , @Type , @PhoneNumber , @Address , @TcKimlik , @vergiKimlik , 0 );
  SET @userId = SCOPE_IDENTITY();
  INSERT INTO totalBox(company, totalBox) VALUES (@userId,0);
	Set @Success = 1;
	COMMIT TRANSACTION
RETURN
END TRY

BEGIN CATCH
	Set @Success = 0;
  ROLLBACK TRANSACTION
END CATCH

END

GO

-- ----------------------------
-- Procedure structure for DepotManager_productIn
-- ----------------------------
DROP PROCEDURE [dbo].[DepotManager_productIn]
GO
CREATE PROCEDURE [dbo].[DepotManager_productIn]
(
  @CompanyId INT ,
  @ProductId INT,
  @DepotId  INT,
  
  @Units BIGINT,
  @UnitsType TINYINT,
  @Price BIGINT,
  @PriceType TINYINT,
  
  @PartNo INT,
  @Description TEXT,
  @CompanyUserId INT,
  @Success INT OUT,
  @Exception NVARCHAR(255) OUT
)
AS
BEGIN
  BEGIN TRY
  BEGIN TRANSACTION
  -- routine body goes here, e.g.
  -- SELECT 'Navicat for SQL Server'
    DECLARE @UserId as INT;
    DECLARE @ProcessType AS TINYINT;
    DECLARE @CurrTime AS DATETIME;
    DECLARE @Uid As INT;
    set @ProcessType = 0;
    
--     DBCC CHECKIDENT('tablename', RESEED)  
    
--     UPDATE OR INSERT owner
    DECLARE @ownerName as VARCHAR(64);
    DECLARE @LastUnique as INT
    DECLARE @UserTable TABLE (id INT);
    SET @ownerName = SYSTEM_USER;
    SET @CurrTime = SYSDATETIME();
--   IDENT_CURRENT returns the last identity value generated for a specific table in any session and any scope.
--   @@IDENTITY returns the last identity value generated for any table in the current session, across all scopes.
--   SCOPE_IDENTITY returns the last identity value generated for any table in the current session and the current scope.
    
--     SELECT IDENT_INCR ('process')
--     IDENT_CURRENT(commercialUsers.id);
--     MERGE INTO commercialUsers AS Owners
--     USING (VALUES (@ownerName)) AS Source (NewName)
--     ON Owners.ntlmName = Source.NewName WHEN MATCHED THEN
--     UPDATE SET ntlmName = @ownerName
--     WHEN NOT MATCHED BY TARGET THEN
--     INSERT ( ntlmName , userName , userSurname , tcKimlikNo ) VALUES ( @ownerName , 'Bilinmiyor' , 'Bilinmiyor' , 0 );
   SET @UserId = (SELECT id FROM commercialUsers WHERE ntlmName= @ownerName );
   IF @UserId IS NULL
    BEGIN
      INSERT INTO commercialUsers( ntlmName , userName , userSurname , tcKimlikNo ) OUTPUT INSERTED.id INTO @UserTable(id) VALUES(@ownerName , 'Bilinmiyor' , 'Bilinmiyor' , 0);
      Set @UserId = (Select id from @UserTable);
    END
    
  INSERT INTO process(product, depot, processType, company, units , unitsType  , description, partNo, owner, actionDate , companyUserId , price , priceType )
    VALUES           (@ProductId,@DepotId,@ProcessType,@CompanyId,@Units , @UnitsType ,@Description,@PartNo, @UserId , @CurrTime , @CompanyUserId , @Price , @PriceType );
    
  Set @Uid = (Select id FROM totalProducts WHERE totalProducts.company = @CompanyId AND totalProducts.depot = @DepotId AND totalProducts.partiNo = @PartNo AND totalProducts.product = @ProductId)
    IF @Uid IS NULL 
  INSERT  INTO totalProducts( company, depot, product, dateAdded, units, partiNo, unitType)
                    VALUES (  @CompanyId , @DepotId , @ProductId , @CurrTime , @Units , @PartNo , @UnitsType );
    ELSE 
      UPDATE totalProducts SET totalProducts.units = totalProducts.units + @Units WHERE totalProducts.id = @Uid;
  -- --     Total products tablosuna ekleme yap
--   MERGE INTO totalProducts AS Owners
--   USING (VALUES (@CompanyId,@DepotId,@ProductId,@PartNo)) AS Source (CompanyId,DepotId,ProductId,PartNo)
--   ON Owners.company = Source.CompanyId AND Owners.depot = Source.DepotId AND Owners.product = Source.ProductId WHEN MATCHED THEN
--   UPDATE SET totalBox = totalBox+@ProcessAmount
--   WHEN NOT MATCHED BY TARGET THEN
--   INSERT (  product , company , depot , totalBox , partNo , dateAdded ) VALUES ( @ProductId , @CompanyId , @DepotId , @ProcessAmount , @PartNo , SYSDATETIME() );
--   
--   Set @Success = 1;
  IF EXISTS(SELECT company FROM totalUnits WHERE company = @CompanyId AND unitsType = @UnitsType)
    BEGIN 
      UPDATE totalUnits SET units = units + @Units WHERE company = @CompanyId AND unitsType = @UnitsType
    END
    ELSE
      INSERT INTO totalUnits(company, units , unitsType ) VALUES (@CompanyId,@Units,@UnitsType);
  Set @Success = 1;
  COMMIT TRANSACTION
  RETURN
  END TRY
  
  BEGIN CATCH
    Set @Success = 0;
    Set @Exception = ERROR_MESSAGE();
    ROLLBACK TRANSACTION
  END CATCH
  
END
GO

-- ----------------------------
-- Procedure structure for DepotManager_productOut
-- ----------------------------
DROP PROCEDURE [dbo].[DepotManager_productOut]
GO
CREATE PROCEDURE [dbo].[DepotManager_productOut]
  (
   @CompanyId INT ,
   @DepotId  INT,
   @ProductId INT,
   
   @Units BIGINT,
   @UnitsType TINYINT,
   @Price BIGINT,
   @PriceType TINYINT,
   
   @PartNo INT,
   @Description TEXT,
   @CompanyUserId INT,
   @Success INT OUT ,
   @Exception VARCHAR(255) OUT
  )
  AS
  BEGIN
    BEGIN TRY
    BEGIN TRANSACTION
    
    DECLARE @ProcessType INT = 1;
    DECLARE @CurrTime DATETIME = SYSDATETIME();
    DECLARE @UserId INT;
    DECLARE @UserTable AS TABLE(id INT);
    DECLARE @OwnerName VARCHAR(255) =  SYSTEM_USER;
    
    Set @Success = 0;
    
    
    SET @UserId = (SELECT id FROM commercialUsers WHERE ntlmName= @OwnerName );
    IF @UserId IS NULL
      BEGIN
        INSERT INTO commercialUsers( ntlmName , userName , userSurname , tcKimlikNo ) OUTPUT INSERTED.id INTO @UserTable(id) VALUES(@OwnerName , 'Bilinmiyor' , 'Bilinmiyor' , 0);
        Set @UserId = (Select id from @UserTable);
      END
      
    INSERT INTO process(depot, processType, company, description, partNo, owner, actionDate, product, companyUserId, price, priceType, units, unitsType)
                VALUES (@DepotId ,  @ProcessType, @CompanyId , @Description , @PartNo , @UserId , @CurrTime , @ProductId , @CompanyUserId , @Price , @PriceType , @Units , @UnitsType );
    
--     depodaki totalBox dan cikar
--     totalProducts dan cikar
    UPDATE totalProducts SET units = units - @Units WHERE company = @CompanyId AND unitType = @UnitsType AND partiNo = @PartNo AND totalProducts.depot = @DepotId AND totalProducts.product = @DepotId;
    UPDATE totalUnits SET units = units - @Units WHERE company = @CompanyId AND unitsType = @UnitsType;
    
    
    DELETE totalProducts WHERE company = @CompanyId AND depot = @DepotId AND partiNo = @PartNo AND units <= 0;
    
    Set @Success = 1;
      COMMIT TRAN
    END TRY
      BEGIN CATCH
        Set @Success = 0;
        Set @Exception = ERROR_MESSAGE();
      END CATCH
  END
GO

-- ----------------------------
-- Procedure structure for onLogin
-- ----------------------------
DROP PROCEDURE [dbo].[onLogin]
GO

CREATE PROCEDURE [dbo].[onLogin]
AS
BEGIN
  -- routine body goes here, e.g.
  -- SELECT 'Navicat for SQL Server'
    DECLARE @ownerName [varchar](128)
		SELECT SYSTEM_USER
    SET @ownerName = SCOPE_IDENTITY();
		
    MERGE INTO commercialUsers AS Owners
    USING (VALUES (@ownerName)) AS Source (NewName)
    ON Owners.ntlmName = Source.NewName WHEN MATCHED THEN
    UPDATE SET ntlmName = @ownerName
    WHEN NOT MATCHED BY TARGET THEN
    INSERT ( ntlmName , userName , userSurname , tcKimlikNo ) VALUES ( @ownerName , 'Bilinmiyor' , 'Bilinmiyor' , 0 );
END

GO

-- ----------------------------
-- Function structure for fn_soundxTR
-- ----------------------------
DROP FUNCTION [dbo].[fn_soundxTR]
GO


-- =============================================
-- Author:        Burak ŞEKERCİOĞLU
-- Create date: 13.01.2013
-- Description:    Türkçe Soundex Fonksiyonu
-- =============================================
CREATE FUNCTION [dbo].[fn_soundxTR]
(
@txt as nvarchar(max)
)
RETURNS nvarchar(4)

AS
begin

declare @islem nvarchar(max)

set @txt=upper(@txt)
set @txt=replace(@txt,'Ş','S')
set @txt=replace(@txt,'Ğ','G')
set @txt=replace(@txt,'İ','I')
set @txt=replace(@txt,'Ü','U')
set @txt=replace(@txt,'Ö','O')
set @txt=replace(@txt,'Ç','C')

set @islem=soundex(@txt)
return @islem
end


GO

-- ----------------------------
-- Indexes structure for table commercialUsers
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table commercialUsers
-- ----------------------------
ALTER TABLE [dbo].[commercialUsers] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table companyUsers
-- ----------------------------
CREATE CLUSTERED INDEX [CompanyUsersIndex] ON [dbo].[companyUsers]
([company] ASC, [userName] ASC, [userSurname] ASC)
GO

-- ----------------------------
-- Primary Key structure for table companyUsers
-- ----------------------------
ALTER TABLE [dbo].[companyUsers] ADD PRIMARY KEY NONCLUSTERED ([id])
GO

-- ----------------------------
-- Indexes structure for table customer
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table customer
-- ----------------------------
ALTER TABLE [dbo].[customer] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table customerTypes
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table customerTypes
-- ----------------------------
ALTER TABLE [dbo].[customerTypes] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table depots
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table depots
-- ----------------------------
ALTER TABLE [dbo].[depots] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table process
-- ----------------------------
CREATE CLUSTERED INDEX [ProcessIndex] ON [dbo].[process]
([company] ASC, [depot] ASC, [actionDate] ASC, [processType] ASC)
GO

-- ----------------------------
-- Primary Key structure for table process
-- ----------------------------
ALTER TABLE [dbo].[process] ADD PRIMARY KEY NONCLUSTERED ([id])
GO

-- ----------------------------
-- Indexes structure for table products
-- ----------------------------
CREATE INDEX [productIndex] ON [dbo].[products]
([productName] ASC) 
GO

-- ----------------------------
-- Primary Key structure for table products
-- ----------------------------
ALTER TABLE [dbo].[products] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table totalProducts
-- ----------------------------
CREATE CLUSTERED INDEX [TotalProductsIndex] ON [dbo].[totalProducts]
([company] ASC, [depot] ASC, [product] ASC)
GO
CREATE INDEX [TotalProductsIndex2] ON [dbo].[totalProducts]
([company] ASC, [dateAdded] DESC)
GO
CREATE INDEX [TotalProductsIndex3] ON [dbo].[totalProducts]
([company] ASC, [partiNo] ASC)
GO

-- ----------------------------
-- Primary Key structure for table totalProducts
-- ----------------------------
ALTER TABLE [dbo].[totalProducts] ADD PRIMARY KEY NONCLUSTERED ([id])
GO

-- ----------------------------
-- Indexes structure for table totalUnits
-- ----------------------------
CREATE INDEX [TotalBoxCompanyIndex] ON [dbo].[totalUnits]
([company] ASC)
GO

-- ----------------------------
-- Primary Key structure for table totalUnits
-- ----------------------------
ALTER TABLE [dbo].[totalUnits] ADD PRIMARY KEY ([id])
GO
