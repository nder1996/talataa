import { ChangeDetectorRef, Component, ViewChild, ViewEncapsulation } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { Table } from 'primeng/table';
import { InventariosRequestModel } from 'src/model/Inventarios/Request/InventarioRequestModel';
import { InventariosModel } from 'src/model/Inventarios/Response/InventariosModel';
import { ProductModel } from 'src/model/Productos/Response/ProductMode';
import { ProductosModel } from 'src/model/Productos/Response/ProductosModel';
import { InventarioService } from 'src/service/Inventarios/inventario.service';
import { LocalStorageService } from 'src/service/local-storage.service';
import { ProductosService } from 'src/service/Productos/productos-service';
import { ReferenceDataService } from 'src/service/ReferenceData/reference-data.service';

@Component({
  selector: 'app-crud-inventarios',
  templateUrl: './crud-inventarios.component.html',
  styleUrls: ['./crud-inventarios.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class CrudInventariosComponent {

  constructor(private cdr: ChangeDetectorRef, private inventarioService: InventarioService,
    private messageService: MessageService, private localStorage: LocalStorageService , private productoService:ProductosService,
    private fb: FormBuilder,
  ) {
    this.inventarioForm = this.fb.group({
      idInventario: ['', [Validators.required]],
      idProducto: [null, [Validators.required]],
      cantidad: [null, [Validators.required, Validators.min(1)]],
      nombreProducto: ['',],
      descripcionProducto: ['',],
    });
  }

  public listaInventario: InventariosModel[] = [];
  public listaProducto: ProductModel[] = [];

  public showModalAgregar: boolean = false;
  public showModalEditar: boolean = false;
  public showModalEliminar: boolean = false;

  /* */
  public idEliminarInventario: number = 0;
  public inventarioForm: FormGroup;



  async ngOnInit() {
    await this.getAllInventarios();
  }

  ngAfterViewInit() {
    this.cdr.detectChanges();
  }

  
  @ViewChild('dt') table!: Table;
  clear() {
    this.table.clear();
  }


  aplicarFiltroGlobal(event: Event) {
    const element = event.target as HTMLInputElement;
    this.table.filterGlobal(element.value, 'contains');
  }
  
  async abrirModalEditar(inventory:InventariosModel){
    await this.getAllProductos();
    this.cdr.detectChanges();
    this.inventarioForm.patchValue({
      idInventario:inventory.idInventario,
      idProducto:inventory.idProducto,
      cantidad: inventory.cantidadDisponible,
      nombreProducto: inventory.productoNombre,
      descripcionProducto: inventory.productoDescripcion,
    });
   // console.log("datos modal editar : " + JSON.stringify( this.inventarioForm.value))
    this.inventarioForm.get('descripcionProducto')?.disable();
    this.inventarioForm.get('nombreProducto')?.disable();
    this.inventarioForm.get('idProducto')?.disable();
    this.cdr.detectChanges();

  }


  async actualizarRegistroInventario() {
    try {
      // Obtenemos los valores del formulario
      if(this.inventarioForm.value){
        const formValue = this.inventarioForm.value;
      
        // Creamos el objeto con la estructura requerida
        const inventarioRequest: InventariosRequestModel = {
          idInventario: formValue.idInventario,
          idProducto: formValue.idProducto,
          cantidadDisponible: formValue.cantidad
          // Agrega aquí otros campos si son necesarios
        };
  
        const response = await this.inventarioService.actualizarInventario(inventarioRequest).toPromise();
        
        if (response?.data && response.data['INVENTARIO']) {
          const msj: string = response.data['INVENTARIO'];
          this.messageService.add({
            severity: 'success',
            summary: 'Actualización Exitosa',
            detail: msj || 'Inventario actualizado correctamente'
          });
          
          this.showModalEditar = false;
          this.inventarioForm.reset();
          this.ngOnInit(); // Recarga los datos
        } else {
    
          this.messageService.add({
            severity: 'warn',
            summary: 'Advertencia',
            detail: 'Ocurrió un error al actualizar el inventario. Por favor, intenta nuevamente o contacta al administrador.'
          });
        }
      }

  
    } catch (error: any) {
      console.error('Error al actualizar inventario:', error);
      const errorMessage = error?.error?.details || 'Ocurrió un error desconocido al actualizar el inventario.';
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: errorMessage
      });
    }
  }


  async inactivarRegistroInventario() {
    try {    
      const response = await this.inventarioService.inactivarInventario(this.idEliminarInventario).toPromise();
      if (response?.data && response?.data['INVENTARIO']) {
        const msj: string = response?.data['INVENTARIO'];
        this.messageService.add({
          severity: 'success',
          summary: 'Operación Exitosa',
          detail: msj || 'Inventario Borrado correctamente'
        });
        this.showModalEliminar = false;
        this.ngOnInit();
      } else {
        this.messageService.add({
          severity: 'warn',
          summary: 'Advertencia',
          detail: 'Ocurrió un error al Borrar el inventario. Por favor, intenta nuevamente o contacta al administrador.'
        });
      }
    } catch (error: any) {
      console.error("Error al consumir la API:", error);
      const errorMessage = error?.error?.details || 'Ocurrió un error desconocido al Borrar el inventario.';
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: errorMessage
      });
    }
  }



  async getAllInventarios() {
    try {
      const response = await this.inventarioService.getAllInventarios().toPromise();
      if (response?.data && response?.data['INVENTARIOS']) {
        this.listaInventario = response.data['INVENTARIOS'];
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
        summary: 'Error al cargar los datos del inventario',
        detail: error?.details
      });
    }
  }
  


  async getAllProductos() {
    try {
      const response = await this.productoService.getAllProductos().toPromise();
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


 
}
