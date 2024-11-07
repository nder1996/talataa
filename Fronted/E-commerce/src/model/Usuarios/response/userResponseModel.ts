export class UserModel {
    userId?: number;
    nombreCompleto?: string;
    tipoDocumento?: string;
    genero?: number;
    ciudad?: number;
    email?: number;
    dirreccion?: string;
    telefono?: string;
    rolNombre	?: string;
    nombreUsuario	?: string;
    //
    idTipoDocumento	?: number;
    idGenero	?: number;
    idCiudad	?: number;
    idRol	?: number;
    nombres	?: string;
    apellidos	?: string;
  
    constructor(
        userId?: number,
        nombreCompleto?: string,
        tipoDocumento?: string,
        genero?: number,
        ciudad?: number,
        email?: number,
        dirreccion?: string,
        telefono?: string,
        rolNombre	?: string,
        idTipoDocumento	?: number,
        idGenero	?: number,
        idCiudad	?: number,
        idRol	?: number,
        nombreUsuario	?: string,
        nombres	?: string,
        apellidos	?: string,
    ) {
      this.userId = userId;
      this.nombreCompleto = nombreCompleto;
      this.tipoDocumento = tipoDocumento;
      this.genero = genero;
      this.ciudad = ciudad;
      this.email = email;
      this.dirreccion = dirreccion;
      this.telefono = telefono  ;
      this.rolNombre = rolNombre  ;
      this.nombreUsuario = nombreUsuario  ;
      //
      this.idTipoDocumento = idTipoDocumento  ;
      this.idGenero = idGenero  ;
      this.idCiudad = idCiudad  ;
      this.idRol = idRol  ;
      //
      this.nombres = nombres  ;
      this.apellidos = apellidos  ;
    }
  }