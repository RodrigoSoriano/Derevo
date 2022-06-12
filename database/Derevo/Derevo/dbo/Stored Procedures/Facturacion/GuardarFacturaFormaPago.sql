CREATE PROCEDURE [dbo].[GuardarFacturaFormaPago]
	@id int,
	@nombre varchar(50),
	@descripcion varchar(100)
AS
	if (@id <> 0)
	begin
		update FacturaFormaPago set nombre = @nombre, descripcion = @descripcion 
		where id_Factura_FormaPago = @id
	end
	else
	begin
		insert into FacturaFormaPago(nombre, descripcion) values(@nombre, @descripcion)
	end

	select top 0 * from FacturaFormaPago