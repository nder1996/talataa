package Talataa.E_Commerce.service;

import Talataa.E_Commerce.dto.request.UsuarioRequest;
import Talataa.E_Commerce.dto.response.ApiResponse;
import Talataa.E_Commerce.dto.response.ProductosResponse;
import Talataa.E_Commerce.dto.response.UsuariosResponse;
import Talataa.E_Commerce.model.Rol;
import Talataa.E_Commerce.model.Usuario;
import Talataa.E_Commerce.repository.ProductRepository;
import Talataa.E_Commerce.repository.ReferenceDataRepository;
import Talataa.E_Commerce.repository.UserRepository;
import Talataa.E_Commerce.utils.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReferenceDataRepository referenciaRepository;

    @Autowired
    ResponseApiBuilderService responseApiBuilderService;


    private final PasswordEncoder passwordEncoder;

    public UserService() {
        this.passwordEncoder = new PasswordEncoder();
    }


    public ApiResponse<String> UserAutenticacion(String username , String password){
        try {
            password = this.passwordEncoder.encode(password);
            Usuario users = this.userRepository.BuscarUserActivo(username,password);
            if(users!=null && users.getId()!=null){
                // Para un solo usuario
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("id", users.getId());
                userMap.put("nombreCompleto", users.getNombres() + " " + users.getApellidos());
                /**/
                return this.responseApiBuilderService.successRespuesta(userMap, "Auth");
            }else{
                return this.responseApiBuilderService.errorRespuesta(
                        404,
                        "AUTHENTICATION_FAILED",
                        "Error de autenticación: No fue posible autenticar al usuario. Verifique sus credenciales e intente nuevamente."
                );

            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta(
                    500,
                    "SERVER_ERROR",
                    "Error del servidor: Ocurrió un problema al procesar la solicitud. Por favor, inténtelo nuevamente más tarde o contacte al administrador si el problema persiste."
            );

        }
    }

    public ApiResponse<String> loginAdmin(String username , String password){
        try {
            password = this.passwordEncoder.encode(password);
            Usuario users = this.userRepository.BuscarAdmin(username,password);
            if(users!=null && users.getId()!=null){
                // Para un solo usuario
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("id", users.getId());
                userMap.put("nombreCompleto", users.getNombres() + " " + users.getApellidos());
                /**/
                return this.responseApiBuilderService.successRespuesta(userMap, "AuthAdmin");
            }else{
                return this.responseApiBuilderService.errorRespuesta(
                        404,
                        "AUTHENTICATION_FAILED",
                        "Error de autenticación: No fue posible autenticar al usuario. Verifique sus credenciales e intente nuevamente."
                );

            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta(
                    500,
                    "SERVER_ERROR",
                    "Error del servidor: Ocurrió un problema al procesar la solicitud. Por favor, inténtelo nuevamente más tarde o contacte al administrador si el problema persiste."
            );

        }
    }



    public ApiResponse<String> getAllUsuarios(){
        try {
            List<UsuariosResponse> users = this.userRepository.getAllUsuarios();
            if(users!=null && !users.isEmpty()){
                return this.responseApiBuilderService.successRespuesta(users, "USUARIOS");
            }else{
                return this.responseApiBuilderService.errorRespuesta(
                        404,
                        "USER_NOT_FOUND",
                        "No se encontraron usuarios registrados en el sistema. Verifique los criterios de búsqueda o registre un usuario e intente nuevamente."
                );

            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta(
                    500,
                    "SERVER_ERROR",
                    "Error del servidor: Ocurrió un problema inesperado. Inténtelo nuevamente más tarde o contacte al administrador si el problema persiste."
            );
        }
    }



    public ApiResponse<String> guardarUsuariosXRol(UsuarioRequest user){
        try {
            if(user.getPassword()==null || user.getPassword().isEmpty()){
                user.setPassword(this.passwordEncoder.encode("default1234567890"));
            }else{
                user.setPassword(this.passwordEncoder.encode(user.getPassword()));
            }
            Integer row1 = this.userRepository.guardarUsuario(user);
            if(row1!=null && row1>0){
                Integer row2 = this.userRepository.guardarRolUsuario(user.getUserId(),user.getIdRol());
                if(row2!=null && row2>0){
                    return this.responseApiBuilderService.successRespuesta("El registro se guardó correctamente en el sistema", "USUARIO");
                }else{
                    return this.responseApiBuilderService.errorRespuesta(
                            404,
                            "ERROR_SAVE",
                            "No se pudo guardar el registro: ocurrió un error durante el proceso de guardado. Por favor, verifica los datos e intenta nuevamente. Si el problema persiste, contacta al administrador del sistema."
                    );
                }
            }else {
                return this.responseApiBuilderService.errorRespuesta(
                        404,
                        "ERROR_SAVE",
                        "No fue posible guardar el registro. Verifica los datos ingresados e intenta nuevamente. Si el problema continúa, contacta al administrador del sistema."
                );
            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta(
                    500,
                    "SERVER_ERROR",
                    "Error del servidor: Ocurrió un problema inesperado al procesar la solicitud. Por favor, intenta nuevamente más tarde. Si el problema persiste, contacta al administrador del sistema."
            );
        }
    }


    /**/
    public ApiResponse<String> inactivarRegistroUsuario(Integer id ){
        try {
            Integer row = this.userRepository.InactivarUsuario(id);
            if(row!=null && row>0){
                return this.responseApiBuilderService.successRespuesta("El registro se ha borrado exitosamente.", "USUARIO");
            }else{
                return this.responseApiBuilderService.errorRespuesta(
                        404,
                        "ERROR_DELETE",
                        "No se pudo eliminar el registro. Por favor, inténtalo nuevamente. Si el problema persiste, contacta al soporte técnico para obtener asistencia."
                );
            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta(
                    500,
                    "SERVER_ERROR",
                    "Error en el servidor: Ocurrió un problema inesperado al procesar la solicitud. Por favor, inténtalo nuevamente. Si el error persiste, contacta al soporte técnico para obtener asistencia."
            );
        }
    }
    /**/
    public ApiResponse<String> actualizarUsuarioYRol(UsuarioRequest user) {
        try {
            Integer row1 = this.userRepository.actualizarUsuario(user);
            if (row1 != null && row1 > 0) {

               Integer idUsuarioXRol = this.userRepository.findRoleByUserAndRoleId(user.getIdRol(),user.getUserId());

                Integer row2 = this.userRepository.actualizarRolUsuario(idUsuarioXRol, user.getUserId(), user.getIdRol());
                if (row2 != null && row2 > 0) {
                    return this.responseApiBuilderService.successRespuesta("El registro se actualizó correctamente en el sistema", "USUARIO");
                } else {
                    return this.responseApiBuilderService.errorRespuesta(
                            404,
                            "ERROR_UPDATE",
                            "No se pudo actualizar el rol del usuario. Verifica los datos e intenta nuevamente. Si el problema persiste, contacta al administrador del sistema para obtener asistencia."
                    );

                }
            } else {
                return this.responseApiBuilderService.errorRespuesta(
                        404,
                        "ERROR_UPDATE",
                        "No se pudo actualizar el usuario. Por favor, verifica los datos e intenta nuevamente. Si el problema persiste, contacta al administrador del sistema para obtener asistencia."
                );

            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return this.responseApiBuilderService.errorRespuesta(
                    500,
                    "SERVER_ERROR",
                    "Error en el servidor: Ocurrió un problema inesperado al procesar la solicitud. Por favor, inténtalo nuevamente. Si el error persiste, contacta al soporte técnico para obtener asistencia."
            );

        }
    }



}
