CREATE TABLE [dbo].[Producto] (
    [id_producto]    INT             IDENTITY (1, 1) NOT NULL,
    [nombre]         VARCHAR (50)    NOT NULL,
    [descripcion]    VARCHAR (50)    NULL,
    [peso]           DECIMAL (18, 2) NULL,
    [mano_obra]      DECIMAL (18, 2) NULL,
    [existencia]     INT             NULL,
    [producto_final] BIT             NULL,
    [paga_fundidor]  BIT             NULL,
    CONSTRAINT [PK_Producto] PRIMARY KEY CLUSTERED ([id_producto] ASC)
);

