package Talataa.E_Commerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReporteProductoMasVendidoResponse {
    private String nombre;
    private String descripcion;
    private Double totalVendido;
}
