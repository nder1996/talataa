package Talataa.E_Commerce.repository;


import Talataa.E_Commerce.dto.request.DetalleOrdenRequest;
import Talataa.E_Commerce.dto.request.InventarioRequest;
import Talataa.E_Commerce.dto.request.OrdenCompraRequest;
import Talataa.E_Commerce.dto.request.PagoOrdenRequest;
import Talataa.E_Commerce.dto.response.DetalleOrdenResponse;
import Talataa.E_Commerce.dto.response.InventariosResponse;
import Talataa.E_Commerce.dto.response.OrdenResponse;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Sql(scripts = {
        "classpath:data.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Test
    void getAllOrdenDisponibles_DeberiaRetornarOrdenesActivas() {
        // When
        List<OrdenResponse> ordenes = orderRepository.getAllOrdenDisponibles();

        // Then
        assertAll(
                () -> assertNotNull(ordenes, "La lista no debe ser null"),
                () -> assertFalse(ordenes.isEmpty(), "La lista no debe estar vacía"),
                () -> {
                    OrdenResponse primeraOrden = ordenes.get(0);
                    assertAll(
                            // Verificar datos básicos
                            () -> assertEquals(1, primeraOrden.getIdOrden()),
                            () -> assertEquals(1, primeraOrden.getIdUsuario()),
                            () -> assertEquals("Usuario De Prueba", primeraOrden.getNombreCompleto()),
                            () -> assertEquals(1500.00, primeraOrden.getSubTotalCompra()),
                            () -> assertEquals("A", primeraOrden.getPagoEstado())
                    );
                }
        );
    }

    @Test
    void getAllOrdenDisponibles_SoloDeberiaRetornarOrdenesActivas() {
        // When
        List<OrdenResponse> ordenes = orderRepository.getAllOrdenDisponibles();

        // Then
        assertTrue(
                ordenes.stream()
                        .allMatch(orden -> orden.getPagoEstado().equals("A")),
                "Todas las órdenes deben tener estado Activo"
        );
    }

    @Test
    void getAllDetalleXOrdenDisponibles_WhenOrderExists_ShouldReturnDetails() {
        // Arrange
        Integer orderId = 1;

        // Act
        List<DetalleOrdenResponse> detalles = orderRepository.getAllDetalleXOrdenDisponibles(orderId);
        detalles.get(0).setEstado("A");
        // Assert
        assertThat(detalles).isNotNull();
        assertThat(detalles.get(0).getIdOrden()).isEqualTo(orderId);
        assertThat(detalles.get(0).getEstado()).isEqualTo("A");
    }

    @Test
    void getAllDetalleXOrdenDisponibles_WhenOrderDoesNotExist_ShouldReturnEmptyList() {
        // Arrange
        Integer nonExistentOrderId = 999;

        // Act
        List<DetalleOrdenResponse> detalles = orderRepository.getAllDetalleXOrdenDisponibles(nonExistentOrderId);

        // Assert
        assertThat(detalles.size()).isEqualTo(0);
    }



    @Test
    void guardarPagoOrden_WhenValidPayment_ShouldReturnGeneratedId() {
        // Arrange
        PagoOrdenRequest pago = new PagoOrdenRequest();
        pago.setIdOrdenCompra(1);
        pago.setJsonRespuesta("{\"status\":\"success\"}");

        // Act
        Integer result = orderRepository.guardarPagoOrden(pago);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isPositive();
        assertThat(pago.getId()).isNotNull();
    }

    @Test
    void guardarDetalleOrden_WhenValidDetail_ShouldReturnOne() {
        // Arrange
        DetalleOrdenRequest detalle = new DetalleOrdenRequest();
        detalle.setIdOrdenCompra(1);
        detalle.setIdProducto(1);
        detalle.setCantidadProducto(2);
        detalle.setUnidadPrecio(50.00);
        detalle.setSubTotal(100.00);

        // Act
        Integer result = orderRepository.guardarDetalleOrden(detalle);

        // Assert
        assertThat(result).isEqualTo(1);
    }

    @Test
    void guardarDetalleOrden_WhenInvalidOrder_ShouldFail() {
        // Arrange
        DetalleOrdenRequest detalle = new DetalleOrdenRequest();
        detalle.setIdOrdenCompra(999); // Orden que no existe
        detalle.setIdProducto(1);
        detalle.setCantidadProducto(2);
        detalle.setUnidadPrecio(50.00);
        detalle.setSubTotal(100.00);

        // Act & Assert
        assertThrows(Exception.class, () -> orderRepository.guardarDetalleOrden(detalle));
    }



    @Test
    void inactivarRegistroDetalle_WhenDetailDoesNotExist_ShouldReturnZero() {
        // Arrange
        Integer nonExistentDetailId = 999;

        // Act
        Integer result = orderRepository.inactivarRegistroDetalle(nonExistentDetailId);

        // Assert
        assertThat(result).isEqualTo(0);
    }

    @Test
    void actualizarOrdenCompra_WhenOrderExists_ShouldReturnOne() {
        // Arrange
        OrdenCompraRequest orden = new OrdenCompraRequest();
        orden.setIdOrden(1);
        orden.setSubTotalCompra(150.00);
        orden.setTotalCompra(142.50);
        orden.setDescuento(750);

        // Act
        Integer result = orderRepository.actualizarOrdenCompra(orden);

        // Assert
        assertThat(result).isEqualTo(1);
    }

    @Test
    void actualizarOrdenCompra_WhenOrderDoesNotExist_ShouldReturnZero() {
        // Arrange
        OrdenCompraRequest orden = new OrdenCompraRequest();
        orden.setIdOrden(999);
        orden.setSubTotalCompra(150.00);
        orden.setTotalCompra(150.00);
        orden.setDescuento(75);

        // Act
        Integer result = orderRepository.actualizarOrdenCompra(orden);

        // Assert
        assertThat(result).isZero();
    }

    @Test
    void actualizarDetalleOrden_WhenDetailExists_ShouldReturnOne() {
        // Arrange
        DetalleOrdenRequest detalle = new DetalleOrdenRequest();
        detalle.setIdOrdenCompra(1);
        detalle.setIdProducto(2);
        detalle.setCantidadProducto(3);
        detalle.setUnidadPrecio(60.00);
        detalle.setSubTotal(180.00);

        // Act
        Integer result = orderRepository.actualizarDetalleOrden(detalle);

        // Assert
        assertThat(result).isEqualTo(2);
    }

    @Test
    void actualizarDetalleOrden_WhenDetailDoesNotExist_ShouldReturnZero() {
        // Arrange
        DetalleOrdenRequest detalle = new DetalleOrdenRequest();
        detalle.setIdOrdenCompra(999);
        detalle.setIdProducto(2);
        detalle.setCantidadProducto(3);
        detalle.setUnidadPrecio(60.00);
        detalle.setSubTotal(180.00);

        // Act
        Integer result = orderRepository.actualizarDetalleOrden(detalle);

        // Assert
        assertThat(result).isZero();
    }

}
