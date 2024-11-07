export class OrdenCompraRequest {
    idOrden?: number;
    idUsuario?: number;
    subTotalCompra?: number;
    totalCompra?: number;
    descuento?: number;

    constructor(
        idOrden?: number,
        idUsuario?: number,
        subTotalCompra?: number,
        totalCompra?: number,
        descuento?: number
    ) {
        this.idOrden = idOrden;
        this.idUsuario = idUsuario;
        this.subTotalCompra = subTotalCompra;
        this.totalCompra = totalCompra;
        this.descuento = descuento;
    }
}