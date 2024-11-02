package Talataa.E_Commerce.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoCategoria {

    private Integer id;
    private Integer idCategoriaProducto;
    private Integer idProducto;
}
