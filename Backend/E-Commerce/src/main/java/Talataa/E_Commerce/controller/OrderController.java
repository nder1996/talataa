package Talataa.E_Commerce.controller;


import Talataa.E_Commerce.dto.response.ApiResponse;
import Talataa.E_Commerce.service.OrderService;
import Talataa.E_Commerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;



    @GetMapping("/getAllOrdenesDisponibles")
    public ResponseEntity<ApiResponse<String>> getAllOrdenesDisponibles() {
        return new ResponseEntity<>(this.orderService.getAllOrdenesDisponibles(), HttpStatus.OK);
    }

    @GetMapping("/getAllDetalleXOrdenDisponibles/{idOrden}")
    public ResponseEntity<ApiResponse<String>> getAllDetalleXOrdenDisponibles(@PathVariable("idOrden") Integer idOrden) {
        return new ResponseEntity<>(this.orderService.getAllDetalleXOrdenDisponibles(idOrden), HttpStatus.OK);
    }
}
