CREATE VIEW [dbo].[vInventario]
	AS SELECT 
	id_producto AS ID, 
	nombre AS Clasificacion, 
	Producto.descripcion AS Descripcion, 
	precio_venta as 'Precio de venta', 
	existencia AS Existencia,
	(case when producto_final = 1 then 'SI' else 'NO' end) as 'Producto final'
	FROM [Producto] left join ClasificacionProducto on Producto.id_clasificacionProducto = ClasificacionProducto.Id_clasificacionProducto
