CREATE PROCEDURE [dbo].[AperturarProduccion]
	@id_empleado int,
	@fecha date,
	@nota varchar(100)
AS
	INSERT INTO Produccion(id_empleado, fecha, nota)
				   VALUES(@id_empleado, @fecha, @nota)

	SELECT MAX(id_produccion) AS id_produccion FROM Produccion