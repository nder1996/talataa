package Talataa.E_Commerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DetalleOrdenResponse {

    private Integer idDetalle;
    private Integer idOrden;
    private Integer idProducto;
    private String  nombreProducto;
    private String  descripcion;
    private Double  unidadPrecio;
    private Double  subTotal;
    private Integer cantidadProducto;
}
