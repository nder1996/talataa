package Talataa.E_Commerce.service;


import Talataa.E_Commerce.dto.response.ApiResponse;
import Talataa.E_Commerce.model.Ciudad;
import Talataa.E_Commerce.model.Genero;
import Talataa.E_Commerce.model.Rol;
import Talataa.E_Commerce.model.TipoDocumento;
import Talataa.E_Commerce.repository.ReferenceDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReferenceDataService {


    @Autowired
    ReferenceDataRepository referenceDataRepository;

    @Autowired
    ResponseApiBuilderService responseApiBuilderService;


    public ApiResponse<String> getAllCiudad(){
        try {
            List<Ciudad> ciudades = this.referenceDataRepository.getAllCiudad();
            if(ciudades!=null && !ciudades.isEmpty()){
                return this.responseApiBuilderService.successRespuesta(ciudades, "CIUDADES");
            }else{
                return this.responseApiBuilderService.errorRespuesta(
                        404,
                        "NO_CITY_DATA",
                        "No se encontraron datos para las ciudades solicitadas. Verifica la información e intenta nuevamente. Si el problema persiste, contacta al soporte técnico."
                );

            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta(
                    500,
                    "SERVER_ERROR",
                    "Ocurrió un error interno en el servidor. Intenta nuevamente más tarde. Si el problema persiste, contacta al soporte técnico para asistencia."
            );

        }
    }

    public ApiResponse<String> getAllGeneros(){
        try {
            List<Genero> generos = this.referenceDataRepository.getAllGeneros();
            if(generos!=null && !generos.isEmpty()){
                return this.responseApiBuilderService.successRespuesta(generos, "GENEROS");
            }else{
                return this.responseApiBuilderService.errorRespuesta(
                        404,
                        "NO_GENDER_DATA",
                        "No se encontraron datos de géneros disponibles. Verifica la información e intenta nuevamente. Si el problema persiste, contacta al soporte técnico."
                );

            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta(
                    500,
                    "SERVER_ERROR",
                    "Ocurrió un error interno en el servidor. Intenta nuevamente más tarde. Si el problema persiste, contacta al soporte técnico para asistencia."
            );
        }
    }

    public ApiResponse<String> getAllTipoDocumento(){
        try {
            List<TipoDocumento> tipoDocumento = this.referenceDataRepository.getAllTipoDocumento();
            if(tipoDocumento!=null && !tipoDocumento.isEmpty()){
                return this.responseApiBuilderService.successRespuesta(tipoDocumento, "TIPO_DOCUMENTOS");
            }else{
                return this.responseApiBuilderService.errorRespuesta(
                        404,
                        "NO_DOCUMENT_TYPE_DATA",
                        "No se encontraron tipos de documentos disponibles. Verifica la información e intenta nuevamente. Si el problema persiste, contacta al soporte técnico."
                );

            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta(
                    500,
                    "SERVER_ERROR",
                    "Ocurrió un error interno en el servidor. Intenta nuevamente más tarde. Si el problema persiste, contacta al soporte técnico para asistencia."
            );
        }
    }

    public ApiResponse<String> getAllRoles(){
        try {
            List<Rol> roles = this.referenceDataRepository.getAllRoles();
            if(roles!=null && !roles.isEmpty()){
                return this.responseApiBuilderService.successRespuesta(roles, "ROLES");
            }else{
                return this.responseApiBuilderService.errorRespuesta(
                        404,
                        "NO_ROLE_DATA",
                        "No se encontraron roles disponibles. Verifica la información e intenta nuevamente. Si el problema persiste, contacta al soporte técnico."
                );

            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta(
                    500,
                    "SERVER_ERROR",
                    "Ocurrió un error interno en el servidor. Intenta nuevamente más tarde. Si el problema persiste, contacta al soporte técnico para asistencia."
            );
        }
    }

}
