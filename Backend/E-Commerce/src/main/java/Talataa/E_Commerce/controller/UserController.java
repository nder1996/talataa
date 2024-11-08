package Talataa.E_Commerce.controller;


import Talataa.E_Commerce.dto.request.UsuarioRequest;
import Talataa.E_Commerce.dto.response.ApiResponse;
import Talataa.E_Commerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {


    @Autowired
    UserService userService;


    @GetMapping("/getAllUsuarios")
    public ResponseEntity<ApiResponse<String>> getAllUsuarios() {
        return new ResponseEntity<>(this.userService.getAllUsuarios(), HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<ApiResponse<String>> login( @RequestHeader("username") String username,
                                                      @RequestHeader("password") String password) {
        ApiResponse<String> response = userService.UserAutenticacion(username, password);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/loginAdmin")
    public ResponseEntity<ApiResponse<String>> loginAdmin(@RequestHeader("username") String username,
                                                          @RequestHeader("password") String password) {
        ApiResponse<String> response = userService.loginAdmin(username, password);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/guardarUsuarioXRol")
    public ResponseEntity<ApiResponse<String>> guardarUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        return new ResponseEntity<>(this.userService.guardarUsuariosXRol(usuarioRequest), HttpStatus.OK);
    }



    @PutMapping("/actualizarUsuarioXRol")
    public ResponseEntity<ApiResponse<String>> actualizarUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        return new ResponseEntity<>(this.userService.actualizarUsuarioYRol(usuarioRequest), HttpStatus.OK);
    }

    @PutMapping("/inactivar/{id}")
    public ResponseEntity<ApiResponse<String>> inactivarUsuario(@PathVariable Integer id) {
        return new ResponseEntity<>(this.userService.inactivarRegistroUsuario(id), HttpStatus.OK);
    }

}
