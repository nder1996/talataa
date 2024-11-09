import { Component } from '@angular/core';
import { MessageService } from 'primeng/api';
import { ReporteClienteFrecuenteModel } from 'src/model/Reportes-Dashboard/ReporteClienteFrecuenteModel ';
import { GenerateReportService } from 'src/service/generate-report.service';
import { ReporteDashboardService } from 'src/service/reporte-dashboard.service';


interface Clientes {
  nombreCliente: string;
  numeroCompras: number;
  totalGastado: number;
}
@Component({
  selector: 'app-reporte-cliente-frecuente',
  templateUrl: './reporte-cliente-frecuente.component.html',
  styleUrls: ['./reporte-cliente-frecuente.component.css']
})
export class ReporteClienteFrecuenteComponent {
  constructor(private reporteService: GenerateReportService, private reporteDashboard: ReporteDashboardService,
    private messageService: MessageService
  ) { }

  clientes: Clientes[] = [];
  loading: boolean = true;

  currentDate: Date = new Date();

  // ... resto del código del componente ...

  getTotalValue(): number {
    return this.clientes.reduce((total, cliente) => {
      return total + cliente.totalGastado;
    }, 0);
  }

  listaClientesFrecuentes: ReporteClienteFrecuenteModel[] = [];

  async cargarClientesFrecuentes() {
    try {
      const response = await this.reporteDashboard.getClientesFrecuentes().toPromise();
      if (response?.data && response?.data['CLIENTES_FRECUENTES']) {
        this.listaClientesFrecuentes = response.data['CLIENTES_FRECUENTES'];
      } else {
        this.messageService.add({
          severity: 'warn',
          summary: 'Sin Clientes Frecuentes',
          detail: 'No se encontraron clientes frecuentes.'
        });
      }
    } catch (error: any) {
      console.error("Error al consumir la API:", error?.details);
      this.messageService.add({
        severity: 'error',
        summary: 'Error al cargar clientes frecuentes',
        detail: error?.details
      });
    }
  }

  async ngOnInit() {

   await this.cargarClientesFrecuentes();

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
function firstValueFrom(arg0: any) {
  throw new Error('Function not implemented.');
}

