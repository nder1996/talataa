package Talataa.E_Commerce.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class DescuentoService {

    // Constantes para los descuentos
    private static final double DESCUENTO_RANGO_TIEMPO = 0.10;
    private static final double DESCUENTO_ORDEN_ALEATORIA = 0.50;
    private static final double DESCUENTO_CLIENTE_FRECUENTE = 0.05;

    private final LocalDateTime horaInicioDescuento;
    private final LocalDateTime horaFinDescuento;

    public DescuentoService() {
        this.horaInicioDescuento = LocalDateTime.now().withHour(0).withMinute(0);
        this.horaFinDescuento = LocalDateTime.now().withHour(23).withMinute(59);
    }

    public double calcularDescuentoOrden(Long idCliente, boolean esOrdenAleatoria, int cantidadOrdenesCliente) {
        double descuentoTotal = 0.0;
        LocalDateTime ahora = LocalDateTime.now();

        // Verificar si está dentro del rango de tiempo
        if (estaEnHorarioDescuento(ahora)) {
            // Aplicar descuento por rango de tiempo
            descuentoTotal += DESCUENTO_RANGO_TIEMPO;

            // Aplicar descuento si es orden aleatoria
            if (esOrdenAleatoria) {
                descuentoTotal += DESCUENTO_ORDEN_ALEATORIA;
            }

            // Aplicar descuento si es cliente frecuente (más de 5 órdenes)
            if (cantidadOrdenesCliente > 5) {
                descuentoTotal += DESCUENTO_CLIENTE_FRECUENTE;
            }
        }

        return descuentoTotal;
    }

    protected  boolean estaEnHorarioDescuento(LocalDateTime horaOrden) {
        return horaOrden.isAfter(horaInicioDescuento) && horaOrden.isBefore(horaFinDescuento);
    }

}
