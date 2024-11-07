import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { enviroment } from 'src/enviroments/enviroment';
import { ApiResponse } from 'src/model/api-response.model';
import { InventariosRequestModel } from 'src/model/Inventarios/Request/InventarioRequestModel';
import { InventariosModel } from 'src/model/Inventarios/Response/InventariosModel';


@Injectable({
  providedIn: 'root'
})
export class InventarioService {

  constructor(private httpClient: HttpClient) { }


  public inventarioUrl:string = enviroment.apiRestURL + 'api/inventory'

  public getAllInventarios(): Observable<ApiResponse<InventariosRequestModel[]>> {
    return this.httpClient.get<ApiResponse<InventariosModel[]>>(`${this.inventarioUrl}/getAllInventarios`);
  }

  /**/ 

   // POST - Guardar nuevo inventario
   public guardarInventario(inventario: InventariosRequestModel): Observable<ApiResponse<string>> {
    return this.httpClient.post<ApiResponse<string>>(`${this.inventarioUrl}/guardarInventario`, inventario);
  }

  // PUT - Actualizar inventario
  public actualizarInventario(inventario: InventariosRequestModel): Observable<ApiResponse<string>> {
    return this.httpClient.put<ApiResponse<string>>(`${this.inventarioUrl}/actualizarInventario`, inventario);
  }

  // PUT - Inactivar inventario
  public inactivarInventario(id: number): Observable<ApiResponse<string>> {
    return this.httpClient.put<ApiResponse<string>>(`${this.inventarioUrl}/inactivar/${id}`, {});
  }

  
}
