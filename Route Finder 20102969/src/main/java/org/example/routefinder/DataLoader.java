package org.example.routefinder;

import javafx.geometry.Point2D;
import org.example.routefinder.CityGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class DataLoader {

    public static void loadLandmarks(String filename, CityGraph cityGraph, Map<String, Point2D> nodeCoordinates) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isFirstLine = true; // Assume there's a header line to skip
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }
                String[] data = line.split(",");
                if (data.length == 3) {
                    try {
                        String landmark = data[0].trim();
                        double latitude = Double.parseDouble(data[1].trim());
                        double longitude = Double.parseDouble(data[2].trim());
                        Point2D point = new Point2D(latitude, longitude);
                        nodeCoordinates.put(landmark, point);
                        cityGraph.addNode(landmark); // Also add landmark to the CityGraph
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping line due to number format error: " + line + " | Error: " + e.getMessage());
                    }
                } else {
                    System.err.println("Skipping malformed line: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading landmark data from file: " + filename + " - " + e.getMessage());
        }
        System.out.println("Loaded landmarks: " + nodeCoordinates.keySet());
    }


    public static void loadConnections(String filename, CityGraph cityGraph) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the header line
                }
                String[] data = line.split(",");
                if (data.length == 4) {
                    try {
                        String landmark = data[0].trim();
                        String connectedLandmark = data[1].trim();
                        double distance = Double.parseDouble(data[2].trim());
                        double culturalImportance = Double.parseDouble(data[3].trim());

                        cityGraph.addNode(landmark);
                        cityGraph.addNode(connectedLandmark);
                        cityGraph.addEdge(landmark, connectedLandmark, distance, culturalImportance);
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping line due to number format error: " + line + " | Error: " + e.getMessage());
                    }
                } else {
                    System.err.println("Skipping malformed line: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading connection data from file: " + filename + " - " + e.getMessage());
        }
        System.out.println("Available landmarks: " + cityGraph.getGraph().keySet());
    }
}
