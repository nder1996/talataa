export class ProductosModel {
    idProducto?: number;
    idCategoriaProducto?: number;
    productoNombre?: string;
    productoDescripcion?: string;
    productoPrecioUnidad?: number;
    nombreCategoria?: number;
    descripcionCategoria?: number;
    cantidadSeleccionada?:number
    constructor(
        idProducto?: number,
        idCategoriaProducto?: number,
        productoNombre?: string,
        productoDescripcion?: string,
        productoPrecioUnidad?: number,
        nombreCategoria?: number,
        descripcionCategoria?: number,
        cantidadSeleccionada?:number

    ) {
      this.idProducto = idProducto;
      this.idCategoriaProducto = idCategoriaProducto;
      this.productoNombre = productoNombre;
      this.productoDescripcion = productoDescripcion;
      this.productoPrecioUnidad = productoPrecioUnidad;
      this.nombreCategoria = nombreCategoria;
      this.descripcionCategoria = descripcionCategoria;
      this.cantidadSeleccionada = cantidadSeleccionada;
    }
  }