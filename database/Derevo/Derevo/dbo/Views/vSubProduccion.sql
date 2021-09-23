CREATE VIEW [dbo].[vSubProduccion]
	AS SELECT Producto.id_producto AS ID,
			  nombre AS Clasificación,
			  descripcion AS Producto,
			  cantidad AS Cantidad,
			  id_produccion
	FROM [SubProduccion]
	LEFT OUTER JOIN Producto ON SubProduccion.id_producto = Producto.id_producto
