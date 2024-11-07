export class MetodoPagoRequest {
    nombreTitular?: string;
    numeroTarjeta?: string;
    fechaExpiracion?: string;
    cvv?: string;

    constructor(
        nombreTitular?: string,
        numeroTarjeta?: string,
        fechaExpiracion?: string,
        cvv?: string
    ) {
        this.nombreTitular = nombreTitular;
        this.numeroTarjeta = numeroTarjeta;
        this.fechaExpiracion = fechaExpiracion;
        this.cvv = cvv;
    }
}