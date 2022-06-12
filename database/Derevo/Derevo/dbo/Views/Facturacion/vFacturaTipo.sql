CREATE VIEW [dbo].[vFacturaTipo]
	AS 
SELECT id_Factura_Tipo AS ID,
	   nombre AS [Tipo de Factura],
	   descripcion AS Descripcion
FROM [FacturaTipo]