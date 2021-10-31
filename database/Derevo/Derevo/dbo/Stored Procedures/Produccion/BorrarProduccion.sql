CREATE PROCEDURE [dbo].[BorrarProduccion]
	@id_produccion int
AS
	--DELETE SubProduccion WHERE id_produccion = @id_produccion
	DELETE Produccion WHERE id_produccion = @id_produccion

	SELECT TOP 0 * FROM Produccion