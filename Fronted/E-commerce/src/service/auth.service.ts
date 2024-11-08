import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, firstValueFrom, Observable, tap } from 'rxjs';
import { enviroment } from 'src/enviroments/enviroment';
import { LocalStorageService } from './local-storage.service';
import { UsuariosService } from './Usuarios/usuarios.service';
import { ApiResponse } from 'src/model/api-response.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private currentUserSubject: BehaviorSubject<any>;
  public currentUser: Observable<any>;

  public usuarioUrl: string = enviroment.apiRestURL + 'api/user'


  constructor(
    private httpClient: HttpClient,
    private router: Router,
    private localStorage: LocalStorageService,
    private userService: UsuariosService
  ) {
    this.currentUserSubject = new BehaviorSubject<any>(
      this.getUserFromStorage()
    );
    this.currentUser = this.currentUserSubject.asObservable();
  }

  // Obtener usuario del localStorage
  private getUserFromStorage() {
    const userString = this.localStorage.get('currentUser');
    if (userString) {
      try {
        return JSON.parse(userString);
      } catch {
        this.localStorage.remove('currentUser');
        return null;
      }
    }
    return null;
  }


  private getUserAdminFromStorage() {
    const userString = this.localStorage.get('currentUserAdmin');
    if (userString) {
      try {
        return JSON.parse(userString);
      } catch {
        this.localStorage.remove('currentUserAdmin');
        return null;
      }
    }
    return null;
  }



  login(username: string, password: string): Promise<void> {
    return new Promise((resolve, reject) => {
      this.userService.login(username, password).subscribe({
        next: (response: ApiResponse<string>) => {
          try {
            if (response?.data && response?.data['Auth']) {
              this.localStorage.save('currentUser', response.data['Auth']);
              resolve();
            } else {
              reject(new Error('No se recibió un token válido'));
            }
          } catch (error) {
            reject(error);
          }
        },
        error: (error) => {
          reject(error);
        }
      });
    });
  }
  


  // Versión con async/await (recomendada)
  loginAdmin(username: string, password: string): Promise<void> {
    return new Promise((resolve, reject) => {
      this.userService.loginAdmin(username, password).subscribe({
        next: (response: ApiResponse<string>) => {
          if (response?.data && response?.data['AuthAdmin']) {
           // console.log("response.data['AuthAdmin'] " + response.data['AuthAdmin']);
            this.localStorage.save('currentUserAdmin', response.data['AuthAdmin']);
            resolve();
          } else {
            reject(new Error('No AuthAdmin data found'));
          }
        },
        error: (error) => {
          console.error('Login error:', error);
          reject(error);
        }
      });
    });
  }
  
  // Verificar si hay token y está logueado
  isLoggedIn(): boolean {
    const currentUser = this.getUserFromStorage();
    return currentUser && currentUser.token ? true : false;
  }

  // Obtener el token
  getToken(): string | null {
    const currentUser = this.getUserFromStorage();
    return currentUser ? currentUser.token : null;
  }

    // Obtener el token
    getTokenAdmin(): string | null {
      const currentUser = this.getUserFromStorage();
      return currentUser ? currentUser.token : null;
    }

  // Logout
  logout(): void {
    // Limpiar localStorage
    this.localStorage.remove('currentUser');
    // Actualizar el BehaviorSubject
    this.currentUserSubject.next(null);
    // Redirigir al login
    this.router.navigate(['/principal']);
  }

  
  // Logout
  logoutAdmin(): void {
    // Limpiar localStorage
    this.localStorage.remove('currentUserAdmin');
    // Actualizar el BehaviorSubject
    this.currentUserSubject.next(null);
    // Redirigir al login
    this.router.navigate(['/login']);
  }





  // Obtener el usuario actual
  getCurrentUser(): any {
    return this.currentUserSubject.value;
  }
}
