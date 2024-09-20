package org.example.routefinder;

import java.util.Objects;


public class Edge {
    private final String destination;
    private final double distance;
    private final double culturalImportance;


    public Edge(String destination, double distance, double culturalImportance) {
        if (destination == null || destination.isEmpty()) {
            throw new IllegalArgumentException("Destination cannot be null or empty");
        }
        if (distance < 0) {
            throw new IllegalArgumentException("Distance cannot be negative");
        }
        if (culturalImportance < 0) {
            throw new IllegalArgumentException("Cultural importance cannot be negative");
        }

        this.destination = destination;
        this.distance = distance;
        this.culturalImportance = culturalImportance;
    }


    public String getDestination() {
        return destination;
    }


    public double getDistance() {
        return distance;
    }


    public double getCulturalImportance() {
        return culturalImportance;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "destination='" + destination + '\'' +
                ", distance=" + distance +
                ", culturalImportance=" + culturalImportance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Double.compare(edge.distance, distance) == 0 &&
                Double.compare(edge.culturalImportance, culturalImportance) == 0 &&
                destination.equals(edge.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(destination, distance, culturalImportance);
    }
}
