CREATE VIEW [dbo].[vFacturaCondicion]
	AS 
SELECT id_Factura_Condicion AS ID,
	   nombre AS [Condicion Factura],
	   descripcion AS Descripcion
FROM [FacturaCondicion]