package Talataa.E_Commerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InventariosResponse {

    private Integer idInventario;
    private Integer idProducto;
    private String  productoNombre;
    private String  productoDescripcion;
    private Integer cantidadDisponible;

}
