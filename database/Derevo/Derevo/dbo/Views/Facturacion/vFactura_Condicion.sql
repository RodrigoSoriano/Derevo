CREATE VIEW [dbo].[vFactura_Condicion]
	AS 
SELECT id_Factura_Condicion AS ID,
	   nombre AS [Tipo de Factura],
	   descripcion AS Descripcion
FROM [Factura_Condicion]