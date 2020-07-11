package com.graphs;

import com.graphs.graph.Graph;
import com.graphs.road.Road;
import com.graphs.road.Town;

public class TownRoadNetwork {
    private final Graph<Town> townGraph = new Graph<>();

    public void addConnection(Road road) {
        townGraph.addEdge(road.getTownA(), road.getTownB(), road.getLength());
    }

    public int getAvgPopulation() {
        double avg = townGraph.getUnweightedGraph().keySet().stream()
                .mapToInt(Town::getPopulation)
                .average()
                .orElse(Double.NaN);

        return (int) avg;
    }

    public boolean areTownsConnected() {
        return townGraph.isConnected();
    }
}
