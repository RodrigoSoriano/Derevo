CREATE VIEW [dbo].[vClasificacionProducto]
	AS SELECT clasificacionproducto.id_clasificacionProducto as ID, nombre as Nombre, ClasificacionProducto.descripcion as Descripcion, isnull(count(Producto.id_producto), 0) as 'Cantidad Productos'
	FROM [ClasificacionProducto]
	left join Producto on ClasificacionProducto.Id_clasificacionProducto = Producto.id_clasificacionProducto
	group by clasificacionproducto.id_clasificacionProducto, nombre, ClasificacionProducto.descripcion