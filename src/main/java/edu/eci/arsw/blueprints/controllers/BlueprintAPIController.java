package edu.eci.arsw.blueprints.controllers;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para exponer los blueprints
 */
@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {

  @Autowired
  private BlueprintsServices blueprintsServices;

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<?> getAllBlueprints() {
    try {
      Set<Blueprint> blueprints = blueprintsServices.getAllBlueprints();
      return new ResponseEntity<>(blueprints, HttpStatus.OK);
    } catch (Exception e) {
      Logger
        .getLogger(BlueprintAPIController.class.getName())
        .log(Level.SEVERE, null, e);
      return new ResponseEntity<>(
        "Error al obtener los blueprints",
        HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }

  @RequestMapping(method = RequestMethod.GET, path = "/{author}")
  public ResponseEntity<?> getBlueprintsByAuthor(@PathVariable String author) {
    try {
      Set<Blueprint> blueprints = blueprintsServices.getBlueprintsByAuthor(
        author
      );
      if (blueprints.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna 404 si no hay planos
      }
      return new ResponseEntity<>(blueprints, HttpStatus.OK);
    } catch (Exception e) {
      Logger
        .getLogger(BlueprintAPIController.class.getName())
        .log(Level.SEVERE, null, e);
      return new ResponseEntity<>(
        "Error al obtener los blueprints",
        HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }

  @RequestMapping(method = RequestMethod.GET, path = "/{author}/{bpname}")
  public ResponseEntity<?> getBlueprint(
    @PathVariable String author,
    @PathVariable String bpname
  ) {
    try {
      Blueprint blueprint = blueprintsServices.getBlueprint(author, bpname);
      return new ResponseEntity<>(blueprint, HttpStatus.OK);
    } catch (Exception e) {
      Logger
        .getLogger(BlueprintAPIController.class.getName())
        .log(Level.SEVERE, null, e);
      return new ResponseEntity<>(
        "Error al obtener el blueprint",
        HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<?> addNewBlueprint(@RequestBody Blueprint blueprint) {
    try {
      blueprintsServices.addNewBlueprint(blueprint);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
      Logger
        .getLogger(BlueprintAPIController.class.getName())
        .log(Level.SEVERE, null, e);
      return new ResponseEntity<>(
        "Error al agregar el blueprint",
        HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }
}
