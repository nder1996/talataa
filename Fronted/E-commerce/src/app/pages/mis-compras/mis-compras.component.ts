import { ChangeDetectorRef, Component,ViewEncapsulation } from '@angular/core';
import { MessageService } from 'primeng/api';
import { ProductosModel } from 'src/model/Productos/Response/ProductosModel';
import { LocalStorageService } from 'src/service/local-storage.service';
import { ProductosService } from 'src/service/Productos/productos-service';

@Component({
  selector: 'app-mis-compras',
  templateUrl: './mis-compras.component.html',
  styleUrls: ['./mis-compras.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class MisComprasComponent {

  constructor(private cdr: ChangeDetectorRef, private productoService: ProductosService,
    private messageService: MessageService, private localStorage: LocalStorageService
  ) { }

  public listProductosAgregados:ProductosModel[] = [];

  ngOnInit() {
    const productosAgregados:ProductosModel[] = this.localStorage.getList("ProductosAgregados") || [] ;
    if(productosAgregados){
      this.listProductosAgregados = productosAgregados;
    }
    //console.log("productos : "+JSON.stringify(productosAgregados))

   // this.getAllProductos();
  }

  ngAfterViewInit() {
    this.cdr.detectChanges();
  }



}
