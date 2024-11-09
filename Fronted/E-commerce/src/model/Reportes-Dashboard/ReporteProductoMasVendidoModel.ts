// reporte-producto-vendido.model.ts
export class ReporteProductoMasVendidoModel {
    nombre?: string;
    descripcion?: string;
    totalVendido?: number;

    constructor(
        nombre?: string,
        descripcion?: string,
        totalVendido?: number
    ) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.totalVendido = totalVendido;
    }
}