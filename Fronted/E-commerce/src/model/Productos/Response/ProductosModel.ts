export class ProductosModel {
    idProducto?: number;
    productoNombre?: string;
    productoDescripcion?: Date;
    productoPrecioUnidad?: number;
    nombreCategoria?: number;
    descripcionCategoria?: number;
    cantidadDisponible?: string;
    urlImg?: string;
    cantidadSeleccionada?:number
  
    constructor(
        idProducto?: number,
        productoNombre?: string,
        productoDescripcion?: Date,
        productoPrecioUnidad?: number,
        nombreCategoria?: number,
        descripcionCategoria?: number,
        cantidadDisponible?: string,
        urlImg?: string,
        cantidadSeleccionada?:number

    ) {
      this.idProducto = idProducto;
      this.productoNombre = productoNombre;
      this.productoDescripcion = productoDescripcion;
      this.productoPrecioUnidad = productoPrecioUnidad;
      this.nombreCategoria = nombreCategoria;
      this.descripcionCategoria = descripcionCategoria;
      this.cantidadDisponible = cantidadDisponible;
      this.urlImg = urlImg  ;
      this.cantidadSeleccionada = cantidadSeleccionada;
    }
  }