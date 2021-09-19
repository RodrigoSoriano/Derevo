CREATE PROCEDURE [dbo].[RemoverProduccion]
	@id_produccion int,
	@id_producto int
AS
	DELETE SubProduccion WHERE id_produccion = @id_produccion AND id_producto = @id_producto