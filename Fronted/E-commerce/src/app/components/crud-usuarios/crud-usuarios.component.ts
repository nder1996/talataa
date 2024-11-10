import { ChangeDetectorRef, Component, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { CiudadModel } from 'src/model/ReferenceData/ciudadModel';
import { GeneroModel } from 'src/model/ReferenceData/generoModel';
import { RolModel } from 'src/model/ReferenceData/rolModel';
import { TipoDocumentoModel } from 'src/model/ReferenceData/tipoDocumentoModel';
import { UserRequestModel } from 'src/model/Usuarios/request/UserRequestModel';
import { UserModel } from 'src/model/Usuarios/response/userResponseModel';
import { LocalStorageService } from 'src/service/local-storage.service';
import { ReferenceDataService } from 'src/service/ReferenceData/reference-data.service';
import { UsuariosService } from 'src/service/Usuarios/usuarios.service';

@Component({
  selector: 'app-crud-usuarios',
  templateUrl: './crud-usuarios.component.html',
  styleUrls: ['./crud-usuarios.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class CrudUsuariosComponent {


  constructor(private cdr: ChangeDetectorRef, private userService: UsuariosService,
    private messageService: MessageService, private localStorage: LocalStorageService,
    private referenciaService: ReferenceDataService, private fb: FormBuilder
  ) {
    this.userForm = this.fb.group({
      nombres: [null, [Validators.required]],
      apellidos: ['', [Validators.required]],
      tipoDocumento: [null, Validators.required],
      numeroDocumento: ['', [Validators.required]],
      genero: [null, Validators.required],
      rol: [null, Validators.required],
      ciudad: [null, Validators.required],
      email: ['', [Validators.required, Validators.email]],
      direccion: ['', Validators.required],
      telefono: ['', [Validators.required]],
      nombreUsuario: ['', [Validators.required]]
    });
  }

  public listaUsuarios: UserModel[] = [];
  public showModalEditar: boolean = false;
  public showModalEliminar: boolean = false;
  public showModaAgregar: boolean = false;

  /**/
  public listaCiudad: CiudadModel[] = [];
  public listaGenero: GeneroModel[] = [];
  public listaTipoDocumento: TipoDocumentoModel[] = [];
  public listaRol: RolModel[] = [];


  async ngOnInit() {
    await this.getAllUsuarios();
  }

  ngAfterViewInit() {
    this.cdr.detectChanges();
  }



  async abrirModalEditar(usuario: UserRequestModel) {
    await this.getAllTipoDocumento();
    await this.getAllCiudad();
    await this.getAllGenero();
    await this.getAllRol();
    this.idUsuarioEliminado = usuario.userId ?? 0;
    this.userForm.patchValue({
      nombres: usuario.nombres,
      apellidos: usuario.apellidos,
      tipoDocumento: usuario.idTipoDocumento,
      numeroDocumento: usuario.numeroDocumento,
      genero: usuario.idGenero,
      rol: usuario.idRol,
      ciudad: usuario.idCiudad,
      email: usuario.email,
      direccion: usuario.dirreccion,
      telefono: usuario.telefono,
      nombreUsuario: usuario.nombreUsuario
    });
    this.editarUsuarioModal.userId = usuario.userId;
  }

  public userForm!: FormGroup;
  async abrirModalAgregar() {
    this.userForm.patchValue({
      nombres: null,
      apellidos: null,
      tipoDocumento: null,
      numeroDocumento: null,
      genero: null,
      rol: null,
      ciudad: null,
      email: null,
      direccion: null,
      telefono: null,
      nombreUsuario: null
    });

    /**/
    await this.getAllTipoDocumento();
    // alert("entro al modal editar")
    await this.getAllCiudad();
    await this.getAllGenero();
    await this.getAllRol();

  }

  guardarRegistro() {
    // alert("entro al guardar el registro")
    if (this.userForm.valid) {
      const formValues = this.userForm.value;
      const userRequest: UserRequestModel = {
        nombres: formValues.nombres,
        apellidos: formValues.apellidos,
        idTipoDocumento: formValues.tipoDocumento,
        idGenero: formValues.genero,
        idCiudad: formValues.ciudad,
        idRol: formValues.rol,
        email: formValues.email,
        numeroDocumento: formValues.numeroDocumento,
        dirreccion: formValues.direccion,
        telefono: formValues.telefono,
        nombreUsuario: formValues.nombreUsuario
      };
      // console.log("guardar registro user : " + JSON.stringify(userRequest))
      this.saveRegistroUsuario(userRequest)
    } else {
      this.messageService.add({
        severity: 'warn',
        summary: '',
        detail: 'Ocurrió un error al guardar el registro. Por favor, intenta nuevamente o contacta al administrador.'
      });
    }
  }


  async abrirModalEliminar(usuario: UserRequestModel) {
    this.idUsuarioEliminado = usuario.userId ?? 0;
  }




  public idUsuarioEliminado: number = 0;
  public editarUsuarioModal: UserRequestModel = new UserRequestModel();
  actualizarRegistroDB() {
    if (this.userForm.valid) {
      const formValues = this.userForm.value;
      const userRequest: UserRequestModel = {
        userId: this.editarUsuarioModal.userId,
        nombres: formValues.nombres,
        apellidos: formValues.apellidos,
        idTipoDocumento: formValues.tipoDocumento,
        idGenero: formValues.genero,
        idCiudad: formValues.ciudad,
        idRol: formValues.rol,
        email: formValues.email,
        numeroDocumento: formValues.numeroDocumento,
        dirreccion: formValues.direccion,
        telefono: formValues.telefono,
        nombreUsuario: formValues.nombreUsuario,
        idRolAnterior: formValues.rol

      };
      this.editarUsuarioModal = userRequest;
      this.updateUsuario();
    }

  }


  /* CONSUMO DE SERVICIOS */

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
        this.showModaAgregar = false;
        this.ngOnInit();
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

  async inactivarRegistroUsuario() {
    try {
      const response = await this.userService.inactivarUsuario(this.idUsuarioEliminado).toPromise();
      if (response?.data && response?.data['USUARIO']) {
        const msj: string = response?.data['USUARIO'];
        this.messageService.add({
          severity: 'success',
          summary: 'Operación Exitosa',
          detail: msj || 'Usuario inactivado correctamente'
        });
        this.showModalEliminar = false;
        this.ngOnInit();
      } else {
        this.messageService.add({
          severity: 'warn',
          summary: 'Advertencia',
          detail: 'Ocurrió un error al inactivar el usuario. Por favor, intenta nuevamente o contacta al administrador.'
        });
      }
    } catch (error: any) {
      console.error("Error al consumir la API:", error);
      const errorMessage = error?.error?.details || 'Ocurrió un error desconocido al inactivar el usuario.';
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: errorMessage
      });
    }
  }

  async updateUsuario() {
    try {
      const response = await this.userService.actualizarUsuario(this.editarUsuarioModal).toPromise();
      if (response?.data && response.data['USUARIO']) {
        const msj: string = response.data['USUARIO'];
        this.messageService.add({
          severity: 'success',
          summary: 'Actualización Exitosa',
          detail: msj
        });
        this.showModalEditar = false;
        this.ngOnInit();
      } else {
        this.messageService.add({
          severity: 'warn',
          summary: 'Advertencia',
          detail: 'Ocurrió un error al actualizar el registro. Por favor, intenta nuevamente o contacta al administrador.'
        });
      }
    } catch (error: any) {
      const errorMessage = error?.error?.details || 'Ocurrió un error desconocido al actualizar el registro.';
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: errorMessage
      });
    }


  }

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


  async getAllUsuarios() {
    try {
      const response = await this.userService.getAllUsuarios().toPromise();
      if (response?.data && response?.data['USUARIOS']) {
        this.listaUsuarios = response.data['USUARIOS'];
        //      console.log("lista de usuarios : " + JSON.stringify(this.listaUsuarios))
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
        summary: 'Error al cargar los usurios',
        detail: error?.details
      });
    }
  }

}
