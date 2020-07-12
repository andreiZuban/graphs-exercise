package com.graphs.road;

public class Town {
    public Town(String name, int population) {
        this.name = name;
        this.population = population;
    }

    private final String name;
    private final int population;

    @Override
    public String toString() {
        return name;
    }

    public int getPopulation() {
        return population;
    }
}
