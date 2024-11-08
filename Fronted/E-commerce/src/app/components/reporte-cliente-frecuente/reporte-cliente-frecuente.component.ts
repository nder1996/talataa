import { Component } from '@angular/core';
import { GenerateReportService } from 'src/service/generate-report.service';


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
  constructor(private reporteService: GenerateReportService) { }

  clientes: Clientes[] = [];
  loading: boolean = true;

  currentDate: Date = new Date();

  // ... resto del código del componente ...

  getTotalValue(): number {
    return this.clientes.reduce((total, cliente) => {
      return total + cliente.totalGastado;
    }, 0);
  }

  ngOnInit() {
    // Simulamos una carga de datos
    setTimeout(() => {
      this.clientes = [
        {
          "nombreCliente": "María Rodríguez",
          "numeroCompras": 23,
          "totalGastado": 500
          },
          {
          "nombreCliente": "Juan Pérez",
          "numeroCompras": 15,
          "totalGastado": 755
          },
          {
          "nombreCliente": "Ana García",
          "numeroCompras": 42,
          "totalGastado": 9501
          },
          {
          "nombreCliente": "Carlos López",
          "numeroCompras": 8,
          "totalGastado": 74158
          },
          {
          "nombreCliente": "Laura Martínez",
          "numeroCompras": 31,
          "totalGastado": 96523
          }
      ]
      this.loading = false;
    }, 1000); // Simulamos 1 segundo de carga
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
