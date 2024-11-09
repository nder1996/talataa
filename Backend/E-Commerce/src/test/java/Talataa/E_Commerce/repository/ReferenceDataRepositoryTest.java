package Talataa.E_Commerce.repository;

import Talataa.E_Commerce.model.Ciudad;
import Talataa.E_Commerce.model.Genero;
import Talataa.E_Commerce.model.Rol;
import Talataa.E_Commerce.model.TipoDocumento;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {
        "classpath:data.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ReferenceDataRepositoryTest {

    @Autowired
    private ReferenceDataRepository referenceDataRepository;

    // Tests para Ciudades
    @Test
    void getAllCiudad_DeberiaRetornarListaOrdenada() {
        // When
        List<Ciudad> ciudades = referenceDataRepository.getAllCiudad();
        List<String> nombresOrdenados = ciudades.stream()
                .map(Ciudad::getNombre)
                .sorted()
                .collect(Collectors.toList());
        List<String> nombresOriginales = ciudades.stream()
                .map(Ciudad::getNombre)
                .collect(Collectors.toList());

        // Then
        assertAll(
                () -> assertNotNull(ciudades.toString(), "La lista no debe ser null"),
                () -> assertFalse(ciudades.isEmpty(), "La lista no debe estar vacía"),
                () -> assertEquals(3, ciudades.size(), "Deberían haber 3 ciudades"),
                () -> assertEquals(nombresOrdenados, nombresOriginales,
                        "Las ciudades deberían estar ordenadas alfabéticamente")
        );
    }

    @Test
    void getAllCiudad_SoloDeberiaRetornarActivas() {
        // When
        List<Ciudad> ciudades = referenceDataRepository.getAllCiudad();

        // Then
        assertTrue(ciudades.stream()
                        .allMatch(ciudad -> "A".equals(ciudad.getEstado())),
                "Todas las ciudades deben tener estado 'A'");
    }

    // Tests para Géneros
    @Test
    void getAllGeneros_DeberiaRetornarListaCompleta() {
        // When
        List<Genero> generos = referenceDataRepository.getAllGeneros();

        // Then
        assertAll(
                () -> assertNotNull(generos.toString(), "La lista no debe ser null"),
                () -> assertEquals(3, generos.size(), "Deberían haber 3 géneros"),
                () -> assertTrue(generos.stream()
                        .anyMatch(g -> g.getNombre().equals("M")), "Debe existir género Masculino"),
                () -> assertTrue(generos.stream()
                        .anyMatch(g -> g.getNombre().equals("F")), "Debe existir género Femenino")
        );
    }

    // Tests para Tipo Documento
    @Test
    void getAllTipoDocumento_DeberiaRetornarListaOrdenada() {
        // When
        List<TipoDocumento> tiposDocumento = referenceDataRepository.getAllTipoDocumento();

        // Then
        assertAll(
                () -> assertNotNull(tiposDocumento.toString(), "La lista no debe ser null"),
                () -> assertFalse(tiposDocumento.isEmpty(), "La lista no debe estar vacía"),
                () -> assertEquals(3, tiposDocumento.size(), "Deberían haber 3 tipos de documento"),
                () -> {
                    List<String> nombresOrdenados = tiposDocumento.stream()
                            .map(TipoDocumento::getNombre)
                            .collect(Collectors.toList());

                    List<String> nombresEsperados = Arrays.asList("CC", "CE", "PA");
                    assertEquals(nombresEsperados, nombresOrdenados,
                            "Los tipos de documento deben estar ordenados alfabéticamente");
                }
        );
    }

    // Tests para Roles
    @Test
    void getAllRoles_DeberiaRetornarListaCompleta() {
        // When
        List<Rol> roles = referenceDataRepository.getAllRoles();

        // Then
        assertAll(
                () -> assertNotNull(roles.toString(), "La lista no debe ser null"),
                () -> assertEquals(3, roles.size(), "Deberían haber 3 roles"),
                () -> assertTrue(roles.stream()
                                .anyMatch(r -> r.getNombre().equals("ADMINISTRADOR")),
                        "Debe existir rol ADMINISTRADOR"),
                () -> assertTrue(roles.stream()
                                .anyMatch(r -> r.getNombre().equals("CLIENTE")),
                        "Debe existir rol CLIENTE"),
                () -> assertTrue(roles.stream()
                                .anyMatch(r -> r.getNombre().equals("VISUALIZADOR")),
                        "Debe existir rol VISUALIZADOR")
        );
    }

    @Test
    void getAllRoles_SoloDeberiaRetornarActivos() {
        List<Rol> roles = referenceDataRepository.getAllRoles();
        assertTrue(roles.stream()
                        .allMatch(rol -> "A".equals(rol.getEstado())),
                "Todos los roles deben tener estado 'A'");
    }
}
