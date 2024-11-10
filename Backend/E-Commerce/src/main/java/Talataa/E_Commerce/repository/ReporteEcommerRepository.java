package Talataa.E_Commerce.repository;

import Talataa.E_Commerce.dto.response.ReporteClienteFrecuenteResponse;
import Talataa.E_Commerce.dto.response.ReporteProductoMasVendidoResponse;
import Talataa.E_Commerce.dto.response.ReporteProductosActivos;
import Talataa.E_Commerce.model.Ciudad;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReporteEcommerRepository {

    @Select("SELECT  " +
            "    u.nombreUsuario as nombreUsuario, " +
            "    CONCAT(u.Nombres, ' ', u.apellidos) as nombreCompleto , " +
            "    COUNT(oc.id) as totalCompras, " +
            "    SUM(oc.totalCompra) as montoTotal " +
            "FROM USUARIOS u " +
            "INNER JOIN ORDEN_COMPRA oc ON u.id = oc.idUsuario " +
            "WHERE oc.estado = 'A' " +
            "GROUP BY u.id, u.nombreUsuario, u.Nombres, u.apellidos " +
            "ORDER BY totalCompras DESC, montoTotal DESC " +
            "LIMIT 5 ")
    List<ReporteClienteFrecuenteResponse> clientesFrecuentes();

    @Select("SELECT  " +
            "    p.nombre, " +
            "    p.descripcion, " +
            "    SUM(p.precioUnidad) as totalVendido " +
            "FROM PRODUCTOS p " +
            "LEFT JOIN DETALLES_ORDEN details ON p.id = details.idProducto " +
            "LEFT JOIN ORDEN_COMPRA oc ON details.idOrdenCompra = oc.id " +
            "WHERE details.estado = 'A' " +
            "GROUP BY p.id, p.nombre, p.descripcion " +
            "ORDER BY totalVendido DESC " +
            "LIMIT 5 ")
    List<ReporteProductoMasVendidoResponse> topProductosVendidos();


    @Select("SELECT " +
            "    p.id AS id, " +
            "    p.nombre AS nombre, " +
            "    p.descripcion AS descripcion, " +
            "    i.cantidadDisponible AS cantidadDisponible, " +
            "    cp.nombre AS nombreCategoria, " +
            "    p.precioUnidad AS precio " +
            "FROM  " +
            "    PRODUCTOS p " +
            "LEFT JOIN " +
            "    PRODUCTOS_CATEGORIAS pc ON p.id = pc.idProducto " +
            "LEFT JOIN " +
            "    CATEGORIA_PRODUCTO cp ON pc.idCategoriaProducto = cp.id " +
            "LEFT JOIN " +
            "    INVENTARIO_PRODUCTOS i ON p.id = i.idProducto " +
            "LEFT JOIN  " +
            "    DETALLES_ORDEN do ON p.id = do.idProducto " +
            "WHERE " +
            "    p.estado = 'A' AND i.estado = 'A' " +
            "ORDER BY  " +
            "    p.nombre ASC")
    List<ReporteProductosActivos> productosActivos();








}
