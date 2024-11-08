package Talataa.E_Commerce.service;


import Talataa.E_Commerce.dto.request.*;
import Talataa.E_Commerce.dto.response.ApiResponse;
import Talataa.E_Commerce.dto.response.DetalleOrdenResponse;
import Talataa.E_Commerce.dto.response.OrdenResponse;
import Talataa.E_Commerce.model.Producto;
import Talataa.E_Commerce.repository.OrderRepository;
import Talataa.E_Commerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
public class OrderService {


    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ResponseApiBuilderService responseApiBuilderService;

    private final DescuentoService descuentoService;

    public OrderService() {
        this.descuentoService = new DescuentoService();
    }


    public ApiResponse<String> getAllOrdenesDisponibles() {
        try {
            List<OrdenResponse> orden = this.orderRepository.getAllOrdenDisponibles();
            if (orden != null && !orden.isEmpty()) {
                return this.responseApiBuilderService.successRespuesta(orden, "ORDEN_DISPONIBLE");
            } else {
                return this.responseApiBuilderService.errorRespuesta(
                        404,
                        "NO_ORDERS_FOUND",
                        "No se encontraron órdenes disponibles. Verifica los filtros o la información e intenta nuevamente. Si el problema persiste, contacta al soporte técnico."
                );

            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta(
                    500,
                    "SERVER_ERROR",
                    "Ocurrió un error interno en el servidor. Intenta nuevamente más tarde. Si el problema persiste, contacta al soporte técnico para asistencia."
            );
        }
    }

    public ApiResponse<String> getAllDetalleXOrdenDisponibles(Integer idOrden) {
        try {
            List<DetalleOrdenResponse> detalle = this.orderRepository.getAllDetalleXOrdenDisponibles(idOrden);
            if (detalle != null && !detalle.isEmpty()) {
                return this.responseApiBuilderService.successRespuesta(detalle, "DETALLESXORDEN");
            } else {
                return this.responseApiBuilderService.errorRespuesta(
                        404,
                        "NO_DETAILS_FOUND_FOR_ORDER",
                        "No se encontraron detalles para la orden especificada. Verifica la información e intenta nuevamente. Si el problema persiste, contacta al soporte técnico."
                );

            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta(
                    500,
                    "SERVER_ERROR",
                    "Ocurrió un error interno en el servidor. Intenta nuevamente más tarde. Si el problema persiste, contacta al soporte técnico para asistencia."
            );
        }
    }



    public ApiResponse<String> procesarOrdenCompleta(CompraRequest request) {
        try {
            OrdenCompraRequest orden = request.getOrden();
            MetodoPagoRequest metodoPago = request.getPago();
            List<DetalleOrdenRequest> detalle = request.getDetailsOrden();

            // Calcular el total original
            double totalOriginal = calcularTotalOriginal(detalle);

            // Calcular descuentos aplicables
            double descuentoPorcentaje = descuentoService.calcularDescuentoOrden(
                    Long.valueOf(orden.getIdUsuario()),
                    this.isOrdenAleatoria(),
                    orden.getIdOrden()
            );

            double totalConDescuento = totalOriginal * (1 - descuentoPorcentaje);
            orden.setTotalCompra(totalConDescuento);

            Integer ordenId = this.guardarOrden(orden);
            if (ordenId != null && ordenId > 0) {
                PagoOrdenRequest pago = new PagoOrdenRequest();
                pago.setIdOrdenCompra(orden.getIdOrden());

                String json = String.format("""
                    {
                        "success": true,
                        "data": {
                            "ORDEN": {
                                "ordenId": "%s",
                                "estado": "COMPLETADO",
                                "mensaje": "Compra realizada exitosamente",
                                "fechaCompra": "%s",
                                "totalOriginal": %.2f,
                                "descuentoAplicado": %.2f,
                                "totalFinal": %.2f
                            }
                        },
                        "message": "Orden procesada correctamente"
                    }""",
                        orden.getIdOrden(),
                        LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                        totalOriginal,
                        descuentoPorcentaje * 100, // Convertir a porcentaje
                        totalConDescuento
                );

                pago.setJsonRespuesta(json);
                Integer idPago = this.guardarPago(pago);
                if (idPago != null && idPago > 0) {
                    boolean details = this.guardarDetallesOrden(detalle, orden.getIdOrden());
                    if (details) {
                        return this.responseApiBuilderService.successRespuesta(
                                "La orden se procesó correctamente con los descuentos aplicados",
                                "ORDEN_COMPRA"
                        );
                    }
                }
            }
            return this.responseApiBuilderService.errorRespuesta(
                    404,
                    "ERROR_SAVE",
                    "Error al guardar los detalles de la orden. Por favor, intente nuevamente."
            );
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta(
                    500,
                    "SERVER_ERROR",
                    "Ocurrió un error interno en el servidor. Intenta nuevamente más tarde. Si el problema persiste, contacta al soporte técnico para asistencia."
            );
        }
    }

    public boolean isOrdenAleatoria() {
        Random random = new Random();
        return random.nextInt(10) == 0;
    }

    private double calcularTotalOriginal(List<DetalleOrdenRequest> detalles) {
        return detalles.stream()
                .mapToDouble(detalle ->
                        detalle.getUnidadPrecio() * detalle.getCantidadProducto())
                .sum();
    }

    // Método para cambiar estado de la orden
    public ApiResponse<String> cambiarEstadoOrden(Integer idOrdenCompra) {
        try {
            Integer ordenActualizada = this.orderRepository.inactivarRegistroOrden(idOrdenCompra);
            if (ordenActualizada > 0) {
                return this.responseApiBuilderService.successRespuesta(
                        "La orden fue Borrada correctamente",
                        "ORDEN_ACTUALIZADA"
                );
            } else {
                return this.responseApiBuilderService.errorRespuesta(
                        404,
                        "ERROR_UPDATE",
                        "Error al actualizar el estado de la orden. Por favor, intente nuevamente."
                );
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta(
                    500,
                    "SERVER_ERROR",
                    "Ocurrió un error interno en el servidor. Intenta nuevamente más tarde. Si el problema persiste, contacta al soporte técnico para asistencia."
            );
        }
    }

    // Método para cambiar estado de los detalles
    public ApiResponse<String> cambiarEstadoDetallesOrden(Integer idDetalle) {
        try {
            Integer detallesActualizados = this.orderRepository.inactivarRegistroDetalle(idDetalle);

            if (detallesActualizados > 0) {
                return this.responseApiBuilderService.successRespuesta(
                        "Los detalles de la orden fueron borraddos correctamente",
                        "DETALLES_ACTUALIZADOS"
                );
            } else {
                return this.responseApiBuilderService.errorRespuesta(
                        404,
                        "ERROR_UPDATE",
                        "Error al actualizar el estado de los detalles. Por favor, intente nuevamente."
                );
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta(
                    500,
                    "SERVER_ERROR",
                    "Ocurrió un error interno en el servidor. Intenta nuevamente más tarde. Si el problema persiste, contacta al soporte técnico para asistencia."
            );
        }
    }






    // Método para guardar la orden
    private Integer guardarOrden(OrdenCompraRequest ordenRequest) {
        try {
            Integer row = this.orderRepository.guardarOrdenCompra(ordenRequest);
            if(row != null && row > 0) {
                return row;
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 0;
        }
    }

    private Integer guardarPago(PagoOrdenRequest pagoRequest) {
        if (pagoRequest.getIdOrdenCompra() == null || pagoRequest.getJsonRespuesta() == null) {
            throw new RuntimeException("Datos de pago incompletos");
        }
        Integer idPago = orderRepository.guardarPagoOrden(pagoRequest);
        if(idPago!=null && idPago>0){
            return idPago;
        }
        return null;
    }

    private boolean guardarDetallesOrden(List<DetalleOrdenRequest> detalles, Integer idOrdenCompra) {
        if (detalles == null || detalles.isEmpty()) {
            throw new RuntimeException("No hay detalles para procesar");
        }

        for (DetalleOrdenRequest detalle : detalles) {
            // Asignar el ID de la orden a cada detalle
            detalle.setIdOrdenCompra(idOrdenCompra);

            // Validar datos del detalle
            if (detalle.getIdProducto() == null || detalle.getCantidadProducto() == null) {
                throw new RuntimeException("Datos de detalle incompletos");
            }

            // Guardar cada detalle
            Integer resultado = orderRepository.guardarDetalleOrden(detalle);
            if (resultado == null || resultado == 0) {
                return false;
            }
        }

        return true;
    }

    /**/

    public ApiResponse<String> actualizarOrden(OrdenCompraRequest ordenRequest) {
        try {
            Integer ordenActualizada = this.orderRepository.actualizarOrdenCompra(ordenRequest);

            if (ordenActualizada != null && ordenActualizada > 0) {
                return this.responseApiBuilderService.successRespuesta(
                        "La orden fue actualizada correctamente",
                        "ORDEN_ACTUALIZADA"
                );
            } else {
                return this.responseApiBuilderService.errorRespuesta(
                        404,
                        "ERROR_UPDATE",
                        "Error al actualizar la orden. Por favor, intente nuevamente."
                );
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta(
                    500,
                    "SERVER_ERROR",
                    "Ocurrió un error interno en el servidor. Intenta nuevamente más tarde. Si el problema persiste, contacta al soporte técnico para asistencia."
            );
        }
    }


    public ApiResponse<String> actualizarDetalleOrden(DetalleOrdenRequest detalleRequest) {
        try {
            Integer detalleActualizado = this.orderRepository.actualizarDetalleOrden(detalleRequest);

            if (detalleActualizado != null && detalleActualizado > 0) {
                return this.responseApiBuilderService.successRespuesta(
                        "El detalle de la orden fue actualizado correctamente",
                        "DETALLE_ACTUALIZADO"
                );
            } else {
                return this.responseApiBuilderService.errorRespuesta(
                        404,
                        "ERROR_UPDATE",
                        "Error al actualizar el detalle de la orden. Por favor, intente nuevamente."
                );
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta(
                    500,
                    "SERVER_ERROR",
                    "Ocurrió un error interno en el servidor. Intenta nuevamente más tarde. Si el problema persiste, contacta al soporte técnico para asistencia."
            );
        }
    }
}
