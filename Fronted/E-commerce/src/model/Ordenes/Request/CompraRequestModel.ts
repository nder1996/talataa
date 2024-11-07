import { DetalleOrdenRequest } from "./DetalleOrdenRequestModel";
import { MetodoPagoRequest } from "./MetodoPagoRequestMode";
import { OrdenCompraRequest } from "./OrdenCompraRequestModel";
import { PagoOrdenRequest } from "./PagoOrdenRequestModel";

export class CompraRequest {
    orden?: OrdenCompraRequest;
    pago?: MetodoPagoRequest;
    detailsOrden?: DetalleOrdenRequest[];

    constructor(
        orden?: OrdenCompraRequest,
        pago?: MetodoPagoRequest,
        detailsOrden?: DetalleOrdenRequest[]
    ) {
        this.orden = orden;
        this.pago = pago;
        this.detailsOrden = detailsOrden;
    }
}