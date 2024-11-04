package Talataa.E_Commerce.service;

import Talataa.E_Commerce.dto.request.UsuarioRequest;
import Talataa.E_Commerce.dto.response.ApiResponse;
import Talataa.E_Commerce.dto.response.ProductosResponse;
import Talataa.E_Commerce.dto.response.UsuariosResponse;
import Talataa.E_Commerce.model.Usuario;
import Talataa.E_Commerce.repository.ProductRepository;
import Talataa.E_Commerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ResponseApiBuilderService responseApiBuilderService;


    public ApiResponse<String> getAllUsuarios(){
        try {
            List<UsuariosResponse> users = this.userRepository.getAllUsuarios();
            if(users!=null && !users.isEmpty()){
                return this.responseApiBuilderService.successRespuesta(users, "USUARIOS");
            }else{
                return this.responseApiBuilderService.errorRespuesta("USER_NOT_FOUND");
            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta("SERVER_ERROR");
        }
    }


    public ApiResponse<String> guardarUsuariosXRol(UsuarioRequest user){
        try {
            Integer row1 = this.userRepository.guardarUsuario(user);
            if(row1!=null && row1>0){
                Integer row2 = this.userRepository.guardarRolUsuario(user.getUserId(),user.getIdRol());
                if(row2!=null && row2>0){
                    return this.responseApiBuilderService.successRespuesta("El registro se guardó correctamente en el sistema", "USUARIO");
                }else{
                    return this.responseApiBuilderService.errorRespuestaPersonalizado(404,"ERROR_SAVE","Ocurrió un error al guardar el registro. Por favor, intenta nuevamente o contacta al administrador.");
                }
            }else {
                return this.responseApiBuilderService.errorRespuestaPersonalizado(404,"ERROR_SAVE","Ocurrió un error al guardar el registro. Por favor, intenta nuevamente o contacta al administrador.");
            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta("SERVER_ERROR");
        }
    }

    public Integer guardarUsuario(UsuarioRequest user){
        try {
            Integer row = this.userRepository.guardarUsuario(user);
            if(row!=null && row>0){
                return row;
            }else{
                return 0;
            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return 0;
        }
    }


    public Integer guardarRolUsuario(Integer idRol,Integer idUsuario){
        try {
            Integer row = this.userRepository.guardarRolUsuario(idRol,idUsuario);
            if(row!=null && row>0){
                return row;
            }else{
                return 0;
            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return 0;
        }
    }

    /**/
    public ApiResponse<String> inactivarRegistroUsuario(Integer id ){
        try {
            Integer row = this.userRepository.InactivarUsuario(id);
            if(row!=null && row>0){
                return this.responseApiBuilderService.successRespuesta("El registro se ha borrado exitosamente.", "USUARIO");
            }else{
                return this.responseApiBuilderService.errorRespuestaPersonalizado(404,"ERROR_DELETE","No se pudo eliminar el registro. Intente de nuevo o consulte al soporte.");
            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta("SERVER_ERROR");
        }
    }
    /**/
    public ApiResponse<String> actualizarUsuarioYRol(UsuarioRequest user) {
        try {
            Integer row1 = this.userRepository.actualizarUsuario(user);
            if (row1 != null && row1 > 0) {
                Integer row2 = this.userRepository.actualizarRolUsuario(user.getUserId(), user.getUserId(), user.getIdRol());
                if (row2 != null && row2 > 0) {
                    return this.responseApiBuilderService.successRespuesta("El registro se actualizó correctamente en el sistema", "USUARIO");
                } else {
                    return this.responseApiBuilderService.errorRespuestaPersonalizado(404, "ERROR_UPDATE",
                            "Ocurrió un error al actualizar el rol del usuario. Por favor, intenta nuevamente o contacta al administrador.");
                }
            } else {
                return this.responseApiBuilderService.errorRespuestaPersonalizado(404, "ERROR_UPDATE",
                        "Ocurrió un error al actualizar el usuario. Por favor, intenta nuevamente o contacta al administrador.");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta("SERVER_ERROR");
        }
    }

    public Integer actualizarUsuario(UsuarioRequest user) {
        try {
            Integer row = this.userRepository.actualizarUsuario(user);
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

    public Integer actualizarRolUsuario(Integer id, Integer idUsuario, Integer idRol) {
        try {
            Integer row = this.userRepository.actualizarRolUsuario(id, idUsuario, idRol);
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
