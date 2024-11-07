export class InventariosModel {
    idInventario?: number;
    idProducto?: number;
    productoNombre?: string;
    productoDescripcion?: string;
    cantidadDisponible?: string;

    constructor(
        idInventario?: number,
        productoNombre?: string,
        productoDescripcion?: string,
        cantidadDisponible?: string,
        idProducto?: number

    ) {
        this.idInventario = idInventario;
        this.productoNombre = productoNombre;
        this.productoDescripcion = productoDescripcion;
        this.cantidadDisponible = cantidadDisponible;
        this.idProducto = idProducto;
    }
}