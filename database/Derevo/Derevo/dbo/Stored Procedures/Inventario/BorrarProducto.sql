CREATE PROCEDURE [dbo].[BorrarProducto]
	@id_producto int
AS
	delete Dependencia where id_producto = @id_producto
	delete Producto where id_producto = @id_producto

	select top 0 * from Producto