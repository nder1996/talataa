export class InventariosRequestModel {
    idInventario?: number;
    idProducto?: number;
    cantidadDisponible?: number;

    constructor(
        idInventario?: number,
        idProducto?: number,
        cantidadDisponible?: number
    ) {
        this.idInventario = idInventario;
        this.idProducto = idProducto;
        this.cantidadDisponible = cantidadDisponible;
    }
}