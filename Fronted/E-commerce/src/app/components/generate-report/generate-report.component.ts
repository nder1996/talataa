import { Component } from '@angular/core';
import { GenerateReportService } from 'src/service/generate-report.service';



@Component({
  selector: 'app-generate-report',
  templateUrl: './generate-report.component.html',
  styleUrls: ['./generate-report.component.css']
})
export class GenerateReportComponent {

  constructor(private pdfService: GenerateReportService) {}

  descargarPDF() {
    this.pdfService.generatePDF('contenidoParaPDF', 'mi-documento.pdf');
  }
}

