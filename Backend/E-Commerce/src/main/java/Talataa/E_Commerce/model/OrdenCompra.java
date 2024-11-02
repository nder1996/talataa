package Talataa.E_Commerce.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

//@NoArgsConstructor
//@AllArgsConstructor
//@Data
public class OrdenCompra {

    private Integer id;
    private Integer idUsuario;
    private Double subTotalCompra;
    private Double totalCompra;
    private String estado;
    private Date createAt;
    private Date updateAt;
    private Integer descuento;

    // Relaciones
    private Usuario usuario;
    private List<DetallesOrden> detallesOrden;

}
