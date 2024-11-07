import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { enviroment } from 'src/enviroments/enviroment';
import { ApiResponse } from 'src/model/api-response.model';
import { OrdenModel } from 'src/model/Ordenes/Response/OrdenModel';
import { DetalleXOrdenModel } from 'src/model/Ordenes/Response/DetalleXOrdenModel';
import { CompraRequest } from 'src/model/Ordenes/Request/CompraRequestModel';
import { DetalleOrdenRequest } from 'src/model/Ordenes/Request/DetalleOrdenRequestModel';
import { OrdenCompraRequest } from 'src/model/Ordenes/Request/OrdenCompraRequestModel';

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

  /* */
  // POST: Procesar una nueva orden de compra
  procesarCompra(request: CompraRequest): Observable<ApiResponse<string>> {
    return this.httpClient.post<ApiResponse<string>>(`${this.ordenUrl}/procesarCompra`, request);
  }

  // PUT: Actualizar una orden de compra
  actualizarOrden(ordenRequest: OrdenCompraRequest): Observable<ApiResponse<string>> {
    return this.httpClient.put<ApiResponse<string>>(`${this.ordenUrl}/ordenCompra/actualizar`, ordenRequest);
  }

  // PUT: Actualizar detalle de orden
  actualizarDetalle(detalleRequest: DetalleOrdenRequest): Observable<ApiResponse<string>> {
    return this.httpClient.put<ApiResponse<string>>(`${this.ordenUrl}/detalle/actualizar`, detalleRequest);
  }

  // PUT: Inactivar orden
  inactivarOrden(idDetalle: number): Observable<ApiResponse<string>> {
    return this.httpClient.put<ApiResponse<string>>(`${this.ordenUrl}/orden/inactivar/${idDetalle}`, {});
  }

  // PUT: Inactivar detalles de orden
  inactivarDetallesOrden(idOrden: number): Observable<ApiResponse<string>> {
    return this.httpClient.put<ApiResponse<string>>(`${this.ordenUrl}/detalle/inactivar/${idOrden}`, {});
  }

}
