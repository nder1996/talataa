package Talataa.E_Commerce.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductoRequest {

    private Integer id;
    private Integer idCategoriaProducto;
    private String nombre;
    private String descripcion;
    private Double precioUnidad;
    private String estado;
    private Date create_at;
    private Date update_at;
    private Integer cantidadSeleccion;

}
