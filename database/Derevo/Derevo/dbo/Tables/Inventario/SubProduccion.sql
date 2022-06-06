CREATE TABLE [dbo].[SubProduccion] (
    [id] INT NOT NULL IDENTITY, 
    [id_produccion] INT NOT NULL, 
    [id_producto] INT  NOT NULL,
    [cantidad]    INT  NOT NULL, 
    CONSTRAINT [PK_SubProduccion] PRIMARY KEY ([id]), 
    CONSTRAINT [FK_SubProduccion_Produccion] FOREIGN KEY ([id_produccion]) REFERENCES [Produccion]([id_produccion]), 
    CONSTRAINT [FK_SubProduccion_Producto] FOREIGN KEY ([id_producto]) REFERENCES [Producto]([id_producto]),
);

