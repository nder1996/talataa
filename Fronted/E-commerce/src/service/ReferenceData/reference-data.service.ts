import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { enviroment } from 'src/enviroments/enviroment';
import { ApiResponse } from 'src/model/api-response.model';
import { CiudadModel } from 'src/model/ReferenceData/ciudadModel';
import { GeneroModel } from 'src/model/ReferenceData/generoModel';
import { RolModel } from 'src/model/ReferenceData/rolModel';
import { TipoDocumentoModel } from 'src/model/ReferenceData/tipoDocumentoModel';

@Injectable({
  providedIn: 'root'
})
export class ReferenceDataService {

  constructor(private httpClient: HttpClient) { }


  public referenciaUrl:string = enviroment.apiRestURL + 'api/referenceData'

  public getAllTipoDocumento(): Observable<ApiResponse<TipoDocumentoModel[]>> {
    return this.httpClient.get<ApiResponse<TipoDocumentoModel[]>>(`${this.referenciaUrl}/getAllTipoDocumento`);
  }

  public getAllCiudad(): Observable<ApiResponse<CiudadModel[]>> {
    return this.httpClient.get<ApiResponse<CiudadModel[]>>(`${this.referenciaUrl}/getAllCiudad`);
  }  

  public getAllGeneros(): Observable<ApiResponse<GeneroModel[]>> {
    return this.httpClient.get<ApiResponse<GeneroModel[]>>(`${this.referenciaUrl}/getAllGeneros`);
  }  

  public getAllRoles(): Observable<ApiResponse<RolModel[]>> {
    return this.httpClient.get<ApiResponse<RolModel[]>>(`${this.referenciaUrl}/getAllRoles`);
  }   




}
