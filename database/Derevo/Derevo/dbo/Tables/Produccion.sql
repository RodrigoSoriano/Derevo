CREATE TABLE [dbo].[Produccion] (
    [id_produccion] INT NOT NULL IDENTITY, 
    [id_empleado] INT  NOT NULL,
    [id_producto] INT  NOT NULL,
    [cantidad]    INT  NOT NULL,
    [fecha]       DATE NOT NULL,
    CONSTRAINT [FK_Produccion_Empleado] FOREIGN KEY ([id_empleado]) REFERENCES [dbo].[Empleado] ([id_empleado]),
    CONSTRAINT [FK_Produccion_Producto] FOREIGN KEY ([id_producto]) REFERENCES [dbo].[Producto] ([id_producto]), 
    CONSTRAINT [PK_Produccion] PRIMARY KEY ([id_produccion])
);

