package edu.eci.arsw.blueprints.test.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import edu.eci.arsw.blueprints.filter.BlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import edu.eci.arsw.blueprintsapi.BlueprintsAPIApplication;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = BlueprintsAPIApplication.class)
public class ApplicationServicesTests {

  @Mock
  private BlueprintsPersistence blueprintsPersistence; // Mock para el repositorio

  @Mock
  private BlueprintFilter filter; // Mock para el filtro

  @InjectMocks
  private BlueprintsServices blueprintsServices; // Servicio que se va a probar

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this); // Inicializa los mocks
  }

  @Test
  public void contextLoads() {
    // Verifica que el contexto de la aplicación se carga correctamente
  }

  @Test
  public void getBlueprintsByAuthorTest() throws BlueprintNotFoundException {
    // Definir el comportamiento del mock
    Set<Blueprint> blueprints = new HashSet<>();
    Point[] pts = new Point[] { new Point(140, 140), new Point(115, 115) };
    Blueprint bp = new Blueprint("authorPrueba", "bpPrueba", pts);
    blueprints.add(bp);
    when(blueprintsPersistence.getBlueprintsByAuthor("authorPrueba"))
      .thenReturn(blueprints);
    when(filter.filter(any(Blueprint.class))).thenReturn(bp); // Configura el filtro

    // Probar el servicio
    Set<Blueprint> returnedBlueprints = blueprintsServices.getBlueprintsByAuthor(
      "authorPrueba"
    );

    // Verificar que el servicio llamó al método del repositorio
    verify(blueprintsPersistence).getBlueprintsByAuthor("authorPrueba");

    // Verificar que el servicio retornó el valor correcto
    assertEquals(1, returnedBlueprints.size());
    assertEquals(bp, returnedBlueprints.iterator().next());
  }

  @Test
  public void addNewBlueprintTest() throws BlueprintPersistenceException {
    // Crear un nuevo plano
    Point[] pts = new Point[] { new Point(140, 140), new Point(115, 115) };
    Blueprint bp = new Blueprint("authorPrueba", "bpPrueba", pts);

    // Configurar el mock para que no lance excepciones
    doNothing().when(blueprintsPersistence).saveBlueprint(bp);

    // Probar el servicio
    blueprintsServices.addNewBlueprint(bp);

    // Verificar que el servicio llamó al método del repositorio
    verify(blueprintsPersistence).saveBlueprint(bp);
  }

  @Test
  public void getBlueprintTest() throws BlueprintNotFoundException {
    // Crear un nuevo plano
    Point[] pts = new Point[] { new Point(140, 140), new Point(115, 115) };
    Blueprint bp = new Blueprint("authorPrueba", "bpPrueba", pts);

    // Configurar el mock para que devuelva el plano
    when(blueprintsPersistence.getBlueprint("authorPrueba", "bpPrueba"))
      .thenReturn(bp);
    when(filter.filter(bp)).thenReturn(bp); // Configura el filtro

    // Probar el servicio
    Blueprint returnedBlueprint = blueprintsServices.getBlueprint(
      "authorPrueba",
      "bpPrueba"
    );

    // Verificar que el servicio llamó al método del repositorio
    verify(blueprintsPersistence).getBlueprint("authorPrueba", "bpPrueba");

    // Verificar que el servicio retornó el valor correcto
    assertEquals(bp, returnedBlueprint);
  }

  @Test
  public void getBlueprintNotFoundTest() throws BlueprintNotFoundException {
    // Configurar el mock para que lance una excepción
    when(blueprintsPersistence.getBlueprint("authorPrueba", "nonExistent"))
      .thenThrow(new BlueprintNotFoundException("Blueprint not found"));

    // Verificar que se lance la excepción
    assertThrows(
      BlueprintNotFoundException.class,
      () -> {
        blueprintsServices.getBlueprint("authorPrueba", "nonExistent");
      }
    );
  }

  @Test
  public void getAllBlueprintsTest() throws BlueprintNotFoundException {
    // Crear algunos planos
    Set<Blueprint> blueprints = new HashSet<>();
    Point[] pts1 = new Point[] { new Point(140, 140), new Point(115, 115) };
    Blueprint bp1 = new Blueprint("author1", "bp1", pts1);
    blueprints.add(bp1);

    Point[] pts2 = new Point[] { new Point(150, 150), new Point(125, 125) };
    Blueprint bp2 = new Blueprint("author2", "bp2", pts2);
    blueprints.add(bp2);

    // Configurar el mock para que devuelva los planos
    when(blueprintsPersistence.getAllBlueprints()).thenReturn(blueprints);
    when(filter.filter(any(Blueprint.class)))
      .thenAnswer(invocation -> invocation.getArgument(0)); // No filtrar en esta prueba

    // Probar el servicio
    Set<Blueprint> returnedBlueprints = blueprintsServices.getAllBlueprints();

    // Verificar que el servicio llamó al método del repositorio
    verify(blueprintsPersistence).getAllBlueprints();

    // Verificar que el servicio retornó el valor correcto
    assertEquals(2, returnedBlueprints.size());
  }
}
