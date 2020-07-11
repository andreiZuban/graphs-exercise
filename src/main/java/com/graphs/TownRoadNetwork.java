package com.graphs;

import com.graphs.graph.Graph;
import com.graphs.road.Road;
import com.graphs.road.Town;

import java.util.*;
import java.util.stream.IntStream;

public class TownRoadNetwork {
    private final Graph<Town> townGraph = new Graph<>();

    public void addConnection(Road road) {
        townGraph.addEdge(road.getTownA(), road.getTownB(), road.getLength());
    }

    public void breadthFirstSearch(Town start) {
        Map<Town, Set<Town>> nodes = townGraph.getUnweightedGraph();
        Deque<Town> queue = new ArrayDeque<>();
        Map<Town, Boolean> visited = new HashMap<>();

        nodes.forEach((k,v) -> visited.put(k, false));

        visited.put(start, true);
        queue.add(start);

        while (!queue.isEmpty()) {
            Town current = queue.pollFirst();

            System.out.println("vertex " + current.toString());

            nodes.get(current).forEach(vertex -> {
                if(!visited.get(vertex)) {
                    visited.put(vertex, true);
                    queue.add(vertex);
                }
            });
        }
    }

    public int getAvgPopulation() {
        Double avg = townGraph.getUnweightedGraph().keySet().stream()
                .mapToInt(Town::getPopulation)
                .average()
                .orElse(Double.NaN);

        return avg.intValue();
    }
}
