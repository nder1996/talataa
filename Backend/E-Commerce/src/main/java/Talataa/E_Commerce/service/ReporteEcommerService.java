package Talataa.E_Commerce.service;


import Talataa.E_Commerce.dto.response.ApiResponse;
import Talataa.E_Commerce.dto.response.ReporteClienteFrecuenteResponse;
import Talataa.E_Commerce.dto.response.ReporteProductoMasVendidoResponse;
import Talataa.E_Commerce.dto.response.ReporteProductosActivos;
import Talataa.E_Commerce.model.Ciudad;
import Talataa.E_Commerce.model.Genero;
import Talataa.E_Commerce.model.TipoDocumento;
import Talataa.E_Commerce.repository.ReporteEcommerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteEcommerService {

    @Autowired
    ResponseApiBuilderService responseApiBuilderService;

    @Autowired
    ReporteEcommerRepository reporteEcommerRepository;


    public ApiResponse<String> clientesFrecuentes(){
        try {
            List<ReporteClienteFrecuenteResponse> cliente = this.reporteEcommerRepository.clientesFrecuentes();
            if(cliente!=null && !cliente.isEmpty()){
                return this.responseApiBuilderService.successRespuesta(cliente, "CLIENTES_FRECUENTES");
            }else{
                return this.responseApiBuilderService.errorRespuesta(
                        404,
                        "NO_CITY_DATA",
                        "No se encontraron datos de los clientes. Verifica la información e intenta nuevamente. Si el problema persiste, contacta al soporte técnico."
                );

            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta(
                    500,
                    "SERVER_ERROR",
                    "Ocurrió un error interno en el servidor. Intenta nuevamente más tarde. Si el problema persiste, contacta al soporte técnico para asistencia."
            );

        }
    }

    public ApiResponse<String> topProductosVendidos(){
        try {
            List<ReporteProductoMasVendidoResponse> topProductos = this.reporteEcommerRepository.topProductosVendidos();
            if(topProductos!=null && !topProductos.isEmpty()){
                return this.responseApiBuilderService.successRespuesta(topProductos, "TOP_PRODUCTOS");
            }else{
                return this.responseApiBuilderService.errorRespuesta(
                        404,
                        "NO_GENDER_DATA",
                        "No se encontraron datos de los top productos. Verifica la información e intenta nuevamente. Si el problema persiste, contacta al soporte técnico."
                );

            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta(
                    500,
                    "SERVER_ERROR",
                    "Ocurrió un error interno en el servidor. Intenta nuevamente más tarde. Si el problema persiste, contacta al soporte técnico para asistencia."
            );
        }
    }

    public ApiResponse<String> productosActivos(){
        try {
            List<ReporteProductosActivos> productos = this.reporteEcommerRepository.productosActivos();
            if(productos!=null && !productos.isEmpty()){
                return this.responseApiBuilderService.successRespuesta(productos, "PRODUCTOS_ACTIVOS");
            }else{
                return this.responseApiBuilderService.errorRespuesta(
                        404,
                        "NO_DOCUMENT_TYPE_DATA",
                        "No se encontraron productos activos . Verifica la información e intenta nuevamente. Si el problema persiste, contacta al soporte técnico."
                );

            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta(
                    500,
                    "SERVER_ERROR",
                    "Ocurrió un error interno en el servidor. Intenta nuevamente más tarde. Si el problema persiste, contacta al soporte técnico para asistencia."
            );
        }
    }

}
