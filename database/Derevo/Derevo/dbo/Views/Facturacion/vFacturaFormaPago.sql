CREATE VIEW [dbo].[vFacturaFormaPago]
	AS 
SELECT id_Factura_FormaPago AS ID,
	   nombre AS [Forma de Pago],
	   descripcion AS Descripcion
FROM [FacturaFormaPago]