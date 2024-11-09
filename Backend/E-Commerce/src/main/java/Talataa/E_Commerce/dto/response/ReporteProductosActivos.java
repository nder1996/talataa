package Talataa.E_Commerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReporteProductosActivos {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer cantidadDisponible;
    private String nombreCategoria;
    private Double precio;
}
