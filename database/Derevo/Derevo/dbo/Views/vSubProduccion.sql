CREATE VIEW [dbo].[vSubProduccion]
	AS SELECT Producto.id_producto AS ID,
			  nombre AS Producto,
			  cantidad AS Cantidad
	FROM [SubProduccion]
	LEFT OUTER JOIN Producto ON SubProduccion.id_producto = Producto.id_producto
