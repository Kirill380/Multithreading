package com.redkite.multithreding.webgraph;


public class Address {
    private final String address;
    private boolean visited;

    public Address(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public boolean isVisited() {
        return visited;
    }

    public void visited() {
        this.visited = true;
    }
}
