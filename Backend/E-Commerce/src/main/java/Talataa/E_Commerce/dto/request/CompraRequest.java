package Talataa.E_Commerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompraRequest {

    private OrdenCompraRequest orden;
    private MetodoPagoRequest pago;
    private List<DetalleOrdenRequest> detailsOrden;
}
