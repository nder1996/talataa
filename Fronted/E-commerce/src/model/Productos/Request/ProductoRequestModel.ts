export class ProductoRequestModel {
    id?: number;
    idCategoriaProducto?: number;
    nombre?: string;
    descripcion?: string;
    precioUnidad?: number;
    estado?: string;
    create_at?: Date;
    update_at?: Date;

    constructor(
        id?: number,
        idCategoriaProducto?: number,
        nombre?: string,
        descripcion?: string,
        precioUnidad?: number,
        estado?: string,
        create_at?: Date,
        update_at?: Date,
    ) {
        this.id = id;
        this.idCategoriaProducto = idCategoriaProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioUnidad = precioUnidad;
        this.estado = estado;
        this.create_at = create_at;
        this.update_at = update_at;
    }
}