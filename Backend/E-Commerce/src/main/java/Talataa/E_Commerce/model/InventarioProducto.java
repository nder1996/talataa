package Talataa.E_Commerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InventarioProducto {
    private Integer id;
    private Integer idProducto;
    private Integer cantidadDisponible;
    private Date createAt;
    private Date updateAt;
    private String estado;

}
