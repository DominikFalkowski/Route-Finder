//package org.example.routefinder;
//
//import javafx.geometry.Point2D;
//import java.util.*;
//
//public class PathFinder {
//    private static class Node implements Comparable<Node> {
//        Point2D point;
//        double cost;
//        double heuristic;
//
//        Node(Point2D point, double cost, double heuristic) {
//            this.point = point;
//            this.cost = cost;
//            this.heuristic = heuristic;
//        }
//
//        @Override
//        public int compareTo(Node other) {
//            return Double.compare(this.cost + this.heuristic, other.cost + other.heuristic);
//        }
//    }
//
//    public List<Point2D> findPath(Point2D start, Point2D end, boolean[][] grid) {
//        int width = grid.length;
//        int height = grid[0].length;
//        PriorityQueue<Node> openSet = new PriorityQueue<>();
//        Map<Point2D, Point2D> cameFrom = new HashMap<>();
//        Map<Point2D, Double> gScore = new HashMap<>();
//        Set<Point2D> closedSet = new HashSet<>();
//
//        openSet.add(new Node(start, 0, start.distance(end)));
//        gScore.put(start, 0.0);
//
//        while (!openSet.isEmpty()) {
//            Node current = openSet.poll();
//            if (current.point.equals(end)) {
//                return reconstructPath(cameFrom, current.point);
//            }
//
//            closedSet.add(current.point);
//
//            for (Point2D neighbor : getNeighbors(current.point, width, height)) {
//                if (!grid[(int) neighbor.getX()][(int) neighbor.getY()] || closedSet.contains(neighbor)) {
//                    continue;
//                }
//
//                double tentativeGScore = gScore.getOrDefault(current.point, Double.MAX_VALUE) + current.point.distance(neighbor);
//
//                if (tentativeGScore < gScore.getOrDefault(neighbor, Double.MAX_VALUE)) {
//                    cameFrom.put(neighbor, current.point);
//                    gScore.put(neighbor, tentativeGScore);
//                    openSet.add(new Node(neighbor, tentativeGScore, neighbor.distance(end)));
//                }
//            }
//        }
//
//        System.out.println("No path found from " + start + " to " + end);
//        return Collections.emptyList();
//    }
//
//    private List<Point2D> reconstructPath(Map<Point2D, Point2D> cameFrom, Point2D current) {
//        List<Point2D> path = new ArrayList<>();
//        while (current != null) {
//            path.add(current);
//            current = cameFrom.get(current);
//        }
//        Collections.reverse(path);
//        return path;
//    }
//
//    private List<Point2D> getNeighbors(Point2D point, int width, int height) {
//        List<Point2D> neighbors = new ArrayList<>();
//        int x = (int) point.getX();
//        int y = (int) point.getY();
//
//        if (x > 0) neighbors.add(new Point2D(x - 1, y));
//        if (x < width - 1) neighbors.add(new Point2D(x + 1, y));
//        if (y > 0) neighbors.add(new Point2D(x, y - 1));
//        if (y < height - 1) neighbors.add(new Point2D(x, y + 1));
//
//        return neighbors;
//    }
//}
//
