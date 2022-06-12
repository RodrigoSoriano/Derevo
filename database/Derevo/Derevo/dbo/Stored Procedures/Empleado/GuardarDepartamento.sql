CREATE PROCEDURE [dbo].[GuardarDepartamento]
	@id_departamento int,
	@nombre varchar(50),
	@descripcion varchar(100)
AS
	if (@id_departamento <> 0)
	begin
		update Departamento set nombre = @nombre, descripcion = @descripcion 
		where id_departamento = @id_departamento
	end
	else
	begin
		insert into Departamento(nombre, descripcion) values(@nombre, @descripcion)
	end

	select top 0 * from Departamento