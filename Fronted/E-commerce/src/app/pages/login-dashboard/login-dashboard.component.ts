import { ChangeDetectorRef, Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { AuthService } from 'src/service/auth.service';
import { LocalStorageService } from 'src/service/local-storage.service';
import { ReferenceDataService } from 'src/service/ReferenceData/reference-data.service';
import { UsuariosService } from 'src/service/Usuarios/usuarios.service';

@Component({
  selector: 'app-login-dashboard',
  templateUrl: './login-dashboard.component.html',
  styleUrls: ['./login-dashboard.component.css']
})
export class LoginDashboardComponent {
  loginForm: FormGroup;
  isLoading = false;
  showPassword = false;

  constructor(private cdr: ChangeDetectorRef, private userService: UsuariosService,
    private messageService: MessageService, private localStorage: LocalStorageService,
    private referenciaService: ReferenceDataService, private fb: FormBuilder,
    private authService:AuthService,   private router: Router
  ) { 
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', Validators.required]
    });
  }

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  public userSession:string="";
  async login() {
    try {
      // Check if form is valid before proceeding
      if (this.loginForm.valid) {
        const credentials = {
          username: this.loginForm.get('username')?.value,
          password: this.loginForm.get('password')?.value
        };


      await this.authService.loginAdmin(credentials.username,credentials.password)

      this.router.navigate(['/dashboard']);

      console.log("loginAdmin : "+JSON.stringify(this.localStorage.get("currentUserAdmin")))

        const userSession:any =  this.localStorage.get("currentUserAdmin");
      
        
        
        if(userSession && userSession.id && userSession.nombreCompleto){
          this.messageService.add({
            severity: 'success',
            summary: 'Login Exitoso',
            detail: 'Bienvenido al sistema'
          });
          //this.userSession = userSession.nombreCompleto;
          this.router.navigate(['/dashboard']);

        }else{
          this.messageService.add({
            severity: 'warn',
            summary: 'Advertencia',
            detail: 'Credenciales inválidas. Por favor, verifica tus datos.'
          });
          this.userSession ="";
        }
      } else {
        this.messageService.add({
          severity: 'warn',
          summary: 'Advertencia',
          detail: 'Por favor, complete todos los campos requeridos.'
        });
      }
    } catch (error: any) {
      const errorMessage = error?.error?.details || 'Error al intentar iniciar sesión. Por favor, intente nuevamente.';
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: errorMessage
      });
    }
  }

  get userName(): string {
    const user = localStorage.getItem("currentUser");
    return user ? JSON.parse(user).nombreCompleto : '';
  }
}
