package com.graphs;

import com.graphs.graph.Graph;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        var g = new Graph<Integer>();

        g.addEdge(0, 1, 1);
        g.addEdge(0, 2, 2);
        g.addEdge(1, 2, 3);
        g.addEdge(2, 3, 5);
        g.addEdge(4,5,6);
        g.addEdge(5,6,20);
        g.addEdge(3,6,100);
        g.addEdge(2, 6, 1);

        g.printGraphAdjacency();

        g.breadthFirstTraversal(2);

        List<Integer> nodes = g.nodesWithinRangeOf(0, 3);

        System.out.println("===================");
        nodes.forEach(System.out::println);
        System.out.println("===================");

        System.out.println(g.isConnected());
    }
}
