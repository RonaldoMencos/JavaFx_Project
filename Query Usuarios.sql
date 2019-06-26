use DBNextSport2017013
go



Create table Usuarios 
(
codigoUsuario int identity (1,1) primary key,
nombreUsuario varchar(60),
email varchar(60),
usuario varchar(60),
contrasena varchar(60),
tipoDeUsuario varchar(60),

)
go





-------------------------------------------- Agregar Usuario -------------------------------------------------------------------
Create   procedure sp_AgregarUsuario @nombreUsuario varchar(60),@email varchar(60),@usuario varchar(60),@contrasena varchar(60),@TipoDeUsuario varchar(60)
as
	begin
		insert into Usuarios(nombreUsuario,email,usuario,contrasena,TipoDeUsuario) values (@nombreUsuario,@email,@usuario,@contrasena,@TipoDeUsuario)
	end
go

execute sp_AgregarUsuario "oscar","oscar","ronaldo","123","Admin"
go
-------------------------------------------- Listar Usuarios ------------------------------------------------------
Create  procedure sp_ListarUsuarios 
as 
	begin
		select Usuarios.codigoUsuario,
				Usuarios.nombreUsuario,
				Usuarios.email,
				Usuarios.usuario,
				Usuarios.contrasena,
				Usuarios.TipoDeUsuario
				from Usuarios 
	end
go
--------------------------------------- Seleccionar Usuarios ------------------------------------------
Create procedure sp_Usuarios @usuario varchar(60),@contrasena varchar(60),@tipoDeUsuario varchar(60)
as
	begin
		select  Usuarios.usuario,
				Usuarios.contrasena,
				Usuarios.tipoDeUsuario
				from Usuarios
		 where @usuario = usuario and @contrasena = contrasena and @tipoDeUsuario = tipoDeUsuario
	end
	go