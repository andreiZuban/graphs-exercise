package com.graphs.road;

public class Road {
    public static final String lengthMeasurementUnit = "Km";

    private final Town townA;
    private final Town townB;
    private final int length;

    public Road(Town townA, Town townB, int length) {
        this.townA = townA;
        this.townB = townB;
        this.length = length;
    }

    public Town getTownA() {
        return townA;
    }

    public Town getTownB() {
        return townB;
    }

    public int getLength() {
        return length;
    }
}
