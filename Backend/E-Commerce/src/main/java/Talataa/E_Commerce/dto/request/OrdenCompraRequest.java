package Talataa.E_Commerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrdenCompraRequest {

    private Integer idOrden;
    private Integer idUsuario;
    private Double subTotalCompra;
    private Double totalCompra;
    private Integer descuento;



}
