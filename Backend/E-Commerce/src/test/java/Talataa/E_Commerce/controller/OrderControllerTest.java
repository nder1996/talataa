package Talataa.E_Commerce.controller;

import Talataa.E_Commerce.dto.request.CompraRequest;
import Talataa.E_Commerce.dto.request.DetalleOrdenRequest;
import Talataa.E_Commerce.dto.request.MetodoPagoRequest;
import Talataa.E_Commerce.dto.request.OrdenCompraRequest;
import Talataa.E_Commerce.dto.response.ApiResponse;
import Talataa.E_Commerce.dto.response.OrdenResponse;
import Talataa.E_Commerce.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.*;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(OrderController.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void testGetAllOrdenesDisponibles() throws Exception {
        // 1. Crear datos de prueba
        OrdenResponse orden = new OrdenResponse();
        orden.setIdOrden(1);
        orden.setNombreCompleto("Juan Pérez");
        orden.setTotalCompra(1000.0);
        orden.setPagoEstado("PENDIENTE");

        List<OrdenResponse> ordenes = Arrays.asList(orden);

        // 2. Crear la respuesta API esperada
        ApiResponse<String> response = new ApiResponse<>();
        Map<String, Object> data = new HashMap<>();
        data.put("ORDENES", ordenes);
        response.setData(data);
        response.setMeta(new ApiResponse.Meta("Operación exitosa", 200));

        // 3. Configurar el comportamiento del mock
        when(orderService.getAllOrdenesDisponibles()).thenReturn(response);

        // 4. Ejecutar el test y verificar
        // Test simple
        mockMvc.perform(get("/api/order/getAllOrdenesDisponibles"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testGetAllDetalleXOrdenDisponibles() throws Exception {
        // Crear datos de prueba
        Integer idOrden = 1;

        // Crear la respuesta API
        ApiResponse<String> response = new ApiResponse<>();
        Map<String, Object> data = new HashMap<>();
        data.put("DETALLES_ORDEN", Arrays.asList(
                // Aquí tus datos de prueba
                Map.of("id", 1, "producto", "Producto 1", "cantidad", 2)
        ));
        response.setData(data);
        response.setMeta(new ApiResponse.Meta("Operación exitosa", 200));

        // Mock del servicio
        when(orderService.getAllDetalleXOrdenDisponibles(idOrden)).thenReturn(response);

        // Ejecutar test
        mockMvc.perform(get("/api/order/getAllDetalleXOrdenDisponibles/{idOrden}", idOrden))
                .andExpect(status().isOk())
                .andDo(print());
    }

    // Test opcional para caso de error o no encontrado
    @Test
    void testGetAllDetalleXOrdenDisponiblesNotFound() throws Exception {
        Integer idOrden = 999; // ID que no existe

        ApiResponse<String> response = new ApiResponse<>();
        response.setMeta(new ApiResponse.Meta("Orden no encontrada", 404));

        when(orderService.getAllDetalleXOrdenDisponibles(idOrden)).thenReturn(response);

        mockMvc.perform(get("/api/order/getAllDetalleXOrdenDisponibles/{idOrden}", idOrden))
                .andExpect(status().isOk())
                .andDo(print());
    }

/*
    @Test
    void testProcesarOrden() throws Exception {
        // 1. Crear OrdenCompraRequest
        OrdenCompraRequest ordenCompra = new OrdenCompraRequest();
        ordenCompra.setIdUsuario(1);
        ordenCompra.setSubTotalCompra(100.0);
        ordenCompra.setTotalCompra(120.0);
        ordenCompra.setDescuento(10);

        // 2. Crear MetodoPagoRequest
        MetodoPagoRequest metodoPago = new MetodoPagoRequest();
        metodoPago.setNombreTitular("Juan Pérez");
        metodoPago.setNumeroTarjeta("4111111111111111");
        metodoPago.setFechaExpiracion("12/25");
        metodoPago.setCv("123");

        // 3. Crear lista de DetalleOrdenRequest
        List<DetalleOrdenRequest> detalles = new ArrayList<>();

        DetalleOrdenRequest detalle1 = new DetalleOrdenRequest();
        detalle1.setIdProducto(1);
        detalle1.setCantidadProducto(2);
        detalle1.setUnidadPrecio(50.0);
        detalle1.setSubTotal(100.0);
        detalles.add(detalle1);

        DetalleOrdenRequest detalle2 = new DetalleOrdenRequest();
        detalle2.setIdProducto(2);
        detalle2.setCantidadProducto(1);
        detalle2.setUnidadPrecio(20.0);
        detalle2.setSubTotal(20.0);
        detalles.add(detalle2);

        // 4. Crear CompraRequest principal
        CompraRequest request = new CompraRequest();
        request.setOrden(ordenCompra);
        request.setPago(metodoPago);
        request.setDetailsOrden(detalles);

        // 5. Crear la respuesta esperada
        ApiResponse<String> response = new ApiResponse<>();
        Map<String, Object> data = new HashMap<>();
        data.put("ORDEN_PROCESADA", "Orden procesada exitosamente");
        response.setData(data);
        response.setMeta(new ApiResponse.Meta("Operación exitosa", 200));

        // 6. Configurar el mock
        when(orderService.procesarOrdenCompleta(any(CompraRequest.class))).thenReturn(response);

        // 7. Ejecutar el test
        mockMvc.perform(post("/api/order/procesarCompra")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    // Test opcional para caso de error
    @Test
    void testProcesarOrdenError() throws Exception {
        CompraRequest request = new CompraRequest();
        OrdenCompraRequest nOrdenResponse =  request.getOrden();
        nOrdenResponse.setIdUsuario(1);
        // ... otros campos

        ApiResponse<String> response = new ApiResponse<>();
        response.setError(new ApiResponse.ErrorDetails("ERROR-001", "Error al procesar orden"));
        response.setMeta(new ApiResponse.Meta("Error en la operación", 400));

        when(orderService.procesarOrdenCompleta(any(CompraRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/order/procesarCompra")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }
    */

}










