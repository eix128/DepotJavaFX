/*
Navicat SQL Server Data Transfer

Source Server         : WindowsDepoServer
Source Server Version : 120000
Source Host           : deposrv:1433
Source Database       : jdepo
Source Schema         : dbo

Target Server Type    : SQL Server
Target Server Version : 120000
File Encoding         : 65001

Date: 2016-04-20 21:29:12
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
DBCC CHECKIDENT(N'[dbo].[commercialUsers]', RESEED, 19)
GO

-- ----------------------------
-- Records of commercialUsers
-- ----------------------------
SET IDENTITY_INSERT [dbo].[commercialUsers] ON
GO
INSERT INTO [dbo].[commercialUsers] ([id], [ntlmName], [userName], [userSurname], [tcKimlikNo]) VALUES (N'17', N'KadirBWin81\Kadir', N'Bilinmiyor', N'Bilinmiyor', N'0')
GO
GO
INSERT INTO [dbo].[commercialUsers] ([id], [ntlmName], [userName], [userSurname], [tcKimlikNo]) VALUES (N'18', N'OZIDAS\kadir.basol', N'Bilinmiyor', N'Bilinmiyor', N'0')
GO
GO
INSERT INTO [dbo].[commercialUsers] ([id], [ntlmName], [userName], [userSurname], [tcKimlikNo]) VALUES (N'19', N'OZIDAS\aysun.temel', N'Bilinmiyor', N'Bilinmiyor', N'0')
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
[companyid] int NULL ,
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
INSERT INTO [dbo].[companyUsers] ([userName], [userSurname], [tcIdentityNo], [userEmail], [userPhone], [companyid], [status], [id]) VALUES (N'MElike', N'Bakir', N'12131314151', N'melike.bakir@ozidas', N'05544826523', N'1', N'1', N'1')
GO
GO
INSERT INTO [dbo].[companyUsers] ([userName], [userSurname], [tcIdentityNo], [userEmail], [userPhone], [companyid], [status], [id]) VALUES (N'Aysun', N'Temel', N'12131517181', N'Aysun.temel@ozidas.com', N'05356826989', N'2', N'1', N'2')
GO
GO
INSERT INTO [dbo].[companyUsers] ([userName], [userSurname], [tcIdentityNo], [userEmail], [userPhone], [companyid], [status], [id]) VALUES (N'Kadir ', N'Basol', N'12556987451', N'Kadir.basol@hotmail.com', N'05446936356', N'3', N'1', N'3')
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
[title] nvarchar(64) NOT NULL ,
[type] tinyint NULL ,
[phoneNumber] varchar(32) NULL ,
[address] nvarchar(255) NULL ,
[tcKimlik] bigint NULL ,
[vergiKimlik] bigint NULL ,
[status] bigint NULL ,
[country] nvarchar(64) NULL ,
[province] nvarchar(64) NULL ,
[city] nvarchar(64) NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[customer]', RESEED, 4)
GO

-- ----------------------------
-- Records of customer
-- ----------------------------
SET IDENTITY_INSERT [dbo].[customer] ON
GO
INSERT INTO [dbo].[customer] ([id], [name], [surname], [title], [type], [phoneNumber], [address], [tcKimlik], [vergiKimlik], [status], [country], [province], [city]) VALUES (N'1', N'Ahmet', N'Sayin', N'Karadeniz A.Ş', N'2', N'05443383639', N'Zeytinburnu', N'49171875074', N'13212', N'1', N'Türkiye', N'Zeytinburnu', N'Istanbul')
GO
GO
INSERT INTO [dbo].[customer] ([id], [name], [surname], [title], [type], [phoneNumber], [address], [tcKimlik], [vergiKimlik], [status], [country], [province], [city]) VALUES (N'2', N'Mehmet', N'Ak', N'Sagdiclar', N'2', N'05338503214', N'Ahmet yesevi', N'47121562063', N'23658', N'1', N'Türkiye', N'Esenler', N'Istanbul')
GO
GO
INSERT INTO [dbo].[customer] ([id], [name], [surname], [title], [type], [phoneNumber], [address], [tcKimlik], [vergiKimlik], [status], [country], [province], [city]) VALUES (N'3', N'Hüseyin', N'Kaya', N'Ucanlar A.Ş', N'3', N'05382113285', N'Beylik Mah.', N'45612364582', N'23652', N'1', N'Türkiye', N'Beylikdüxü', N'Kocaeli')
GO
GO
INSERT INTO [dbo].[customer] ([id], [name], [surname], [title], [type], [phoneNumber], [address], [tcKimlik], [vergiKimlik], [status], [country], [province], [city]) VALUES (N'4', N'Osman', N'Çiçek', N'Deneme Ltd Şti.', N'1', N'05369292955', N'Şemsettin Günaltay Cd Burak Apt No 125 Daire 42 Kazasker İstanbul', N'25835037250', N'23444', N'1', N'Türkiye', N'Kadıköy', N'İstanbul')
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
[depotid] int NULL ,
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
INSERT INTO [dbo].[depots] ([id], [depotid], [depotName]) VALUES (N'1', N'1', N'-18 1A')
GO
GO
INSERT INTO [dbo].[depots] ([id], [depotid], [depotName]) VALUES (N'2', N'2', N'-18 1B')
GO
GO
INSERT INTO [dbo].[depots] ([id], [depotid], [depotName]) VALUES (N'3', N'3', N'-18 2')
GO
GO
INSERT INTO [dbo].[depots] ([id], [depotid], [depotName]) VALUES (N'4', N'4', N'-18 3')
GO
GO
INSERT INTO [dbo].[depots] ([id], [depotid], [depotName]) VALUES (N'5', N'5', N'Sok Odasi 8')
GO
GO
INSERT INTO [dbo].[depots] ([id], [depotid], [depotName]) VALUES (N'8', N'8', N'Sok Odasi 11')
GO
GO
INSERT INTO [dbo].[depots] ([id], [depotid], [depotName]) VALUES (N'9', N'9', N'Sok Odasi 12')
GO
GO
INSERT INTO [dbo].[depots] ([id], [depotid], [depotName]) VALUES (N'10', N'10', N'Taze Muhafaza Çipura-Levrek')
GO
GO
SET IDENTITY_INSERT [dbo].[depots] OFF
GO

-- ----------------------------
-- Table structure for partNoIndex
-- ----------------------------
DROP TABLE [dbo].[partNoIndex]
GO
CREATE TABLE [dbo].[partNoIndex] (
[companyid] int NOT NULL ,
[partNoid] bigint NULL ,
[partNo] varchar(32) NULL ,
[productid] int NULL ,
[id] bigint NOT NULL IDENTITY(1,1) 
)


GO

-- ----------------------------
-- Records of partNoIndex
-- ----------------------------
SET IDENTITY_INSERT [dbo].[partNoIndex] ON
GO
INSERT INTO [dbo].[partNoIndex] ([companyid], [partNoid], [partNo], [productid], [id]) VALUES (N'1', N'20042016', N'20042016', N'1', N'1')
GO
GO
SET IDENTITY_INSERT [dbo].[partNoIndex] OFF
GO

-- ----------------------------
-- Table structure for priceTypes
-- ----------------------------
DROP TABLE [dbo].[priceTypes]
GO
CREATE TABLE [dbo].[priceTypes] (
[id] int NOT NULL ,
[priceName] varchar(16) NULL ,
[priceShortcut] varchar(8) NULL 
)


GO

-- ----------------------------
-- Records of priceTypes
-- ----------------------------
INSERT INTO [dbo].[priceTypes] ([id], [priceName], [priceShortcut]) VALUES (N'-1', N'Unknown', N'Unk')
GO
GO
INSERT INTO [dbo].[priceTypes] ([id], [priceName], [priceShortcut]) VALUES (N'1', N'DOLLAR', N'USD')
GO
GO
INSERT INTO [dbo].[priceTypes] ([id], [priceName], [priceShortcut]) VALUES (N'2', N'EURO', N'EUR')
GO
GO
INSERT INTO [dbo].[priceTypes] ([id], [priceName], [priceShortcut]) VALUES (N'3', N'Turkish Lira', N'TL')
GO
GO

-- ----------------------------
-- Table structure for process
-- ----------------------------
DROP TABLE [dbo].[process]
GO
CREATE TABLE [dbo].[process] (
[id] bigint NOT NULL IDENTITY(1,1) ,
[depotid] int NOT NULL ,
[processType] tinyint NOT NULL ,
[companyid] int NOT NULL ,
[units] bigint NOT NULL ,
[description] text NULL ,
[unitsType] tinyint NOT NULL ,
[partNo] bigint NULL ,
[ownerid] int NULL ,
[actionDate] datetime NULL ,
[productid] int NULL ,
[companyUserid] int NULL ,
[price] bigint NULL ,
[priceType] tinyint NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[process]', RESEED, 355)
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
INSERT INTO [dbo].[process] ([id], [depotid], [processType], [companyid], [units], [description], [unitsType], [partNo], [ownerid], [actionDate], [productid], [companyUserid], [price], [priceType]) VALUES (N'355', N'1', N'0', N'1', N'100', N'fe pgfpwefpwfk wefpğwk f', N'1', N'20042016', N'18', N'2016-04-20 20:52:28.713', N'1', N'1', N'10', N'3')
GO
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
[companyid] int NOT NULL ,
[depotid] int NOT NULL ,
[units] bigint NULL ,
[productid] int NOT NULL ,
[partiNo] bigint NULL ,
[dateAdded] datetime NULL ,
[unitType] tinyint NULL ,
[id] int NOT NULL IDENTITY(1,1) 
)


GO
DBCC CHECKIDENT(N'[dbo].[totalProducts]', RESEED, 95)
GO

-- ----------------------------
-- Records of totalProducts
-- ----------------------------
SET IDENTITY_INSERT [dbo].[totalProducts] ON
GO
INSERT INTO [dbo].[totalProducts] ([companyid], [depotid], [units], [productid], [partiNo], [dateAdded], [unitType], [id]) VALUES (N'1', N'1', N'100', N'1', N'20042016', N'2016-04-20 20:52:28.713', N'1', N'95')
GO
GO
SET IDENTITY_INSERT [dbo].[totalProducts] OFF
GO

-- ----------------------------
-- Table structure for totalUnits
-- ----------------------------
DROP TABLE [dbo].[totalUnits]
GO
CREATE TABLE [dbo].[totalUnits] (
[companyid] int NOT NULL ,
[units] bigint NULL ,
[unitsType] tinyint NULL ,
[id] int NOT NULL IDENTITY(1,1) 
)


GO
DBCC CHECKIDENT(N'[dbo].[totalUnits]', RESEED, 3)
GO

-- ----------------------------
-- Records of totalUnits
-- ----------------------------
SET IDENTITY_INSERT [dbo].[totalUnits] ON
GO
INSERT INTO [dbo].[totalUnits] ([companyid], [units], [unitsType], [id]) VALUES (N'1', N'100', N'1', N'3')
GO
GO
SET IDENTITY_INSERT [dbo].[totalUnits] OFF
GO

-- ----------------------------
-- Table structure for transferTypes
-- ----------------------------
DROP TABLE [dbo].[transferTypes]
GO
CREATE TABLE [dbo].[transferTypes] (
[id] int NOT NULL ,
[transferType] varchar(32) NULL 
)


GO

-- ----------------------------
-- Records of transferTypes
-- ----------------------------

-- ----------------------------
-- Table structure for unitTypes
-- ----------------------------
DROP TABLE [dbo].[unitTypes]
GO
CREATE TABLE [dbo].[unitTypes] (
[id] int NOT NULL ,
[unitName] varchar(255) NULL 
)


GO

-- ----------------------------
-- Records of unitTypes
-- ----------------------------
INSERT INTO [dbo].[unitTypes] ([id], [unitName]) VALUES (N'-1', N'OTHER')
GO
GO
INSERT INTO [dbo].[unitTypes] ([id], [unitName]) VALUES (N'1', N'BOX')
GO
GO
INSERT INTO [dbo].[unitTypes] ([id], [unitName]) VALUES (N'2', N'KG')
GO
GO
INSERT INTO [dbo].[unitTypes] ([id], [unitName]) VALUES (N'3', N'TON')
GO
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
	@Title VARCHAR(64) ,
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
	INSERT INTO customer(name,surname,title,type,phoneNumber,address,tcKimlik,vergiKimlik,status)
		VALUES ( @Name , @SurName , @Title , @Type , @PhoneNumber , @Address , @TcKimlik , @vergiKimlik , 0 );
  SET @userId = SCOPE_IDENTITY();
  INSERT INTO totalUnits(companyid, units, unitsType) VALUES (@userId,0,1);
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

  @PartNo BIGINT,
  @Description TEXT,
  @CompanyUserId INT,
  @ProcessOut BIGINT OUT,
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
  DECLARE @ProcessId As TABLE (id BIGINT)
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
      INSERT INTO commercialUsers( ntlmName , userName , userSurname , tcKimlikNo )
      OUTPUT INSERTED.id INTO @UserTable(id)
      VALUES(@ownerName , 'Bilinmiyor' , 'Bilinmiyor' , 0);
      Set @UserId = (Select id from @UserTable);
    END

  INSERT INTO process(productid, depotid, processType, companyid, units , unitsType  , description, partNo, ownerid, actionDate , companyUserid , price , priceType )
  OUTPUT INSERTED.id INTO @ProcessId(id)
  VALUES           (@ProductId,@DepotId,@ProcessType,@CompanyId,@Units , @UnitsType ,@Description,@PartNo, @UserId , @CurrTime , @CompanyUserId , @Price , @PriceType );

  Set @Uid = (Select id FROM totalProducts WHERE totalProducts.companyid = @CompanyId AND totalProducts.depotid = @DepotId AND totalProducts.partiNo = @PartNo AND totalProducts.productid = @ProductId)
  IF @Uid IS NULL
    INSERT  INTO totalProducts( companyid, depotid, productid, dateAdded, units, partiNo, unitType)
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
  IF EXISTS(SELECT companyid FROM totalUnits WHERE companyid = @CompanyId AND unitsType = @UnitsType)
    BEGIN
      UPDATE totalUnits SET units = units + @Units WHERE companyid = @CompanyId AND unitsType = @UnitsType
    END
  ELSE
    INSERT INTO totalUnits(companyid, units , unitsType ) VALUES (@CompanyId,@Units,@UnitsType);
    
    IF NOT EXISTS(SELECT * FROM partNoIndex WHERE companyid = @CompanyId AND partNoIndex.partNoid = @PartNo AND partNoIndex.productid = @ProductId)
      BEGIN
        INSERT INTO partNoIndex(companyid, partNoid, partNo, productid) 
                      VALUES (@CompanyId,@PartNo,CONVERT(varchar(32), @PartNo) , @ProductId );

          END
  Set @ProcessOut = (Select id FROM @ProcessId);
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
   
   @PartNo BIGINT,
   @Description TEXT,
   @CompanyUserId INT,
   @ProcessOut BIGINT OUT,
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
    DECLARE @ProcessId As TABLE (id INT);
    
    Set @Success = 0;
    
    
    SET @UserId = (SELECT id FROM commercialUsers WHERE ntlmName= @OwnerName );
    IF @UserId IS NULL
      BEGIN
        INSERT INTO commercialUsers( ntlmName , userName , userSurname , tcKimlikNo ) OUTPUT INSERTED.id INTO @UserTable(id) VALUES(@OwnerName , 'Bilinmiyor' , 'Bilinmiyor' , 0);
        Set @UserId = (Select id from @UserTable);
      END
      
    INSERT INTO process(depotid, processType, companyid, description, partNo, ownerid, actionDate, productid, companyUserid, price, priceType, units, unitsType)
               OUTPUT INSERTED.id INTO @ProcessId(id)
                VALUES (@DepotId ,  @ProcessType, @CompanyId , @Description , @PartNo , @UserId , @CurrTime , @ProductId , @CompanyUserId , @Price , @PriceType , @Units , @UnitsType );
    
--     depodaki totalBox dan cikar
--     totalProducts dan cikar
    UPDATE totalProducts SET units = units - @Units WHERE companyid = @CompanyId AND unitType = @UnitsType AND partiNo = @PartNo AND totalProducts.depotid = @DepotId AND totalProducts.productid = @DepotId;
    UPDATE totalUnits SET units = units - @Units WHERE companyid = @CompanyId AND unitsType = @UnitsType;
    
    
    DELETE totalProducts WHERE companyid = @CompanyId AND depotid = @DepotId AND partiNo = @PartNo AND units <= 0;
    
    Set @ProcessOut = (SELECT id FROM @ProcessId)
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
([companyid] ASC, [userName] ASC, [userSurname] ASC) 
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
CREATE INDEX [depotIdIndex] ON [dbo].[depots]
([depotid] ASC) 
GO

-- ----------------------------
-- Primary Key structure for table depots
-- ----------------------------
ALTER TABLE [dbo].[depots] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table partNoIndex
-- ----------------------------
CREATE CLUSTERED INDEX [partNoindex] ON [dbo].[partNoIndex]
([companyid] ASC, [productid] ASC, [partNoid] ASC) 
GO
CREATE INDEX [partNoTextIndex] ON [dbo].[partNoIndex]
([partNo] ASC) 
GO

-- ----------------------------
-- Primary Key structure for table partNoIndex
-- ----------------------------
ALTER TABLE [dbo].[partNoIndex] ADD PRIMARY KEY NONCLUSTERED ([id])
GO

-- ----------------------------
-- Indexes structure for table priceTypes
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table priceTypes
-- ----------------------------
ALTER TABLE [dbo].[priceTypes] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table process
-- ----------------------------
CREATE CLUSTERED INDEX [ProcessIndex] ON [dbo].[process]
([companyid] ASC, [depotid] ASC, [actionDate] ASC, [processType] ASC) 
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
([companyid] ASC, [depotid] ASC, [productid] ASC) 
GO
CREATE INDEX [TotalProductsIndex2] ON [dbo].[totalProducts]
([companyid] ASC, [dateAdded] DESC) 
GO
CREATE INDEX [TotalProductsIndex3] ON [dbo].[totalProducts]
([companyid] ASC, [partiNo] ASC) 
GO

-- ----------------------------
-- Primary Key structure for table totalProducts
-- ----------------------------
ALTER TABLE [dbo].[totalProducts] ADD PRIMARY KEY NONCLUSTERED ([id])
GO

-- ----------------------------
-- Indexes structure for table totalUnits
-- ----------------------------
CREATE INDEX [unitsTypes] ON [dbo].[totalUnits]
([units] ASC) 
GO

-- ----------------------------
-- Primary Key structure for table totalUnits
-- ----------------------------
ALTER TABLE [dbo].[totalUnits] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table transferTypes
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table transferTypes
-- ----------------------------
ALTER TABLE [dbo].[transferTypes] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table unitTypes
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table unitTypes
-- ----------------------------
ALTER TABLE [dbo].[unitTypes] ADD PRIMARY KEY ([id])
GO
