package dev.riko.golftourplanner.pathfinding;

import java.util.List;

/**
 * An interface for utility functions used in graph traversal and pathfinding.
 *
 * @param <T> the type of graph nodes
 */
public interface GraphUtilFunctions<T extends GraphNode> {
    /**
     * Calculates the score between the current node and the next node in the traversal.
     *
     * @param currentNode the current node being evaluated
     * @param nextNode    the next node being evaluated
     * @return the score between the two nodes
     */
    double calculateScore(T currentNode, T nextNode);

    /**
     * Builds the shortest path from the final node back to the start node.
     *
     * @param finalNode the node that represents the end of the path
     * @return a list of nodes representing the shortest path from the start node to the final node
     */
    List<T> buildShortestPath(T finalNode);
}
