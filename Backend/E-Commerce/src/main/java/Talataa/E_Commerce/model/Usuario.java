package Talataa.E_Commerce.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Usuario {

    private Integer id;
    private Integer idCiudad;
    private Integer idTipoDocumento;
    private Integer idGenero;
    private String nombreUsuario;
    private String contraseña;
    private String estado;
    private Date createAt;
    private Date updateAt;
    private String correoElectronico;
    private String direccion;
    private String nombres;
    private String apellidos;

    // Relaciones
    private Ciudad ciudad;
    private TipoDocumento tipoDocumento;
    private Genero genero;
    private List<Rol> roles;


}
