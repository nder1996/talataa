import { ChangeDetectorRef, Component, ViewEncapsulation } from '@angular/core';
import { Subscription } from 'rxjs';
import { ProductosService } from 'src/service/Productos/productos-service';
import { MessageService } from 'primeng/api';
import { ProductosModel } from 'src/model/Productos/Response/ProductosModel';
import { LocalStorageService } from 'src/service/local-storage.service';
import { ProductModel } from 'src/model/Productos/Response/ProductMode';


@Component({
  selector: 'app-pagina-principal',
  templateUrl: './pagina-principal.component.html',
  styleUrls: ['./pagina-principal.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class PaginaPrincipalComponent {

  constructor(private cdr: ChangeDetectorRef, private productoService: ProductosService,
    private messageService: MessageService, private localStorage: LocalStorageService
  ) { }

  private subscription!: Subscription;
  public listaProducto: ProductosModel[] = [];
  public showAgregarProducto: boolean = false;




  ngOnInit() {
    this.getAllProductos();
  }

  ngAfterViewInit() {
    this.cdr.detectChanges();
  }


  async getAllProductos() {
    try {
      const response = await this.productoService.todosProductosEcommer().toPromise();
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

 // public lstaAgregadoProducto: ProductosModel[] = [];
 
  AgregarProducto(product: ProductosModel) {
      if (!product) return;

      let productsList: ProductosModel[] = this.localStorage.getList("ProductosAgregados") || [];
      let existingProduct = productsList.find(p => p.idProducto === product.idProducto);
  
      if (existingProduct) {
        // Incrementar la cantidad seleccionada
        existingProduct.cantidadSeleccionada = (existingProduct.cantidadSeleccionada || 0) + 1;
      } else {
        // Agregar el nuevo producto
        product.cantidadSeleccionada = 1;
        productsList.push(product);
      }
  
      // Actualizar la lista en el localStorage
      this.localStorage.updateCompleteList("ProductosAgregados", productsList);

      this.messageService.add({
        severity: 'success',
        summary: '',
        detail: 'PRODUCTO AGREGADO CON ÉXITO'
      });
     // console.log("Lista de productos:", this.localStorage.getList("ProductosAgregados"));
  }


  


}
