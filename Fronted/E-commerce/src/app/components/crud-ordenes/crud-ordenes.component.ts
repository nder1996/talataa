import { ChangeDetectorRef, Component } from '@angular/core';
import { MessageService } from 'primeng/api';
import { LocalStorageService } from 'src/service/local-storage.service';
import { OrdenCompraService } from 'src/service/Ordenes/orden-compra.service';
import { ProductosService } from 'src/service/Productos/productos-service';
import { OrdenModel } from 'src/model/Ordenes/Response/OrdenModel';
import { DetalleXOrdenModel } from 'src/model/Ordenes/Response/DetalleXOrdenModel';

@Component({
  selector: 'app-crud-ordenes',
  templateUrl: './crud-ordenes.component.html',
  styleUrls: ['./crud-ordenes.component.css']
})
export class CrudOrdenesComponent {

  constructor(private cdr: ChangeDetectorRef, private ordenService: OrdenCompraService,
    private messageService: MessageService, private localStorage: LocalStorageService , private productoService:ProductosService
  ) { }



  public listaOrdenCompra:OrdenModel[]=[];
  public listaDetallesCompra:DetalleXOrdenModel[]=[];

  public showModalEditar:boolean=false;
  public showModalEliminar:boolean = false;
  public showModalAgregar:boolean = false;

  public showTablaDetalleOrden:boolean=false;
  public showModalEditarDetalle:boolean=false;
  public showModalEliminarDetalle:boolean = false;
  public showModalAgregarDetalle:boolean = false;

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
  
  async abrirModalEditar(){
  //  await this.getAllProductos();
  }


  async getAllOrdenesDisponibles() {
    try {
      const response = await this.ordenService.getAllOrdenesDisponibles().toPromise();
      if (response?.data && response?.data['ORDEN_DISPONIBLE']) {
        this.listaOrdenCompra = response.data['ORDEN_DISPONIBLE'];
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
  
  async getAllDetalleXOrdenDisponibles(idOrden:number) {
    try {
      const response = await this.ordenService.getAllDetalleXOrdenDisponibles(idOrden).toPromise();
      if (response?.data && response?.data['DETALLESXORDEN']) {
        this.listaDetallesCompra = response.data['DETALLESXORDEN'];
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
  

}
