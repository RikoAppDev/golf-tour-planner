package dev.riko.golftourplanner.pathfinding;

/**
 * This interface represents a container that can provide an iterator to iterate through its elements.
 */
public interface Container {
    /**
     * Returns an iterator to iterate through the elements of the container starting at the specified node.
     *
     * @param node the starting node of the iterator
     * @return an iterator to iterate through the elements of the container
     */
    Iterator getIterator(Dijkstra.Node node);
}
