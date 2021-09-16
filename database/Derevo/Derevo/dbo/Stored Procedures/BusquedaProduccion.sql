CREATE PROCEDURE [dbo].[BusquedaProduccion] @Busqueda VARCHAR(100)
AS
SELECT * FROM Produccion
WHERE
id_produccion like '%'+@Busqueda+'%' or 
id_empleado like '%'+@Busqueda+'%' or 
id_producto like '%'+@Busqueda+'%' or
cantidad like '%'+@Busqueda+'%' or
fecha like '%'+@Busqueda+'%' or 
fecha like '%'+@Busqueda+'%'