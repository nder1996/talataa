package Talataa.E_Commerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrdenResponse {

    private Integer idOrden;
    private String nombreCompleto;
    private String descripcion;
    private String numeroDocumento;
    private String ciudad;
    private String CorreoElectronico;
    private String telefono;
    private String genero;
    private Double SubTotalCompra;
    private Double totalCompra;
    private Integer descuento;
    private String pagoEstado;

    private Integer idUsuario;
    private Integer idTipoDocumento;
    private Integer idCiudad;
    private Integer idGenero;

}
