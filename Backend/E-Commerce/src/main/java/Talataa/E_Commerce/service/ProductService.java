package Talataa.E_Commerce.service;

import Talataa.E_Commerce.dto.request.InventarioRequest;
import Talataa.E_Commerce.dto.request.ProductoRequest;
import Talataa.E_Commerce.dto.response.ApiResponse;
import Talataa.E_Commerce.dto.response.ProductosResponse;
import Talataa.E_Commerce.model.CategoriaProducto;
import Talataa.E_Commerce.model.Ciudad;
import Talataa.E_Commerce.model.Producto;
import Talataa.E_Commerce.model.ProductoCategoria;
import Talataa.E_Commerce.repository.ProductRepository;
import Talataa.E_Commerce.repository.ReferenceDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {


    @Autowired
    ProductRepository productRepository;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    ResponseApiBuilderService responseApiBuilderService;




    public ApiResponse<String> todosProductosEcommer() {
        try {
            List<ProductosResponse> productos = this.productRepository.todosProductosEcommer();
            if (productos != null && !productos.isEmpty()) {
                return this.responseApiBuilderService.successRespuesta(productos, "PRODUCTOS");
            } else {
                return this.responseApiBuilderService.errorRespuesta(
                        404,
                        "PRODUCTS_NOT_FOUND",
                        "No se encontraron productos disponibles. Verifica los filtros o la información e intenta nuevamente. Si el problema persiste, contacta al soporte técnico."
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



    public ApiResponse<String> todosProductosCrud() {
        try {
            List<ProductosResponse> productos = this.productRepository.todosProductosCrud();
            if (productos != null && !productos.isEmpty()) {
                return this.responseApiBuilderService.successRespuesta(productos, "PRODUCTOS");
            } else {
                return this.responseApiBuilderService.errorRespuesta(
                        404,
                        "PRODUCTS_NOT_FOUND",
                        "No se encontraron productos disponibles. Verifica los filtros o la información e intenta nuevamente. Si el problema persiste, contacta al soporte técnico."
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














    public ApiResponse<String> getAllProductosCrud() {
        try {
            List<Producto> productos = this.productRepository.getAllProductosCrud();
            if (productos != null && !productos.isEmpty()) {
                return this.responseApiBuilderService.successRespuesta(productos, "PRODUCTOS");
            } else {
                return this.responseApiBuilderService.errorRespuesta(
                        404,
                        "PRODUCTS_NOT_FOUND",
                        "No se encontraron productos disponibles. Verifica los filtros o la información e intenta nuevamente. Si el problema persiste, contacta al soporte técnico."
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



    public ApiResponse<String> getAllCategoriasProductos() {
        try {
            List<CategoriaProducto> categorias = this.productRepository.getAllCategoriasProductos();
            if (categorias != null && !categorias.isEmpty()) {
                return this.responseApiBuilderService.successRespuesta(categorias, "CATEGORIAS");
            } else {
                return this.responseApiBuilderService.errorRespuesta(
                        404,
                        "NO_PRODUCT_CATEGORIES",
                        "No se encontraron categorías de productos disponibles. Verifica la información e intenta nuevamente. Si el problema persiste, contacta al soporte técnico."
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

    public ApiResponse<String> guardarProductoYCategoria(ProductoRequest producto) {
        try {
            Integer row1 = this.guardarProducto(producto);
            if (row1 != null && row1 > 0) {
                Integer row2 = this.guardarProductoCategoria(producto.getId(), producto.getIdCategoriaProducto());
                if (row2 != null && row2 > 0) {
                    /**/
                    InventarioRequest inventarioRequest = new InventarioRequest(null,producto.getId(),1);;
                    ApiResponse<String> respuesta =  this.inventoryService.guardarInventario(inventarioRequest);
                    /**/
                    if(respuesta.getError()==null){
                        return this.responseApiBuilderService.successRespuesta(
                                "El producto se guardó correctamente en el sistema",
                                "PRODUCTO"
                        );
                    }else{
                        return this.responseApiBuilderService.errorRespuesta(
                                404,
                                "ERROR_SAVE",
                                "No se pudo guardar la categoría del producto. Verifica los datos e intenta nuevamente. Si el problema persiste, contacta al administrador para asistencia."
                        );

                    }
                } else {
                    return this.responseApiBuilderService.errorRespuesta(
                            404,
                            "ERROR_SAVE",
                            "Ocurrió un error al guardar la categoría del producto. Por favor, intenta nuevamente o contacta al administrador."
                    );
                }
            } else {
                return this.responseApiBuilderService.errorRespuesta(
                        404,
                        "ERROR_SAVE",
                        "Ocurrió un error al guardar el producto. Por favor, intenta nuevamente o contacta al administrador."
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



    public Integer guardarProducto(ProductoRequest producto) {
        try {
            Integer row = this.productRepository.guardarProductoCrud(producto);
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


    public Integer guardarProductoCategoria(Integer idProducto, Integer idCategoria) {
        try {
            Integer row = this.productRepository.guardarProductoCategoriaCrud(idProducto, idCategoria);
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


    public ApiResponse<String> inactivarProducto(Integer id) {
        try {
            Integer row = this.productRepository.inactivarProductoCrud(id);
            if(row != null && row > 0) {
                return this.responseApiBuilderService.successRespuesta(
                        "El producto se ha borrado exitosamente.",
                        "PRODUCTO"
                );
            } else {
                return this.responseApiBuilderService.errorRespuesta(
                        404,
                        "ERROR_DELETE",
                        "No se pudo borrar el producto. Verifica los datos e intenta nuevamente. Si el problema persiste, contacta al soporte técnico para asistencia."
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

    public ApiResponse<String> actualizarProductoYCategoria(ProductoRequest producto) {
        try {
            Integer row1 = this.actualizarProducto(producto);
            if (row1 != null && row1 > 0) {
                Integer row2 = this.actualizarProductoCategoria(
                        producto.getId(),
                        producto.getIdCategoriaProducto()
                );
                if (row2 != null && row2 > 0) {
                    return this.responseApiBuilderService.successRespuesta(
                            "El producto se actualizó correctamente en el sistema",
                            "PRODUCTO"
                    );
                } else {
                    return this.responseApiBuilderService.errorRespuesta(
                            404,
                            "ERROR_UPDATE",
                            "Ocurrió un error al actualizar la categoría del producto. Por favor, intenta nuevamente o contacta al administrador."
                    );
                }
            } else {
                return this.responseApiBuilderService.errorRespuesta(
                        404,
                        "ERROR_UPDATE",
                        "Ocurrió un error al actualizar el producto. Por favor, intenta nuevamente o contacta al administrador."
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


    public Integer actualizarProducto(ProductoRequest producto) {
        try {
            Integer row = this.productRepository.actualizarProductoCrud(producto);
            if (row != null && row > 0) {
                return row;
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 0;
        }
    }



    public Integer actualizarProductoCategoria(Integer idProducto, Integer idCategoria) {
        try {
            Integer row = this.productRepository.actualizarProductoCategoriaCrud(idProducto, idCategoria);
            if (row != null && row > 0) {
                return row;
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 0;
        }
    }
}
