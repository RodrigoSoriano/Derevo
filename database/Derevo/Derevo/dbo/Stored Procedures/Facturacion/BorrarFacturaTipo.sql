CREATE PROCEDURE [dbo].[BorrarFacturaTipo]
	@id int
AS
	delete FacturaTipo where id_Factura_Tipo = @id;

	select top 0 * from FacturaTipo