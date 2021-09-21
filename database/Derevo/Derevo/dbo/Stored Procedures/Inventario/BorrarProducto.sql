CREATE PROCEDURE [dbo].[BorrarProducto]
	@id_producto int
AS
	delete Producto where id_producto = @id_producto

	return select top 0 * from Producto