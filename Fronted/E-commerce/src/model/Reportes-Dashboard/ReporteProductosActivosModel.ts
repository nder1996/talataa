// reporte-productos-activos.model.ts
export class ReporteProductosActivosModel {
    id?: number;
    nombre?: string;
    descripcion?: string;
    cantidadDisponible?: number;
    nombreCategoria?: string;
    precio?: number;

    constructor(
        id?: number,
        nombre?: string,
        descripcion?: string,
        cantidadDisponible?: number,
        nombreCategoria?: string,
        precio?: number

    ) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidadDisponible = cantidadDisponible;
        this.nombreCategoria = nombreCategoria;
        this.precio = precio;
    }
}