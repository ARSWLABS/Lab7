package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryBlueprintPersistence implements BlueprintsPersistence {

  private final Map<Tuple<String, String>, Blueprint> blueprints = new ConcurrentHashMap<>();

  public InMemoryBlueprintPersistence() {
    // load stub data
    Point[] pts = new Point[] { new Point(140, 140), new Point(115, 115) };
    Blueprint bp = new Blueprint("_authorname_", "_bpname_", pts);
    blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);

    // Cargando los 3 planos pedidos en la parte 1 en punto 2
    Point[] pts1 = new Point[] { new Point(140, 140), new Point(120, 120) };
    Blueprint bp1 = new Blueprint("author1", "bp1", pts1);
    blueprints.put(new Tuple<>(bp1.getAuthor(), bp1.getName()), bp1);

    Point[] pts2 = new Point[] { new Point(50, 50), new Point(60, 60) };
    Blueprint bp2 = new Blueprint("author1", "bp2", pts2);
    blueprints.put(new Tuple<>(bp2.getAuthor(), bp2.getName()), bp2);

    Point[] pts3 = new Point[] { new Point(70, 70), new Point(80, 80) };
    Blueprint bp3 = new Blueprint("author2", "bp3", pts3);
    blueprints.put(new Tuple<>(bp3.getAuthor(), bp3.getName()), bp3);
  }

  @Override
  public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
    if (blueprints.containsKey(new Tuple<>(bp.getAuthor(), bp.getName()))) {
      throw new BlueprintPersistenceException(
        "The given blueprint already exists: " + bp
      );
    } else {
      blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);
    }
  }

  @Override
  public Set<Blueprint> getBlueprintsByAuthor(String author)
    throws BlueprintNotFoundException {
    Set<Blueprint> result = new HashSet<>();
    for (Tuple<String, String> key : blueprints.keySet()) {
      if (key.getElem1().equals(author)) {
        result.add(blueprints.get(key));
      }
    }
    if (result.isEmpty()) {
      throw new BlueprintNotFoundException("Author not found: " + author);
    }
    return result;
  }

  @Override
  public synchronized Blueprint getBlueprint(String author, String name)
    throws BlueprintNotFoundException {
    Blueprint bp = blueprints.get(new Tuple<>(author, name));
    if (bp == null) {
      throw new BlueprintNotFoundException("Blueprint not found: " + name);
    }
    return bp;
  }

  //Nuevo metodo para obtener todos los planos requerido para punto 3 de parte 1
  @Override
  public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {
    Set<Blueprint> result = new HashSet<>();
    for (Tuple<String, String> key : blueprints.keySet()) {
      result.add(blueprints.get(key));
    }
    if (result.isEmpty()) {
      throw new BlueprintNotFoundException("Blueprints not found");
    }
    return result;
  }
}
