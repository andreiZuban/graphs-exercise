package com.graphs.graph;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A generic implementation of weighted unidirected graph
 * @param <N> the generic type of nodes.
 */
public class Graph <N> {
    /**
     * Adjacency representation. Each node is mapped to a collection of adjacency nodes. Each adjacency is represented by a mapping
     * from destination node to corresponding wight of the road.
     */
    private final Map<N, Map<N, Integer>> nodes = new HashMap<>();

    public void addEdge(N a, N b, int weight) {
        addOneWayEdge(a, b, weight);
        addOneWayEdge(b, a, weight);
    }

    public void clear() {
        nodes.clear();
    }

    private void addOneWayEdge(N from, N to, int weight) {
        if(!nodes.containsKey(from)) {
            nodes.put(from, new HashMap<>());
        }

        if(nodes.get(from).containsKey(to)) {
            return;
        }

        nodes.get(from).put(to, weight);
    }

    public String toString() {
        return nodes.entrySet().stream()
            .map(entry -> entry.getKey().toString() + " -> "
                        + entry.getValue().keySet().stream().map(Object::toString).collect(Collectors.joining(", ")))
            .collect(Collectors.joining("\n"));
    }

    public void printGraphAdjacency() {
        nodes.forEach((key, value) ->
                System.out.println(key.toString() + " -> "
                    + value.keySet().stream().map(Object::toString).collect(Collectors.joining(", "))));
    }

    public Map<N, Map<N, Integer>> getWeightedGraph() {
        return Map.copyOf(nodes);
    }

    public Map<N, Set<N>> getUnweightedGraph() {
        return nodes.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().keySet()));
    }
}
