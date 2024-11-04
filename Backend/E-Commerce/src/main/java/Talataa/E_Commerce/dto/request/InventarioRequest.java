package Talataa.E_Commerce.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InventarioRequest {

    private Integer idInventario;
    private Integer idProducto;
    private Integer cantidadDisponible;
}
