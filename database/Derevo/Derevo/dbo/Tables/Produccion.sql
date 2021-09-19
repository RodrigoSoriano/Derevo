CREATE TABLE [dbo].[Produccion] (
    [id_produccion] INT NOT NULL IDENTITY, 
    [id_empleado] INT  NOT NULL,
    [fecha]       DATE NOT NULL, 
    [nota] VARCHAR(100) NULL, 
    CONSTRAINT [PK_Produccion] PRIMARY KEY ([id_produccion]), 
    CONSTRAINT [FK_Produccion_Empleado] FOREIGN KEY ([id_empleado]) REFERENCES [Empleado]([id_empleado])
);

