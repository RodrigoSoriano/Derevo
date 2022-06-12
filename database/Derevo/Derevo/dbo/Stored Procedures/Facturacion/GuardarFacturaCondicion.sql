CREATE PROCEDURE [dbo].[GuardarFacturaCondicion]
	@id int,
	@nombre varchar(50),
	@descripcion varchar(100)
AS
	if (@id <> 0)
	begin
		update FacturaCondicion set nombre = @nombre, descripcion = @descripcion 
		where id_Factura_Condicion = @id
	end
	else
	begin
		insert into FacturaCondicion(nombre, descripcion) values(@nombre, @descripcion)
	end

	select top 0 * from FacturaCondicion