package edu.eci.arsw.blueprints.filter.impl;

import edu.eci.arsw.blueprints.filter.BlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component("redundancyFilter")
public class RedundancyFilter implements BlueprintFilter {

  @Override
  public Blueprint filter(Blueprint blueprint) {
    List<Point> points = blueprint.getPoints();
    List<Point> filteredPoints = new ArrayList<>();

    if (!points.isEmpty()) {
      filteredPoints.add(points.get(0));
      for (int i = 1; i < points.size(); i++) {
        if (!points.get(i).equals(points.get(i - 1))) {
          filteredPoints.add(points.get(i));
        }
      }
    }

    return new Blueprint(
      blueprint.getAuthor(),
      blueprint.getName(),
      filteredPoints.toArray(new Point[0])
    );
  }
}
