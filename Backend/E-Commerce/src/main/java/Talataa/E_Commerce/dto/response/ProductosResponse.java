package Talataa.E_Commerce.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductosResponse {

    private Integer idProducto;
    private String productoNombre;
    private String productoDescripcion;
    private Double productoPrecioUnidad;
    private String nombreCategoria;
    private String descripcionCategoria;
    private Integer cantidadDisponible;
    private String urlImg;



}
