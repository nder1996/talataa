export class DetalleOrdenRequest {
    idOrdenCompra?: number;
    idProducto?: number;
    cantidadProducto?: number;
    unidadPrecio?: number;
    subTotal?: number;

    constructor(
        idOrdenCompra?: number,
        idProducto?: number,
        cantidadProducto?: number,
        unidadPrecio?: number,
        subTotal?: number
    ) {
        this.idOrdenCompra = idOrdenCompra;
        this.idProducto = idProducto;
        this.cantidadProducto = cantidadProducto;
        this.unidadPrecio = unidadPrecio;
        this.subTotal = subTotal;
    }
}
