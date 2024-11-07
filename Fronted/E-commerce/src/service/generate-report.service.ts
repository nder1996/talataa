import { Injectable } from '@angular/core';
import { jsPDF } from 'jspdf';
import html2canvas from 'html2canvas';


@Injectable({
  providedIn: 'root'
})
export class GenerateReportService {

  constructor() { }

  async generatePDF(elementId: string, filename: string = 'documento.pdf') {
    // Obtener el elemento HTML
    const element = document.getElementById(elementId);
    
    if (!element) {
      console.error('Elemento no encontrado');
      return;
    }

    try {
      // Convertir el HTML a canvas
      const canvas = await html2canvas(element, {
        scale: 2,
        useCORS: true,
        logging: false
      });

      // Dimensiones del PDF (A4)
      const imgWidth = 210; // A4 width en mm
      const pageHeight = 297; // A4 height en mm
      const imgHeight = canvas.height * imgWidth / canvas.width;
      
      // Crear el PDF
      const pdf = new jsPDF('p', 'mm', 'a4');
      
      // Añadir la imagen del canvas al PDF
      pdf.addImage(canvas.toDataURL('image/png'), 'PNG', 0, 0, imgWidth, imgHeight);
      
      // Descargar el PDF
      pdf.save(filename);
    } catch (error) {
      console.error('Error al generar el PDF:', error);
    }
  }
}
