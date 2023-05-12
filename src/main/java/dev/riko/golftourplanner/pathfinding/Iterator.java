package dev.riko.golftourplanner.pathfinding;

/**
 * An iterator interface used for traversing the shortest path found by the {@link Dijkstra} algorithm.
 */
public interface Iterator {
    /**
     * Returns {@code true} if there are more nodes to visit.
     *
     * @return {@code true} if there are more nodes to visit, {@code false} otherwise.
     */
    boolean hasNext();

    /**
     * Returns the next node to visit.
     *
     * @return the next node to visit.
     */
    Dijkstra.Node getNext();
}
