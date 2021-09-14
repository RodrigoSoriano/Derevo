CREATE TABLE [dbo].[Empleado] (
    [id_empleado] INT             IDENTITY (1, 1) NOT NULL,
    [cedula]      VARCHAR (50)    NULL,
    [nombres]     VARCHAR (50)    NOT NULL,
    [apellidos]   VARCHAR (50)    NULL,
    [telefono]    VARCHAR (50)    NULL,
    [fecha]       DATE            NULL,
    [sueldo_base] DECIMAL (18, 2) NULL,
    CONSTRAINT [PK_Empleado] PRIMARY KEY CLUSTERED ([id_empleado] ASC)
);

