package Talataa.E_Commerce.repository;


import Talataa.E_Commerce.dto.request.UsuarioRequest;
import Talataa.E_Commerce.dto.response.UsuariosResponse;
import Talataa.E_Commerce.model.Ciudad;
import Talataa.E_Commerce.model.Usuario;
import Talataa.E_Commerce.model.UsuarioRol;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserRepository {

    @Select("select useri.id as userId, tipoDocumento.id as idTipoDocumento , genero.id as idGenero ,ciudad.id as idCiudad , rol.id as idRol  " +
            " , CONCAT(Nombres, ' ', Apellidos) AS nombreCompleto ,tipoDocumento.descripcion as tipoDocumento, useri.numeroDocumento as numeroDocumento ,genero.descripcion genero, ciudad.nombre  as ciudad,useri.correoElectronico as email, useri.direccion as dirreccion , useri.telefono as telefono , rol.nombre as rolNombre , useri.NombreUsuario , useri.Nombres, useri.Apellidos " +
            "from USUARIOS as useri " +
            "left join TIPO_DOCUMENTO as tipoDocumento on useri.idTipoDocumento = tipoDocumento.id " +
            "left join GENEROS as genero on useri.idGenero = genero.id " +
            "left join CIUDADES as ciudad on useri.idCiudad = ciudad.id " +
            "left join USUARIOS_ROLES as userRol on useri.id = userRol.idUsuario " +
            "left join ROLES as rol on userRol.idRol = rol.id " +
            "where  " +
            "useri.estado = 'A'")
    List<UsuariosResponse> getAllUsuarios();


    @Insert("INSERT INTO USUARIOS (idCiudad, idTipoDocumento, idGenero, numeroDocumento, estado, create_at, update_at, correoElectronico, direccion, Nombres, Apellidos, telefono, nombreUsuario, contraseña) " +
            "VALUES (#{idCiudad}, #{idTipoDocumento}, #{idGenero}, #{numeroDocumento}, 'A', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, #{email}, #{dirreccion}, #{nombres}, #{apellidos}, #{telefono}, #{nombreUsuario}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "id")
    Integer guardarUsuario(UsuarioRequest usuario);

    @Insert("INSERT INTO USUARIOS_ROLES (idUsuario, idRol) VALUES (#{idUsuario}, #{idRol})")
    Integer guardarRolUsuario(@Param("idUsuario") Integer idUsuario, @Param("idRol") Integer idRol);

    /**/
    @Update("UPDATE USUARIOS SET estado = 'I', update_at = CURRENT_TIMESTAMP WHERE id = #{id}")
    Integer InactivarUsuario(@Param("id") Integer id);

    @Update("UPDATE USUARIOS SET idCiudad = #{idCiudad}, idTipoDocumento = #{idTipoDocumento}, idGenero = #{idGenero}, numeroDocumento = #{numeroDocumento}, update_at = CURRENT_TIMESTAMP, correoElectronico = #{email}, direccion = #{dirreccion}, Nombres = #{nombres}, Apellidos = #{apellidos}, telefono = #{telefono}, nombreUsuario = #{nombreUsuario} WHERE id = #{userId}")
    Integer actualizarUsuario(UsuarioRequest usuario);

    @Update("UPDATE USUARIOS_ROLES SET idUsuario = #{idUsuario}, idRol = #{idRol} WHERE id = #{id}")
    Integer actualizarRolUsuario(@Param("id") Integer id, @Param("idUsuario") Integer idUsuario, @Param("idRol") Integer idRol);

    /**/
    @Select("select * from USUARIOS as useri where " +
            "useri.estado = 'A' and useri.nombreUsuario = #{username} and useri.contraseña = #{password}")
    Usuario BuscarUserActivo(@Param("username") String username, @Param("password") String password);

}
