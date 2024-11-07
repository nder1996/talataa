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
import { GenerateReportComponent } from './components/generate-report/generate-report.component';


const routes: Routes = [
  { path: '', redirectTo: 'principal', pathMatch: 'full' },
  {
    path: 'dashboard', component: DashboardComponent, children: [
      { path: 'modulo-usuario', component: CrudUsuariosComponent },
      { path: 'modulo-inventario', component: CrudInventariosComponent },
      { path: 'modulo-producto', component: CrudProductosComponent },
      { path: 'modulo-orden', component: CrudOrdenesComponent },
      { path: 'modulo-reporte', component: GenerateReportComponent } 
    ]
  },
 
  { path: 'loading', component: LoadingComponent },    
  { path: 'principal', component: PaginaPrincipalComponent } ,
  { path: 'mis-compras', component: MisComprasComponent },
  //
  { path: 'modulo-usuario', component: CrudUsuariosComponent },
  { path: 'modulo-inventario', component: CrudInventariosComponent },
  { path: 'modulo-producto', component: CrudProductosComponent },
  { path: 'modulo-orden', component: CrudOrdenesComponent },
  { path: 'modulo-reporte', component: GenerateReportComponent }        
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
