package Talataa.E_Commerce.repository;



import Talataa.E_Commerce.dto.request.DetalleOrdenRequest;
import Talataa.E_Commerce.dto.request.OrdenCompraRequest;
import Talataa.E_Commerce.dto.request.PagoOrdenRequest;
import Talataa.E_Commerce.dto.response.DetalleOrdenResponse;
import Talataa.E_Commerce.dto.response.OrdenResponse;
import org.apache.ibatis.annotations.*;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface OrderRepository {



    @Select("select orden.id as idOrden, useri.id as idUsuario,tipoDocumento.id as idTipoDocumento,ciudad.id AS idCiudad ,genero.id as idGenero  ,CONCAT_WS(' ', useri.Nombres, useri.Apellidos) as nombreCompleto,tipoDocumento.descripcion,useri.numeroDocumento,ciudad.nombre as ciudad,useri.correoElectronico,useri.telefono,genero.descripcion as genero, " +
            "orden.subTotalCompra , orden.descuento ,orden.totalCompra , pago.estado as pagoEstado from ORDEN_COMPRA as orden " +
            "left join PAGOS_ORDEN as pago on orden.id = pago.idOrdenCompra " +
            "left join USUARIOS as useri on orden.idUsuario = useri.id " +
            "left join GENEROS as genero on useri.idGenero = genero.id " +
            "left join TIPO_DOCUMENTO as tipoDocumento on useri.idTipoDocumento = tipoDocumento.id " +
            "left join CIUDADES as ciudad on useri.idCiudad = ciudad.id " +
            "where orden.estado = 'A'")
    List<OrdenResponse> getAllOrdenDisponibles();

    @Select("SELECT COALESCE(MAX(id), 0) + 1 FROM ORDEN_COMPRA")
    Integer getNextId();

    @Select("select detalle.id as idDetalle , detalle.idOrdenCompra as idOrden,product.id idProducto,product.nombre nombreProducto,product.descripcion ,detalle.unidadPrecio,detalle.subTotal,detalle.cantidadProducto from DETALLES_ORDEN as detalle " +
            "left join ORDEN_COMPRA as orden on detalle.idOrdenCompra = orden.id " +
            "left join PRODUCTOS as product on detalle.idProducto = product.id " +
            "where " +
            "detalle.estado = 'A' and detalle.idOrdenCompra = #{idOrden}")
    List<DetalleOrdenResponse> getAllDetalleXOrdenDisponibles(@Param("idOrden") Integer idOrden);

    /**/
    @Insert("INSERT INTO ORDEN_COMPRA (idUsuario, subTotalCompra, totalCompra, estado, create_at, update_at, descuento) VALUES ( " +
    " #{idUsuario},#{subTotalCompra},#{totalCompra},'A', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,#{descuento}) ")
    @Options(useGeneratedKeys = true, keyProperty = "idOrden", keyColumn = "id")
    Integer guardarOrdenCompra(OrdenCompraRequest orden);


    @Insert("INSERT INTO PAGOS_ORDEN ( idOrdenCompra, jsonRespuesta, create_at, estado) VALUES (#{idOrdenCompra},#{jsonRespuesta}, CURRENT_TIMESTAMP,'A')")
    Integer guardarPagoOrden(PagoOrdenRequest pago);

    @Insert("INSERT INTO DETALLES_ORDEN ( idOrdenCompra, idProducto, cantidadProducto, create_at, update_at, unidadPrecio, subTotal, estado " +
    ") VALUES (#{idOrdenCompra},#{idProducto},#{cantidadProducto},CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,#{unidadPrecio},#{subTotal},'A')")
    Integer guardarDetalleOrden(DetalleOrdenRequest detalle);

    @Update("UPDATE DETALLES_ORDEN SET estado = 'I', update_at = CURRENT_TIMESTAMP WHERE id = #{idDetalle}")
    Integer inactivarRegistroDetalle(@Param("idDetalle") Integer idDetalle);

    @Update("UPDATE ORDEN_COMPRA SET estado = 'I', update_at = CURRENT_TIMESTAMP WHERE id = #{idOrdenCompra}")
    Integer inactivarRegistroOrden(@Param("idOrdenCompra") Integer idOrdenCompra);

    /**/
    @Update("UPDATE ORDEN_COMPRA SET " +
            "subTotalCompra = #{subTotalCompra}, " +
            "totalCompra = #{totalCompra}, " +
            "descuento = #{descuento}, " +
            "update_at = CURRENT_TIMESTAMP " +
            "WHERE id = #{idOrden}")
    Integer actualizarOrdenCompra(OrdenCompraRequest orden);

    @Update("UPDATE DETALLES_ORDEN SET " +
            "idProducto = #{idProducto}, " +
            "cantidadProducto = #{cantidadProducto}, " +
            "unidadPrecio = #{unidadPrecio}, " +
            "subTotal = #{subTotal}, " +
            "update_at = CURRENT_TIMESTAMP " +
            "WHERE idOrdenCompra = #{idOrdenCompra}")
    Integer actualizarDetalleOrden(DetalleOrdenRequest detalle);

}
