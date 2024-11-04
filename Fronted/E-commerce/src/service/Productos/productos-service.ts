import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { enviroment } from 'src/enviroments/enviroment';
import { ProductosModel } from 'src/model/Productos/Response/ProductosModel';
import { Observable } from 'rxjs';
import { ApiResponse } from 'src/model/api-response.model';  // Importa tu modelo
import { CategoriaProductoModel } from 'src/model/Productos/Response/CategoriaProductoModel';
import { ProductModel } from 'src/model/Productos/Response/ProductMode';


@Injectable({
  providedIn: 'root'
})
export class ProductosService {

  constructor(private httpClient: HttpClient) { }


  public productoUrl:string = enviroment.apiRestURL + 'api/productos'

  public getAllProductoDisponibles(): Observable<ApiResponse<ProductosModel[]>> {
    return this.httpClient.get<ApiResponse<ProductosModel[]>>(`${this.productoUrl}/getAllProductoDisponibles`);
  }

  public getAllCategoriasProductos(): Observable<ApiResponse<CategoriaProductoModel[]>> {
    return this.httpClient.get<ApiResponse<CategoriaProductoModel[]>>(`${this.productoUrl}/getAllCategoriasProductos`);
  }

  public getAllProductos(): Observable<ApiResponse<ProductModel[]>> {
    return this.httpClient.get<ApiResponse<ProductModel[]>>(`${this.productoUrl}/getAllProducto`);
  }

}
