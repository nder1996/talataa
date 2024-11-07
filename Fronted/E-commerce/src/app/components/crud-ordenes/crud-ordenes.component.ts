import { ChangeDetectorRef, Component, ViewEncapsulation } from '@angular/core';
import { MessageService } from 'primeng/api';
import { LocalStorageService } from 'src/service/local-storage.service';
import { OrdenCompraService } from 'src/service/Ordenes/orden-compra.service';
import { ProductosService } from 'src/service/Productos/productos-service';
import { OrdenModel } from 'src/model/Ordenes/Response/OrdenModel';
import { DetalleXOrdenModel } from 'src/model/Ordenes/Response/DetalleXOrdenModel';
import { Router } from '@angular/router';

@Component({
  selector: 'app-crud-ordenes',
  templateUrl: './crud-ordenes.component.html',
  styleUrls: ['./crud-ordenes.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class CrudOrdenesComponent {

  constructor(private cdr: ChangeDetectorRef, private ordenService: OrdenCompraService,
    private messageService: MessageService, private localStorage: LocalStorageService , private productoService:ProductosService,
    private router: Router
  ) { }



  public listaOrdenCompra:OrdenModel[]=[];
  public listaDetallesCompra:DetalleXOrdenModel[]=[];

  public showModalEliminar:boolean = false;

  public showTablaDetalleOrden:boolean=false;
  public showModalEliminarDetalle:boolean = false;

  async ngOnInit() {
    await this.getAllOrdenesDisponibles();
  }

  ngAfterViewInit() {
    this.cdr.detectChanges();
  }



  async abrirModalDetalleOrden(orden:OrdenModel){
    if(orden && orden.idOrden ){
      await this.getAllDetalleXOrdenDisponibles(orden.idOrden);
      this.showTablaDetalleOrden = true;
    }

  }
  
 

  public idEliminarOrden:number=0;
  async inactivarOrden() {
    try {
      const response = await this.ordenService.inactivarOrden(this.idEliminarOrden).toPromise();
      if (response?.data) {
        const msj: any = response.data;
        this.messageService.add({
          severity: 'success',
          summary: 'Operación Exitosa',
          detail:'La Orden de Compra fue Borrado con Exito'
        });
        const currentUrl = this.router.url;
        this.router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
            this.router.navigate([currentUrl]);
        });
        this.cdr.detectChanges();
        this.showModalEliminar = false;
      } else {
        this.messageService.add({
          severity: 'warn',
          summary: 'Advertencia',
          detail: 'Ocurrió un error al Borrar laorden. Por favor, intenta nuevamente o contacta al administrador.'
        });
      }
    } catch (error: any) {
      console.error("Error al consumir la API:", error);
      const errorMessage = error?.error?.details || 'Ocurrió un error desconocido a lborrar la orden.';
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: errorMessage
      });
    }
  }

  public idEliminarDetalle:number=0;
  async inactivarDetalle() {
    try {
      const response = await this.ordenService.inactivarDetallesOrden(this.idEliminarDetalle).toPromise();
      if (response?.data) {
        const msj: any = response.data;
        this.messageService.add({
          severity: 'success',
          summary: 'Operación Exitosa',
          detail:'el detalle de la compra fue Borrado con Exito'
        });
        const currentUrl = this.router.url;
        this.router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
            this.router.navigate([currentUrl]);
        });
        this.cdr.detectChanges();
        this.showModalEliminar = false;
      } else {
        this.messageService.add({
          severity: 'warn',
          summary: 'Advertencia',
          detail: 'Ocurrió un error al Borrar los detalles de la orden. Por favor, intenta nuevamente o contacta al administrador.'
        });
      }
    } catch (error: any) {
      console.error("Error al consumir la API:", error);
      const errorMessage = error?.error?.details || 'Ocurrió un error desconocido al Borrar los detalles de la orden.';
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: errorMessage
      });
    }
  }
  



  async getAllOrdenesDisponibles() {
    try {
      const response = await this.ordenService.getAllOrdenesDisponibles().toPromise();
      if (response?.data && response?.data['ORDEN_DISPONIBLE']) {
        this.listaOrdenCompra = response.data['ORDEN_DISPONIBLE'];
      } else {
        this.messageService.add({
          severity: 'warn',
          summary: '',
          detail: 'No se encontraron Ordenes disponibles.'
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
  
  async getAllDetalleXOrdenDisponibles(idOrden:number) {
    try {
      const response = await this.ordenService.getAllDetalleXOrdenDisponibles(idOrden).toPromise();
      if (response?.data && response?.data['DETALLESXORDEN']) {
        this.listaDetallesCompra = response.data['DETALLESXORDEN'];
      } else {
        this.messageService.add({
          severity: 'warn',
          summary: '',
          detail: 'No se encontraron Ordenes disponibles.'
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
  

}
