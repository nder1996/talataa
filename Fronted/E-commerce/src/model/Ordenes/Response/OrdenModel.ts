export class OrdenModel {
  idOrden?: number;
  nombreCompleto?: string;
  descripcion?: string;
  numeroDocumento?: string;
  ciudad?: string;
  correoElectronico?: string;
  telefono?: string;
  genero?: string;
  subTotalCompra?: number;
  descuento?: number;
  pagoEstado?: string;
  idUsuario?: number;
  idTipoDocumento?: number;
  idCiudad?: number;
  idGenero?: number;
  totalCompra?: number;
  constructor(
      idOrden?: number,
      nombreCompleto?: string,
      descripcion?: string,
      numeroDocumento?: string,
      ciudad?: string,
      correoElectronico?: string,
      telefono?: string,
      genero?: string,
      subTotalCompra?: number,
      descuento?: number,
      pagoEstado?: string,
      idUsuario?: number,
      idTipoDocumento?: number,
      idCiudad?: number,
      idGenero?: number,
      totalCompra?: number
  ) {
      this.idOrden = idOrden;
      this.nombreCompleto = nombreCompleto;
      this.descripcion = descripcion;
      this.numeroDocumento = numeroDocumento;
      this.ciudad = ciudad;
      this.correoElectronico = correoElectronico;
      this.telefono = telefono;
      this.genero = genero;
      this.subTotalCompra = subTotalCompra;
      this.descuento = descuento;
      this.pagoEstado = pagoEstado;
      this.idUsuario = idUsuario;
      this.idTipoDocumento = idTipoDocumento;
      this.idCiudad = idCiudad;
      this.idGenero = idGenero;
      this.totalCompra = totalCompra;
  }
}