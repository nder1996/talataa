import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { enviroment } from 'src/enviroments/enviroment';
import { ApiResponse } from 'src/model/api-response.model';
import { ReporteClienteFrecuenteModel } from 'src/model/Reportes-Dashboard/ReporteClienteFrecuenteModel ';
import { ReporteProductoMasVendidoModel } from 'src/model/Reportes-Dashboard/ReporteProductoMasVendidoModel';
import { ReporteProductosActivosModel } from 'src/model/Reportes-Dashboard/ReporteProductosActivosModel';

@Injectable({
  providedIn: 'root'
})
export class ReporteDashboardService {

public apiUrl:string = enviroment.apiRestURL + 'api/reportes'

  constructor(private http: HttpClient) {}

  getClientesFrecuentes(): Observable<ApiResponse<ReporteClienteFrecuenteModel[]>> {
    return this.http.get<ApiResponse<ReporteClienteFrecuenteModel[]>>(`${this.apiUrl}/clientesFrecuentes`);
  }

  getProductosMasVendidos(): Observable<ApiResponse<ReporteProductoMasVendidoModel[]>> {
    return this.http.get<ApiResponse<ReporteProductoMasVendidoModel[]>>(`${this.apiUrl}/topProductosVendidos`);
  }

  getProductosActivos(): Observable<ApiResponse<ReporteProductosActivosModel[]>> {
    return this.http.get<ApiResponse<ReporteProductosActivosModel[]>>(`${this.apiUrl}/productosActivos`);
  }

}
