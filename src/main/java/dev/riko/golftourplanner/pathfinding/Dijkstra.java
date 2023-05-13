package dev.riko.golftourplanner.pathfinding;

import dev.riko.golftourplanner.exceptions.NoPathFoundException;
import dev.riko.golftourplanner.world.World;
import dev.riko.golftourplanner.world.place.Place;

import java.util.*;
import java.util.stream.Collectors;

/**
 * {@code Dijkstra} class that implements {@code GraphUtilFunctions} and {@code Container} interfaces for calculating the shortest path between two places in a world.
 */
public class Dijkstra implements GraphUtilFunctions<Dijkstra.Node>, Container {
    /**
     * Inner class {@code Node} that represents a node in the graph for Dijkstra algorithm.
     */
    protected static class Node implements GraphNode {
        /**
         * The value for infinity.
         */
        private static final double INFINITY = Double.POSITIVE_INFINITY;
        /**
         * The place represented by this node.
         */
        private final Place place;
        /**
         * The current distance score of this node used by the Dijkstra's algorithm.
         */
        private double distanceScore;
        /**
         * The previous node used for reconstruction of the shortest path.
         */
        private Node previousNode;

        /**
         * Creates a new node for the given place.
         *
         * @param place the place to represent with this node.
         */
        public Node(Place place) {
            this.place = place;
            distanceScore = INFINITY;
        }

        /**
         * Gets the ID of the node.
         *
         * @return the ID of the node.
         */
        @Override
        public String getId() {
            return String.valueOf(place.hashCode());
        }
    }

    /**
     * The list of nodes that have not been visited yet by the algorithm.
     */
    private final List<Node> unvisitedNodes = new ArrayList<>();
    /**
     * The graph used by the Dijkstra algorithm.
     */
    private final Graph<Node> graph;
    /**
     * The starting place for the algorithm.
     */
    private final Place startPlace;
    /**
     * The final destination for the algorithm.
     */
    private final Place finalPlace;

    /**
     * Creates a new instance of the Dijkstra algorithm for the given world, start and final places.
     *
     * @param world      the world containing the places.
     * @param startPlace the starting place.
     * @param finalPlace the final place.
     */
    public Dijkstra(World world, Place startPlace, Place finalPlace) {
        this.startPlace = startPlace;
        this.finalPlace = finalPlace;

        List<Place> places = world.getPlaceList();
        Set<Node> graphNodes = new HashSet<>();
        Map<String, Set<String>> graphMap = new HashMap<>();

        for (Place place : places) {
            Node node = new Node(place);
            graphNodes.add(node);
            graphMap.put(
                    node.getId(),
                    place.getPlaceConnections().stream()
                            .map(p -> String.valueOf(p.hashCode()))
                            .collect(Collectors.toSet())
            );
            unvisitedNodes.add(node);
        }

        graph = new Graph<>(graphNodes, graphMap);
    }

    /**
     * Main Dijkstra's algorithm which find the shortest path between the start and final places.
     *
     * @return the list of places representing the shortest path.
     * @throws NoPathFoundException if there is no path between the start and final places.
     */
    public List<Place> getTheShortestPath() throws NoPathFoundException {
        Node currentNode = graph.getNode(String.valueOf(startPlace.hashCode()));
        currentNode.distanceScore = 0;

        while (true) {
            currentNode = getMinDistanceScoreNode(unvisitedNodes);
            unvisitedNodes.remove(currentNode);

            for (Node node : graph.getConnections(currentNode)) {
                if (unvisitedNodes.contains(node)) {
                    double newDistanceScore = calculateScore(currentNode, node);
                    if (newDistanceScore < node.distanceScore) {
                        node.distanceScore = newDistanceScore;
                        node.previousNode = currentNode;
                    }
                }
            }

            if (currentNode.place == finalPlace) {
                return buildShortestPath(graph.getNode(
                        String.valueOf(finalPlace.hashCode())
                )).stream()
                        .map(node -> node.place)
                        .collect(Collectors.toList());
            }
            if (getMinDistanceScoreNode(unvisitedNodes).distanceScore == Double.POSITIVE_INFINITY) {
                throw new NoPathFoundException();
            }
        }
    }

    /**
     * Gets the node with the minimum distance score from a list of unvisited nodes.
     *
     * @param unvisitedNodes a list of unvisited nodes.
     * @return the node with the minimum distance score.
     */
    private Node getMinDistanceScoreNode(List<Node> unvisitedNodes) {
        Node minDistanceNode = unvisitedNodes.get(0);
        for (int i = 1; i < unvisitedNodes.size(); i++) {
            if (unvisitedNodes.get(i).distanceScore < minDistanceNode.distanceScore) {
                minDistanceNode = unvisitedNodes.get(i);
            }
        }

        return minDistanceNode;
    }

    /**
     * Calculates the score between two nodes.
     *
     * @param currentNode the current node.
     * @param nextNode    the next node.
     * @return the score between the two nodes.
     */
    @Override
    public double calculateScore(Node currentNode, Node nextNode) {
        return nextNode.place.distanceFrom(currentNode.place) + currentNode.distanceScore;
    }

    /**
     * Builds the shortest path from the start node to the final node using {@link ShortestPathIterator} pattern.
     *
     * @param finalNode the final node of the shortest path.
     * @return the list of nodes that make up the shortest path.
     */
    @Override
    public List<Node> buildShortestPath(Node finalNode) {
        List<Node> shortestPath = new ArrayList<>();
        shortestPath.add(finalNode);

        Iterator shortestPathIterator = getIterator(finalNode);
        while (shortestPathIterator.hasNext()) {
            shortestPath.add(shortestPathIterator.getNext());
        }

        Collections.reverse(shortestPath);
        return shortestPath;
    }

    /**
     * A private static class that implements the Iterator interface for iterating over the nodes in the shortest path in reverse order.
     */
    private static class ShortestPathIterator implements Iterator {
        /**
         * The current node in the iteration.
         */
        Node actualNode;

        /**
         * Constructs a new ShortestPathIterator with the given starting node.
         *
         * @param actualNode the starting node for the iteration
         */
        public ShortestPathIterator(Node actualNode) {
            this.actualNode = actualNode;
        }

        /**
         * Returns {@code true} if there are more nodes to iterate over in the shortest path.
         *
         * @return {@code true} if there are more nodes to iterate over in the shortest path
         */
        @Override
        public boolean hasNext() {
            return actualNode.previousNode != null;
        }

        /**
         * Returns the next node in the shortest path and updates the current node to the previous node.
         *
         * @return the next node in the shortest path
         */
        @Override
        public Node getNext() {
            if (this.hasNext()) {
                actualNode = actualNode.previousNode;
                return actualNode;
            }
            return null;
        }
    }

    /**
     * Gets an iterator for the nodes of the shortest path.
     *
     * @param object the final node of the shortest path.
     * @return an iterator for the nodes of the shortest path.
     */
    @Override
    public Iterator getIterator(Node object) {
        return new ShortestPathIterator(object);
    }
}
