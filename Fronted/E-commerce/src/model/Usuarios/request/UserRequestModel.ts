export class UserRequestModel {
    userId?: number;
    idTipoDocumento?: number;
    idGenero?: number;
    idCiudad?: number;
    idRol?: number;
    nombres?: string;
    telefono?: string;
    apellidos?: string;
    email?: string;
    numeroDocumento?: string;
    dirreccion?: string;
    nombreUsuario?: string;
    password?: string;
    constructor(
        userId?: number,
        idTipoDocumento?: number,
        idGenero?: number,
        idCiudad?: number,
        idRol?: number,
        nombres?: string,
        telefono?: string,
        apellidos?: string,
        email?: string,
        numeroDocumento?: string,
        dirreccion?: string,
        nombreUsuario?: string,
        password?: string
    ) {
        this.userId = userId;
        this.idTipoDocumento = idTipoDocumento;
        this.idGenero = idGenero;
        this.idCiudad = idCiudad;
        this.idRol = idRol;
        this.nombres = nombres;
        this.telefono = telefono;
        this.apellidos = apellidos;
        this.email = email;
        this.numeroDocumento = numeroDocumento;
        this.dirreccion = dirreccion;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
    }
}