CREATE PROCEDURE [dbo].[RemoverProduccion]
	@id_produccion int,
	@id_producto int
AS
	DECLARE @cantidad INT

	SELECT @cantidad = cantidad FROM SubProduccion WHERE id_produccion = @id_produccion AND 
									    id_producto = @id_producto

	DELETE SubProduccion WHERE id_produccion = @id_produccion AND 
							     id_producto = @id_producto

    UPDATE Producto SET existencia = (SELECT existencia FROM Producto WHERE id_producto = @id_producto) - @cantidad WHERE id_producto = @id_producto

	SELECT TOP 0 * FROM SubProduccion