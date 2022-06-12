CREATE PROCEDURE [dbo].[BorrarFacturaFormaPago]
	@id int
AS
	delete FacturaFormaPago where id_Factura_FormaPago = @id;

	select top 0 * from FacturaFormaPago