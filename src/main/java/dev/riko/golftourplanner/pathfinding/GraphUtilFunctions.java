package dev.riko.golftourplanner.pathfinding;

import java.util.List;

public interface GraphUtilFunctions<T extends GraphNode> {
    double calculateScore(T currentNode, T nextNode);

    List<T> buildShortestPath(T finalNode);
}
