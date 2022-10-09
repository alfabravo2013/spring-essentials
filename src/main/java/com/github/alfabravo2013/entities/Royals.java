package com.github.alfabravo2013.entities;

public class Royals implements Team {
    private final String name = "Kansas City Royals";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
