import { ChangeDetectorRef, Component, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { CompraRequest } from 'src/model/Ordenes/Request/CompraRequestModel';
import { DetalleOrdenRequest } from 'src/model/Ordenes/Request/DetalleOrdenRequestModel';
import { ProductosModel } from 'src/model/Productos/Response/ProductosModel';
import { LocalStorageService } from 'src/service/local-storage.service';
import { OrdenCompraService } from 'src/service/Ordenes/orden-compra.service';
import { ProductosService } from 'src/service/Productos/productos-service';

@Component({
  selector: 'app-mis-compras',
  templateUrl: './mis-compras.component.html',
  styleUrls: ['./mis-compras.component.css']
})
export class MisComprasComponent {

  constructor(private cdr: ChangeDetectorRef, private ordenService: OrdenCompraService,
    private messageService: MessageService, private localStorage: LocalStorageService, private fb: FormBuilder,
    private router: Router
  ) {
    this.formTarjeta = this.fb.group({
      nombreTitular: ['', [Validators.required, Validators.minLength(3)]],
      numeroTarjeta: ['', [Validators.required, Validators.pattern('[0-9]{16}')]],
      fechaExpiracion: ['', [Validators.required, Validators.pattern('[0-9]{2}/[0-9]{2}')]],
      cvv: ['', [Validators.required, Validators.pattern('[0-9]{3,4}')]]
    });
  }

  public listProductosAgregados: ProductosModel[] = [];
  public formTarjeta!: FormGroup;


  ngOnInit() {
    const productosAgregados: ProductosModel[] = this.localStorage.getList("ProductosAgregados") || [];
    if (productosAgregados) {
      this.listProductosAgregados = productosAgregados;
      console.log("productos guardados en el save : " + JSON.stringify(this.listProductosAgregados))
    }
  }

  ngAfterViewInit() {
    this.cdr.detectChanges();
  }

  calcularTotal(): number {
    return this.listProductosAgregados.reduce((total, product) => {
        return total + ((product.productoPrecioUnidad ?? 0) * (product.cantidadSeleccionada ?? 0));
    }, 0);
}

  eliminarProducto(product: ProductosModel) {
    this.listProductosAgregados = this.listProductosAgregados.filter(
      productDelete => productDelete.idProducto !== product.idProducto
    );
    this.localStorage.updateCompleteList("ProductosAgregados", this.listProductosAgregados);
    this.cdr.detectChanges();
  }

  public precioTotalSinDescuento: number = 0;
  public precioTotalConDescuento: number = 0;


  enviarCompra() {
    // 1. Validar autenticación
    const auth:any = this.localStorage.get("currentUser");
    console.log("auth : "+auth)
    if (!this.formTarjeta.valid || !auth?.id || !auth?.nombreCompleto) {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: 'Por favor, complete todos los campos requeridos'
      });
      return;
    }
  
    // 2. Procesar productos y calcular totales
    const detalleCotizacion: DetalleOrdenRequest[] = [];
    let subTotal = 0;
    const DESCUENTO_PORCENTAJE = 10;
  
    this.listProductosAgregados.forEach(producto => {
      // Convertir datos a números
      const cantidad = Number(producto.cantidadSeleccionada);
      const precioUnitario = Number(producto.productoPrecioUnidad);
      
      // Calcular precio por producto
      const precioProducto = cantidad * precioUnitario;
      subTotal += precioProducto;
  
      // Agregar detalle del producto
      detalleCotizacion.push({
        idProducto: Number(producto.idProducto),
        cantidadProducto: cantidad,
        unidadPrecio: precioUnitario,
        subTotal:subTotal
      });
    });
  
    // 3. Calcular totales finales
    const descuentoTotal = (subTotal * DESCUENTO_PORCENTAJE) / 100;
    const totalFinal = subTotal - descuentoTotal;
  
    // 4. Preparar datos de pago
    const datosPago = {
      pago: {
        nombreTitular: this.formTarjeta.get('nombreTitular')?.value, 
        numeroTarjeta: this.formTarjeta.get('numeroTarjeta')?.value,
        fechaExpiracion: this.formTarjeta.get('fechaExpiracion')?.value,
        cvv: this.formTarjeta.get('cvv')?.value
      },
      orden: {
        idUsuario: auth.id,
        subTotalCompra: subTotal,
        totalCompra: totalFinal,
        descuento: descuentoTotal
      },
      detailsOrden: detalleCotizacion
    };
  
    
    console.log("datos de la compra : "+JSON.stringify(datosPago))
    // 5. Enviar al servicio (ejemplo)
    this.procesarOrden(datosPago);
  }
  

   async procesarOrden(datosPago: any) {
    try {
      const response = await this.ordenService.procesarCompra(datosPago).toPromise();
      
      if (response?.data && response?.data['ORDEN_COMPRA']) {
        // Guardar datos de la orden si es necesario
        const ordenProcesada = response.data['ORDEN_COMPRA'];
        
        this.messageService.add({
          severity: 'success',
          summary: 'Éxito',
          detail: `Orden fue procesada correctamente`
        });

        const currentUrl = this.router.url;
        this.router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
            this.router.navigate([currentUrl]);
        });
        this.cdr.detectChanges();
            this.localStorage.remove("ProductosAgregados");
      } else {
        this.messageService.add({
          severity: 'warn',
          summary: 'Advertencia',
          detail: 'No se pudo procesar la orden. Verifique los datos e intente nuevamente.'
        });
      }
    } catch (error: any) {
      console.error("Error al procesar la orden:", error?.details);
      this.messageService.add({
        severity: 'error',
        summary: 'Error al procesar orden',
        detail: error?.details || 'Ocurrió un error al procesar la orden'
      });
      throw error; // Re-lanzar el error si necesitas manejarlo en el componente padre
    }
  }


  get userName(): string {
    const user = localStorage.getItem("currentUser");
    return user ? JSON.parse(user).nombreCompleto : '';
  }





}
