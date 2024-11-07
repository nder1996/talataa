export class PagoOrdenRequest {
    idOrdenCompra?: number;
    jsonRespuesta?: string;

    constructor(
        idOrdenCompra?: number,
        jsonRespuesta?: string
    ) {
        this.idOrdenCompra = idOrdenCompra;
        this.jsonRespuesta = jsonRespuesta;
    }
}