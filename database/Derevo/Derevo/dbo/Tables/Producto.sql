CREATE TABLE [dbo].[Producto] (
    [id_producto]    INT             IDENTITY (1, 1) NOT NULL,
    [id_clasificacionProducto]         INT    NOT NULL,
    [descripcion]    VARCHAR (100)    NULL,
    [peso]           DECIMAL (18, 2) NULL,
    [mano_obra]      DECIMAL (18, 2) NULL,
    [existencia]     INT             NULL,
    [producto_final] BIT             NULL,
    [paga_fundidor]  BIT             NULL,
    [precio_costo] DECIMAL(18, 2) NULL, 
    [precio_venta] DECIMAL(18, 2) NULL, 
    [dependencia] BIT NULL, 
    CONSTRAINT [PK_Producto] PRIMARY KEY CLUSTERED ([id_producto] ASC), 
    CONSTRAINT [FK_Producto_ToClasificacionProducto] FOREIGN KEY ([id_clasificacionProducto]) REFERENCES [ClasificacionProducto]([Id_clasificacionProducto])
);

