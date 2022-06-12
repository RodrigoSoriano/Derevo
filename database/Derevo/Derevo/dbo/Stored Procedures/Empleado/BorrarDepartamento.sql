CREATE PROCEDURE [dbo].[BorrarDepartamento]
	@id_departamento int
AS
	delete Departamento where id_departamento = @id_departamento;

	select top 0 * from Departamento