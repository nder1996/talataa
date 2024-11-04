export class DetalleXOrdenModel {
    idDetalle?: number;
    idOrden?: number;
    idProducto?: number;
    nombreProducto?: string;
    descripcion?: string;
    unidadPrecio?: number;
    subTotal?: number;
    cantidadProducto?: number;

    constructor(
        idDetalle?: number,
        idOrden?: number,
        idProducto?: number,
        nombreProducto?: string,
        descripcion?: string,
        unidadPrecio?: number,
        subTotal?: number,
        cantidadProducto?: number
    ) {
        this.idDetalle = idDetalle;
        this.idOrden = idOrden;
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.unidadPrecio = unidadPrecio;
        this.subTotal = subTotal;
        this.cantidadProducto = cantidadProducto;
    }
}