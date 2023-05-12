package dev.riko.golftourplanner.pathfinding;

/**
 * A node in a graph. GraphNode objects are connected to each other to form a Graph.
 */
public interface GraphNode {
    /**
     * Returns the unique identifier of the GraphNode.
     *
     * @return The identifier of the node.
     */
    String getId();
}
