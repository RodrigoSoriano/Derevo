CREATE VIEW [dbo].[vFactura_Tipo]
	AS 
SELECT id_Factura_Tipo AS ID,
	   nombre AS [Tipo de Factura],
	   descripcion AS Descripcion
FROM [Factura_Tipo]