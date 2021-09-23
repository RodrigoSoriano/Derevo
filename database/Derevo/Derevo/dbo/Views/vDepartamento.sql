CREATE VIEW [dbo].[vDepartamento]
	AS SELECT Departamento.nombre as Nombre, Departamento.descripcion as Descripcion, isnull(count(Empleado.nombres), 0) as 'Cantidad Registrados'
	FROM [Departamento] 
	left join Empleado on Departamento.id_departamento = Empleado.id_departamento
	group by Departamento.nombre, Departamento.descripcion
