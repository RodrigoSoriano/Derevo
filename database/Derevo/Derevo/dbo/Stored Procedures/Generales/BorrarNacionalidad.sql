CREATE PROCEDURE [dbo].[BorrarNacionalidad]
	@id_nacionalidad int
AS
	delete Nacionalidad where id_nacionalidad = @id_nacionalidad;

	select top 0 * from Nacionalidad