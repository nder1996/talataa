import { Component } from '@angular/core';
import { GenerateReportService } from 'src/service/generate-report.service';


interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  stock: number;
  category: {
    name: string;
    description: string;
  },
  quantity_sold: number;
}
@Component({
  selector: 'app-reporte-top-productos',
  templateUrl: './reporte-top-productos.component.html',
  styleUrls: ['./reporte-top-productos.component.css']
})
export class ReporteTopProductosComponent {
  constructor(private reporteService: GenerateReportService) { }

  products: Product[] = [];
  loading: boolean = true;

  currentDate: Date = new Date();

  // ... resto del código del componente ...

  getTotalValue(): number {
    return this.products.reduce((total, product) => total + (product.price * product.stock), 0);
  }

  ngOnInit() {
    // Simulamos una carga de datos
    setTimeout(() => {
      this.products = [
        {
          "id": 1,
          "name": "Laptop HP Pavilion",
          "description": "Laptop HP Pavilion 15.6 pulgadas, 8GB RAM, 256GB SSD",
          "price": 899.99,
          "stock": 15,
          "category": {
            "name": "Computadoras",
            "description": "Equipos de cómputo y accesorios"
          },
          "quantity_sold": 23
        },
        {
          "id": 2,
          "name": "Smartphone Samsung Galaxy S21",
          "description": "Samsung Galaxy S21 128GB, 8GB RAM, Pantalla AMOLED",
          "price": 799.99,
          "stock": 28,
          "category": {
            "name": "Teléfonos",
            "description": "Teléfonos móviles y accesorios"
          },
          "quantity_sold": 45
        },
        {
          "id": 3,
          "name": "Monitor LG UltraWide",
          "description": "Monitor LG UltraWide 34 pulgadas, 3440x1440, HDR",
          "price": 499.99,
          "stock": 10,
          "category": {
            "name": "Monitores",
            "description": "Pantallas y monitores para computadora"
          },
          "quantity_sold": 12
        },
        {
          "id": 4,
          "name": "Teclado Mecánico Logitech",
          "description": "Teclado mecánico gaming RGB retroiluminado",
          "price": 129.99,
          "stock": 42,
          "category": {
            "name": "Periféricos",
            "description": "Accesorios y periféricos para computadora"
          },
          "quantity_sold": 67
        },
        {
          "id": 5,
          "name": "Impresora Canon PIXMA",
          "description": "Impresora multifuncional inalámbrica con scanner",
          "price": 159.99,
          "stock": 20,
          "category": {
            "name": "Impresoras",
            "description": "Impresoras y consumibles"
          },
          "quantity_sold": 34
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
