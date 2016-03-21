/*
Navicat SQL Server Data Transfer

Source Server         : RemoteLANServer
Source Server Version : 120000
Source Host           : 10.3.115.60:1433
Source Database       : jdepo
Source Schema         : dbo

Target Server Type    : SQL Server
Target Server Version : 120000
File Encoding         : 65001

Date: 2016-03-17 18:04:15
*/


-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE [dbo].[customer]
GO
CREATE TABLE [dbo].[customer] (
[id] int NOT NULL IDENTITY(1,1) ,
[name] varchar(255) NULL ,
[surname] varchar(255) NULL ,
[unvan] varchar(255) NULL ,
[type] varchar(255) NULL ,
[phoneNumber] varchar(255) NULL ,
[address] varchar(255) NULL ,
[tcKimlik] bigint NULL ,
[vergiKimlik] varchar(255) NULL 
)


GO

-- ----------------------------
-- Records of customer
-- ----------------------------
SET IDENTITY_INSERT [dbo].[customer] ON
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
[info] varchar(255) NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[customerTypes]', RESEED, 4)
GO

-- ----------------------------
-- Records of customerTypes
-- ----------------------------
SET IDENTITY_INSERT [dbo].[customerTypes] ON
GO
INSERT INTO [dbo].[customerTypes] ([id], [customerType], [info]) VALUES (N'1', N'1', N'Şahıs')
GO
GO
INSERT INTO [dbo].[customerTypes] ([id], [customerType], [info]) VALUES (N'2', N'2', N'Firma')
GO
GO
INSERT INTO [dbo].[customerTypes] ([id], [customerType], [info]) VALUES (N'3', N'3', N'Esnaf')
GO
GO
INSERT INTO [dbo].[customerTypes] ([id], [customerType], [info]) VALUES (N'4', N'4', N'Diğer')
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
[depotId] int NULL ,
[depotName] varchar(255) NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[depots]', RESEED, 12)
GO

-- ----------------------------
-- Records of depots
-- ----------------------------
SET IDENTITY_INSERT [dbo].[depots] ON
GO
INSERT INTO [dbo].[depots] ([id], [depotId], [depotName]) VALUES (N'1', N'1', N'-18 1A ')
GO
GO
INSERT INTO [dbo].[depots] ([id], [depotId], [depotName]) VALUES (N'2', N'2', N'-18 1B')
GO
GO
INSERT INTO [dbo].[depots] ([id], [depotId], [depotName]) VALUES (N'3', N'3', N'-18 2
')
GO
GO
INSERT INTO [dbo].[depots] ([id], [depotId], [depotName]) VALUES (N'4', N'4', N'-18 3
')
GO
GO
INSERT INTO [dbo].[depots] ([id], [depotId], [depotName]) VALUES (N'5', N'5', N'Şok Odası 8
')
GO
GO
INSERT INTO [dbo].[depots] ([id], [depotId], [depotName]) VALUES (N'6', N'6', N'* TRIAL * TRI')
GO
GO
INSERT INTO [dbo].[depots] ([id], [depotId], [depotName]) VALUES (N'7', N'7', N'* TRIAL * TRIA')
GO
GO
INSERT INTO [dbo].[depots] ([id], [depotId], [depotName]) VALUES (N'8', N'8', N'Şok Odası 11
')
GO
GO
INSERT INTO [dbo].[depots] ([id], [depotId], [depotName]) VALUES (N'9', N'9', N'Şok Odası 12
')
GO
GO
INSERT INTO [dbo].[depots] ([id], [depotId], [depotName]) VALUES (N'10', N'10', N'Taze Muhafaza Çipura-Levrek
')
GO
GO
INSERT INTO [dbo].[depots] ([id], [depotId], [depotName]) VALUES (N'11', N'11', N'* TRIAL * TRIAL * TRI')
GO
GO
INSERT INTO [dbo].[depots] ([id], [depotId], [depotName]) VALUES (N'12', N'12', N'* TRIAL * TR')
GO
GO
SET IDENTITY_INSERT [dbo].[depots] OFF
GO

-- ----------------------------
-- Table structure for dummy
-- ----------------------------
DROP TABLE [dbo].[dummy]
GO
CREATE TABLE [dbo].[dummy] (
[id] int NOT NULL IDENTITY(1,1) ,
[name] varchar(255) NOT NULL ,
[tableIndex] int NOT NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[dummy]', RESEED, 24)
GO

-- ----------------------------
-- Records of dummy
-- ----------------------------
SET IDENTITY_INSERT [dbo].[dummy] ON
GO
INSERT INTO [dbo].[dummy] ([id], [name], [tableIndex]) VALUES (N'4', N'Kemalettin', N'0')
GO
GO
INSERT INTO [dbo].[dummy] ([id], [name], [tableIndex]) VALUES (N'5', N'Onur', N'1')
GO
GO
INSERT INTO [dbo].[dummy] ([id], [name], [tableIndex]) VALUES (N'6', N'Kadir', N'2')
GO
GO
INSERT INTO [dbo].[dummy] ([id], [name], [tableIndex]) VALUES (N'7', N'* TRIAL ', N'3')
GO
GO
INSERT INTO [dbo].[dummy] ([id], [name], [tableIndex]) VALUES (N'8', N'Melike', N'4')
GO
GO
INSERT INTO [dbo].[dummy] ([id], [name], [tableIndex]) VALUES (N'9', N'İsmail', N'5')
GO
GO
INSERT INTO [dbo].[dummy] ([id], [name], [tableIndex]) VALUES (N'10', N'Aslan', N'6')
GO
GO
INSERT INTO [dbo].[dummy] ([id], [name], [tableIndex]) VALUES (N'11', N'Feyyaz', N'7')
GO
GO
INSERT INTO [dbo].[dummy] ([id], [name], [tableIndex]) VALUES (N'12', N'Mehmet', N'8')
GO
GO
INSERT INTO [dbo].[dummy] ([id], [name], [tableIndex]) VALUES (N'13', N'Merve', N'9')
GO
GO
INSERT INTO [dbo].[dummy] ([id], [name], [tableIndex]) VALUES (N'14', N'Hasan', N'10')
GO
GO
INSERT INTO [dbo].[dummy] ([id], [name], [tableIndex]) VALUES (N'15', N'Gökay', N'11')
GO
GO
INSERT INTO [dbo].[dummy] ([id], [name], [tableIndex]) VALUES (N'16', N'Feride', N'12')
GO
GO
INSERT INTO [dbo].[dummy] ([id], [name], [tableIndex]) VALUES (N'17', N'Leyla', N'13')
GO
GO
INSERT INTO [dbo].[dummy] ([id], [name], [tableIndex]) VALUES (N'18', N'Puffy', N'14')
GO
GO
INSERT INTO [dbo].[dummy] ([id], [name], [tableIndex]) VALUES (N'19', N'Ceyda', N'15')
GO
GO
INSERT INTO [dbo].[dummy] ([id], [name], [tableIndex]) VALUES (N'20', N'Şule', N'16')
GO
GO
INSERT INTO [dbo].[dummy] ([id], [name], [tableIndex]) VALUES (N'21', N'Kerim', N'17')
GO
GO
INSERT INTO [dbo].[dummy] ([id], [name], [tableIndex]) VALUES (N'22', N'Aycan', N'18')
GO
GO
INSERT INTO [dbo].[dummy] ([id], [name], [tableIndex]) VALUES (N'23', N'Gökhan', N'19')
GO
GO
INSERT INTO [dbo].[dummy] ([id], [name], [tableIndex]) VALUES (N'24', N'Akın', N'20')
GO
GO
SET IDENTITY_INSERT [dbo].[dummy] OFF
GO

-- ----------------------------
-- Table structure for process
-- ----------------------------
DROP TABLE [dbo].[process]
GO
CREATE TABLE [dbo].[process] (
[id] int NOT NULL IDENTITY(1,1) ,
[productId] int NULL ,
[depotId] int NULL ,
[processType] int NULL ,
[companyId] int NULL ,
[processAmount] int NULL 
)


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
[productName] varchar(255) NULL ,
[measureType] varchar(255) NULL 
)


GO

-- ----------------------------
-- Records of products
-- ----------------------------
SET IDENTITY_INSERT [dbo].[products] ON
GO
SET IDENTITY_INSERT [dbo].[products] OFF
GO

-- ----------------------------
-- Table structure for totalProducts
-- ----------------------------
DROP TABLE [dbo].[totalProducts]
GO
CREATE TABLE [dbo].[totalProducts] (
[productId] int NOT NULL ,
[companyId] int NULL ,
[depotId] int NULL ,
[totalBox] int NULL 
)


GO

-- ----------------------------
-- Records of totalProducts
-- ----------------------------

-- ----------------------------
-- Table structure for world_cities_free
-- ----------------------------
DROP TABLE [dbo].[world_cities_free]
GO
CREATE TABLE [dbo].[world_cities_free] (
[cc_fips] varchar(2) NULL ,
[full_name_nd] varchar(200) NULL 
)


GO

-- ----------------------------
-- Records of world_cities_free
-- ----------------------------

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
-- Indexes structure for table dummy
-- ----------------------------
CREATE UNIQUE INDEX [tableIndexDummy] ON [dbo].[dummy]
([tableIndex] ASC) 
WITH (IGNORE_DUP_KEY = ON)
GO

-- ----------------------------
-- Primary Key structure for table dummy
-- ----------------------------
ALTER TABLE [dbo].[dummy] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table process
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table process
-- ----------------------------
ALTER TABLE [dbo].[process] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table products
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table products
-- ----------------------------
ALTER TABLE [dbo].[products] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table totalProducts
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table totalProducts
-- ----------------------------
ALTER TABLE [dbo].[totalProducts] ADD PRIMARY KEY ([productId])
GO

-- ----------------------------
-- Indexes structure for table world_cities_free
-- ----------------------------
CREATE INDEX [idx_cc_fips1] ON [dbo].[world_cities_free]
([cc_fips] ASC) 
GO
