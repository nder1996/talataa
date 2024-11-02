package Talataa.E_Commerce.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genero {

    private Integer id;
    private String nombre;
    private String descripcion;
    private String estado;

}
