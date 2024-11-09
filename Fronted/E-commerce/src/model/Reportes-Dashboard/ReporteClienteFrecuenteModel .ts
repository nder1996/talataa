export class ReporteClienteFrecuenteModel {
    nombreUsuario?: string;
    nombreCompleto?: string;
    totalCompras?: number;
    montoTotal?: number;

    constructor(
        nombreUsuario?: string,
        nombreCompleto?: string,
        totalCompras?: number,
        montoTotal?: number
    ) {
        this.nombreUsuario = nombreUsuario;
        this.nombreCompleto = nombreCompleto;
        this.totalCompras = totalCompras;
        this.montoTotal = montoTotal;
    }
}