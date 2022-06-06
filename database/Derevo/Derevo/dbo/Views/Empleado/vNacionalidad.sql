CREATE VIEW [dbo].[vNacionalidad]
	AS SELECT Nacionalidad.id_nacionalidad as ID, nombre as Nombre, descripcion as Descripción, isnull(count(Empleado.nombres), 0) as 'Cantidad Registrados'
	FROM [Nacionalidad]
	left join Empleado on Nacionalidad.id_nacionalidad = Empleado.id_nacionalidad
	group by Nacionalidad.id_nacionalidad, nombre, descripcion