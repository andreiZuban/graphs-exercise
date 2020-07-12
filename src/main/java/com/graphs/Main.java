package com.graphs;

import com.graphs.graph.Graph;

public class Main {
    public static void main(String[] args) {
        var graph = new Graph<Integer>();

        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 2);
        graph.addEdge(1, 2, 3);
        graph.addEdge(2, 3, 50);
        graph.addEdge(4,5,6);
        graph.addEdge(5,6,20);
        graph.addEdge(2, 6, 1);
        graph.addEdge(6, 3, 1);

//        g.printGraphAdjacency();
//
//        g.breadthFirstTraversal(2);
//
//        List<Integer> nodes = g.nodesWithinRangeOf(0, 3);
//
//        System.out.println("===================");
//        nodes.forEach(System.out::println);
//        System.out.println("===================");
//
//        System.out.println(g.isConnected());
//
//
    }
}
