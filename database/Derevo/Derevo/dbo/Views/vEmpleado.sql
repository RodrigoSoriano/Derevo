CREATE VIEW [dbo].[vEmpleado]
	AS SELECT id_empleado as ID, cedula as Identificación, nombres as Nombres, apellidos as Apellidos, Departamento.nombre as Departamento
	FROM Empleado
	left join Departamento on Empleado.id_departamento = Departamento.id_departamento
