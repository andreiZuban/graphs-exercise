package com.graphs.road;

public class Road {
    public static final String lengthMeasurementUnit = "Km";

    Town townA;
    Town townB;
    int length;

    public Road(Town townA, Town townB, int length) {
        this.townA = townA;
        this.townB = townB;
        this.length = length;
    }

    public Road() {
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

    public void setLength(int length) {
        this.length = length;
    }
}
