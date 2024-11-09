import { Component } from '@angular/core';
import { MessageService } from 'primeng/api';
import { ReporteProductoMasVendidoModel } from 'src/model/Reportes-Dashboard/ReporteProductoMasVendidoModel';
import { GenerateReportService } from 'src/service/generate-report.service';
import { ReporteDashboardService } from 'src/service/reporte-dashboard.service';



@Component({
  selector: 'app-reporte-top-productos',
  templateUrl: './reporte-top-productos.component.html',
  styleUrls: ['./reporte-top-productos.component.css']
})
export class ReporteTopProductosComponent {
  constructor(private reporteService: GenerateReportService,
    private reporteDashboard: ReporteDashboardService,
    private messageService: MessageService
  ) { }

  listaProductosMasVendidos: ReporteProductoMasVendidoModel[] = [];


  currentDate: Date = new Date();

  // ... resto del código del componente ...



  async ngOnInit() {
    await this.cargarProductosMasVendidos();

  }


  async cargarProductosMasVendidos() {
    try {
      const response = await this.reporteDashboard.getProductosMasVendidos().toPromise();
      if (response?.data && response?.data['TOP_PRODUCTOS']) {
        this.listaProductosMasVendidos = response.data['TOP_PRODUCTOS'];
      } else {
        this.messageService.add({
          severity: 'warn',
          summary: 'Sin Productos Vendidos',
          detail: 'No se encontraron productos vendidos.'
        });
      }
    } catch (error: any) {
      console.error("Error al consumir la API:", error?.details);
      this.messageService.add({
        severity: 'error',
        summary: 'Error al cargar productos más vendidos',
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
