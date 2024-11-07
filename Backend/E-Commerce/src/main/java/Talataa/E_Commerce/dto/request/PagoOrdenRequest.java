package Talataa.E_Commerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PagoOrdenRequest {

    private Integer id;
    private Integer idOrdenCompra;
    private String jsonRespuesta;

}
