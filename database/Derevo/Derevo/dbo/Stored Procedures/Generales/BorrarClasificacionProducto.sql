CREATE PROCEDURE [dbo].[BorrarClasificacionProducto]
	@id_clasificacionProducto int
AS
	delete ClasificacionProducto where Id_clasificacionProducto = @id_clasificacionProducto;

	select top 0 * from ClasificacionProducto