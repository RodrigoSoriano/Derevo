CREATE VIEW [dbo].[vInventario]
	AS SELECT id_producto AS ID, nombre AS Clasificacion, descripcion AS Descripcion, existencia AS Existencia FROM [Producto]
