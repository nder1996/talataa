package Talataa.E_Commerce.service;

import Talataa.E_Commerce.dto.response.ApiResponse;
import Talataa.E_Commerce.dto.response.ErrorDetailResponse;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ResponseApiBuilderService {


    public List<ErrorDetailResponse> getErrorDetails() {
        return ErrorDetailResponse.ERRORS;
    }

    public ErrorDetailResponse byErrorDetailsListXCode(String codeName, List<ErrorDetailResponse> errorDetailsList) {
        try {
            Optional<ErrorDetailResponse> optionalErrorDetail = errorDetailsList.stream()
                    .filter(errorDetail -> errorDetail.getCodeName().equals(codeName))
                    .findFirst();
            if (optionalErrorDetail.isPresent()) {
                return optionalErrorDetail.get();
            }
        } catch (Exception ex) {
            System.out.println("Error al crear la respuesta del servidor : " + ex.getMessage());
            return null;
        }
        return null;
    }


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


    public ApiResponse<String> errorRespuesta(String code) {
        ApiResponse<String> response = new ApiResponse<>();
        try {
            List<ErrorDetailResponse> errorDetailsList = getErrorDetails();
            ErrorDetailResponse errorDetail = this.byErrorDetailsListXCode(code, errorDetailsList);
            response.setData(null);
            response.setMeta(new ApiResponse.Meta("Solicitud Incorrecta", errorDetail.getCodeHttp()));
            response.setError(new ApiResponse.ErrorDetails(errorDetail.getCodeName(), errorDetail.getCodeDescripcion()));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return response;
    }

    public ApiResponse<String> errorRespuestaPersonalizado(Integer codeHttp, String codeName, String codeDescripcion) {
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

