CREATE TABLE [dbo].[Empleado] (
    [id_empleado] INT             IDENTITY (1, 1) NOT NULL,
    [cedula]      VARCHAR (50)    NULL,
    [nombres]     VARCHAR (50)    NOT NULL,
    [apellidos]   VARCHAR (50)    NULL,
    [telefono]    VARCHAR (50)    NULL,
    [fecha]       DATE            NULL,
    [sueldo_base] DECIMAL (18, 2) NULL,
    [id_departamento] INT NULL, 
    CONSTRAINT [PK_Empleado] PRIMARY KEY CLUSTERED ([id_empleado] ASC), 
    CONSTRAINT [FK_Empleado_ToDepartamento] FOREIGN KEY ([id_departamento]) REFERENCES [Departamento]([id_departamento])
);

