import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { CrudOrdenesComponent } from './components/crud-ordenes/crud-ordenes.component';
import { CrudProductosComponent } from './components/crud-productos/crud-productos.component';
import { PaginaPrincipalComponent } from './pages/pagina-principal/pagina-principal.component';
import { LoadingComponent } from './components/loading/loading.component';
import { CrudInventariosComponent } from './components/crud-inventarios/crud-inventarios.component';
import { CrudUsuariosComponent } from './components/crud-usuarios/crud-usuarios.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { ButtonModule } from 'primeng/button';
import { PanelModule } from 'primeng/panel';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';

import { HttpClientModule } from '@angular/common/http';

import { ImageModule } from 'primeng/image';
import { DialogModule } from 'primeng/dialog';
import { MisComprasComponent } from './pages/mis-compras/mis-compras.component';
import { DataViewModule } from 'primeng/dataview';

import { InputTextModule } from 'primeng/inputtext';
import { InputMaskModule } from 'primeng/inputmask';
import { InputNumberModule } from 'primeng/inputnumber';
import { TableModule } from 'primeng/table';
import { AvatarModule } from 'primeng/avatar';
import { DropdownModule } from 'primeng/dropdown';

import { ReactiveFormsModule } from '@angular/forms';


import { InputTextareaModule } from 'primeng/inputtextarea';
import { CrudDetallesComponent } from './components/crud-detalles/crud-detalles.component';

import { TabViewModule } from 'primeng/tabview';
import { PasswordModule } from 'primeng/password';
import { SplitterModule } from 'primeng/splitter';
import { MenuModule } from 'primeng/menu';
import { ReportesProductosActivosComponent } from './components/reportes-productos-activos/reportes-productos-activos.component';
import { ReporteTopProductosComponent } from './components/reporte-top-productos/reporte-top-productos.component';
import { ReporteClienteFrecuenteComponent } from './components/reporte-cliente-frecuente/reporte-cliente-frecuente.component';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { LoginDashboardComponent } from './pages/login-dashboard/login-dashboard.component';
import { MessagesModule } from 'primeng/messages';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardComponent,
    CrudOrdenesComponent,
    CrudProductosComponent,
    PaginaPrincipalComponent,
    LoadingComponent,
    CrudInventariosComponent,
    CrudUsuariosComponent,
    NavbarComponent,
    MisComprasComponent,
    CrudDetallesComponent,
    ReportesProductosActivosComponent,
    ReporteTopProductosComponent,
    ReporteClienteFrecuenteComponent,
    LoginDashboardComponent,
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ButtonModule,
    PanelModule,
    ToastModule,
    HttpClientModule,
    ImageModule,
    DialogModule,
    DataViewModule,
    InputTextModule,
    InputMaskModule,
    InputNumberModule,
    TableModule,
    DialogModule,
    AvatarModule,
    DropdownModule,
    InputTextareaModule,
    ReactiveFormsModule,
    TabViewModule,
    PasswordModule,
    SplitterModule,
    MenuModule,
    OverlayPanelModule,
    MessagesModule
  ],
  providers: [MessageService],
  bootstrap: [AppComponent]
})
export class AppModule { }
