CREATE PROCEDURE [dbo].[BusquedaEmpleado] @Busqueda VARCHAR(100)
AS
SELECT * FROM Empleado
WHERE
id_empleado like '%'+@Busqueda+'%' or 
cedula like '%'+@Busqueda+'%' or 
nombres like '%'+@Busqueda+'%' or
apellidos like '%'+@Busqueda+'%' or
telefono like '%'+@Busqueda+'%' or 
fecha like '%'+@Busqueda+'%' or
sueldo_base like '%'+@Busqueda+'%'