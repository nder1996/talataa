import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoadingComponent } from './components/loading/loading.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { CrudUsuariosComponent } from './components/crud-usuarios/crud-usuarios.component';
import { CrudInventariosComponent } from './components/crud-inventarios/crud-inventarios.component';
import { CrudProductosComponent } from './components/crud-productos/crud-productos.component';
import { CrudOrdenesComponent } from './components/crud-ordenes/crud-ordenes.component';
import { PaginaPrincipalComponent } from './pages/pagina-principal/pagina-principal.component';
import { MisComprasComponent } from './pages/mis-compras/mis-compras.component';
import { ReportesProductosActivosComponent } from './components/reportes-productos-activos/reportes-productos-activos.component';
import { ReporteTopProductosComponent } from './components/reporte-top-productos/reporte-top-productos.component';
import { ReporteClienteFrecuenteComponent } from './components/reporte-cliente-frecuente/reporte-cliente-frecuente.component';
import { LoginDashboardComponent } from './pages/login-dashboard/login-dashboard.component';
import { AuthGuard } from './auth/guards/auth.guard';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'principal',
    pathMatch: 'full'
  },
  {
    path: 'principal',
    component: PaginaPrincipalComponent
  },
  {
    path: 'login',
    component: LoginDashboardComponent
  },
  {
    path: 'loading',
    component: LoadingComponent
  },
  {
    path: 'mis-compras',
    component: MisComprasComponent
  },

  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    children: [
      {
        path: 'modulo-usuario',
        component: CrudUsuariosComponent
      },
      {
        path: 'modulo-inventario',
        component: CrudInventariosComponent
      },
      {
        path: 'modulo-producto',
        component: CrudProductosComponent
      },
      {
        path: 'modulo-orden',
        component: CrudOrdenesComponent
      },
      {
        path: 'reporte-productos-activos',
        component: ReportesProductosActivosComponent
      },
      {
        path: 'reporte-top-productos',
        component: ReporteTopProductosComponent
      },
      {
        path: 'reporte-cliente-frecuente',
        component: ReporteClienteFrecuenteComponent
      },
    ]
  },

  // Ruta para manejar rutas no encontradas
  {
    path: '**',
    redirectTo: 'principal'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
