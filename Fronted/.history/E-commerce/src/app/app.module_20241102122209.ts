import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { CrudUsuarioComponent } from './components/crud-usuario/crud-usuario.component';
import { CrudOrdenesComponent } from './components/crud-ordenes/crud-ordenes.component';
import { CrudProductosComponent } from './components/crud-productos/crud-productos.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardComponent,
    CrudUsuarioComponent,
    CrudOrdenesComponent,
    CrudProductosComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
