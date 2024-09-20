package org.example.routefinder;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CityGraphTest {

    @Test
    public void testAddNode() {
        CityGraph cityGraph = new CityGraph();
        cityGraph.addNode("A");
        cityGraph.addNode("B");
        assertTrue(cityGraph.getGraph().containsKey("A"));
        assertTrue(cityGraph.getGraph().containsKey("B"));
    }

    @Test
    public void testAddEdge() {
        CityGraph cityGraph = new CityGraph();
        cityGraph.addNode("A");
        cityGraph.addNode("B");
        cityGraph.addEdge("A", "B", 10.0, 5.0);
        assertTrue(cityGraph.getGraph().get("A").stream().anyMatch(edge -> edge.getDestination().equals("B")));
        assertTrue(cityGraph.getGraph().get("B").stream().anyMatch(edge -> edge.getDestination().equals("A")));
    }

    @Test
    public void testFindRoutesDFS() {
        CityGraph cityGraph = new CityGraph();
        cityGraph.addNode("A");
        cityGraph.addNode("B");
        cityGraph.addNode("C");
        cityGraph.addEdge("A", "B", 10.0, 5.0);
        cityGraph.addEdge("B", "C", 15.0, 3.0);
        List<List<String>> routes = cityGraph.findRoutesDFS("A", "C", 3);
        assertEquals(1, routes.size());
        assertEquals(List.of("A", "B", "C"), routes.getFirst());
    }

    @Test
    public void testFindShortestRouteBFS() {
        CityGraph cityGraph = new CityGraph();

        cityGraph.addNode("A");
        cityGraph.addNode("B");
        cityGraph.addNode("C");
        cityGraph.addNode("D");
        cityGraph.addNode("E");

        // Adding edges
        cityGraph.addEdge("A", "B", 1.0, 0.0);
        cityGraph.addEdge("A", "C", 1.0, 0.0);
        cityGraph.addEdge("B", "D", 1.0, 0.0);
        cityGraph.addEdge("C", "E", 1.0, 0.0);
        cityGraph.addEdge("D", "E", 1.0, 0.0);

        List<String> route = cityGraph.findShortestRouteBFS("A", "E");
        System.out.println("Shortest route from A to E: " + route);
    }

    @Test
    public void testFindRouteDijkstra() {
        CityGraph cityGraph = new CityGraph();
        cityGraph.addNode("A");
        cityGraph.addNode("B");
        cityGraph.addNode("C");
        cityGraph.addEdge("A", "B", 10.0, 5.0);
        cityGraph.addEdge("B", "C", 15.0, 3.0);
        List<String> shortestRoute = cityGraph.findRouteDijkstra("A", "C", false);
        assertEquals(List.of("A", "B", "C"), shortestRoute);
    }

    @Test
    public void testFindShortestOrCulturalRoute() {
        CityGraph cityGraph = new CityGraph();
        cityGraph.addNode("A");
        cityGraph.addNode("B");
        cityGraph.addNode("C");
        cityGraph.addEdge("A", "B", 10.0, 5.0);
        cityGraph.addEdge("B", "C", 15.0, 3.0);
    }
}
