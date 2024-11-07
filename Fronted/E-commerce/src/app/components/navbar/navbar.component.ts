import { ChangeDetectorRef, Component, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { CiudadModel } from 'src/model/ReferenceData/ciudadModel';
import { GeneroModel } from 'src/model/ReferenceData/generoModel';
import { RolModel } from 'src/model/ReferenceData/rolModel';
import { TipoDocumentoModel } from 'src/model/ReferenceData/tipoDocumentoModel';
import { UserRequestModel } from 'src/model/Usuarios/request/UserRequestModel';
import { LocalStorageService } from 'src/service/local-storage.service';
import { ReferenceDataService } from 'src/service/ReferenceData/reference-data.service';
import { UsuariosService } from 'src/service/Usuarios/usuarios.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class NavbarComponent {

  constructor(private cdr: ChangeDetectorRef, private userService: UsuariosService,
    private messageService: MessageService, private localStorage: LocalStorageService,
    private referenciaService: ReferenceDataService, private fb: FormBuilder
  ) { }


  public listaCiudad: CiudadModel[] = [];
  public listaGenero: GeneroModel[] = [];
  public listaTipoDocumento: TipoDocumentoModel[] = [];
  public listaRol: RolModel[] = [];
  /* */

  public showModalBienvenida: boolean = false;
  public loginForm!: FormGroup;
  public userForm!: FormGroup;
  async modalBienvenida() {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', Validators.required]
    });
    this.userForm = this.fb.group({
      nombres: [null, [Validators.required]],
      apellidos: ['', [Validators.required]],
      tipoDocumento: [null, Validators.required],
      numeroDocumento: ['', [Validators.required]],
      genero: [null, Validators.required],
      rol: [2, Validators.required],
      ciudad: [null, Validators.required],
      email: ['', [Validators.required, Validators.email]],
      direccion: ['', Validators.required],
      telefono: ['', [Validators.required]],
      nombreUsuario: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
    await this.getAllCiudad();
    await this.getAllTipoDocumento();
    await this.getAllGenero();
    await this.getAllRol();
    this.userForm.get('rol')?.disable();

  }



 async  guardarRegistro() {
    if (this.userForm.valid) {
      const formValues = this.userForm.value;
      const userRequest: UserRequestModel = {
        nombres: formValues.nombres,
        apellidos: formValues.apellidos,
        idTipoDocumento: formValues.tipoDocumento,
        idGenero: formValues.genero,
        idCiudad: formValues.ciudad,
        idRol: 2,
        email: formValues.email,
        numeroDocumento: formValues.numeroDocumento,
        dirreccion: formValues.direccion,
        telefono: formValues.telefono,
        nombreUsuario: formValues.nombreUsuario,
        password: formValues.password
      };
     // console.log("idRol : "+formValues.rol)
      // console.log("guardar e-ccomers registro user : " + JSON.stringify(userRequest))
     await this.saveRegistroUsuario(userRequest)
    } else {
      this.messageService.add({
        severity: 'warn',
        summary: '',
        detail: 'Ocurrió un error al guardar el registro. Por favor, intenta nuevamente o contacta al administrador.'
      });
    }
  }

  /* */
 

  

  async getAllTipoDocumento() {
    try {
      const response = await this.referenciaService.getAllTipoDocumento().toPromise();
      if (response?.data && response?.data['TIPO_DOCUMENTOS']) {
        this.listaTipoDocumento = response.data['TIPO_DOCUMENTOS'];
        //console.log("listaTipoDocumento : "+JSON.stringify(this.listaTipoDocumento))
      } else {
        this.messageService.add({
          severity: 'warn',
          summary: 'Sin Productos',
          detail: 'No se encontraron productos disponibles.'
        });
      }
    } catch (error: any) {
      console.error("Error al consumir la API:", error?.details);
      this.messageService.add({
        severity: 'error',
        summary: 'Error al cargar los tipo Documento',
        detail: error?.details
      });
    }
  }

  async getAllCiudad() {
    try {
      const response = await this.referenciaService.getAllCiudad().toPromise();
      if (response?.data && response?.data['CIUDADES']) {
        this.listaCiudad = response.data['CIUDADES'];
      } else {
        this.messageService.add({
          severity: 'warn',
          summary: 'Sin Productos',
          detail: 'No se encontraron productos disponibles.'
        });
      }
    } catch (error: any) {
      console.error("Error al consumir la API:", error?.details);
      this.messageService.add({
        severity: 'error',
        summary: 'Error al cargar las ciudades',
        detail: error?.details
      });
    }
  }

  async getAllGenero() {
    try {
      const response = await this.referenciaService.getAllGeneros().toPromise();
      if (response?.data && response?.data['GENEROS']) {
        this.listaGenero = response.data['GENEROS'];
      } else {
        this.messageService.add({
          severity: 'warn',
          summary: 'Sin Productos',
          detail: 'No se encontraron productos disponibles.'
        });
      }
    } catch (error: any) {
      console.error("Error al consumir la API:", error?.details);
      this.messageService.add({
        severity: 'error',
        summary: 'Error al cargar los Genero',
        detail: error?.details
      });
    }
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
  
        // Convert the Observable to Promise and await the response
        const response = await this.userService.login(
          credentials.username,
          credentials.password
        ).toPromise();
  
        if (response?.data && response?.data['Auth']) {
          // Successful login
          this.messageService.add({
            severity: 'success',
            summary: 'Login Exitoso',
            detail: 'Bienvenido al sistema'
          });
          this.showModalBienvenida = false;
          this.localStorage.save("Auth",JSON.stringify(response?.data['Auth']))
          this.userSession = response?.data['Auth'].nombreCompleto;
          //console.log("usuario loguiado : "+JSON.stringify(response?.data['Auth']))
          // Here you can handle successful login actions like:
          // - Storing the token
          // - Redirecting to dashboard
          // - Setting user session
          
        } else {
          // No data in response
          this.messageService.add({
            severity: 'warn',
            summary: 'Advertencia',
            detail: 'Credenciales inválidas. Por favor, verifica tus datos.'
          });
          this.userSession ="";
        }
      } else {
        // Form validation failed
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


  
  async saveRegistroUsuario(user: UserRequestModel) {
    try {
      const response = await this.userService.guardarUsuarioXRol(user).toPromise();
      if (response?.data && response.data['USUARIO']) {
        const msj: string = response.data['USUARIO'];
        this.messageService.add({
          severity: 'success',
          summary: 'Registro Exitoso',
          detail: msj
        });
       this.showModalBienvenida = false;
      } else {
        this.messageService.add({
          severity: 'warn',
          summary: 'Advertencia',
          detail: 'Ocurrió un error al guardar el registro. Por favor, intenta nuevamente o contacta al administrador.'
        });
      }
    } catch (error: any) {
      //console.error("Error al consumir la API:", error);
      const errorMessage = error?.error?.details || 'Ocurrió un error desconocido al guardar el registro.';
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: errorMessage
      });
    }
  }

  async getAllRol() {
    try {
      const response = await this.referenciaService.getAllRoles().toPromise();
      if (response?.data && response?.data['ROLES']) {
        this.listaRol = response.data['ROLES'];
      } else {
        this.messageService.add({
          severity: 'warn',
          summary: 'Sin Productos',
          detail: 'No se encontraron rol disponibles.'
        });
      }
    } catch (error: any) {
      console.error("Error al consumir la API:", error?.details);
      this.messageService.add({
        severity: 'error',
        summary: 'Error al cargar los roles',
        detail: error?.details
      });
    }
  }

}
