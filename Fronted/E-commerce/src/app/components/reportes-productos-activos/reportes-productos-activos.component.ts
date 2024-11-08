import { Component, ViewEncapsulation } from '@angular/core';
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
  };
}
@Component({
  selector: 'app-reportes-productos-activos',
  templateUrl: './reportes-productos-activos.component.html',
  styleUrls: ['./reportes-productos-activos.component.css']

})
export class ReportesProductosActivosComponent {
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
          id: 1,
          name: "Laptop HP Pavilion",
          description: "Laptop HP Pavilion 15.6\", Intel Core i5, 8GB RAM",
          price: 899.99,
          stock: 125,
          category: {
            name: "Computadoras",
            description: "Equipos de cómputo portátiles y de escritorio"
          }
        },
        {
          id: 2,
          name: "iPhone 13 Pro",
          description: "iPhone 13 Pro, 256GB, Grafito",
          price: 1099.99,
          stock: 45,
          category: {
            name: "Smartphones",
            description: "Teléfonos inteligentes de última generación"
          }
        },
        {
          id: 3,
          name: "Samsung Galaxy Watch 5",
          description: "Smartwatch Samsung Galaxy Watch 5, 44mm",
          price: 279.99,
          stock: 75,
          category: {
            name: "Accesorios",
            description: "Accesorios y complementos electrónicos"
          }
        },
        {
          id: 4,
          name: "iPad Air",
          description: "iPad Air 10.9\", 256GB, Wi-Fi",
          price: 749.99,
          stock: 92,
          category: {
            name: "Tablets",
            description: "Tablets y dispositivos móviles"
          }
        },
        {
          id: 5,
          name: "Sony WH-1000XM4",
          description: "Auriculares inalámbricos con cancelación de ruido",
          price: 349.99,
          stock: 60,
          category: {
            name: "Accesorios",
            description: "Accesorios y complementos electrónicos"
          }
        },
        {
          id: 6,
          name: "Dell XPS 13",
          description: "Dell XPS 13, Intel Core i7, 16GB RAM, 512GB SSD",
          price: 1299.99,
          stock: 30,
          category: {
            name: "Computadoras",
            description: "Equipos de cómputo portátiles y de escritorio"
          }
        },
        {
          id: 7,
          name: "Samsung Galaxy S23",
          description: "Samsung Galaxy S23 Ultra, 256GB, 5G",
          price: 1199.99,
          stock: 55,
          category: {
            name: "Smartphones",
            description: "Teléfonos inteligentes de última generación"
          }
        },
        {
          id: 8,
          name: "Apple Watch Series 8",
          description: "Apple Watch Series 8, GPS + Cellular, 45mm",
          price: 499.99,
          stock: 40,
          category: {
            name: "Accesorios",
            description: "Accesorios y complementos electrónicos"
          }
        },
        {
          id: 9,
          name: "MacBook Pro 14",
          description: "MacBook Pro 14\", M2 Pro, 16GB RAM, 512GB SSD",
          price: 1999.99,
          stock: 25,
          category: {
            name: "Computadoras",
            description: "Equipos de cómputo portátiles y de escritorio"
          }
        },
        {
          id: 10,
          name: "Google Pixel 7",
          description: "Google Pixel 7 Pro, 128GB, 5G",
          price: 899.99,
          stock: 35,
          category: {
            name: "Smartphones",
            description: "Teléfonos inteligentes de última generación"
          }
        },
        {
          id: 1,
          name: "Laptop HP Pavilion",
          description: "Laptop HP Pavilion 15.6\", Intel Core i5, 8GB RAM",
          price: 899.99,
          stock: 125,
          category: {
            name: "Computadoras",
            description: "Equipos de cómputo portátiles y de escritorio"
          }
        },
        {
          id: 2,
          name: "iPhone 13 Pro",
          description: "iPhone 13 Pro, 256GB, Grafito",
          price: 1099.99,
          stock: 45,
          category: {
            name: "Smartphones",
            description: "Teléfonos inteligentes de última generación"
          }
        },
        {
          id: 3,
          name: "Samsung Galaxy Watch 5",
          description: "Smartwatch Samsung Galaxy Watch 5, 44mm",
          price: 279.99,
          stock: 75,
          category: {
            name: "Accesorios",
            description: "Accesorios y complementos electrónicos"
          }
        },
        {
          id: 4,
          name: "iPad Air",
          description: "iPad Air 10.9\", 256GB, Wi-Fi",
          price: 749.99,
          stock: 92,
          category: {
            name: "Tablets",
            description: "Tablets y dispositivos móviles"
          }
        },
        {
          id: 5,
          name: "Sony WH-1000XM4",
          description: "Auriculares inalámbricos con cancelación de ruido",
          price: 349.99,
          stock: 60,
          category: {
            name: "Accesorios",
            description: "Accesorios y complementos electrónicos"
          }
        },
        {
          id: 6,
          name: "Dell XPS 13",
          description: "Dell XPS 13, Intel Core i7, 16GB RAM, 512GB SSD",
          price: 1299.99,
          stock: 30,
          category: {
            name: "Computadoras",
            description: "Equipos de cómputo portátiles y de escritorio"
          }
        },
        {
          id: 7,
          name: "Samsung Galaxy S23",
          description: "Samsung Galaxy S23 Ultra, 256GB, 5G",
          price: 1199.99,
          stock: 55,
          category: {
            name: "Smartphones",
            description: "Teléfonos inteligentes de última generación"
          }
        },
        {
          id: 8,
          name: "Apple Watch Series 8",
          description: "Apple Watch Series 8, GPS + Cellular, 45mm",
          price: 499.99,
          stock: 40,
          category: {
            name: "Accesorios",
            description: "Accesorios y complementos electrónicos"
          }
        },
        {
          id: 9,
          name: "MacBook Pro 14",
          description: "MacBook Pro 14\", M2 Pro, 16GB RAM, 512GB SSD",
          price: 1999.99,
          stock: 25,
          category: {
            name: "Computadoras",
            description: "Equipos de cómputo portátiles y de escritorio"
          }
        },
        {
          id: 10,
          name: "Google Pixel 7",
          description: "Google Pixel 7 Pro, 128GB, 5G",
          price: 899.99,
          stock: 35,
          category: {
            name: "Smartphones",
            description: "Teléfonos inteligentes de última generación"
          }
        }
      ];
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
