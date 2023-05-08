package dev.riko.golftourplanner.pathfinding;

import dev.riko.golftourplanner.exeptions.NoPathFound;
import dev.riko.golftourplanner.world.World;
import dev.riko.golftourplanner.world.place.Place;

import java.util.*;
import java.util.stream.Collectors;

public class Dijkstra implements GraphUtilFunctions<Dijkstra.Node> {
    protected static class Node implements GraphNode {
        private static final double INFINITY = Double.POSITIVE_INFINITY;

        private final Place place;
        private double distanceScore;
        private Node previousNode;

        public Node(Place place) {
            this.place = place;
            distanceScore = INFINITY;
        }

        @Override
        public String getId() {
            return String.valueOf(place.hashCode());
        }
    }

    private final List<Node> unvisitedNodes = new ArrayList<>();
    private final Graph<Node> graph;
    private final Place startPlace;
    private final Place finalPlace;

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

    public List<Place> getTheShortestPath() throws NoPathFound {
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
                throw new NoPathFound();
            }
        }
    }

    private Node getMinDistanceScoreNode(List<Node> unvisitedNodes) {
        Node minDistanceNode = unvisitedNodes.get(0);
        for (int i = 1; i < unvisitedNodes.size(); i++) {
            if (unvisitedNodes.get(i).distanceScore < minDistanceNode.distanceScore) {
                minDistanceNode = unvisitedNodes.get(i);
            }
        }

        return minDistanceNode;
    }

    @Override
    public double calculateScore(Node currentNode, Node nextNode) {
        return nextNode.place.distanceFrom(currentNode.place) + currentNode.distanceScore;
    }

    @Override
    public List<Node> buildShortestPath(Node finalNode) {
        List<Node> shortestPath = new ArrayList<>();

        Node currentNode = finalNode;
        while (currentNode != null) {
            shortestPath.add(currentNode);
            currentNode = currentNode.previousNode;
        }

        List<Node> shortestPathCorrectOrder = new ArrayList<>();
        for (int i = shortestPath.size() - 1; i >= 0; i--) {
            shortestPathCorrectOrder.add(shortestPath.get(i));
        }

        return shortestPathCorrectOrder;
    }
}
