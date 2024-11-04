export class ProductModel {
    id?: number;
    nombre?: string;
    descripcion?: string;
    precioUnidad?: number;
    estado?: string;
    createAt?: Date;
    updateAt?: Date;
    url_img?: string;

    constructor(
        id?: number,
        nombre?: string,
        descripcion?: string,
        precioUnidad?: number,
        estado?: string,
        createAt?: Date,
        updateAt?: Date,
        url_img?: string
    

    ) {
      this.id = id;
      this.nombre = nombre;
      this.descripcion = descripcion;
      this.precioUnidad = precioUnidad;
      this.estado = estado;
      this.createAt = createAt;
      this.updateAt = updateAt;
      this.url_img = url_img  ;
    }
  }