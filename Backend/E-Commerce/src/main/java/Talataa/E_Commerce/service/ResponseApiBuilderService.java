package Talataa.E_Commerce.service;

import Talataa.E_Commerce.dto.response.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ResponseApiBuilderService {



    public ApiResponse<String> successRespuesta(Object data, String key) {
        ApiResponse<String> response = new ApiResponse<>();
        try {
            response.setMeta(new ApiResponse.Meta("Operación Exitosa", 200));
            response.setData(Collections.singletonMap(key, data));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return response;
    }



    public ApiResponse<String> errorRespuesta(Integer codeHttp, String codeName, String codeDescripcion) {
        ApiResponse<String> response = new ApiResponse<>();
        try {
            response.setData(null);
            response.setMeta(new ApiResponse.Meta("Solicitud Incorrecta", codeHttp));
            response.setError(new ApiResponse.ErrorDetails(codeName, codeDescripcion));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return response;
    }
}

