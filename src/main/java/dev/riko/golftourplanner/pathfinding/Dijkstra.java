package dev.riko.golftourplanner.pathfinding;

import dev.riko.golftourplanner.world.World;
import dev.riko.golftourplanner.world.place.Place;

import java.util.ArrayList;
import java.util.List;

public class Dijkstra {
    private static class Node {
        private static final int INFINITY = Integer.MAX_VALUE;

        private final Place place;
        private double distanceValue;
        private Node previousNode;

        public Node(Place place) {
            this.place = place;
            distanceValue = INFINITY;
        }
    }

    private final List<Node> unvisitedNodes = new ArrayList<>();
    private final List<Node> visitedNodes = new ArrayList<>();
    private final Place startPlace;
    private final Place finalPlace;

    public Dijkstra(World world, Place startPlace, Place finalPlace) {
        this.startPlace = startPlace;
        this.finalPlace = finalPlace;

        List<Place> places = world.getPlaceList();

        for (Place place : places) {
            unvisitedNodes.add(new Node(place));
        }
    }

    public List<Place> getShortestPath() {
        Node currentNode = getNode(startPlace);
        currentNode.distanceValue = 0;

        while (currentNode.place != finalPlace) {
            for (Place place : currentNode.place.getPlaceConnections()) {
                Node node = getNode(place);

                if (node != null) {
                    double distance = place.distanceFrom(currentNode.place);
                    distance += currentNode.distanceValue;

                    if (distance < node.distanceValue) {
                        node.distanceValue = distance;
                        node.previousNode = currentNode;
                    }
                }
            }

            visitedNodes.add(currentNode);
            unvisitedNodes.remove(currentNode);

            currentNode = getMinDistanceNode(unvisitedNodes);
        }

        List<Place> shortestPath = new ArrayList<>();
        shortestPath.add(currentNode.place);

        while (currentNode.previousNode != null) {
            currentNode = currentNode.previousNode;
            try {
                shortestPath.add(0, currentNode.place);
            } catch (NullPointerException e) {
                System.out.println("!!! Unexpected bug occurred, we are working on it !!!");
                break;
            }
        }

        return shortestPath;
    }

    private Node getMinDistanceNode(List<Node> unvisitedNodes) {
        Node minDistanceNode = unvisitedNodes.get(0);
        for (int i = 1; i < unvisitedNodes.size(); i++) {
            if (unvisitedNodes.get(i).distanceValue < minDistanceNode.distanceValue) {
                minDistanceNode = unvisitedNodes.get(i);
            }
        }

        return minDistanceNode;
    }

    private Node getNode(Place place) {
        Node rNode = null;

        for (Node node : unvisitedNodes) {
            if (node.place == place) {
                rNode = node;
            }
        }
        return rNode;
    }
}
