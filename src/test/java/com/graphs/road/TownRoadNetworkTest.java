package com.graphs.road;

import com.graphs.road.FullRoad;
import com.graphs.road.Road;
import com.graphs.road.Town;
import com.graphs.road.TownRoadNetwork;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class TownRoadNetworkTest {
    TownRoadNetwork townRoadNetwork;

    @Before
    public void init() {
        townRoadNetwork = new TownRoadNetwork();
    }

    @Test
    public void testAddTown() {
        Town firstTown = new Town("First Town", 10000);
        Town secondTown = new Town("Second Town", 14000);
        Road road = new Road(firstTown, secondTown, 100);

        townRoadNetwork.addConnection(road);

        Set<Town> towns = townRoadNetwork.getTowns();

        assertEquals(2, towns.size());
        assertTrue(towns.contains(firstTown));
        assertTrue(towns.contains(secondTown));
    }

    @Test
    public void testAllTownsConnected_True() {

        Town firstTown = new Town("First Town", 10000);
        Town secondTown = new Town("Second Town", 14000);
        Town thirdTown = new Town("Third Town", 5000);

        Road road1 = new Road(firstTown, secondTown, 100);
        Road road2 = new Road(secondTown, thirdTown, 500);

        townRoadNetwork.addConnection(road1);
        townRoadNetwork.addConnection(road2);

        assertTrue(townRoadNetwork.areAllTownsConnected());
    }

    @Test
    public void testAllTownsConnected_False() {
        Town firstTown = new Town("First Town", 10000);
        Town secondTown = new Town("Second Town", 14000);
        Town thirdTown = new Town("Third Town", 5000);
        Town fourthTown = new Town("Fourth Town", 6000);
        Town fifthTown = new Town("Fifth Town", 16000);

        Road road1 = new Road(firstTown, secondTown, 100);
        Road road2 = new Road(secondTown, thirdTown, 500);
        Road road3 = new Road(fourthTown, fifthTown, 500);

        townRoadNetwork.addConnection(road1);
        townRoadNetwork.addConnection(road2);
        townRoadNetwork.addConnection(road3);

        assertFalse(townRoadNetwork.areAllTownsConnected());
    }

    @Test
    public void testAveragePopulation() {
        int town_1_size = 10000;
        int town_2_size = 14000;
        int town_3_size = 5000;
        int town_4_size = 6000;
        int town_5_size = 16000;

        int expectedAvg = (town_1_size
                + town_2_size
                + town_3_size
                + town_4_size
                + town_5_size) / 5;

        Town firstTown = new Town("First Town", 10000);
        Town secondTown = new Town("Second Town", 14000);
        Town thirdTown = new Town("Third Town", 5000);
        Town fourthTown = new Town("Fourth Town", 6000);
        Town fifthTown = new Town("Fifth Town", 16000);

        Road road1 = new Road(firstTown, secondTown, 100);
        Road road2 = new Road(secondTown, thirdTown, 500);
        Road road3 = new Road(fourthTown, fifthTown, 500);

        townRoadNetwork.addConnection(road1);
        townRoadNetwork.addConnection(road2);
        townRoadNetwork.addConnection(road3);

        int avgPopulation = townRoadNetwork.getAvgPopulation();

        assertEquals(expectedAvg, avgPopulation);
    }

    @Test
    public void testGetRoadsInRange() {
        Town firstTown = new Town("First Town", 10000);
        Town secondTown = new Town("Second Town", 14000);
        Town thirdTown = new Town("Third Town", 5000);
        Town fourthTown = new Town("Fourth Town", 6000);
        Town fifthTown = new Town("Fifth Town", 16000);
        Town sixthTown = new Town("Sixth Town", 16000);
        Town seventhTown = new Town("Seventh Town", 16000);

        Road road1 = new Road(firstTown, secondTown, 100);
        Road road2 = new Road(secondTown, thirdTown, 500);
        Road road3 = new Road(secondTown, fifthTown, 20);
        Road road4 = new Road(fifthTown, thirdTown, 50);
        Road road5 = new Road(thirdTown, sixthTown, 200);
        Road road6 = new Road(sixthTown, seventhTown, 200);
        Road road7 = new Road(seventhTown, fourthTown, 180);
        Road road8 = new Road(seventhTown, fifthTown, 450);
        Road road9 = new Road(fourthTown, fifthTown, 630);

        townRoadNetwork.addConnection(road1);
        townRoadNetwork.addConnection(road2);
        townRoadNetwork.addConnection(road3);
        townRoadNetwork.addConnection(road4);
        townRoadNetwork.addConnection(road5);
        townRoadNetwork.addConnection(road6);
        townRoadNetwork.addConnection(road7);
        townRoadNetwork.addConnection(road8);
        townRoadNetwork.addConnection(road9);

        List<Town> actual = townRoadNetwork.getTownsWithinRange(fifthTown, 300);
        List<Town> expected = List.of(firstTown, secondTown, thirdTown, sixthTown);

        assertTrue(actual.containsAll(expected));
        assertTrue(expected.containsAll(actual));
    }

    @Test
    public void testGetRoadTo() {
        Town firstTown = new Town("First Town", 10000);
        Town secondTown = new Town("Second Town", 14000);
        Town thirdTown = new Town("Third Town", 5000);
        Town fourthTown = new Town("Fourth Town", 6000);
        Town fifthTown = new Town("Fifth Town", 16000);
        Town sixthTown = new Town("Sixth Town", 16000);
        Town seventhTown = new Town("Seventh Town", 16000);

        Road road1 = new Road(firstTown, secondTown, 100);
        Road road2 = new Road(secondTown, thirdTown, 500);
        Road road3 = new Road(secondTown, fifthTown, 20);
        Road road4 = new Road(fifthTown, thirdTown, 50);
        Road road5 = new Road(thirdTown, sixthTown, 200);
        Road road6 = new Road(sixthTown, seventhTown, 200);
        Road road7 = new Road(seventhTown, fourthTown, 180);

        townRoadNetwork.addConnection(road1);
        townRoadNetwork.addConnection(road2);
        townRoadNetwork.addConnection(road3);
        townRoadNetwork.addConnection(road4);
        townRoadNetwork.addConnection(road5);
        townRoadNetwork.addConnection(road6);
        townRoadNetwork.addConnection(road7);

        FullRoad shortestRoad = townRoadNetwork.roadTo(firstTown, thirdTown);

        assertEquals(170, shortestRoad.getDistance());

        String actualTownsInOrder = shortestRoad.getTowns().stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));

        Set<Town> expectedTownsSet = new LinkedHashSet<>();
        expectedTownsSet.add(firstTown);
        expectedTownsSet.add(secondTown);
        expectedTownsSet.add(fifthTown);
        expectedTownsSet.add(thirdTown);

        String expectedTownsInOrder = expectedTownsSet.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));

        assertEquals(expectedTownsInOrder, actualTownsInOrder);
    }
}
