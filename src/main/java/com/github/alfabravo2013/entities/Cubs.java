package com.github.alfabravo2013.entities;

import org.springframework.stereotype.Component;

@Component
public class Cubs implements Team {

    @Override
    public String getName() {
        return "Chicago Cubs";
    }
}
