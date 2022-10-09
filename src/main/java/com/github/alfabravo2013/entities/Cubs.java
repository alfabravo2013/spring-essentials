package com.github.alfabravo2013.entities;

import org.springframework.stereotype.Component;

@Component
public class Cubs implements Team {

    private final String name = "Chicago Cubs";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
