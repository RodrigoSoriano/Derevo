CREATE PROCEDURE [dbo].[GuardarClasificacionProducto]
	@id_clasificacionProducto int,
	@nombre varchar(50),
	@descripcion varchar(100)
AS
	if (@id_clasificacionProducto <> 0)
	begin
		update ClasificacionProducto set nombre = @nombre, descripcion = @descripcion 
		where Id_clasificacionProducto = @id_clasificacionProducto
	end
	else
	begin
		insert into ClasificacionProducto(nombre, descripcion) values(@nombre, @descripcion)
	end

	select top 0 * from ClasificacionProducto