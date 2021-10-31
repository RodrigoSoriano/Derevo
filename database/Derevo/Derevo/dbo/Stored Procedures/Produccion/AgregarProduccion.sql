CREATE PROCEDURE [dbo].[AgregarProduccion]
	@id_produccion int,
	@id_producto int,
	@cantidad int
AS
	DECLARE @id int
	SELECT @id = id FROM SubProduccion WHERE id_produccion = @id_produccion AND id_producto = @id_producto

	IF (@id > 0)
	BEGIN
		UPDATE SubProduccion SET cantidad = (SELECT cantidad FROM SubProduccion WHERE id = @id) + @cantidad WHERE id = @id
	END
	ELSE
	BEGIN
		INSERT INTO SubProduccion(id_produccion, id_producto, cantidad)
						  VALUES(@id_produccion, @id_producto, @cantidad)
	END

	UPDATE Producto SET producidas = (SELECT producidas FROM Producto WHERE id_producto = @id_producto) + @cantidad WHERE id_producto = @id_producto

	SELECT TOP 0 * FROM SubProduccion