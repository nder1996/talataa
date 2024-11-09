package Talataa.E_Commerce.controller;


import Talataa.E_Commerce.dto.request.ProductoRequest;
import Talataa.E_Commerce.dto.response.ApiResponse;
import Talataa.E_Commerce.service.ProductService;
import Talataa.E_Commerce.service.ReferenceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productos")
public class ProductController {


    @Autowired
    ProductService productService;


    @GetMapping("/todosProductosEcommer")
    public ResponseEntity<ApiResponse<String>> todosProductosEcommer() {
        return new ResponseEntity<>(this.productService.todosProductosEcommer(), HttpStatus.OK);
    }

    @GetMapping("/todosProductosCrud")
    public ResponseEntity<ApiResponse<String>> todosProductosCrud() {
        return new ResponseEntity<>(this.productService.todosProductosCrud(), HttpStatus.OK);
    }



    @GetMapping("/getAllProductoDisponibles")
    public ResponseEntity<ApiResponse<String>> getAllProductoDisponibles() {
        return new ResponseEntity<>(this.productService.todosProductosCrud(), HttpStatus.OK);
    }

    @GetMapping("/getAllProducto")
    public ResponseEntity<ApiResponse<String>> getAllProducto() {
        return new ResponseEntity<>(this.productService.getAllProductosCrud(), HttpStatus.OK);
    }

    @GetMapping("/getAllCategoriasProductos")
    public ResponseEntity<ApiResponse<String>> getAllCategoriasProductos() {
        return new ResponseEntity<>(this.productService.getAllCategoriasProductos(), HttpStatus.OK);
    }

    @PostMapping("/guardarProductoXCategoria")
    public ResponseEntity<ApiResponse<String>> guardarProducto(@RequestBody ProductoRequest productoRequest) {
        return new ResponseEntity<>(this.productService.guardarProductoYCategoria(productoRequest), HttpStatus.OK);

    }

    @PutMapping("/actualizarProductoXCategoria")
    public ResponseEntity<ApiResponse<String>> actualizarProducto(@RequestBody ProductoRequest productoRequest) {
        return new ResponseEntity<>(this.productService.actualizarProductoYCategoria(productoRequest), HttpStatus.OK);
    }

    @PutMapping("/inactivar/{id}")
    public ResponseEntity<ApiResponse<String>> inactivarProducto(@PathVariable Integer id) {
        return new ResponseEntity<>(this.productService.inactivarProducto(id), HttpStatus.OK);
    }



}
