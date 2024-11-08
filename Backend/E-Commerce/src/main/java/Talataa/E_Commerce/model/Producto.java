package Talataa.E_Commerce.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Producto {

    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer precioUnidad;
    private String estado;
    private Date create_at;
    private Integer update_at;
    private String url_img;

}
