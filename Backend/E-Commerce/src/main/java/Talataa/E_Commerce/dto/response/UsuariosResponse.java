package Talataa.E_Commerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuariosResponse {

    private Integer userId;
    private String nombreCompleto;
    private String tipoDocumento;
    private String numeroDocumento;
    private String genero;
    private String ciudad;
    private String email;
    private String dirreccion;
    private String telefono;
    private String rolNombre;
    /**/
    private Integer idTipoDocumento;
    private Integer idGenero;
    private Integer idCiudad;
    private Integer idRol;

}
