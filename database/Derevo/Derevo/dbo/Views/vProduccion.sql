CREATE VIEW [dbo].[vProduccion]
	AS SELECT                 Produccion.id_produccion AS ID,
			     Empleado.nombres + ' ' + Empleado.apellidos AS Empleado,
			                          Produccion.fecha AS Fecha,
				                       Produccion.nota AS Nota
	   FROM [Produccion] INNER JOIN Empleado ON Produccion.id_empleado = Empleado.id_empleado