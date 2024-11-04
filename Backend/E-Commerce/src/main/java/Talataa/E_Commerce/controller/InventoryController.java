package Talataa.E_Commerce.controller;


import Talataa.E_Commerce.dto.request.InventarioRequest;
import Talataa.E_Commerce.dto.response.ApiResponse;
import Talataa.E_Commerce.service.InventoryService;
import Talataa.E_Commerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @GetMapping("/getAllInventarios")
    public ResponseEntity<ApiResponse<String>> getAllInventarios() {
        return new ResponseEntity<>(this.inventoryService.getAllInventarios(), HttpStatus.OK);
    }

    @PostMapping("/guardarInventario")
    public ResponseEntity<ApiResponse<String>> guardarInventario(@RequestBody InventarioRequest inventarioRequest) {
        return new ResponseEntity<>(this.inventoryService.guardarInventario(inventarioRequest), HttpStatus.OK);
    }

    @PutMapping("/actualizarInventario")
    public ResponseEntity<ApiResponse<String>> actualizarInventario(@RequestBody InventarioRequest inventarioRequest) {
        return new ResponseEntity<>(this.inventoryService.actualizarInventario(inventarioRequest), HttpStatus.OK);
    }

    @PutMapping("/inactivar/{id}")
    public ResponseEntity<ApiResponse<String>> inactivarInventario(@PathVariable Integer id) {
        return new ResponseEntity<>(this.inventoryService.inactivarInventario(id), HttpStatus.OK);
    }

}
