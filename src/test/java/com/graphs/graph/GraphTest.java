package com.graphs.graph;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class GraphTest {
    Graph<Integer> graph = new Graph<>();

    @Before
    public void init() {
        graph.clear();
    }

    @Test
    public void testAddEdge_Bidirectional() {
        graph.addEdge(1,2,1);

        assertEquals(2, graph.getSize());
        assertTrue(graph.getNodes().contains(2));
        assertTrue(graph.getNodes().contains(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeNull_Arg1_NotAllowed() {
        graph.addEdge(null, 1, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeNull_Arg2_NotAllowed() {
        graph.addEdge(1, null, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeNull_BothArgsNotAllowed() {
        graph.addEdge(null, null, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEdge_NegativeWeightNotAllowed() {
        graph.addEdge(1, 2, -2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEdge_ZeroWeightNotAllowed() {
        graph.addEdge(1, 2, 0);
        assertTrue(graph.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEdge_SameNodeNotAllowed() {
        graph.addEdge(1, 1, 2);
        assertTrue(graph.isEmpty());
    }

    @Test
    public void testGetNeighboringNodes() {
        graph.addEdge(1,2,10);
        graph.addEdge(1,3,10);
        graph.addEdge(2,3,10);
        graph.addEdge(3,4,10);

        Set<Integer> expected = Set.of(2,3);

        Set<Integer> actual = graph.getNeighboringNodes(1);

        assertTrue(actual.containsAll(expected));
        assertTrue(expected.containsAll(actual));
    }

    @Test
    public void testGraphIsConnected() {
        graph.addEdge(1,2,10);
        graph.addEdge(1,3,10);
        graph.addEdge(2,3,10);
        graph.addEdge(3,4,10);
        graph.addEdge(3, 5, 10);
        graph.addEdge(2, 6, 2);

        assertTrue(graph.isConnected());
    }

    @Test
    public void testGraph_IsNOT_Connected() {
        graph.addEdge(1,2,10);
        graph.addEdge(1,3,10);
        graph.addEdge(2,3,10);
        graph.addEdge(8, 5, 10);
        graph.addEdge(4, 6, 2);

        assertFalse(graph.isConnected());
    }

    @Test
    public void testNodesWithinRangeOf() {
        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 2);
        graph.addEdge(1, 2, 3);
        graph.addEdge(1, 3, 50);;
        graph.addEdge(1,5,6);
        graph.addEdge(2,4,20);
        graph.addEdge(4,6,100);
        graph.addEdge(2, 6, 1);
        graph.addEdge(1, 4, 2);

        List<Integer> expected = List.of(0,2,4);

        List<Integer> actual = graph.nodesWithinRangeOf(1,3);

        assertTrue(expected.containsAll(actual));
        assertTrue(actual.containsAll(expected));
    }

    @Test
    public void testBreadthFirstTraversal() {
        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 2);
        graph.addEdge(1, 2, 2);
        graph.addEdge(2, 0, 4);
        graph.addEdge(2, 3, 1);

        Map<Integer, Integer> traversalResult = graph.breadthFirstTraversal(2);

        List<Integer> resultList = new ArrayList<>(traversalResult.keySet());

        assertEquals(4, resultList.size());
        assertEquals(2, resultList.get(0).intValue());
        assertEquals(0, resultList.get(1).intValue());
        assertEquals(1, resultList.get(2).intValue());
        assertEquals(3, resultList.get(3).intValue());
    }

    @Test
    public void testShortestPath() {
        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 2);
        graph.addEdge(1, 2, 3);
        graph.addEdge(2, 3, 50);
        graph.addEdge(4,5,6);
        graph.addEdge(5,6,20);
        graph.addEdge(2, 6, 1);
        graph.addEdge(6, 3, 1);

        //Path should be : 0 -> 1 -> 2 -> 6 -> 3
        ShortestPath<Integer> shortestPath = graph.shortestPath(0, 3, false);

        String actualNodesInOrder = shortestPath.getNodes().stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));

        Set<Integer> expectedNodesSet = new LinkedHashSet<>();
        expectedNodesSet.add(0);
        expectedNodesSet.add(1);
        expectedNodesSet.add(2);
        expectedNodesSet.add(6);
        expectedNodesSet.add(3);

        String expectedNodes = expectedNodesSet.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));

        assertEquals(4, shortestPath.getCost());
        assertEquals(expectedNodes, actualNodesInOrder);
    }
}
