CREATE VIEW [dbo].[vProduccion]
	AS SELECT                 Produccion.id_produccion AS ID,
		   Empleado.nombres + ' ' + Empleado.apellidos AS Empleado,
			                          Produccion.fecha AS Fecha,
				                       Produccion.nota AS Nota,
							   count(SubProduccion.id) AS 'Cantidad Producción'
	   FROM [Produccion] 
			INNER JOIN Empleado ON Produccion.id_empleado = Empleado.id_empleado
			LEFT JOIN SubProduccion ON Produccion.id_produccion = SubProduccion.id_produccion
	   GROUP BY Produccion.id_produccion, Empleado.nombres, Empleado.apellidos, Produccion.fecha, Produccion.nota