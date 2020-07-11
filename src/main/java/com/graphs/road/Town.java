package com.graphs.road;

public class Town {
    public Town(String name, int population) {
        this.name = name;
        this.population = population;
    }

    String name;
    int population;

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }
}
