package Talataa.E_Commerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorDetailResponse {


    private Integer codeHttp;
    private String codeName;
    private String codeDescripcion;

    public static final List<ErrorDetailResponse> ERRORS = Arrays.asList(
            // Errores de Autenticación y Usuario (400, 401, 403)
            new ErrorDetailResponse(401, "INVALID_CREDENTIALS", "Las credenciales proporcionadas son inválidas. Por favor, verifica tu nombre de usuario y contraseña."),
            new ErrorDetailResponse(403, "UNAUTHORIZED_ACCESS", "No tienes los permisos necesarios para acceder a este recurso."),
            new ErrorDetailResponse(400, "USER_ALREADY_EXISTS", "Ya existe un usuario registrado con este correo electrónico o nombre de usuario."),
            new ErrorDetailResponse(400, "INVALID_USER_DATA", "Los datos del usuario proporcionados no son válidos o están incompletos."),

            // Errores de Productos (404, 400)
            new ErrorDetailResponse(404, "PRODUCT_NOT_FOUND", "El producto solicitado no existe en el sistema. Por favor, verifica el identificador."),
            new ErrorDetailResponse(404, "NO_PRODUCTS_FOUND", "No se encontraron productos que coincidan con los criterios de búsqueda."),
            new ErrorDetailResponse(400, "INVALID_PRODUCT_DATA", "Los datos del producto proporcionados no son válidos o están incompletos."),
            new ErrorDetailResponse(400, "PRODUCT_OUT_OF_STOCK", "El producto solicitado no tiene existencias disponibles."),
            new ErrorDetailResponse(400, "PRODUCTS_NOT_FOUND", "No existen productos registrados en la base de datos."),

            // Errores de Inventario (400, 404)
            new ErrorDetailResponse(400, "INSUFFICIENT_STOCK", "No hay suficiente stock disponible para completar la orden."),
            new ErrorDetailResponse(404, "INVENTORY_NOT_FOUND", "No se encontró el registro de inventario para el producto especificado."),
            new ErrorDetailResponse(400, "INVALID_INVENTORY_UPDATE", "La actualización de inventario no es válida. La cantidad no puede ser negativa."),

            // Errores de Órdenes (400, 404)




            // Errores de Descuentos y Promociones (400)
            new ErrorDetailResponse(400, "DISCOUNT_NOT_APPLICABLE", "El descuento solicitado no es aplicable a esta orden."),
            new ErrorDetailResponse(400, "PROMOTION_EXPIRED", "La promoción o descuento ha expirado."),
            new ErrorDetailResponse(400, "INVALID_DISCOUNT_TIME", "El descuento temporal no está disponible en este momento."),

            // Errores de Búsqueda y Filtrado (400)
            new ErrorDetailResponse(400, "INVALID_SEARCH_CRITERIA", "Los criterios de búsqueda proporcionados no son válidos."),
            new ErrorDetailResponse(400, "INVALID_FILTER_PARAMETERS", "Los parámetros de filtrado proporcionados no son válidos."),

            // Errores de Categorías (404, 400)
            new ErrorDetailResponse(404, "CATEGORY_NOT_FOUND", "La categoría solicitada no existe en el sistema."),
            new ErrorDetailResponse(400, "INVALID_CATEGORY_DATA", "Los datos de la categoría no son válidos o están incompletos."),

            // Errores de Auditoría (500)
            new ErrorDetailResponse(500, "AUDIT_LOG_ERROR", "Error al registrar la auditoría de la operación."),

            // Errores del Sistema (500)
            new ErrorDetailResponse(500, "DATABASE_ERROR", "Error en la base de datos al procesar la solicitud."),
            new ErrorDetailResponse(500, "SERVER_ERROR", "Error interno del servidor al procesar la solicitud."),
            new ErrorDetailResponse(500, "SERVICE_UNAVAILABLE", "El servicio no está disponible en este momento. Por favor, inténtalo más tarde."),

            // Error general del servidor (500)
            new ErrorDetailResponse(500, "SERVER_ERROR", "Error en el servidor al procesar la solicitud. Por favor, inténtelo más tarde."),

            // Errores para Ciudad (400, 404)
            // Error general del servidor (500)
            new ErrorDetailResponse(500, "SERVER_ERROR", "Error en el servidor al procesar la solicitud. Por favor, inténtelo más tarde."),

            // Errores para Ciudad (400, 404)
            new ErrorDetailResponse(404, "CITY_NOT_FOUND", "No se encontró la ciudad especificada en el sistema."),
            new ErrorDetailResponse(400, "NO_CITY_DATA", "No existen datos de la ciudad en el sistema."),

            // Errores para Género (400, 404)
            new ErrorDetailResponse(404, "GENDER_NOT_FOUND", "No se encontró el género especificado en el sistema."),
            new ErrorDetailResponse(400, "NO_GENDER_DATA", "No existen datos del género en el sistema."),

            // Errores para Tipo de Documento (400, 404)
            new ErrorDetailResponse(404, "DOCUMENT_TYPE_NOT_FOUND", "No se encontró el tipo de documento especificado en el sistema."),
            new ErrorDetailResponse(400, "NO_DOCUMENT_TYPE_DATA", "No existen datos del tipo de documento en el sistema."),

            // Errores para Rol (400, 404)
            new ErrorDetailResponse(404, "ROLE_NOT_FOUND", "No se encontró el rol especificado en el sistema."),
            new ErrorDetailResponse(400, "NO_ROLE_DATA", "No existen datos del rol en el sistema."),

            // Errores para Rol (400, 404)
            new ErrorDetailResponse(404, "RECORD_NOT_FOUND", "No se encontró el registro relacionado al ID especificado."),
     new ErrorDetailResponse(404, "USER_NOT_FOUND", "No se encontró ningún registro de usuarios."),
     new ErrorDetailResponse(400, "UPDATE_FAILED", "No se pudo actualizar el registro."),
            new ErrorDetailResponse(400, "DELETE_FAILED", "No se pudo eliminar el registro del usuario."),


     new ErrorDetailResponse(
            404,
                    "CATEGORY_NOT_FOUND",
                    "No se encontró la categoría de producto especificada."),
    new ErrorDetailResponse(
            404,
                    "NO_PRODUCT_CATEGORIES",
                    "No existen categorías de productos en el sistema."),

    new ErrorDetailResponse(
        404,
                "INVENTORY_NOT_FOUND",
                "No se encontraron datos en el inventario."),
new ErrorDetailResponse(
        404,
                "INVENTORY_ITEM_NOT_FOUND",
                "El item buscado en el inventario no se encontró."),
            new ErrorDetailResponse(
                    404,
                    "ORDER_NOT_FOUND",
                    "No se encontró la orden especificada."),
            new ErrorDetailResponse(
                    404,
                    "NO_ORDERS_FOUND",
                    "No existen órdenes en el sistema."),
            new ErrorDetailResponse(
                    404,
                    "DETAIL_ORDER_NOT_FOUND",
                    "No se encontró el detalle de orden especificado."),
            new ErrorDetailResponse(
                    404,
                    "NO_DETAILS_FOUND",
                    "No existen detalles de orden en el sistema."),
            new ErrorDetailResponse(
                    404,
                    "NO_DETAILS_FOUND_FOR_ORDER",
                    "No existen detalles para la orden especificada.")



    );
    //

}
