package org.example.routefinder;

import javafx.geometry.Point2D;
import java.util.*;

public class CityGraph {
    private Map<String, List<Edge>> graph = new HashMap<>();

    public void addNode(String landmark) {
        graph.putIfAbsent(landmark, new ArrayList<>());
    }

    public void addEdge(String start, String destination, double distance, double culturalImportance) {
        graph.computeIfAbsent(start, k -> new ArrayList<>()).add(new Edge(destination, distance, culturalImportance));
        graph.computeIfAbsent(destination, k -> new ArrayList<>()).add(new Edge(start, distance, culturalImportance));
    }

    public boolean isEmpty() {
        return graph.isEmpty();
    }

    public Map<String, List<Edge>> getGraph() {
        return Collections.unmodifiableMap(graph);
    }

    public List<List<String>> findRoutesDFS(String start, String end, int maxRoutes) {
        List<List<String>> routes = new ArrayList<>();
        dfsUtil(start, end, new HashSet<>(), new ArrayList<>(), routes, maxRoutes);
        return routes;
    }

    private void dfsUtil(String current, String end, Set<String> visited, List<String> path, List<List<String>> routes, int maxRoutes) {
        visited.add(current);
        path.add(current);

        if (current.equals(end)) {
            if (routes.size() < maxRoutes) {
                routes.add(new ArrayList<>(path));
            }
        } else {
            for (Edge edge : graph.getOrDefault(current, Collections.emptyList())) {
                if (!visited.contains(edge.getDestination())) {
                    dfsUtil(edge.getDestination(), end, visited, path, routes, maxRoutes);
                }
            }
        }

        path.remove(path.size() - 1);
        visited.remove(current);
    }

    public List<String> findShortestRouteBFS(String start, String end) {
        Queue<String> queue = new LinkedList<>();
        Map<String, String> prev = new HashMap<>();
        Set<String> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);
        prev.put(start, null);

        while (!queue.isEmpty()) {
            String node = queue.poll();
            System.out.println("Visiting node: " + node);
            if (node.equals(end)) {
                System.out.println("End node reached: " + end);
                return reconstructPath(prev, end);
            }

            for (Edge edge : graph.getOrDefault(node, Collections.emptyList())) {
                if (!visited.contains(edge.getDestination())) {
                    visited.add(edge.getDestination());
                    queue.add(edge.getDestination());
                    prev.put(edge.getDestination(), node);
                    System.out.println("Adding node to queue: " + edge.getDestination());
                }
            }
        }

        System.out.println("No path found from " + start + " to " + end);
        return Collections.emptyList();
    }

    private List<String> reconstructPath(Map<String, String> prev, String end) {
        List<String> path = new ArrayList<>();
        for (String at = end; at != null; at = prev.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }


    public List<String> findRouteDijkstra(String start, String end, boolean byCultural) {
        Map<String, Double> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparing(dist::get));

        dist.put(start, 0.0);
        pq.add(start);
        while (!pq.isEmpty()) {
            String current = pq.poll();

            if (current.equals(end)) {
                return reconstructPath(prev, end);
            }

            for (Edge edge : graph.getOrDefault(current, Collections.emptyList())) {
                String next = edge.getDestination();
                double weight = byCultural ? edge.getCulturalImportance() : edge.getDistance();
                double alt = dist.get(current) + weight;
                if (!dist.containsKey(next) || alt < dist.get(next)) {
                    dist.put(next, alt);
                    prev.put(next, current);
                    pq.add(next);
                }
            }
        }

        return Collections.emptyList();
    }

    public String findNearestLandmark(Point2D point, Map<String, Point2D> nodeCoordinates) {
        double minDistance = Double.MAX_VALUE;
        String nearestLandmark = null;

        for (Map.Entry<String, Point2D> entry : nodeCoordinates.entrySet()) {
            double distance = entry.getValue().distance(point);
            if (distance < minDistance) {
                minDistance = distance;
                nearestLandmark = entry.getKey();
            }
        }

        System.out.println("Nearest landmark to point " + point + " is " + nearestLandmark + " with distance " + minDistance);
        return nearestLandmark;
    }
}
