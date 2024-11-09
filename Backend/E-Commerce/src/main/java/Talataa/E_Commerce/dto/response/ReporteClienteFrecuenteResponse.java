package Talataa.E_Commerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReporteClienteFrecuenteResponse {

    private String nombreUsuario;
    private String nombreCompleto;
    private Long totalCompras;
    private Double montoTotal;

}
