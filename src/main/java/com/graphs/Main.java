package com.graphs;

import com.graphs.graph.Graph;

public class Main {
    public static void main(String[] args) {
        var g = new Graph<Integer>();

        g.addEdge(0, 1, 1);
        g.addEdge(0, 2, 2);
        g.addEdge(1, 2, 3);
        g.addEdge(2, 3, 5);

        g.printGraphAdjacency();
    }
}
