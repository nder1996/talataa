package Talataa.E_Commerce.controller;


import Talataa.E_Commerce.dto.response.ApiResponse;
import Talataa.E_Commerce.model.Ciudad;
import Talataa.E_Commerce.service.ReferenceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/referenceData")
public class ReferenceDataController {


    @Autowired
    ReferenceDataService referenceDataService;


    @GetMapping("/getAllCiudad")
    public ResponseEntity<ApiResponse<String>> getAllCiudad() {
        return new ResponseEntity<>(this.referenceDataService.getAllCiudad(), HttpStatus.OK);
    }

    @GetMapping("/getAllGeneros")
    public ResponseEntity<ApiResponse<String>> getAllGeneros() {
        return new ResponseEntity<>(this.referenceDataService.getAllGeneros(), HttpStatus.OK);
    }


    @GetMapping("/getAllTipoDocumento")
    public ResponseEntity<ApiResponse<String>> getAllTipoDocumento() {
        return new ResponseEntity<>(this.referenceDataService.getAllTipoDocumento(), HttpStatus.OK);
    }

    @GetMapping("/getAllRoles")
    public ResponseEntity<ApiResponse<String>> getAllRoles() {
        return new ResponseEntity<>(this.referenceDataService.getAllRoles(), HttpStatus.OK);
    }

}
