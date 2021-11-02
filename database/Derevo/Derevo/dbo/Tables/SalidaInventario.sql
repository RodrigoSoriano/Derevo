CREATE TABLE [dbo].[SalidaInventario] (
    [id_salida]      INT IDENTITY (1, 1) NOT NULL,
    [id_producto]    INT                 NOT NULL,
    [cantidad]       INT                 NULL,
    [razon]          VARCHAR(100)        NULL,
    [fecha]          DATE                NULL
    CONSTRAINT [PK_SalidaInventario] PRIMARY KEY CLUSTERED ([id_salida] ASC), 
    CONSTRAINT [FK_SalidaInventario_ToProducto] FOREIGN KEY ([id_producto]) REFERENCES [Producto]([id_producto]) 
);