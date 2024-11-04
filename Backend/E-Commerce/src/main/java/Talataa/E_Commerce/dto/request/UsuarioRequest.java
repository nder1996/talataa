package Talataa.E_Commerce.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioRequest {

    private Integer userId;
    private Integer idTipoDocumento;
    private Integer idGenero;
    private Integer idCiudad;
    private Integer idRol;
    /**/
    private String nombres;
    private String telefono;
    private String apellidos;
    private String email;
    private String numeroDocumento;
    private String dirreccion;
    private String nombreUsuario;
}
