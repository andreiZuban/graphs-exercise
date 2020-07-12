package com.graphs.road;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class FullRoad {
    private final Set<Town> towns = new LinkedHashSet<>();
    private int distance;

    public FullRoad(Collection<Town> towns, int distance) {
        this.towns.addAll(towns);
        this.distance = distance;
    }

    public Set<Town> getTowns() {
        return towns;
    }

    public int getDistance() {
        return distance;
    }
}
