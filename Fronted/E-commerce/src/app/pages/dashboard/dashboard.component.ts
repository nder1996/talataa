import { Component, ElementRef } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { filter } from 'rxjs';
import { AuthService } from 'src/service/auth.service';

declare var bootstrap: any;

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {

  constructor(private elementRef: ElementRef, private auth: AuthService, private router: Router) {}

  currentRouteMessage: string = '';
  showMessage: boolean = false;
  items: MenuItem[] = [];
  home: MenuItem | undefined;
  private readonly LAST_ROUTE_KEY = 'lastDashboardRoute';


  // Mapping of route paths to friendly names
  private routeNames: { [key: string]: string } = {
    'dashboard': 'Panel Principal',
    'modulo-usuario': 'Gestión de Usuarios',
    'modulo-inventario': 'Control de Inventario',
    'modulo-producto': 'Gestión de Productos',
    'modulo-orden': 'Gestión de Órdenes',
    'reporte-productos-activos': 'Productos Activos',
    'reporte-top-productos': 'Top Productos',
    'reporte-cliente-frecuente': 'Clientes Frecuentes'
  };


  ngOnInit(): void {
   this.home = {
      icon: 'pi pi-home',
      routerLink: '/dashboard'
    };

    // Restaurar última ruta
    this.restoreLastRoute();

    // Suscribirse a eventos de navegación con el tipo correcto
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd && event.urlAfterRedirects.includes('/dashboard')) {
        this.saveLastRoute(event.urlAfterRedirects);
        this.updateBreadcrumb();
      }
    });

    // Evento beforeunload
    window.addEventListener('beforeunload', () => {
      this.saveLastRoute(this.router.url);
    });

    // Evento beforeunload
    window.addEventListener('beforeunload', () => {
      this.saveLastRoute(this.router.url);
    });


    const sidebarToggle = this.elementRef.nativeElement.querySelector('#sidebar-toggle');
    if (sidebarToggle) {
      sidebarToggle.addEventListener('click', () => {
        this.toggleSidebar();
      });
    }
  }

  private restoreLastRoute(): void {
    const savedRoute = localStorage.getItem(this.LAST_ROUTE_KEY);
    if (savedRoute) {
      // Verificar que la ruta sea válida
      if (savedRoute.startsWith('/dashboard')) {
        this.router.navigate([savedRoute]).then(() => {
          this.updateBreadcrumb();
        }).catch(() => {
          // Si falla la navegación, ir a una ruta por defecto
          this.router.navigate(['/dashboard/modulo-usuario']);
        });
      } else {
        // Si la ruta no es válida, ir a una ruta por defecto
        this.router.navigate(['/dashboard/modulo-usuario']);
      }
    } else {
      // Si no hay ruta guardada, ir a una ruta por defecto
      this.router.navigate(['/dashboard/modulo-usuario']);
    }
  }

  private updateBreadcrumb(): void {
    const currentRoute = this.router.url;
    const segments = currentRoute.split('/').filter(segment => segment);
    
    this.items = segments.map((segment, index) => ({
      label: this.routeNames[segment] || this.formatLabel(segment),
      routerLink: '/' + segments.slice(0, index + 1).join('/'),
      command: () => {
        const path = '/' + segments.slice(0, index + 1).join('/');
        this.saveLastRoute(path);
      }
    }));

    // Actualizar mensaje si es necesario
    const currentSegment = segments[segments.length - 1];
    this.currentRouteMessage = this.routeNames[currentSegment] || this.formatLabel(currentSegment);
    this.showMessage = !!currentSegment;
  }

  private saveLastRoute(route: string): void {
      localStorage.setItem(this.LAST_ROUTE_KEY, route);
  }
  private formatLabel(segment: string): string {
    return segment.charAt(0).toUpperCase() + 
           segment.slice(1).toLowerCase().replace(/-/g, ' ');
  }



  private toggleSidebar(): void {
    const sidebar = this.elementRef.nativeElement.querySelector('#sidebar');
    if (sidebar) {
      sidebar.classList.toggle('collapsed');
    }
  }

  handleThemeToggle(): void {
    this.toggleLocalStorage();
    this.toggleRootClass();
  }

  private toggleRootClass(): void {
    const current = document.documentElement.getAttribute('data-bs-theme') as 'dark' | 'light' || 'light';
    const inverted: 'dark' | 'light' = current === 'dark' ? 'light' : 'dark';
    document.documentElement.setAttribute('data-bs-theme', inverted);
  }

  private toggleLocalStorage(): void {
    if (this.isLight()) {
      localStorage.removeItem('light');
    } else {
      localStorage.setItem('light', 'set');
    }
  }

  private isLight(): boolean {
    return !!localStorage.getItem('light');
  }

  // Es buena práctica limpiar los event listeners
  ngOnDestroy(): void {
    const sidebarToggle = this.elementRef.nativeElement.querySelector('#sidebar-toggle');
    if (sidebarToggle) {
      sidebarToggle.replaceWith(sidebarToggle.cloneNode(true));
    }
  }

  logout() {
    this.auth.logoutAdmin();
  }

  /* */


}
