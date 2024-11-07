import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { enviroment } from 'src/enviroments/enviroment';
import { Observable } from 'rxjs';
import { UserModel } from 'src/model/Usuarios/response/userResponseModel';
import { ApiResponse } from 'src/model/api-response.model';
import { UserRequestModel } from 'src/model/Usuarios/request/UserRequestModel';


@Injectable({
  providedIn: 'root'
})
export class UsuariosService {


  constructor(private httpClient: HttpClient) { }

  public usuarioUrl: string = enviroment.apiRestURL + 'api/user'

  public getAllUsuarios(): Observable<ApiResponse<UserModel[]>> {
    return this.httpClient.get<ApiResponse<UserModel[]>>(`${this.usuarioUrl}/getAllUsuarios`);
  }

  guardarUsuarioXRol(usuario: UserRequestModel): Observable<ApiResponse<string>> {
    return this.httpClient.post<ApiResponse<string>>(`${this.usuarioUrl}/guardarUsuarioXRol`, usuario);
  }

  inactivarUsuario(id: number): Observable<ApiResponse<string>> {
    return this.httpClient.put<ApiResponse<string>>(`${this.usuarioUrl}/inactivar/${id}`, {});
  }

  actualizarUsuario(usuarioRequest: UserRequestModel): Observable<ApiResponse<string>> {
    return this.httpClient.put<ApiResponse<string>>(`${this.usuarioUrl}/actualizarUsuarioXRol`, usuarioRequest);
  }

  login(username: string, password: string): Observable<ApiResponse<string>> {
    return this.httpClient.get<ApiResponse<string>>(`${this.usuarioUrl}/login`, {
      params: { username, password }
    });
  }

}
