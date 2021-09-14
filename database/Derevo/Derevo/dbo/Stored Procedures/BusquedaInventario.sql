CREATE PROCEDURE [dbo].[BusquedaInventario] @Busqueda VARCHAR(100)
AS
select * from Producto 
where
id_producto like '%'+@Busqueda+'%' or 
nombre like '%'+@Busqueda+'%' or
descripcion like '%'+@Busqueda+'%' or
peso like '%'+@Busqueda+'%' or 
mano_obra like '%'+@Busqueda+'%' or
--existencia like '%'+@Busqueda+'%'