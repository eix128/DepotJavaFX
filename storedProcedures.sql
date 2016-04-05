ALTER PROCEDURE [dbo].[DepotManager_productIn]
(
  @CompanyId INT ,
  @ProductId INT,
  @DepotId  INT,

  @Units BIGINT,
  @UnitsType TINYINT,

  @Price BIGINT,
  @PriceType TINYINT,

  @PartNo INT,
  @Description NTEXT,

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
  COMMIT TRANSACTION
  Set @Success = 1;
  RETURN
  END TRY

  BEGIN CATCH
    Set @Success = 0;
    Set @Exception = ERROR_MESSAGE();
    ROLLBACK TRANSACTION
  END CATCH

END
GO



































ALTER PROCEDURE [dbo].[DepotManager_productOut]
(
  @CompanyId     INT,
  @DepotId       INT,
  @ProductId     INT,
  @Units         BIGINT,
  @UnitsType     TINYINT,
  @Price         BIGINT,
  @PriceType     TINYINT,
  @PartNo        INT,
  @CompanyUserId INT,
  @Description   NTEXT,
  @Success       INT OUT ,
  @Exception     NVARCHAR(255) OUT
)
AS
BEGIN
  BEGIN TRY
  BEGIN TRAN
  DECLARE @ProcessType TINYINT = 1;
  DECLARE @UserId AS INT;
  DECLARE @CurrTime AS DATETIME;
  DECLARE @ownerName AS VARCHAR(64);
  DECLARE @UserTable TABLE(id INT);
  DECLARE @Uid AS INT;

  SET @Success = 0;
  SET @ownerName = SYSTEM_USER;

  SET @UserId = (SELECT id
                 FROM commercialUsers
                 WHERE ntlmName = @ownerName);
  IF @UserId IS NULL
    BEGIN
      INSERT INTO commercialUsers (ntlmName, userName, userSurname, tcKimlikNo)
      OUTPUT INSERTED.id INTO @UserTable(id) VALUES (@ownerName, 'Bilinmiyor', 'Bilinmiyor', 0);
      SET @UserId = (SELECT id
                     FROM @UserTable);
    END


  INSERT INTO process (product, depot, processType, company, units, unitsType, description, partNo, owner, actionDate, companyUserId, price, priceType)
  VALUES (@ProductId, @DepotId, @ProcessType, @CompanyId, @Units, @UnitsType, @Description, @PartNo, @UserId, @CurrTime,@CompanyUserId, @Price, @PriceType);

  UPDATE totalProducts
  SET totalProducts.units = totalProducts.units - @Units
  WHERE
    totalProducts.company = @CompanyId AND
    totalProducts.depot = @DepotId AND
    totalProducts.partiNo = @PartNo AND
    totalProducts.product = @ProductId;

  UPDATE totalUnits
  SET units = units - @Units
  WHERE company = @CompanyId AND unitsType = @UnitsType


  DELETE FROM totalProducts WHERE @CompanyId = company AND product = @ProductId AND @PartNo = partiNo AND units <= 0;

  COMMIT TRAN
  SET @Success = 1;
  END TRY
  BEGIN CATCH
    ROLLBACK TRAN
    SET @Success = 0;
    Set @Exception = ERROR_MESSAGE();
  END CATCH
END
GO