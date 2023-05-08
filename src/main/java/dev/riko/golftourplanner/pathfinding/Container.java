package dev.riko.golftourplanner.pathfinding;

public interface Container {
    Iterator getIterator(Dijkstra.Node o);
}
