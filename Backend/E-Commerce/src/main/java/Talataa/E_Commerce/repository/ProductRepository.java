package Talataa.E_Commerce.repository;


import Talataa.E_Commerce.dto.request.ProductoRequest;
import Talataa.E_Commerce.dto.response.ProductosResponse;
import Talataa.E_Commerce.model.CategoriaProducto;
import Talataa.E_Commerce.model.Ciudad;
import Talataa.E_Commerce.model.Producto;
import Talataa.E_Commerce.model.ProductoCategoria;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductRepository {



    @Select(" select product.id as idProducto, product.nombre as productoNombre," +
            " product.descripcion as productoDescripcion , product.precioUnidad productoPrecioUnidad," +
            " categoria.id as idCategoriaProducto, categoria.nombre as nombreCategoria, categoria.id as idCategoria ,categoria.descripcion descripcionCategoria " +
            " from PRODUCTOS as product" +
            " left join PRODUCTOS_CATEGORIAS as productoXCategoria on product.id = productoXCategoria.idProducto " +
            " left join CATEGORIA_PRODUCTO as categoria on productoXCategoria.idCategoriaProducto = categoria.id" +
            " left join INVENTARIO_PRODUCTOS as inventarioProducto on product.id = inventarioProducto.idProducto " +
            " where product.estado = 'A' and inventarioProducto.estado = 'A' " +
            " and inventarioProducto.cantidadDisponible>0 order by product.nombre")
    List<ProductosResponse> todosProductosEcommer();




    @Select(" select product.id as idProducto, product.nombre as productoNombre," +
            " product.descripcion as productoDescripcion , product.precioUnidad productoPrecioUnidad," +
            " categoria.id as idCategoriaProducto, categoria.nombre as nombreCategoria, categoria.id as idCategoria ,categoria.descripcion descripcionCategoria," +
            " inventarioProducto.cantidadDisponible as cantidadDisponible , product.url_img as urlImg from PRODUCTOS as product" +
            " left join PRODUCTOS_CATEGORIAS as productoXCategoria on product.id = productoXCategoria.idProducto " +
            " left join CATEGORIA_PRODUCTO as categoria on productoXCategoria.idCategoriaProducto = categoria.id" +
            " left join INVENTARIO_PRODUCTOS as inventarioProducto on product.id = inventarioProducto.idProducto " +
            " where product.estado = 'A' and inventarioProducto.estado = 'A'" +
            " and inventarioProducto.cantidadDisponible>0 order by product.nombre")
    List<ProductosResponse> todosProductosCrud();






    /* CRUD DE PRODUCTOS */

    @Select("select * from PRODUCTOS where estado = 'A' order by nombre")
    List<Producto> getAllProductosCrud();

    @Select("select * from CATEGORIA_PRODUCTO where estado = 'A' order by nombre")
    List<CategoriaProducto> getAllCategoriasProductos();


    @Insert("INSERT INTO PRODUCTOS (" +
            "nombre, " +
            "descripcion, " +
            "precioUnidad, " +
            "estado, " +
            "create_at, " +
            "update_at) " +
            "VALUES (" +
            "#{nombre}, " +
            "#{descripcion}, " +
            "#{precioUnidad}, " +
            "'A', " +
            "CURRENT_TIMESTAMP, " +
            "CURRENT_TIMESTAMP " +
            ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer guardarProductoCrud(ProductoRequest producto);

    @Insert("INSERT INTO PRODUCTOS_CATEGORIAS (idProducto, idCategoriaProducto) VALUES (#{idProducto}, #{idCategoriaProducto})")
    Integer guardarProductoCategoriaCrud(@Param("idProducto") Integer idProducto, @Param("idCategoriaProducto") Integer idCategoriaProducto);

    //
    @Update("UPDATE PRODUCTOS SET " +
            "estado = 'I', " +
            "update_at = CURRENT_TIMESTAMP " +
            "WHERE id = #{idProducto}")
    Integer inactivarProductoCrud(@Param("idProducto") Integer idProducto);

    @Update("UPDATE PRODUCTOS SET " +
            "nombre = #{nombre}, " +
            "descripcion = #{descripcion}, " +
            "precioUnidad = #{precioUnidad}, " +
            "update_at = CURRENT_TIMESTAMP " +
            "WHERE id = #{id} ")
    Integer actualizarProductoCrud(ProductoRequest producto);

    @Update("UPDATE PRODUCTOS_CATEGORIAS SET " +
            "idCategoriaProducto = #{idCategoriaProducto} " +
            "WHERE idProducto = #{idProducto}")
    Integer actualizarProductoCategoriaCrud(@Param("idProducto") Integer idProducto, @Param("idCategoriaProducto") Integer idCategoriaProducto);
}
