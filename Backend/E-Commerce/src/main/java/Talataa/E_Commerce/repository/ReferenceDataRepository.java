package Talataa.E_Commerce.repository;


import Talataa.E_Commerce.model.Ciudad;
import Talataa.E_Commerce.model.Genero;
import Talataa.E_Commerce.model.Rol;
import Talataa.E_Commerce.model.TipoDocumento;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReferenceDataRepository {



    @Select("select * from CIUDADES where estado = 'A' order by nombre")
    List<Ciudad> getAllCiudad();

    @Select("select * from GENEROS where estado = 'A' order by nombre")
    List<Genero> getAllGeneros();

    @Select("select * from TIPO_DOCUMENTO where estado = 'A' order by nombre")
    List<TipoDocumento> getAllTipoDocumento();

    @Select("select * from ROLES where estado = 'A' order by nombre")
    List<Rol> getAllRoles();


}
