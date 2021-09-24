CREATE VIEW [dbo].[vInventario]
	AS SELECT id_producto AS ID, nombre AS Clasificacion, Producto.descripcion AS Descripcion, precio_venta as 'Precio de venta', existencia AS Existencia 
	FROM [Producto] left join ClasificacionProducto on Producto.id_clasificacionProducto = ClasificacionProducto.Id_clasificacionProducto
