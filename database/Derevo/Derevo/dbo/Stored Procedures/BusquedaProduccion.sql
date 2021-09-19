CREATE PROCEDURE [dbo].[BusquedaProduccion] @Busqueda VARCHAR(100)
AS
SELECT * FROM vProduccion
WHERE
ID like '%'+@Busqueda+'%' or 
Empleado like '%'+@Busqueda+'%' or 
Fecha like '%'+@Busqueda+'%' or
Nota like '%'+@Busqueda+'%'