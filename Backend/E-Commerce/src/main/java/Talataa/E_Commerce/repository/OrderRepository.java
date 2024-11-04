package Talataa.E_Commerce.repository;



import Talataa.E_Commerce.dto.response.DetalleOrdenResponse;
import Talataa.E_Commerce.dto.response.OrdenResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    @Select("select detalle.id as idDetalle , detalle.idOrdenCompra as idOrden,product.id idProducto,product.nombre nombreProducto,product.descripcion ,detalle.unidadPrecio,detalle.subTotal,detalle.cantidadProducto from DETALLES_ORDEN as detalle " +
            "left join ORDEN_COMPRA as orden on detalle.idOrdenCompra = orden.id " +
            "left join PRODUCTOS as product on detalle.idProducto = product.id " +
            "where " +
            "detalle.estado = 'A' and detalle.idOrdenCompra = #{idOrden}")
    List<DetalleOrdenResponse> getAllDetalleXOrdenDisponibles(@Param("idOrden") Integer idOrden);

}
