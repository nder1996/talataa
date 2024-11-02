package Talataa.E_Commerce.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TipoDocumento {


    private Integer id;
    private String nombre;
    private String descripcion;
    private String estado;
}
