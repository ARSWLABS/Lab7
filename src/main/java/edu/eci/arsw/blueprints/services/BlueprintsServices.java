package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.filter.BlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlueprintsServices {

  @Autowired
  BlueprintsPersistence bpp;

  @Autowired
  BlueprintFilter filter;

  public void addNewBlueprint(Blueprint bp) {
    try {
      bpp.saveBlueprint(bp);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Blueprint getBlueprint(String author, String name)
    throws BlueprintNotFoundException {
    Blueprint bp = bpp.getBlueprint(author, name);
    return filter.filter(bp);
  }

  public Set<Blueprint> getBlueprintsByAuthor(String author)
    throws BlueprintNotFoundException {
    Set<Blueprint> blueprints = bpp.getBlueprintsByAuthor(author);
    return blueprints.stream().map(filter::filter).collect(Collectors.toSet());
  }

  /**
   * Este metodo es para compelementar punto3 de la parte 1
   * Trata de obtener todos los planos almacenados
   * @return
   * @throws BlueprintNotFoundException
   */
  public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {
    Set<Blueprint> blueprints = bpp.getAllBlueprints();
    return blueprints.stream().map(filter::filter).collect(Collectors.toSet());
  }

  /**
   * Trae todos los planos almacenados
   * @param author
   * @param bpname
   * @throws BlueprintNotFoundException
   */
  public void deleteBlueprint(String author, String bpname)
    throws BlueprintNotFoundException {
    bpp.deleteBlueprint(author, bpname);
  }

  /**
   * Actualiza un plano
   * @param author
   * @param bpname
   * @param updatedBlueprint
   * @throws BlueprintNotFoundException
   */
  public void updateBlueprint(
    String author,
    String bpname,
    Blueprint updatedBlueprint
  ) throws BlueprintNotFoundException {
    bpp.updateBlueprint(author, bpname, updatedBlueprint);
  }
}
