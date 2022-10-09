package com.github.alfabravo2013.entities;

import org.springframework.stereotype.Component;

@Component
public class RedSox implements Team {
    private final String name = "Boston Red Sox";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
