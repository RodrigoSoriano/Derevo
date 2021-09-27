CREATE TABLE [dbo].[Dependencia]
(
	[id_dependencia] INT IDENTITY (1, 1) NOT NULL , 
    [id_producto] INT NULL, 
    [id_productoDependencia] INT NULL, 
    [cantidad] INT NULL, 
    CONSTRAINT [PK_Dependencia] PRIMARY KEY ([id_dependencia]), 
    CONSTRAINT [FK_Dependencia_ToProducto] FOREIGN KEY ([id_producto]) REFERENCES [Producto]([id_producto]),
    CONSTRAINT [FK_Dependencia_ToProducto2] FOREIGN KEY ([id_productoDependencia]) REFERENCES [Producto]([id_producto])
)
