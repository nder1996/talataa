package Talataa.E_Commerce.controller;


import Talataa.E_Commerce.dto.request.CompraRequest;
import Talataa.E_Commerce.dto.request.DetalleOrdenRequest;
import Talataa.E_Commerce.dto.request.OrdenCompraRequest;
import Talataa.E_Commerce.dto.response.ApiResponse;
import Talataa.E_Commerce.service.OrderService;
import Talataa.E_Commerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/procesarCompra")
    public ResponseEntity<ApiResponse<String>> procesarOrden(@RequestBody CompraRequest request) {
        ApiResponse<String> response = orderService.procesarOrdenCompleta(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/ordenCompra/actualizar")
    public ResponseEntity<ApiResponse<String>> actualizarOrden(@RequestBody OrdenCompraRequest ordenRequest) {
        return ResponseEntity.ok(orderService.actualizarOrden(ordenRequest));
    }

    @PutMapping("/detalle/actualizar")
    public ResponseEntity<ApiResponse<String>> actualizarDetalle(@RequestBody DetalleOrdenRequest detalleRequest) {
        return ResponseEntity.ok(orderService.actualizarDetalleOrden(detalleRequest));
    }


    @PutMapping("/orden/inactivar/{idOrden}")
    public ResponseEntity<ApiResponse<String>> eliminarOrden(@PathVariable("idOrden") Integer idOrden) {
        return ResponseEntity.ok(orderService.cambiarEstadoOrden(idOrden));
    }

    @PutMapping("/detalle/inactivar/{idDetalle}")
    public ResponseEntity<ApiResponse<String>> eliminarDetallesOrden(@PathVariable("idDetalle") Integer idDetalle) {
        return ResponseEntity.ok(orderService.cambiarEstadoDetallesOrden(idDetalle));
    }
}
