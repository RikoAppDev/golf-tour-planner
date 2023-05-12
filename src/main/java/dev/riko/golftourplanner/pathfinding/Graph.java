package dev.riko.golftourplanner.pathfinding;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A generic class representing a graph consisting of nodes connected by edges.
 *
 * @param <T> the type of node in the graph, must extend GraphNode
 */
public class Graph<T extends GraphNode> {
    /**
     * The set of nodes in the graph.
     */
    private final Set<T> nodes;
    /**
     * The connections between nodes in the graph.
     */
    private final Map<String, Set<String>> connections;

    /**
     * Creates a new Graph object with the given set of nodes and map of connections.
     *
     * @param nodes       the set of nodes in the graph
     * @param connections the map of connections between the nodes
     */
    public Graph(Set<T> nodes, Map<String, Set<String>> connections) {
        this.nodes = nodes;
        this.connections = connections;
    }

    /**
     * Returns the node in the graph with the given ID.
     *
     * @param id the ID of the node to return
     * @return the node in the graph with the given ID
     * @throws IllegalArgumentException if no node is found with the given ID
     */
    public T getNode(String id) {
        return nodes.stream()
                .filter(node -> node.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No node found with ID: " + id));
    }

    /**
     * Returns the set of nodes connected to the given node in the graph.
     *
     * @param node the node to get the connections for
     * @return the set of nodes connected to the given node
     */
    public Set<T> getConnections(T node) {
        return connections.get(node.getId()).stream()
                .map(this::getNode)
                .collect(Collectors.toSet());
    }
}
