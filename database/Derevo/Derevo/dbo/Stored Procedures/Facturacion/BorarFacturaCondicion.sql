CREATE PROCEDURE [dbo].[BorarFacturaCondicion]
	@id int
AS
	delete FacturaCondicion where id_Factura_Condicion = @id;

	select top 0 * from FacturaCondicion