package Talataa.E_Commerce.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MetodoPagoRequest {

    private String nombreTitular;
    private String numeroTarjeta;
    private String fechaExpiracion;
    private String cv;



}
