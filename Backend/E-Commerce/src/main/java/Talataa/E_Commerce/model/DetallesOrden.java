package Talataa.E_Commerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallesOrden {

    private Integer id;
    private Integer idOrdenCompra;
    private Integer idProducto;
    private Integer cantidadProducto;
    private Integer createAt;
    private Integer updateAt;
    private Integer unidadPrecio;
    private Integer subTotal;
    private String estado;

    // Relaciones
    private OrdenCompra ordenCompra;
    private Producto producto;
}
