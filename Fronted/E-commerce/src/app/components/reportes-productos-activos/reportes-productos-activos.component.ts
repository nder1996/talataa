import { Component, ViewEncapsulation } from '@angular/core';
import { MessageService } from 'primeng/api';
import { ReporteProductosActivosModel } from 'src/model/Reportes-Dashboard/ReporteProductosActivosModel';
import { GenerateReportService } from 'src/service/generate-report.service';
import { ReporteDashboardService } from 'src/service/reporte-dashboard.service';



interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  stock: number;
  category: {
    name: string;
    description: string;
  };
}
@Component({
  selector: 'app-reportes-productos-activos',
  templateUrl: './reportes-productos-activos.component.html',
  styleUrls: ['./reportes-productos-activos.component.css']

})
export class ReportesProductosActivosComponent {
  constructor(private reporteService: GenerateReportService,
   private reporteDashboard: ReporteDashboardService,
    private messageService: MessageService
  ) { }

  products: Product[] = [];
  loading: boolean = true;
  listaProductosActivos: ReporteProductosActivosModel[] = [];


  currentDate: Date = new Date();

  // ... resto del código del componente ...

  getTotalValue(): number {
    return this.listaProductosActivos.reduce((total, product) => 
      total + ((product.precio || 0) * (product.cantidadDisponible || 0)), 0);
  }

 async ngOnInit() {
    
    await this.cargarProductosActivos();
    //console.log("productos activos : "+JSON.stringify(this.listaProductosActivos))

  }

  async cargarProductosActivos() {
    try {
      const response = await this.reporteDashboard.getProductosActivos().toPromise();
      if (response?.data && response?.data['PRODUCTOS_ACTIVOS']) {
        this.listaProductosActivos = response.data['PRODUCTOS_ACTIVOS'];
      } else {
        this.messageService.add({
          severity: 'warn',
          summary: 'Sin Productos Activos',
          detail: 'No se encontraron productos activos.'
        });
      }
    } catch (error: any) {
      console.error("Error al consumir la API:", error?.details);
      this.messageService.add({
        severity: 'error',
        summary: 'Error al cargar productos activos',
        detail: error?.details
      });
    }
  }






  descargarPDF() {
    //this.reporteService.generateTablePDF('contenidoParaPDF', 'mi-documento.pdf');
    this.reporteService.generateTablePDF('contenidoParaPDF', 'reporte_completo.pdf', {
      orientation: 'l',
      margins: {
        top: 30,
        right: 15,
        bottom: 25,
        left: 15
      },
      pageNumbers: true,
      headerTitle: 'Reporte de Ventas',
      footerText: '© 2024 Tu Empresa - Documento Confidencial',
      showDate: true,
      showTotalPages: true,
      scale: 2,
      compress: true
    });
  }
}
