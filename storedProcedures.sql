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



















ALTER PROCEDURE [dbo].[DepotManager_productOut]
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