package Talataa.E_Commerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class DetalleOrdenRequest {

    private Integer idOrdenCompra;
    private Integer idProducto;
    private Integer cantidadProducto;
    private Double unidadPrecio;
    private Double subTotal;


}
