package com.graphs.graph;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.emptyMap;
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

    public void addNode(N t) {
        nodes.put(t, new HashMap<>());
    }

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

    public Set<N> getNodes() {
        return nodes.keySet();
    }

    public Set<N> getNeighboringNodes(N node) {
        return nodes.getOrDefault(node, emptyMap())
                .keySet();
    }

    public boolean isConnected() {
        if(nodes.isEmpty()) {
            return false;
        }

        N start = nodes.keySet().stream()
                .findFirst()
                .get();

        return breadthFirstTraversal(start)
                .size() == nodes.size();
    }

    public List<N> nodesWithinRangeOf(N node, int maxRange) {
        Map<N, Integer> distance = shortestPath(node, node, true).getLowestCosts();

        return distance.entrySet().stream()
                .filter(nodeEntry -> nodeEntry.getValue() <= maxRange)
                .filter(nodeEntry -> ! nodeEntry.getKey().equals(node))
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
        Map<N, Integer> visited = new LinkedHashMap<>();

        visited.put(start, 0);
        queue.add(start);

        while (!queue.isEmpty()) {
            N current = queue.pollFirst();

            System.out.println("vertex " + current.toString());

            nodes.get(current).forEach((vertex, distance) -> {
                if(!visited.containsKey(vertex)) {
                    visited.put(vertex, visited.get(current) + distance);
                    queue.add(vertex);
                }
            });
        }

        return visited;
    }

    /**
     * Implementation of Dijkstra's algorithm to find the lowest cost path from source
     * to destination.
     * Supports efficient search that stops when destination node is reached. Also supports
     * full search which stops when all nodes were visited.
     *
     * @param source node
     * @param dest node
     * @param fullSearch boolean to specify whether search should end when all nodes ar visited
     *                   or when destination node is found.
     * @return shortest distance through graph from source to destination
     *         considering weighted edges
     */
    public ShortestPath<N> shortestPath(N source, N dest, boolean fullSearch) {
        final Set<N> visited = new LinkedHashSet<>();
        Map<N, Integer> distance = nodes.keySet().stream()
                .collect(toMap(identity(), __ -> Integer.MAX_VALUE));

        distance.put(source, 0);

        while (!visited.contains(dest)
                || ( fullSearch && visited.size() != nodes.size())) {
            N current = distance.entrySet().stream()
                .filter(e -> !visited.contains(e.getKey()))
                .min(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new RuntimeException("Expected to find a node here"));

            nodes.get(current).forEach((child, dist) -> {
                if (!visited.contains(child)) {
                    int alternate = distance.get(current) + dist;

                    if (alternate < distance.get(child)) {
                        distance.put(child, alternate);
                    }
                }
            });

            visited.add(current);
        }

        ShortestPath<N> shortestPath = new ShortestPath<>();
        shortestPath.setCost(distance.get(dest));
        shortestPath.setNodes(visited);
        shortestPath.setLowestCosts(distance);

        return shortestPath;
    }

    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    public int getSize() {
        return nodes.size();
    }
}
