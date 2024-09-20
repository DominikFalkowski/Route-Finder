package org.example.routefinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RouteNode {
    private final String name;
    private final double priority;
    private final RouteNode parent;

    public RouteNode(String name, double priority) {  // For root node
        this(name, priority, null);
    }

    public RouteNode(String name, double priority, RouteNode parent) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
        this.priority = priority;
        this.parent = parent;
    }

    public boolean isRoot() {
        return parent == null;
    }

    public String getName() {
        return name;
    }

    public double getPriority() {
        return priority;
    }

    public RouteNode getParent() {
        return parent;
    }

    public List<String> getPathFromRoot() {
        List<String> path = new ArrayList<>();
        RouteNode current = this;
        while (current != null) {
            path.add(current.name);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RouteNode routeNode = (RouteNode) obj;
        return name.equals(routeNode.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "RouteNode{" +
                "name='" + name + '\'' +
                ", priority=" + priority +
                ", parent=" + (parent == null ? "null" : parent.getName()) +
                '}';
    }
}
