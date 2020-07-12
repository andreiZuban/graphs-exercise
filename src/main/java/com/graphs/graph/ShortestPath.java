package com.graphs.graph;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class ShortestPath<N> {
    private final Set<N> nodes = new LinkedHashSet<>();
    private int cost;
    private Map<N, Integer> lowestCosts;

    public void addNode(N node) {
        if(node == null) {
            throw new NullPointerException("No null nodes allowed");
        }

        nodes.add(node);
    }

    public void setNodes(Collection<N> nodesss) {
        if(nodesss == null) {
            throw new NullPointerException("No null nodes allowed");
        }

        nodes.clear();

        nodes.addAll(nodesss);
    }

    public Set<N> getNodes() {
        return nodes;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Map<N, Integer> getLowestCosts() {
        return lowestCosts;
    }

    public void setLowestCosts(Map<N, Integer> lowestCosts) {
        this.lowestCosts = lowestCosts;
    }
}
