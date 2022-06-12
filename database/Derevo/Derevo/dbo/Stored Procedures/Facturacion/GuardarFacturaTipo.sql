CREATE PROCEDURE [dbo].[GuardarFacturaTipo]
	@id int,
	@nombre varchar(50),
	@descripcion varchar(100)
AS
	if (@id <> 0)
	begin
		update FacturaTipo set nombre = @nombre, descripcion = @descripcion 
		where id_Factura_Tipo = @id
	end
	else
	begin
		insert into FacturaTipo(nombre, descripcion) values(@nombre, @descripcion)
	end

	select top 0 * from FacturaTipo