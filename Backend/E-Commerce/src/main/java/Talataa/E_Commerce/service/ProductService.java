package Talataa.E_Commerce.service;

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
    ResponseApiBuilderService responseApiBuilderService;



    public ApiResponse<String> getAllProducto() {
        try {
            List<Producto> productos = this.productRepository.getAllProductos();
            if (productos != null && !productos.isEmpty()) {
                return this.responseApiBuilderService.successRespuesta(productos, "PRODUCTOS");
            } else {
                return this.responseApiBuilderService.errorRespuesta("PRODUCTS_NOT_FOUND");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta("SERVER_ERROR");
        }
    }

    public ApiResponse<String> getAllProductoDisponibles() {
        try {
            List<ProductosResponse> productos = this.productRepository.productosDisponibles();
            if (productos != null && !productos.isEmpty()) {
                return this.responseApiBuilderService.successRespuesta(productos, "PRODUCTOS");
            } else {
                return this.responseApiBuilderService.errorRespuesta("PRODUCTS_NOT_FOUND");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta("SERVER_ERROR");
        }
    }

    public ApiResponse<String> getAllCategoriasProductos() {
        try {
            List<CategoriaProducto> categorias = this.productRepository.getAllCategoriasProductos();
            if (categorias != null && !categorias.isEmpty()) {
                return this.responseApiBuilderService.successRespuesta(categorias, "CATEGORIAS");
            } else {
                return this.responseApiBuilderService.errorRespuesta("NO_PRODUCT_CATEGORIES");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta("SERVER_ERROR");
        }
    }

    public ApiResponse<String> guardarProductoYCategoria(ProductoRequest producto) {
        try {
            Integer row1 = this.guardarProducto(producto);
            if (row1 != null && row1 > 0) {
                Integer row2 = this.guardarProductoCategoria(producto.getId(), producto.getIdCategoriaProducto());
                if (row2 != null && row2 > 0) {
                    return this.responseApiBuilderService.successRespuesta(
                            "El producto se guardó correctamente en el sistema",
                            "PRODUCTO"
                    );
                } else {
                    return this.responseApiBuilderService.errorRespuestaPersonalizado(
                            404,
                            "ERROR_SAVE",
                            "Ocurrió un error al guardar la categoría del producto. Por favor, intenta nuevamente o contacta al administrador."
                    );
                }
            } else {
                return this.responseApiBuilderService.errorRespuestaPersonalizado(
                        404,
                        "ERROR_SAVE",
                        "Ocurrió un error al guardar el producto. Por favor, intenta nuevamente o contacta al administrador."
                );
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta("SERVER_ERROR");
        }
    }

    /**/
    // Guardar producto
    public Integer guardarProducto(ProductoRequest producto) {
        try {
            Integer row = this.productRepository.guardarProducto(producto);
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

    // Guardar relación producto-categoría
    public Integer guardarProductoCategoria(Integer idProducto, Integer idCategoria) {
        try {
            Integer row = this.productRepository.guardarProductoCategoria(idProducto, idCategoria);
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

    // Inactivar producto
    public ApiResponse<String> inactivarProducto(Integer id) {
        try {
            Integer row = this.productRepository.inactivarProducto(id);
            if(row != null && row > 0) {
                return this.responseApiBuilderService.successRespuesta(
                        "El producto se ha inactivado exitosamente.",
                        "PRODUCTO"
                );
            } else {
                return this.responseApiBuilderService.errorRespuestaPersonalizado(
                        404,
                        "ERROR_DELETE",
                        "No se pudo inactivar el producto. Intente de nuevo o consulte al soporte."
                );
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta("SERVER_ERROR");
        }
    }

    // Actualizar producto y su categoría
    public ApiResponse<String> actualizarProductoYCategoria(ProductoRequest producto) {
        try {
            Integer row1 = this.productRepository.actualizarProducto(producto);
            if (row1 != null && row1 > 0) {
                Integer row2 = this.productRepository.actualizarProductoCategoria(
                        producto.getId(),
                        producto.getIdCategoriaProducto()
                );
                if (row2 != null && row2 > 0) {
                    return this.responseApiBuilderService.successRespuesta(
                            "El producto se actualizó correctamente en el sistema",
                            "PRODUCTO"
                    );
                } else {
                    return this.responseApiBuilderService.errorRespuestaPersonalizado(
                            404,
                            "ERROR_UPDATE",
                            "Ocurrió un error al actualizar la categoría del producto. Por favor, intenta nuevamente o contacta al administrador."
                    );
                }
            } else {
                return this.responseApiBuilderService.errorRespuestaPersonalizado(
                        404,
                        "ERROR_UPDATE",
                        "Ocurrió un error al actualizar el producto. Por favor, intenta nuevamente o contacta al administrador."
                );
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta("SERVER_ERROR");
        }
    }

    // Actualizar solo producto
    public Integer actualizarProducto(ProductoRequest producto) {
        try {
            Integer row = this.productRepository.actualizarProducto(producto);
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

    // Actualizar solo la categoría del producto
    public Integer actualizarProductoCategoria(Integer idProducto, Integer idCategoria) {
        try {
            Integer row = this.productRepository.actualizarProductoCategoria(idProducto, idCategoria);
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
