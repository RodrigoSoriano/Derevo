CREATE TABLE [dbo].[Usuario] (
    [id_usuario] INT        IDENTITY (1, 1) NOT NULL,
    [usuario]    NCHAR (10) NOT NULL,
    [contrasena] NCHAR (10) NOT NULL,
    [nombres]    NCHAR (10) NULL,
    [apellidos]  NCHAR (10) NULL,
    CONSTRAINT [PK_Usuario] PRIMARY KEY CLUSTERED ([id_usuario] ASC),
    CONSTRAINT [UQ__Usuario__9AFF8FC663790BEE] UNIQUE NONCLUSTERED ([usuario] ASC)
);

