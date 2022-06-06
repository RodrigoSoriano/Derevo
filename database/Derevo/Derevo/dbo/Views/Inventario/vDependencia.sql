CREATE VIEW [dbo].[vDependencia]
	AS SELECT  
		Producto.id_producto as ID,
		ClasificacionProducto.nombre as Clasificación,
		Producto.descripcion AS Producto,
		Dependencia.cantidad as Cantidad,
		Dependencia.id_producto
	FROM Dependencia
	LEFT JOIN Producto ON Producto.id_producto = Dependencia.id_productoDependencia
	left join ClasificacionProducto on Producto.id_clasificacionProducto = ClasificacionProducto.Id_clasificacionProducto
