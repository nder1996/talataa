import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { enviroment } from 'src/enviroments/enviroment';
import { ApiResponse } from 'src/model/api-response.model';
import { OrdenModel } from 'src/model/Ordenes/Response/OrdenModel';
import { DetalleXOrdenModel } from 'src/model/Ordenes/Response/DetalleXOrdenModel';

@Injectable({
  providedIn: 'root'
})
export class OrdenCompraService {

  constructor(private httpClient: HttpClient) { }

   public ordenUrl:string = enviroment.apiRestURL + 'api/order'

   public getAllOrdenesDisponibles(): Observable<ApiResponse<OrdenModel[]>> {
    return this.httpClient.get<ApiResponse<OrdenModel[]>>(`${this.ordenUrl}/getAllOrdenesDisponibles`);
  }

  getAllDetalleXOrdenDisponibles(idOrden: number): Observable<ApiResponse<DetalleXOrdenModel[]>> {
    return this.httpClient.get<ApiResponse<DetalleXOrdenModel[]>>(`${this.ordenUrl}/getAllDetalleXOrdenDisponibles/${idOrden}`);
  }


}
