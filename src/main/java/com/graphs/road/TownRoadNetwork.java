package com.graphs.road;

import com.graphs.graph.Graph;
import com.graphs.graph.ShortestPath;

import java.util.List;
import java.util.Set;

public class TownRoadNetwork {
    private final Graph<Town> townGraph = new Graph<>();

    public void addTown(Town t) {
        townGraph.addNode(t);
    }

    public void addConnection(Road road) {
        townGraph.addEdge(road.getTownA(), road.getTownB(), road.getLength());
    }

    public int getAvgPopulation() {
        double avg = townGraph.getNodes().stream()
                .mapToInt(Town::getPopulation)
                .average()
                .orElse(Double.NaN);

        return (int) avg;
    }

    public boolean areAllTownsConnected() {
        return townGraph.isConnected();
    }

    public FullRoad roadTo(Town from, Town to) {
        ShortestPath<Town> shortestPath = townGraph.shortestPath(from, to, false);
        return new FullRoad(shortestPath.getNodes(), shortestPath.getCost());
    }

    public List<Town> getTownsWithinRange(Town of, int range) {
        return townGraph.nodesWithinRangeOf(of, range);
    }

    public Set<Town> getTowns() {
        return townGraph.getNodes();
    }
}
