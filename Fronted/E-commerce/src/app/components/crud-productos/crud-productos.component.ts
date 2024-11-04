import { ChangeDetectorRef, Component } from '@angular/core';
import { MessageService } from 'primeng/api';
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
    private referenciaService:ReferenceDataService
  ) { }

  public listaProducto: ProductosModel[] = [];
  public listaCategoriaProducto: CategoriaProductoModel[] = [];


  public showModalEditar:boolean=false;
  public showModalEliminar:boolean = false;

  async ngOnInit() {
    await this.getAllProductos();
  }

  ngAfterViewInit() {
    this.cdr.detectChanges();
  }

  async abrirModalEditar(){
    await this.getAllCategoriaProducto();
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
