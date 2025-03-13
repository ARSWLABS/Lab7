package edu.eci.arsw.blueprints.persistence;

import edu.eci.arsw.blueprints.model.Blueprint;
import java.util.Set;

public interface BlueprintsPersistence {
  void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException;

  Blueprint getBlueprint(String author, String name)
    throws BlueprintNotFoundException;

  Set<Blueprint> getBlueprintsByAuthor(String author)
    throws BlueprintNotFoundException;

  // Este metodo es para compelementar punto3 de la parte 1
  Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException;
}
