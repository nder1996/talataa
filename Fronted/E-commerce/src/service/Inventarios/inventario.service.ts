import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { enviroment } from 'src/enviroments/enviroment';
import { ApiResponse } from 'src/model/api-response.model';
import { InventariosModel } from 'src/model/Inventarios/Response/InventariosModel';


@Injectable({
  providedIn: 'root'
})
export class InventarioService {

  constructor(private httpClient: HttpClient) { }


  public inventarioUrl:string = enviroment.apiRestURL + 'api/inventory'

  public getAllInventarios(): Observable<ApiResponse<InventariosModel[]>> {
    return this.httpClient.get<ApiResponse<InventariosModel[]>>(`${this.inventarioUrl}/getAllInventarios`);
  }
}
