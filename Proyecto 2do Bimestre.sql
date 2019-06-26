create database DBNextSport2017013
go

use DBNextSport2017013
go

Create table Categorias
(
codigoCategoria int identity(1,1) not null primary key,
descripcion varchar(60)
)
go

Create table Marcas
(
codigoMarca int identity(1,1) not null primary key,
descripcion varchar(60)
)
go

Create table Tallas 
(
codigoTalla  int identity(1,1) not null primary key,
descripcion varchar(60)
)
go

Create table Productos
(
codigoProducto int identity(1,1) not null primary key,
descripcion varchar(60),
existencia int default 0,
precioUnitario decimal(10,2) default 0.00,
precioDocena decimal(10,2) default 0.00,
precioMayor decimal(10,2) default 0.00,
imagen varchar(60),
codigoCategoria int,
codigoMarca int,
codigoTalla int,
Constraint FK_Productos_Categorias foreign key (codigoCategoria) references Categorias,
Constraint FK_Productos_Marcas foreign key (codigoMarca) references Marcas,
Constraint FK_Productos_Tallas foreign key (codigoTalla) references Tallas
)
go

Create table Proveedores
(
codigoProveedor int identity(1,1) not null primary key,
contactoPrincipal varchar(60),
paginaWeb varchar(60),
direccion varchar(60),
nit varchar(60),
razonSocial varchar(60)
)
go

Create table TelefonoProveedores
(
codigoTelefonoProveedor int identity(1,1) not null primary key,
codigoProveedor int,
numero varchar(80),
descripcion varchar(60),
Constraint FK_TelefonoProveedores_Proveedores foreign key (codigoProveedor) references Proveedores
)
go

Create table EmailProveedores
(
codigoEmailProveedor int identity(1,1) not null primary key,
email varchar(80),
descripcion varchar(60),
codigoProveedor int,
Constraint FK_EmailProveedores_Proveedores foreign key (codigoProveedor) references Proveedores
)
go

Create table Compras
(
numerDocumento int identity(1,1) not null primary key,
descripcion varchar(80),
fecha date,
total decimal(10,2) default 0.00,
codigoProveedor int,
Constraint FK_Compras_Proveedores foreign key (codigoProveedor) references Proveedores
)
go

Create table DetalleCompras
(
codigoDetalleCompra int identity (1,1)not null primary key,
costoUnitario decimal(10,2) default 0.00,
subtotal decimal(10,2) default 0.00,
cantidad int,
codigoProducto int,
codigoProveedor int,
numeroDocumento int,
Constraint FK_detalleCompras_Productos foreign key (codigoProducto) references Productos,
Constraint FK_etalleCompras_Proveedores foreign key (codigoProveedor) references Proveedores,
Constraint FK_detalleCompras_Compras foreign key (numeroDocumento) references Compras
)
go

Create table Clientes
(
codigoCliente  int identity (1,1) primary key,
nombre varchar(60),
direccion varchar(80),
nit varchar(80)
)
go

Create table TelefonoClientes
(
codigoTelefonoCliente int identity (1,1) primary key,
numero varchar(60),
descripcion varchar(60),
codigoCliente int,
Constraint FK_telefonoClientes_Clientes foreign key (codigoCliente) references Clientes
)
go

Create table EmailClientes
(
codigoEmailCliente int identity (1,1) primary key,
email varchar(80),
descripcion varchar(60),
codigoCliente int,
Constraint FK_emailClientes_Clientes foreign key (codigoCliente) references Clientes
)
go

Create table Facturas
(
numeroFactura int identity (1,1) primary key,
estado varchar(60),
fecha date,
nit varchar(80),
total decimal(10,2)default 0.00,
codigoCliente int,
Constraint FK_Clientes_Clientes foreign key (codigoCliente) references Clientes
)
go

Create table DetalleFacturas
(
codigoDetalleFactura int identity (1,1) primary key,
precio decimal(10,2) default 0.00,
cantidad int,
numeroFactura int,
codigoProducto int,
Constraint FK_detalleFactura_Facturas foreign key (numeroFactura) references Facturas,
Constraint FK_detalleFactura_Productos foreign key (codigoProducto) references Productos
)
go

---------------------------------------------------- Procedimientos almacenados de Categorias -------------------------------------------------
----------------------------------------------------- Agregar Categorias ------------------------------------------------------------------

Create procedure sp_AgregarCategoria @descripcion varchar(60)
as
	Begin
		Insert into Categorias( Categorias.descripcion) values (@descripcion)
	End
go



----------------------------------------------------- Listar Categorias ------------------------------------------------------------------------

Create procedure sp_ListarCategoria 
as 
	Begin
		select Categorias.codigoCategoria,
			   Categorias.descripcion
			   from Categorias
	End

go

---------------------------------------------------- Eliminar Categorias -----------------------------------------------------------------------

Create procedure sp_EliminarCategoria @codigoCategoria int
as
	Begin
		delete from Categorias where Categorias.codigoCategoria = @codigoCategoria
	End
go

----------------------------------------------------- Actualizar Categorias --------------------------------------------------------------------------

Create procedure sp_ActualizarCategoria  @descripcion varchar(60),@codigoCategoria int
as
	Begin
		update Categorias
		set Categorias.descripcion = @descripcion
		where Categorias.codigoCategoria = @codigoCategoria
	End
go
------------------------------------------------------- Buscar Categorias ---------------------------------------------------------------------

Create procedure sp_BuscarCategoria @codigoCategoria int
as
	Begin
		Select *from Categorias where Categorias.codigoCategoria = @codigoCategoria
	End
go

---------------------------------------------------- Procedimientos almacenados de Marcas -------------------------------------------------
----------------------------------------------------- Agregar Marcas ------------------------------------------------------------------

Create procedure sp_AgregarMarca @descripcion varchar(60)
as
	Begin
		Insert into Marcas( Marcas.descripcion) values (@descripcion)
	End
go


---------------------------------------------------- Listar Marcas ------------------------------------------------------------------------

Create procedure sp_ListarMarca 
as 
	Begin
		select Marcas.codigoMarca,	
			   Marcas.descripcion
			   from Marcas
	End
go

---------------------------------------------------- Eliminar Marcas -----------------------------------------------------------------------

Create procedure sp_EliminarMarcas @codigoMarca int
as
	Begin
		delete from Marcas where Marcas.codigoMarca = @codigoMarca
	End
go

----------------------------------------------------- Actualizar Marcas --------------------------------------------------------------------------

Create procedure sp_ActualizarMarca @descripcion varchar(60),@codigoMarca int
as
	Begin
		update Marcas
		set Marcas.descripcion = @descripcion
		where Marcas.codigoMarca = @codigoMarca
	End
go
------------------------------------------------------- Buscar Marcas ---------------------------------------------------------------------

Create procedure sp_BuscarMarca @codigoMarca int
as
	Begin
		Select *from Marcas where Marcas.codigoMarca = @codigoMarca
	End
go
---------------------------------------------------- Procedimientos almacenados de Tallas -------------------------------------------------
----------------------------------------------------- Agregar Tallas ------------------------------------------------------------------

Create procedure sp_AgregarTalla @descripcion varchar(60)
as
	Begin
		Insert into Tallas(descripcion) values (@descripcion)
	End
go

----------------------------------------------------- Listar Tallas ------------------------------------------------------------------------

Create procedure sp_ListarTallas
as 
	Begin
		select Tallas.codigoTalla,	
			   Tallas.descripcion
			   from Tallas
	End
go

---------------------------------------------------- Eliminar Tallas -----------------------------------------------------------------------

Create procedure sp_EliminarTallas @codigoTalla int
as
	Begin
		delete from Tallas where Tallas.codigoTalla = @codigoTalla
	End
go

----------------------------------------------------- Actualizar Tallas --------------------------------------------------------------------------

Create procedure sp_ActualizarTalla @descripcion varchar(60),@codigoTalla int
as
	Begin
		update Tallas
		set Tallas.descripcion = @descripcion
		where Tallas.codigoTalla = @codigoTalla 
	End
go
------------------------------------------------------- Buscar Tallas ---------------------------------------------------------------------

Create procedure sp_BuscaTalla @codigoTalla int
as
	Begin
		Select *from Tallas where Tallas.codigoTalla = @codigoTalla
	End
go

---------------------------------------------------- Procedimientos almacenados de Productos -------------------------------------------------
----------------------------------------------------- Agregar Productos ------------------------------------------------------------------

Create  procedure sp_AgregarProducto @codigoCategoria int, @codigoMarca int, @codigoTalla int,@imagen varchar(60),@descripcion varchar(60)

as
	Begin
		Insert into Productos(codigoCategoria,codigoMarca,codigoTalla,imagen,descripcion) values 
		(@codigoCategoria,@codigoMarca,@codigoTalla,@imagen,@descripcion)
	End
go


----------------------------------------------------- Listar Productos ------------------------------------------------------------------------

Create procedure sp_ListarProductos 
as 
	Begin
		select Productos.codigoProducto,
			Productos.descripcion,
			Productos.existencia,
			Productos.precioUnitario,
			Productos.precioDocena,
			Productos.precioMayor,
			Productos.imagen,
			Productos.codigoCategoria,
			Categorias.descripcion as Categoria,
			Productos.codigoMarca,
			Marcas.descripcion as Marca,
			Productos.codigoTalla,
			Tallas.descripcion as Talla
			from Productos
			inner join Categorias on Productos.codigoCategoria = Categorias.codigoCategoria
			inner join Marcas on Productos.codigoMarca = Marcas.codigoMarca
			inner join Tallas on Productos.codigoTalla = Tallas.codigoTalla

			 
	End
go


---------------------------------------------------- Eliminar Productos -----------------------------------------------------------------------

Create procedure sp_EliminarProducto @codigoProducto int
as
	Begin
		delete from Productos where Productos.codigoProducto = @codigoProducto
	End
go

----------------------------------------------------- Actualizar Producos --------------------------------------------------------------------------

Create procedure sp_ActualizarProducto @descripcion varchar(60),@codigoProducto int,@imagen varchar(60),@codigoCategoria int,@codigoMarca int,
@codigoTalla int
as
	Begin
		update Productos set descripcion = @descripcion where codigoProducto = @codigoProducto
		update Productos set imagen = @imagen where codigoProducto = @codigoProducto
		update Productos set codigoCategoria = @codigoCategoria where codigoProducto = @codigoProducto
		update Productos set codigoMarca = @codigoMarca where codigoProducto = @codigoProducto
		update Productos set CodigoTalla = @codigoTalla where codigoProducto = @codigoProducto
		
		
	End
go



------------------------------------------------------- Buscar Productos ---------------------------------------------------------------------

Create procedure sp_BuscarProducto @codigoProducto int
as
	Begin
		Select *from Productos where Productos.codigoProducto = @codigoProducto
	End
go

---------------------------------------------------- Procedimientos almacenados de Proveedores -------------------------------------------------
----------------------------------------------------- Agregar Proveedores ------------------------------------------------------------------

Create procedure sp_AgregarProveedor @contactoPrincipal varchar(60),@paginaWeb varchar(60),@direccion varchar(60),@nit varchar(60),@razonSocial varchar(60)
as 	
	Begin
		Insert into Proveedores(contactoPrincipal,paginaWeb,direccion,nit,razonSocial) values 
		(@contactoPrincipal,@paginaWeb,@direccion,@nit,@razonSocial)
	End
go



----------------------------------------------------- Listar Proveedores ------------------------------------------------------------------------

Create procedure sp_ListarProveedores 
as 
	Begin
		select Proveedores.codigoProveedor,
			Proveedores.contactoPrincipal,
			Proveedores.paginaWeb,
			Proveedores.direccion,
			Proveedores.nit,
			Proveedores.razonSocial
			   from Proveedores
	End
go
---------------------------------------------------- Eliminar Proveedores -----------------------------------------------------------------------

Create procedure sp_EliminarProveedor @codigoProveedor int
as
	Begin
		delete from Proveedores where Proveedores.codigoProveedor = @codigoProveedor
	End
go

----------------------------------------------------- Actualizar Proveedores --------------------------------------------------------------------------

Create procedure sp_ActualizarProveedor @contactoPrincipal varchar(60),@paginaWeb varchar(60),@direccion varchar(60),@nit varchar(80),@razonSocial varchar(60),@codigoProveedor int
as
	Begin
		update Proveedores set Proveedores.contactoPrincipal = @contactoPrincipal where Proveedores.codigoProveedor = @codigoProveedor
		update Proveedores set Proveedores.paginaWeb = @paginaWeb where Proveedores.codigoProveedor = @codigoProveedor
		update Proveedores set Proveedores.direccion = @direccion where Proveedores.codigoProveedor = @codigoProveedor
		update Proveedores set Proveedores.nit = @nit where Proveedores.codigoProveedor = @codigoProveedor
		update Proveedores set Proveedores.razonSocial = @razonSocial where Proveedores.codigoProveedor = @codigoProveedor
		
		
	End
go

------------------------------------------------------- Buscar Proveedores ---------------------------------------------------------------------

Create procedure sp_BuscarProveedor @codigoProveedor int
as
	Begin
		Select *from Proveedores where Proveedores.codigoProveedor = @codigoProveedor
	End
go

---------------------------------------------------- Procedimientos almacenados de TelefonoProveedores -------------------------------------------------
----------------------------------------------------- Agregar TelefonoProveedores ------------------------------------------------------------------

Create procedure sp_AgregarTelefonoProveedor @numero varchar(80),@descripcion varchar(60),@codigoProveedor int
as 	
	Begin
		Insert into TelefonoProveedores(numero,descripcion,codigoProveedor) values 
		(@numero,@descripcion,@codigoProveedor)
	End
go
----------------------------------------------------- Listar TelefonoProveedores ------------------------------------------------------------------------

Create procedure sp_ListarTelefonoProveedores 
as 
	Begin
		select  TelefonoProveedores.codigoTelefonoProveedor,
				TelefonoProveedores.numero,
				TelefonoProveedores.descripcion,
				TelefonoProveedores.codigoProveedor
			   from TelefonoProveedores
	End
go
---------------------------------------------------- Eliminar TelefonoProveedores -----------------------------------------------------------------------

Create procedure sp_EliminarTelefonoProveedor @codigoTelefonoProveedor int
as
	Begin
		delete from TelefonoProveedores where TelefonoProveedores.codigoTelefonoProveedor = @codigoTelefonoProveedor
	End
go

----------------------------------------------------- Actualizar TelefonoProveedores --------------------------------------------------------------------------

Create procedure sp_ActualizarTelefonoProveedor @numero varchar(80),@codigoTelefonoProveedor int
as
	Begin
		update TelefonoProveedores
		set TelefonoProveedores.numero = @numero
		where TelefonoProveedores.codigoTelefonoProveedor = @codigoTelefonoProveedor
	End
go
------------------------------------------------------- Buscar TelefonoProveedores ---------------------------------------------------------------------

Create procedure sp_BuscarTelefonoProveedor @codigoTelefonoProveedor int
as
	Begin
		Select *from TelefonoProveedores where TelefonoProveedores.codigoTelefonoProveedor = @codigoTelefonoProveedor
	End
go

---------------------------------------------------- Procedimientos almacenados de EmailProveedores -------------------------------------------------
----------------------------------------------------- Agregar EmailProveedores ------------------------------------------------------------------

Create procedure sp_AgregarEmailProveedor @email varchar(60),@descripcion varchar(60),@codigoProveedor int
as 	
	Begin
		Insert into EmailProveedores(email,descripcion,codigoProveedor) values 
		(@email,@descripcion,@codigoProveedor)
	End
go
----------------------------------------------------- Listar EmailProveedores ------------------------------------------------------------------------

Create procedure sp_ListarEmailProveedores 
as 
	Begin
		select  EmailProveedores.codigoEmailProveedor,
				EmailProveedores.email,
				EmailProveedores.descripcion,
				EmailProveedores.codigoProveedor
			   from EmailProveedores
	End
go
---------------------------------------------------- Eliminar EmailProveedores -----------------------------------------------------------------------

Create procedure sp_EliminarEmailProveedor @codigoEmailProveedor int
as
	Begin
		delete from EmailProveedores where EmailProveedores.codigoEmailProveedor = @codigoEmailProveedor
	End
go

----------------------------------------------------- Actualizar EmailProveedores --------------------------------------------------------------------------

Create procedure sp_ActualizarEmailProveedor @email varchar(60),@codigoEmailProveedor int
as
	Begin
		update EmailProveedores
		set EmailProveedores.email = @email
		where EmailProveedores.codigoEmailProveedor = @codigoEmailProveedor
	End
go
------------------------------------------------------- Buscar EmailProvvedores ---------------------------------------------------------------------

Create procedure sp_BuscarEmailProveedor @codigoEmailProveedor int
as
	Begin
		Select *from EmailProveedores where EmailProveedores.codigoEmailProveedor = @codigoEmailProveedor
	End
go

---------------------------------------------------- Procedimientos almacenados de Compras-------------------------------------------------
----------------------------------------------------- Agregar Compras ------------------------------------------------------------------

Create procedure sp_AgregarCompra @descripcion varchar(60),@fecha date,@total decimal(10,2),@codigoProveedor int
as 	
	Begin
		Insert Compras(descripcion,fecha,total,codigoProveedor) values 
		(@descripcion,@fecha,@total,@codigoProveedor)
	End
go
----------------------------------------------------- Listar Compras ------------------------------------------------------------------------

Create procedure sp_ListarCompras 
as 
	Begin
		select  Compras.numerDocumento,
				Compras.descripcion,
				Compras.fecha,
				Compras.total,
				Compras.codigoProveedor
			   from Compras
	End
go
---------------------------------------------------- Eliminar Compras -----------------------------------------------------------------------

Create procedure sp_EliminarCompra @NumeroDocumento int
as
	Begin
		delete from Compras where Compras.numerDocumento = @numeroDocumento
	End
go

----------------------------------------------------- Actualizar Compras --------------------------------------------------------------------------

Create procedure sp_ActualizarCompra @total decimal(10,2),@numeroDocumento int
as
	Begin
		update Compras
		set Compras.total = @total
		where Compras.numerDocumento = @numeroDocumento
	End
go
------------------------------------------------------- Buscar Compras ---------------------------------------------------------------------

Create procedure sp_BuscarCompra @numeroDocumento int
as
	Begin
		Select *from Compras where Compras.numerDocumento = @numeroDocumento
	End
go

---------------------------------------------------- Procedimientos almacenados de DetalleCompras-------------------------------------------------
----------------------------------------------------- Agregar DetalleCompras ------------------------------------------------------------------

Create procedure sp_AgregarDetalleCompra @costoUnitario decimal(10,2),@subtotal decimal(10,2),@cantidad int,@codigoProducto int,
@codigoProveedor int, @numeroDocumento int
as 	
	Begin
		Insert DetalleCompras(costoUnitario,subtotal,cantidad,codigoProducto,codigoProveedor,numeroDocumento) values 
		(@costoUnitario,@subtotal,@cantidad,@codigoProducto,@codigoProveedor,@numeroDocumento)
	End
go
----------------------------------------------------- Listar DetalleCompras ------------------------------------------------------------------------

Create procedure sp_ListarDetalleCompras 
as 
	Begin
		select  DetalleCompras.codigoDetalleCompra,
				DetalleCompras.costoUnitario,
				DetalleCompras.subtotal,
				DetalleCompras.cantidad,
				DetalleCompras.codigoProducto,
				DetalleCompras.codigoProveedor,
				DetalleCompras.numeroDocumento
			    from DetalleCompras
	End
go
---------------------------------------------------- Eliminar	DetalleCompras -----------------------------------------------------------------------

Create procedure sp_EliminarDetalleCompras @codigoDetalleCompra int
as
	Begin
		delete from DetalleCompras where DetalleCompras.codigoDetalleCompra = @codigoDetalleCompra
	End
go

----------------------------------------------------- Actualizar DetalleCompras --------------------------------------------------------------------------

Create procedure sp_ActualizarDetalleCompra @subtotal decimal(10,2),@codigoDetalleCompra int
as
	Begin
		update DetalleCompras
		set DetalleCompras.subtotal = @subtotal
		where DetalleCompras.codigoDetalleCompra = @codigoDetalleCompra
	End
go
------------------------------------------------------- Buscar DetalleCompras ---------------------------------------------------------------------

Create procedure sp_BuscarDetalleCompra @codigoDetalleCompra int
as
	Begin
		Select *from DetalleCompras where DetalleCompras.codigoDetalleCompra = @codigoDetalleCompra
	End
go

---------------------------------------------------- Procedimientos almacenados de Clientes-------------------------------------------------
----------------------------------------------------- Agregar Clientes ------------------------------------------------------------------

Create procedure sp_AgregarCliente @nombre varchar(60),@direccion varchar(80),@nit varchar(80)
as 	
	Begin
		Insert Clientes(nombre,direccion,nit) values 
		(@nombre,@direccion,@nit)
	End
go
----------------------------------------------------- Listar Clientes ------------------------------------------------------------------------

Create procedure sp_ListarClientes
as 
	Begin
		select  Clientes.codigoCliente,
				Clientes.nombre,
				Clientes.direccion,
				Clientes.nit
			    from Clientes
	End
go
---------------------------------------------------- Eliminar	Clientes -----------------------------------------------------------------------

Create procedure sp_EliminarCliente @codigoCliente int
as
	Begin
		delete from Clientes where Clientes.codigoCliente = @codigoCliente
	End
go

----------------------------------------------------- Actualizar Clientes --------------------------------------------------------------------------

Create procedure sp_ActualizarCliente @nombre varchar (60),@direccion varchar(60),@nit varchar(80),@codigoCliente int
as
	Begin
		update Clientes set Clientes.nombre = @nombre where Clientes.codigoCliente = @codigoCliente
		update Clientes set Clientes.direccion = @direccion where Clientes.codigoCliente = @codigoCliente
		update Clientes set Clientes.nit = @nit where Clientes.codigoCliente = @codigoCliente
		
		
	End
go
------------------------------------------------------- Buscar Clientes ---------------------------------------------------------------------

Create procedure sp_BuscarCliente @codigoCliente int
as
	Begin
		Select *from Clientes where Clientes.codigoCliente = @codigoCliente
	End
go

---------------------------------------------------- Procedimientos almacenados de TelefonoClientes-------------------------------------------------
----------------------------------------------------- Agregar TelefonoClientes ------------------------------------------------------------------

Create procedure sp_AgregarTelefonoClientes @numero varchar(80),@descripcion varchar(80),@codigoCLiente int
as 	
	Begin
		Insert TelefonoClientes(numero,descripcion,codigoCliente) values 
		(@numero,@descripcion,@codigoCLiente)
	End
go
----------------------------------------------------- Listar TelefonoClientes ------------------------------------------------------------------------

Create procedure sp_ListarTelefonoClientes
as 
	Begin
		select  TelefonoClientes.codigoTelefonoCliente,
				TelefonoClientes.numero,
				TelefonoClientes.descripcion,
				TelefonoClientes.codigoCliente
			    from TelefonoClientes
	End
go
---------------------------------------------------- Eliminar	TelefonoClientes -----------------------------------------------------------------------

Create procedure sp_EliminarTelefonoClientes @codigoTelefonoCliente int
as
	Begin
		delete from TelefonoClientes where TelefonoClientes.codigoTelefonoCliente = @codigoTelefonoCliente
	End
go

----------------------------------------------------- Actualizar TelefonoClientes --------------------------------------------------------------------------

Create procedure sp_ActualizarTelefonoClientes @numero varchar(80),@codigoTelefonoCliente int
as
	Begin
		update TelefonoClientes
		set TelefonoClientes.numero = @numero
		where TelefonoClientes.codigoTelefonoCliente = @codigoTelefonoCliente
	End
go
------------------------------------------------------- Buscar TelefonoClientes ---------------------------------------------------------------------

Create procedure sp_BuscarTelefonoCliente @codigoTelefonoCliente int
as
	Begin
		Select *from TelefonoClientes where TelefonoClientes.codigoTelefonoCliente = @codigoTelefonoCliente
	End
go

---------------------------------------------------- Procedimientos almacenados de EmailClientes-------------------------------------------------
----------------------------------------------------- Agregar EmailClientes ------------------------------------------------------------------

Create procedure sp_AgregarEmailCliente @email varchar(80),@descripcion varchar(80),@codigoCliente int
as 	
	Begin
		Insert EmailClientes(email,descripcion,codigoCliente) values 
		(@email,@descripcion,@codigoCliente)
	End
go
----------------------------------------------------- Listar EmailClientes ------------------------------------------------------------------------

Create procedure sp_ListarEmailClientes
as 
	Begin
		select  EmailClientes.codigoEmailCliente,
				EmailClientes.email,
				EmailClientes.descripcion,
				EmailClientes.codigoCliente
			    from EmailClientes
	End
go
---------------------------------------------------- Eliminar EmailClientes -----------------------------------------------------------------------

Create procedure sp_EliminarEmailCliente @codigoEmailCliente int
as
	Begin
		delete from EmailClientes where EmailClientes.codigoEmailCliente = @codigoEmailCliente
	End
go

----------------------------------------------------- Actualizar EmailClientes --------------------------------------------------------------------------

Create procedure sp_ActualizarEmailCliente @email varchar(80),@codigoEmailCliente int
as
	Begin
		update EmailClientes
		set EmailClientes.email = @email
		where EmailClientes.codigoEmailCliente = @codigoEmailCliente
	End
go
------------------------------------------------------- Buscar EmailClientes ---------------------------------------------------------------------

Create procedure sp_BuscarEmailCliente @codigoEmailCliente int
as
	Begin
		Select *from EmailClientes where EmailClientes.codigoEmailCliente = @codigoEmailCliente
	End
go

---------------------------------------------------- Procedimientos almacenados de Facturas-------------------------------------------------
----------------------------------------------------- Agregar Facturas ------------------------------------------------------------------

Create procedure sp_AgregarFactura @estado varchar(60),@total decimal(10,2),@fecha date,@nit varchar(80),@codigoCliente int
as
	Begin
		Insert Facturas(estado,total,fecha,nit,codigoCliente) values 
		(@estado,@total,@fecha,@nit,@codigoCliente)
	End
go
----------------------------------------------------- Listar Facturas ------------------------------------------------------------------------

Create procedure sp_ListarFacturas
as 
	Begin
		select  Facturas.numeroFactura,
				Facturas.estado,
				Facturas.total,
				Facturas.fecha,
				Facturas.nit,
				Facturas.codigoCliente
			    from Facturas
	End
go
---------------------------------------------------- Eliminar Facturas -----------------------------------------------------------------------

Create procedure sp_EliminarFactura @numeroFactura int
as
	Begin
		delete from Facturas where Facturas.numeroFactura = @numeroFactura
	End
go

----------------------------------------------------- Actualizar Facturas --------------------------------------------------------------------------
Create procedure sp_ActualizarFactura @total decimal(10,2),@numeroFactura int
as
	Begin
		update Facturas
		set Facturas.total = @total
		where Facturas.numeroFactura = @numeroFactura
	End
go
------------------------------------------------------- Buscar Facturas ---------------------------------------------------------------------

Create procedure sp_BuscarFactura @numeroFactura int
as
	Begin
		Select *from Facturas where Facturas.numeroFactura = @numeroFactura
	End
go

---------------------------------------------------- Procedimientos almacenados de DetalleFactura-------------------------------------------------
----------------------------------------------------- Agregar DetalleFactura ------------------------------------------------------------------

Create procedure sp_AgregarDetalleFactura @precio decimal(10,2),@cantidad decimal(10,2),@numeroFactura int,@codigoProducto int
as
	Begin
		Insert DetalleFacturas(precio,cantidad,numeroFactura,codigoProducto) values 
		(@precio,@cantidad,@numeroFactura,@codigoProducto)
	End
go
----------------------------------------------------- Listar DetalleFacturas ------------------------------------------------------------------------

Create procedure sp_ListarDetalleFacturas
as 
	Begin
		select  DetalleFacturas.codigoDetalleFactura,
				DetalleFacturas.precio,
				DetalleFacturas.cantidad,
				DetalleFacturas.numeroFactura,
				DetalleFacturas.codigoProducto
			    from DetalleFacturas
	End
go
---------------------------------------------------- Eliminar DetalleFactura -----------------------------------------------------------------------

Create procedure sp_EliminarDetalleFactura @codigoDetalleFactura int
as
	Begin
		delete from DetalleFacturas where DetalleFacturas.codigoDetalleFactura = @codigoDetalleFactura
	End
go

----------------------------------------------------- Actualizar DetalleFactura --------------------------------------------------------------------------
Create procedure sp_ActualizarDetalleFactura @precio decimal(10,2),@codigoDetalleFactura int
as
	Begin
		update DetalleFacturas
		set DetalleFacturas.precio = @precio
		where DetalleFacturas.codigoDetalleFactura = @codigoDetalleFactura
	End
go
------------------------------------------------------- Buscar DetalleFactura ---------------------------------------------------------------------

Create procedure sp_BuscarDetalleFactura @codigoDetalleFactura int
as
	Begin
		Select *from DetalleFacturas where DetalleFacturas.codigoDetalleFactura = @codigoDetalleFactura
	End
go