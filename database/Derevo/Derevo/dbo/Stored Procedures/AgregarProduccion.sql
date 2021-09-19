CREATE PROCEDURE [dbo].[AgregarProduccion]
	@id_produccion int,
	@id_producto int,
	@cantidad int
AS
	INSERT INTO SubProduccion(id_produccion, id_producto, cantidad)
		VALUES(@id_producto, @id_produccion, @cantidad)
