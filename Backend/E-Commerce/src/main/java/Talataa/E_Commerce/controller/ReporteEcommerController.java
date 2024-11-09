package Talataa.E_Commerce.controller;


import Talataa.E_Commerce.dto.response.ApiResponse;
import Talataa.E_Commerce.service.ReferenceDataService;
import Talataa.E_Commerce.service.ReporteEcommerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reportes")
public class ReporteEcommerController {

    @Autowired
    ReporteEcommerService reporteEcommerService;


    @GetMapping("/clientesFrecuentes")
    public ResponseEntity<ApiResponse<String>> clientesFrecuentes() {
        return new ResponseEntity<>(this.reporteEcommerService.clientesFrecuentes(), HttpStatus.OK);
    }

    @GetMapping("/topProductosVendidos")
    public ResponseEntity<ApiResponse<String>> topProductosVendidos() {
        return new ResponseEntity<>(this.reporteEcommerService.topProductosVendidos(), HttpStatus.OK);
    }


    @GetMapping("/productosActivos")
    public ResponseEntity<ApiResponse<String>> productosActivos() {
        return new ResponseEntity<>(this.reporteEcommerService.productosActivos(), HttpStatus.OK);
    }



}
