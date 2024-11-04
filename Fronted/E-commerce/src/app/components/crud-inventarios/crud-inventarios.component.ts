import { ChangeDetectorRef, Component } from '@angular/core';
import { MessageService } from 'primeng/api';
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
  styleUrls: ['./crud-inventarios.component.css']
})
export class CrudInventariosComponent {

  constructor(private cdr: ChangeDetectorRef, private inventarioService: InventarioService,
    private messageService: MessageService, private localStorage: LocalStorageService , private productoService:ProductosService
  ) { }

  public listaInventario: InventariosModel[] = [];
  public listaProducto: ProductModel[] = [];

  public showModalEditar:boolean=false;
  public showModalEliminar:boolean = false;

  async ngOnInit() {
    await this.getAllInventarios();
  }

  ngAfterViewInit() {
    this.cdr.detectChanges();
  }

  
  async abrirModalEditar(){
    await this.getAllProductos();
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
