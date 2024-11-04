package Talataa.E_Commerce.service;


import Talataa.E_Commerce.dto.response.ApiResponse;
import Talataa.E_Commerce.dto.response.DetalleOrdenResponse;
import Talataa.E_Commerce.dto.response.OrdenResponse;
import Talataa.E_Commerce.model.Producto;
import Talataa.E_Commerce.repository.OrderRepository;
import Talataa.E_Commerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {


    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ResponseApiBuilderService responseApiBuilderService;


    public ApiResponse<String> getAllOrdenesDisponibles() {
        try {
            List<OrdenResponse> orden = this.orderRepository.getAllOrdenDisponibles();
            if (orden != null && !orden.isEmpty()) {
                return this.responseApiBuilderService.successRespuesta(orden, "ORDEN_DISPONIBLE");
            } else {
                return this.responseApiBuilderService.errorRespuesta("NO_ORDERS_FOUND");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta("SERVER_ERROR");
        }
    }

    public ApiResponse<String> getAllDetalleXOrdenDisponibles(Integer idOrden) {
        try {
            List<DetalleOrdenResponse> detalle = this.orderRepository.getAllDetalleXOrdenDisponibles(idOrden);
            if (detalle != null && !detalle.isEmpty()) {
                return this.responseApiBuilderService.successRespuesta(detalle, "DETALLESXORDEN");
            } else {
                return this.responseApiBuilderService.errorRespuesta("NO_DETAILS_FOUND_FOR_ORDER");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta("SERVER_ERROR");
        }
    }

}
