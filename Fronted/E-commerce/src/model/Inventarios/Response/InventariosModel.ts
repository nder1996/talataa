export class InventariosModel {
    idInventario?: number;
    productoNombre?: string;
    productoDescripcion?: string;
    cantidadDisponible?: string;

    constructor(
        idInventario?: number,
        productoNombre?: string,
        productoDescripcion?: string,
        cantidadDisponible?: string,

    ) {
        this.idInventario = idInventario;
        this.productoNombre = productoNombre;
        this.productoDescripcion = productoDescripcion;
        this.cantidadDisponible = cantidadDisponible;
    }
}