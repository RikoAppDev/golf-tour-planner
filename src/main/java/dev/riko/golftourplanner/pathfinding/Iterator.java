package dev.riko.golftourplanner.pathfinding;

public interface Iterator {
    boolean hasNext();

    Dijkstra.Node getNext();
}
