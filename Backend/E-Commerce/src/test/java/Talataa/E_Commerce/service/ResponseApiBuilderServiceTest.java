package Talataa.E_Commerce.service;

import Talataa.E_Commerce.dto.response.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ResponseApiBuilderServiceTest {


    private ResponseApiBuilderService responseBuilder;

    @BeforeEach
    void setUp() {
        responseBuilder = new ResponseApiBuilderService();
    }

    @Test
    void successRespuesta_ConDatosValidos_DebeRetornarRespuestaExitosa() {
        // Arrange
        String data = "Test Data";
        String key = "testKey";

        // Act
        ApiResponse<String> response = responseBuilder.successRespuesta(data, key);

        // Assert
        assertNotNull(response, "La respuesta no debe ser null");
        assertNotNull(response.getMeta(), "Meta no debe ser null");
        assertEquals(200, response.getMeta().getStatus(), "El código de estado debe ser 200");
        assertEquals("Operación Exitosa", response.getMeta().getMessage(), "El mensaje debe indicar operación exitosa");

        Map<String, Object> responseData = (Map<String, Object>) response.getData();
        assertTrue(responseData.containsKey(key), "La respuesta debe contener la clave especificada");
        assertEquals(data, responseData.get(key), "El valor de los datos debe coincidir");
    }

    @Test
    void successRespuesta_ConDatosComplejos_DebeRetornarRespuestaExitosa() {
        // Arrange
        TestDTO testData = new TestDTO("test name", 123);
        String key = "testObject";

        // Act
        ApiResponse<String> response = responseBuilder.successRespuesta(testData, key);

        // Assert
        assertNotNull(response, "La respuesta no debe ser null");
        Map<String, Object> responseData = (Map<String, Object>) response.getData();
        assertEquals(testData, responseData.get(key), "El objeto complejo debe mantenerse intacto");
    }


    @Test
    void errorRespuesta_ConDatosValidos_DebeRetornarRespuestaError() {
        // Arrange
        Integer codeHttp = 400;
        String codeName = "BAD_REQUEST";
        String codeDescripcion = "Los datos proporcionados son inválidos";

        // Act
        ApiResponse<String> response = responseBuilder.errorRespuesta(codeHttp, codeName, codeDescripcion);

        // Assert
        assertNotNull(response, "La respuesta no debe ser null");

        // Verificar Meta
        assertNotNull(response.getMeta(), "Meta no debe ser null");
        assertEquals(codeHttp, response.getMeta().getStatus(), "El código HTTP debe coincidir");
        assertEquals("Solicitud Incorrecta", response.getMeta().getMessage(), "El mensaje debe indicar solicitud incorrecta");

        // Verificar Data
        assertNull(response.getData(), "Data debe ser null en respuesta de error");

        // Verificar Error
        assertNotNull(response.getError(), "Error no debe ser null");
        assertEquals(codeName, response.getError().getCode(), "El código de error debe coincidir");
        assertEquals(codeDescripcion, response.getError().getDetails(), "La descripción del error debe coincidir");
    }

    @Test
    void errorRespuesta_ConCodigoHttp500_DebeRetornarErrorServidor() {
        // Arrange
        Integer codeHttp = 500;
        String codeName = "INTERNAL_SERVER_ERROR";
        String codeDescripcion = "Error interno del servidor";

        // Act
        ApiResponse<String> response = responseBuilder.errorRespuesta(codeHttp, codeName, codeDescripcion);

        // Assert
        assertNotNull(response, "La respuesta no debe ser null");
        assertEquals(500, response.getMeta().getStatus(), "El código HTTP debe ser 500");
        assertEquals("INTERNAL_SERVER_ERROR", response.getError().getCode(), "El código debe indicar error interno");
    }





    @Data
    @AllArgsConstructor
    private static class TestDTO {
        private String name;
        private int value;
    }

}
