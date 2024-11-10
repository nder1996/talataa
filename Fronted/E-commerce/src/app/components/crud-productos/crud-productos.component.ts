import { ChangeDetectorRef, Component, ViewChild, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { Table } from 'primeng/table';
import { ProductoRequestModel } from 'src/model/Productos/Request/ProductoRequestModel';
import { CategoriaProductoModel } from 'src/model/Productos/Response/CategoriaProductoModel';
import { ProductosModel } from 'src/model/Productos/Response/ProductosModel';
import { LocalStorageService } from 'src/service/local-storage.service';
import { ProductosService } from 'src/service/Productos/productos-service';
import { ReferenceDataService } from 'src/service/ReferenceData/reference-data.service';
import { UsuariosService } from 'src/service/Usuarios/usuarios.service';

@Component({
  selector: 'app-crud-productos',
  templateUrl: './crud-productos.component.html',
  styleUrls: ['./crud-productos.component.css']
})
export class CrudProductosComponent {

  constructor(private cdr: ChangeDetectorRef, private productoService: ProductosService,
    private messageService: MessageService, private localStorage: LocalStorageService,
    private referenciaService: ReferenceDataService, private fb: FormBuilder
  ) {
    this.productoForm = this.fb.group({
      id: [null],
      nombre: ['', [Validators.required]],
      descripcion: ['', [Validators.required]],
      idCategoriaProducto: [null, Validators.required],
      precio: [null, [Validators.required,]]
    });
  }

  public listaProducto: ProductosModel[] = [];
  public listaCategoriaProducto: CategoriaProductoModel[] = [];
  public productoForm!: FormGroup;
  


  public showModalAgregar: boolean = false;
  public showModalEditar: boolean = false;
  public showModalEliminar: boolean = false;
  /* */
  public idEliminarProducto: number = 0;


  async ngOnInit() {
    await this.getAllProductos();
  }

  ngAfterViewInit() {
    this.cdr.detectChanges();
  }

  @ViewChild('dt') table!: Table;
  clear() {
    this.table.clear();
  }



  async cargarDatosModalEditar(producto: ProductosModel) {
    await this.getAllCategoriaProducto();
    this.productoForm.patchValue({
      id: producto.idProducto,
      nombre: producto.productoNombre,
      descripcion: producto.productoDescripcion,
      idCategoriaProducto: producto.idCategoriaProducto,
      precio: producto.productoPrecioUnidad
    });
    //console.log("datos modal editar : " + JSON.stringify(producto))
    this.cdr.detectChanges();
  }




  async cargarDatosModalAgregar() {
    await this.getAllCategoriaProducto();
    this.productoForm.patchValue({
      id: null,
      nombre: null,
      descripcion: null,
      idCategoriaProducto: null,
      precio: null
    });
    this.cdr.detectChanges();
  }


  async actualizarProducto() {
    const formValues = this.productoForm.value; // Asegúrate de que `productForm` sea tu formulario de producto
    const productoRequest: ProductoRequestModel = {
      id: formValues.id,
      nombre: formValues.nombre,
      descripcion: formValues.descripcion,
      idCategoriaProducto: formValues.idCategoriaProducto,
      precioUnidad: formValues.precio
    };
    console.log("actualizar Producto : " + JSON.stringify(productoRequest))
    await this.updateProducto(productoRequest);
  }


  async saveRegistroProducto() {
    try {
      if (this.productoForm.valid) {
        const formValues = this.productoForm.value;
        const productoRequest: ProductoRequestModel = {
          id: formValues.id,
          nombre: formValues.nombre,
          descripcion: formValues.descripcion,
          idCategoriaProducto: formValues.idCategoriaProducto,
          precioUnidad: formValues.precio
        };
        console.log("PRODUCTO GUARDADO : "+JSON.stringify(productoRequest))
        const response = await this.productoService.guardarProductoXCategoria(productoRequest).toPromise();

        if (response?.data && response.data['PRODUCTO']) {
          const msj: string = response.data['PRODUCTO'];
          this.messageService.add({
            severity: 'success',
            summary: 'Registro Exitoso',
            detail: msj
          });
          this.showModalAgregar = false;
          this.ngOnInit(); // O el método que uses para recargar la lista
        } else {
          this.messageService.add({
            severity: 'warn',
            summary: 'Advertencia',
            detail: 'Ocurrió un error al guardar el registro. Por favor, intenta nuevamente o contacta al administrador.'
          });
         await this.cargarDatosModalAgregar()
        }
      } else {
        Object.keys(this.productoForm.controls).forEach(key => {
          const control = this.productoForm.get(key);
          if (control?.invalid) {
            control.markAsTouched();
          }
        });
      }
    } catch (error: any) {
      const errorMessage = error?.error?.details || 'Ocurrió un error desconocido al guardar el registro.';
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: errorMessage
      });
    }
  }


  async updateProducto(producto: ProductoRequestModel) {
    try {
      const response = await this.productoService.actualizarProducto(producto).toPromise();
      if (response?.data && response.data['PRODUCTO']) {
        const msj: string = response.data['PRODUCTO'];
        this.messageService.add({
          severity: 'success',
          summary: 'Actualización Exitosa',
          detail: msj
        });
        this.showModalEditar = false; // Si usas un modal para editar productos
        this.ngOnInit(); // Recarga los datos actualizados en la vista
      } else {
        this.messageService.add({
          severity: 'warn',
          summary: 'Advertencia',
          detail: 'Ocurrió un error al actualizar el producto. Por favor, intenta nuevamente o contacta al administrador.'
        });
      }
    } catch (error: any) {
      const errorMessage = error?.error?.details || 'Ocurrió un error desconocido al actualizar el producto.';
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: errorMessage
      });
    }
  }


  async inactivarRegistroProducto() {
    try {
      const response = await this.productoService.inactivarProducto(this.idEliminarProducto).toPromise();
      if (response?.data && response?.data['PRODUCTO']) {
        const msj: string = response?.data['PRODUCTO'];
        this.messageService.add({
          severity: 'success',
          summary: 'Operación Exitosa',
          detail: msj || 'Producto borrar correctamente'
        });
        this.showModalEliminar = false;
        this.ngOnInit();
      } else {
        this.messageService.add({
          severity: 'warn',
          summary: 'Advertencia',
          detail: 'Ocurrió un error al Borrar el producto. Por favor, intenta nuevamente o contacta al administrador.'
        });
      }
    } catch (error: any) {
      console.error("Error al consumir la API:", error);
      const errorMessage = error?.error?.details || 'Ocurrió un error desconocido al Borrar el producto.';
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: errorMessage
      });
    }
  }

  async getAllProductos() {
    try {
      const response = await this.productoService.getAllProductoDisponibles().toPromise();
      if (response?.data && response?.data['PRODUCTOS']) {
        this.listaProducto = response.data['PRODUCTOS'];
     
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
        summary: 'Error al cargar productos',
        detail: error?.details
      });
    }
  }

  async getAllCategoriaProducto() {
    try {
      const response = await this.productoService.getAllCategoriasProductos().toPromise();
      if (response?.data && response?.data['CATEGORIAS']) {
        this.listaCategoriaProducto = response.data['CATEGORIAS'];
        console.log("categorias : "+JSON.stringify( this.listaCategoriaProducto))
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
        summary: 'Error al consumir la Api',
        detail: error?.details
      });
    }
  }


}
