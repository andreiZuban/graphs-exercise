package com.graphs.graph;

import com.graphs.road.Town;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.emptyMap;
import static java.util.Collections.enumeration;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * A generic implementation of weighted unidirected graph
 * @param <N> the generic type of nodes.
 */
public class Graph <N> {
    private static final int UNVISITED_NODE_DISTANCE = -1;
    /**
     * Adjacency representation. Each node is mapped to a collection of adjacency nodes. Each adjacency is represented by a mapping
     * from destination node to corresponding wight of the road.
     */
    private final Map<N, Map<N, Integer>> nodes = new HashMap<>();

    public void addEdge(N from, N to, int weight) {
        if(from == null || to == null) {
            throw new IllegalArgumentException("No null nodes allowed.");
        }

        if(from.equals(to)) {
            throw new IllegalArgumentException("Cannot connect same node with an edge.");
        }

        if(weight <= 0) {
            throw new IllegalArgumentException("Weight cannot be negative or 0.");
        }

        addOneWayEdge(from, to, weight);
        addOneWayEdge(to, from, weight);
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

    public Set<N> getNeighboringNodes(N node) {
        return nodes.getOrDefault(node, emptyMap())
                .keySet();
    }

    public boolean isConnected() {
        N start = nodes.keySet().stream()
                .findFirst()
                .get();

        return ! breadthFirstTraversal(start)
                .containsValue(UNVISITED_NODE_DISTANCE);
    }

    public List<N> nodesWithinRangeOf(N node, int maxRange) {
        return breadthFirstTraversal(node).entrySet().stream()
                .filter(nIntegerEntry -> nIntegerEntry.getValue() <= maxRange)
                .map(Map.Entry::getKey)
                .collect(toList());
    }

    /**
     * Does a breadth first traversal over the graph and computes weight of path from
     * start node to all visited nodes. Unvisited/unreacheable notes have -1 weighted distance.
     * @param start start node
     * @return a mapping for each note to whether it was visited or not
     */
    public Map<N, Integer> breadthFirstTraversal(N start) {
        Deque<N> queue = new ArrayDeque<>();
        Map<N, Integer> visited = new HashMap<>();

        nodes.forEach((k,v) -> visited.put(k, UNVISITED_NODE_DISTANCE));

        visited.put(start, 0);
        queue.add(start);

        while (!queue.isEmpty()) {
            N current = queue.pollFirst();

            System.out.println("vertex " + current.toString());

            nodes.get(current).forEach((vertex, distance) -> {
                if(visited.get(vertex) == -1) {
                    visited.put(vertex, visited.get(current) + distance);
                    queue.add(vertex);
                }
            });
        }

        return visited;
    }

    /**
     * Implementation of Dijkstra's algorithm
     * @param source node
     * @param dest node
     * @return shortest distance through graph from source to destination
     *         considering weighted edges
     */
    public int shortestPath(N source, N dest) {
        Map<N, Boolean> visited = new HashMap<>();
        nodes.forEach((n,__) -> visited.put(n, false));
        Map<N, Integer> tentativeDistance = nodes.keySet().stream()
                .collect(toMap(identity(), __ -> Integer.MAX_VALUE));

        tentativeDistance.put(source, 0);

        N current = source;

        do {
            for(Map.Entry<N, Integer> entry : nodes.get(current).entrySet()) {
                N child = entry.getKey();
                int dist = entry.getValue();

                if (!visited.get(child)) {
                    int alternate = tentativeDistance.get(current) + dist;

                    if (alternate < tentativeDistance.get(child)) {
                        tentativeDistance.put(child, alternate);
                    }
                }
            }

            visited.put(current, true);

            current = tentativeDistance.entrySet().stream()
                    .filter(e-> ! visited.get(e.getKey()))
                    .min(Comparator.comparingInt(Map.Entry::getValue))
                    .map(Map.Entry::getKey)
                    .get();

        } while (current.equals(dest));

        return tentativeDistance.get(dest);
    }
}
