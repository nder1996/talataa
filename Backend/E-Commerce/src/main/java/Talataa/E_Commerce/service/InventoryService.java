package Talataa.E_Commerce.service;


import Talataa.E_Commerce.dto.request.InventarioRequest;
import Talataa.E_Commerce.dto.response.ApiResponse;
import Talataa.E_Commerce.dto.response.InventariosResponse;
import Talataa.E_Commerce.dto.response.ProductosResponse;
import Talataa.E_Commerce.repository.InventoryRepository;
import Talataa.E_Commerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    ResponseApiBuilderService responseApiBuilderService;

    public ApiResponse<String> getAllInventarios() {
        try {
            List<InventariosResponse> inventario = this.inventoryRepository.getAllInventarios();
            if (inventario != null && !inventario.isEmpty()) {
                return this.responseApiBuilderService.successRespuesta(inventario, "INVENTARIOS");
            } else {
                return this.responseApiBuilderService.errorRespuesta("INVENTORY_NOT_FOUND");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta("SERVER_ERROR");
        }
    }

    // Guardar inventario
    public ApiResponse<String> guardarInventario(InventarioRequest inventario) {
        try {
            Integer row = this.inventoryRepository.guardarInventario(inventario);
            if (row != null && row > 0) {
                return this.responseApiBuilderService.successRespuesta(
                        "El inventario se guardó correctamente en el sistema",
                        "INVENTARIO"
                );
            } else {
                return this.responseApiBuilderService.errorRespuestaPersonalizado(
                        404,
                        "ERROR_SAVE",
                        "Ocurrió un error al guardar el inventario. Por favor, intenta nuevamente o contacta al administrador."
                );
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta("SERVER_ERROR");
        }
    }

    // Actualizar inventario
    public ApiResponse<String> actualizarInventario(InventarioRequest inventario) {
        try {
            Integer row = this.inventoryRepository.actualizarInventario(inventario);
            if (row != null && row > 0) {
                return this.responseApiBuilderService.successRespuesta(
                        "El inventario se actualizó correctamente en el sistema",
                        "INVENTARIO"
                );
            } else {
                return this.responseApiBuilderService.errorRespuestaPersonalizado(
                        404,
                        "ERROR_UPDATE",
                        "Ocurrió un error al actualizar el inventario. Por favor, intenta nuevamente o contacta al administrador."
                );
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta("SERVER_ERROR");
        }
    }


    // Inactivar inventario
    public ApiResponse<String> inactivarInventario(Integer idProducto) {
        try {
            Integer row = this.inventoryRepository.inactivarInventario(idProducto);
            if(row != null && row > 0) {
                return this.responseApiBuilderService.successRespuesta(
                        "El inventario se ha borrado con éxito.",
                        "INVENTARIO"
                );
            } else {
                return this.responseApiBuilderService.errorRespuestaPersonalizado(
                        404,
                        "ERROR_DELETE",
                        "No se pudo Borrar el inventario. Intente de nuevo o consulte al soporte."
                );
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta("SERVER_ERROR");
        }
    }

}
