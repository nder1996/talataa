package Talataa.E_Commerce.repository;


import Talataa.E_Commerce.dto.request.InventarioRequest;
import Talataa.E_Commerce.dto.response.InventariosResponse;
import Talataa.E_Commerce.model.Producto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface InventoryRepository {



    @Select("select inventario.id AS idInventario , product.nombre AS productoNombre, product.descripcion AS productoDescripcion , inventario.cantidadDisponible from INVENTARIO_PRODUCTOS as inventario " +
            "left join PRODUCTOS as product on inventario.idProducto = product.id " +
            "where inventario.estado = 'A' and  product.estado = 'A'")
    List<InventariosResponse> getAllInventarios();

    // INSERT para guardar en INVENTARIO_PRODUCTOS
    @Insert("INSERT INTO INVENTARIO_PRODUCTOS (" +
            "idProducto, " +
            "cantidadDisponible, " +
            "estado, " +
            "create_at, " +
            "update_at) " +
            "VALUES (" +
            "#{idProducto}, " +
            "#{cantidadDisponible}, " +
            "'A', " +
            "CURRENT_TIMESTAMP, " +
            "CURRENT_TIMESTAMP)")
    Integer guardarInventario(InventarioRequest inventario);

    // UPDATE para actualizar cantidad en INVENTARIO_PRODUCTOS
    @Update("UPDATE INVENTARIO_PRODUCTOS SET " +
            "cantidadDisponible = #{cantidadDisponible}, " +
            "update_at = CURRENT_TIMESTAMP " +
            "WHERE idProducto = #{idProducto} " +
            "AND estado = 'A'")
    Integer actualizarInventario(InventarioRequest inventario);

    // UPDATE para inactivar registro en INVENTARIO_PRODUCTOS
    @Update("UPDATE INVENTARIO_PRODUCTOS SET " +
            "estado = 'I', " +
            "update_at = CURRENT_TIMESTAMP " +
            "WHERE idProducto = #{idProducto} " +
            "AND estado = 'A'")
    Integer inactivarInventario(@Param("idProducto") Integer idProducto);
}
