CREATE PROCEDURE [dbo].[GuardarNacionalidad]
	@id_nacionalidad int,
	@nombre varchar(50),
	@descripcion varchar(100)
AS
	if (@id_nacionalidad <> 0)
	begin
		update Nacionalidad set nombre = @nombre, descripcion = @descripcion 
		where id_nacionalidad = @id_nacionalidad
	end
	else
	begin
		insert into Nacionalidad(nombre, descripcion) values(@nombre, @descripcion)
	end

	select top 0 * from Nacionalidad