CREATE VIEW [dbo].[vSubProduccion]
	AS SELECT Producto.id_producto AS ID,
			  nombre AS Clasificación,
			  Producto.descripcion AS Producto,
			  cantidad AS Cantidad,
			  id_produccion
	FROM [SubProduccion]
	LEFT JOIN Producto ON SubProduccion.id_producto = Producto.id_producto
	left join ClasificacionProducto on Producto.id_clasificacionProducto = ClasificacionProducto.Id_clasificacionProducto
