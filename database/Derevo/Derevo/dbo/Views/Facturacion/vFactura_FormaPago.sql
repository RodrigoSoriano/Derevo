CREATE VIEW [dbo].[vFactura_FormaPago]
	AS 
SELECT id_Factura_FormaPago AS ID,
	   nombre AS [Tipo de Factura],
	   descripcion AS Descripcion
FROM [Factura_FormaPago]