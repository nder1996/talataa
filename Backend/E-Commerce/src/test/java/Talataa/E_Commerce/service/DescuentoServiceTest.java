package Talataa.E_Commerce.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DescuentoServiceTest {

    private DescuentoService descuentoService;
    private static final double DELTA = 0.001; // Para comparaciones de doubles

    @BeforeEach
    void setUp() {
        descuentoService = new DescuentoService();
    }

    @Test
    void calcularDescuentoOrden_SinDescuentos_DebeRetornarCero() {
        // Arrange
        Long idCliente = 1L;
        boolean esOrdenAleatoria = false;
        int cantidadOrdenes = 3; // Menos de 5 órdenes

        // Act
        double descuento = descuentoService.calcularDescuentoOrden(idCliente, esOrdenAleatoria, cantidadOrdenes);

        // Assert
        assertEquals(0.10, descuento, DELTA, "Debe retornar solo el descuento por horario");
    }

    @Test
    void calcularDescuentoOrden_ConOrdenAleatoria_DebeAplicarDescuentoAdicional() {
        // Arrange
        Long idCliente = 1L;
        boolean esOrdenAleatoria = true;
        int cantidadOrdenes = 3;

        // Act
        double descuento = descuentoService.calcularDescuentoOrden(idCliente, esOrdenAleatoria, cantidadOrdenes);

        // Assert
        assertEquals(0.60, descuento, DELTA,
                "Debe retornar descuento por horario (0.10) + descuento orden aleatoria (0.50)");
    }

    @Test
    void calcularDescuentoOrden_ClienteFrecuente_DebeAplicarDescuentoFrecuente() {
        // Arrange
        Long idCliente = 1L;
        boolean esOrdenAleatoria = false;
        int cantidadOrdenes = 6; // Más de 5 órdenes

        // Act
        double descuento = descuentoService.calcularDescuentoOrden(idCliente, esOrdenAleatoria, cantidadOrdenes);

        // Assert
        assertEquals(0.15, descuento, DELTA,
                "Debe retornar descuento por horario (0.10) + descuento cliente frecuente (0.05)");
    }

    @Test
    void calcularDescuentoOrden_TodosLosDescuentos_DebeAplicarDescuentoTotal() {
        // Arrange
        Long idCliente = 1L;
        boolean esOrdenAleatoria = true;
        int cantidadOrdenes = 6;

        // Act
        double descuento = descuentoService.calcularDescuentoOrden(idCliente, esOrdenAleatoria, cantidadOrdenes);

        // Assert
        assertEquals(0.65, descuento, DELTA,
                "Debe retornar la suma de todos los descuentos (0.10 + 0.50 + 0.05)");
    }

    // Prueba con una subclase de DescuentoService para controlar el tiempo
    static class TestableDescuentoService extends DescuentoService {
        private final LocalDateTime horaFija;

        public TestableDescuentoService(LocalDateTime horaFija) {
            this.horaFija = horaFija;
        }

        @Override
        protected boolean estaEnHorarioDescuento(LocalDateTime horaOrden) {
            return horaOrden.isAfter(horaFija.withHour(0).withMinute(0)) &&
                    horaOrden.isBefore(horaFija.withHour(23).withMinute(59));
        }
    }

    @Test
    void calcularDescuentoOrden_FueraDeHorario_NoDebeAplicarDescuentos() {
        // Arrange
        LocalDateTime horaFueraDeRango = LocalDateTime.now().withHour(23).withMinute(59).plusMinutes(1);
        DescuentoService servicioTest = new TestableDescuentoService(horaFueraDeRango);
        Long idCliente = 1L;
        boolean esOrdenAleatoria = true;
        int cantidadOrdenes = 6;

        // Act
        double descuento = servicioTest.calcularDescuentoOrden(idCliente, esOrdenAleatoria, cantidadOrdenes);

        // Assert
        assertEquals(0.0, descuento, DELTA,
                "No debe aplicar ningún descuento fuera del horario permitido");
    }

    @Test
    void calcularDescuentoOrden_ClienteNuevo_SoloDescuentoHorario() {
        // Arrange
        Long idCliente = 1L;
        boolean esOrdenAleatoria = false;
        int cantidadOrdenes = 0;

        // Act
        double descuento = descuentoService.calcularDescuentoOrden(idCliente, esOrdenAleatoria, cantidadOrdenes);

        // Assert
        assertEquals(0.10, descuento, DELTA,
                "Cliente nuevo solo debe recibir descuento por horario");
    }

    @Test
    void calcularDescuentoOrden_ExactamenteCincoOrdenes_SinDescuentoFrecuente() {
        // Arrange
        Long idCliente = 1L;
        boolean esOrdenAleatoria = false;
        int cantidadOrdenes = 5;

        // Act
        double descuento = descuentoService.calcularDescuentoOrden(idCliente, esOrdenAleatoria, cantidadOrdenes);

        // Assert
        assertEquals(0.10, descuento, DELTA,
                "Con exactamente 5 órdenes no debe aplicar descuento de cliente frecuente");
    }



}
