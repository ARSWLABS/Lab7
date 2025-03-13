package edu.eci.arsw.blueprints.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Blueprint {

  private String author;
  private String name;
  private List<Point> points;

  public Blueprint(String author, String name, Point[] points) {
    this.author = author;
    this.name = name;
    this.points = new ArrayList<>(Arrays.asList(points));
  }

  public Blueprint() {}

  public Blueprint(String author, String name, List<Point> points) {
    this.author = author;
    this.name = name;
    this.points = points;
  }

  public String getAuthor() {
    return author;
  }

  public String getName() {
    return name;
  }

  public List<Point> getPoints() {
    return points;
  }

  @Override
  public String toString() {
    return (
      "Blueprint{" +
      "author='" +
      author +
      '\'' +
      ", name='" +
      name +
      '\'' +
      ", points=" +
      points +
      '}'
    );
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Blueprint blueprint = (Blueprint) o;
    return (
      Objects.equals(author, blueprint.author) &&
      Objects.equals(name, blueprint.name) &&
      Objects.equals(points, blueprint.points)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(author, name, points);
  }
}
