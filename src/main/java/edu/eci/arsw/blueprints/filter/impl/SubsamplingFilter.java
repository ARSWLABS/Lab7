package edu.eci.arsw.blueprints.filter.impl;

import edu.eci.arsw.blueprints.filter.BlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component("subsamplingFilter")
public class SubsamplingFilter implements BlueprintFilter {

  @Override
  public Blueprint filter(Blueprint blueprint) {
    List<Point> points = blueprint.getPoints();
    List<Point> filteredPoints = new ArrayList<>();

    for (int i = 0; i < points.size(); i += 2) {
      filteredPoints.add(points.get(i));
    }

    return new Blueprint(
      blueprint.getAuthor(),
      blueprint.getName(),
      filteredPoints.toArray(new Point[0])
    );
  }
}
