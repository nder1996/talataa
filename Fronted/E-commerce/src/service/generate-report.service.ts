import { Injectable } from '@angular/core';
import { jsPDF } from 'jspdf';
import html2canvas from 'html2canvas';
import { LoadingService } from './loading.service';


interface PdfOptions {
  orientation?: 'p' | 'l';
  margins?: {
    top: number;
    right: number;
    bottom: number;
    left: number;
  };
  pageNumbers?: boolean;
  headerTitle?: string;
  footerText?: string;
  showDate?: boolean;
  showTotalPages?: boolean;
  scale?: number;
  compress?: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class GenerateReportService {

  constructor( private loadingService:LoadingService) { }

  async generateTablePDF(
    elementId: string,
    filename: string = 'tabla.pdf',
    options: PdfOptions = {}
  ) {
    this.loadingService.startLoading();
    // Configuración por defecto
    const defaultOptions: PdfOptions = {
      orientation: 'l',
      margins: {
        top: 30,
        right: 15,
        bottom: 25,
        left: 15
      },
      pageNumbers: true,
      headerTitle: '',
      footerText: '',
      showDate: true,
      showTotalPages: true,
      scale: 2,
      compress: true
    };

    // Combinar opciones por defecto con las proporcionadas
    const finalOptions = { ...defaultOptions, ...options };
    
    const element = document.getElementById(elementId);
    if (!element) {
      console.error('Elemento no encontrado');
      return;
    }

    try {
      // Configuración del canvas
      const canvas = await html2canvas(element, {
        scale: finalOptions.scale,
        useCORS: true,
        logging: false,
        windowWidth: element.scrollWidth,
        windowHeight: element.scrollHeight
      });

      // Configurar dimensiones según orientación
      const pdfWidth = finalOptions.orientation === 'l' ? 297 : 210;
      const pdfHeight = finalOptions.orientation === 'l' ? 210 : 297;
      
      // Crear PDF
      const pdf = new jsPDF({
        orientation: finalOptions.orientation,
        unit: 'mm',
        format: 'a4',
        compress: finalOptions.compress
      });

      // Área imprimible
      const margins = finalOptions.margins!;
      const printWidth = pdfWidth - (margins.left + margins.right);
      const printHeight = pdfHeight - (margins.top + margins.bottom);
      
      // Calcular dimensiones de la imagen
      const imgWidth = printWidth;
      const imgHeight = (canvas.height * printWidth) / canvas.width;
      
      // Variables para paginación
      let heightLeft = imgHeight;
      let position = margins.top;
      let page = 1;

      // Calcular total de páginas
      const totalPages = Math.ceil(imgHeight / printHeight);

      // Función para agregar encabezado
      const addHeader = () => {
        if (finalOptions.headerTitle) {
          pdf.setFontSize(16);
          pdf.setTextColor(0);
          pdf.text(
            finalOptions.headerTitle,
            pdfWidth / 2,
            margins.top / 2,
            { align: 'center' }
          );
        }

        if (finalOptions.showDate) {
          pdf.setFontSize(10);
          pdf.setTextColor(128);
          const fecha = new Date().toLocaleDateString();
          pdf.text(
            fecha,
            pdfWidth - margins.right,
            margins.top / 2,
            { align: 'right' }
          );
        }
      };

      // Función para agregar pie de página
      const addFooter = () => {
        pdf.setFontSize(8);
        pdf.setTextColor(128);

        // Número de página
        if (finalOptions.pageNumbers) {
          const pageText = finalOptions.showTotalPages
            ? `Página ${page} de ${totalPages}`
            : `Página ${page}`;
            
          pdf.text(
            pageText,
            pdfWidth / 2,
            pdfHeight - (margins.bottom / 2),
            { align: 'center' }
          );
        }

        // Texto del pie de página
        if (finalOptions.footerText) {
          pdf.text(
            finalOptions.footerText,
            margins.left,
            pdfHeight - (margins.bottom / 2),
            { align: 'left' }
          );
        }
      };

      // Proceso de paginación
      while (heightLeft >= 0) {
        // Agregar encabezado y pie de página en cada página
        addHeader();
        addFooter();

        // Agregar contenido
        pdf.addImage(
          canvas.toDataURL('image/png'),
          'PNG',
          margins.left,
          position,
          imgWidth,
          imgHeight
        );
        
        heightLeft -= printHeight;
        
        if (heightLeft >= 0) {
          pdf.addPage();
          position = margins.top - printHeight;
          page++;
        }
      }

      // Guardar el PDF
      pdf.save(filename);

    } catch (error) {
      console.error('Error al generar el PDF:', error);
    }
    this.loadingService.stopLoading();
  }
} 
