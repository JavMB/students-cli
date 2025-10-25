package com.javmb.studentscli.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Student {
    private int id;
    private String name;
    private double mark1;
    private double mark2;
    private double markAverage;

    @Override
    public String toString() {
        return "----------------------" + System.lineSeparator() +
                "Student:" + System.lineSeparator() +
                "  id=" + id + System.lineSeparator() +
                "  name=" + name + System.lineSeparator() +
                "  mark1=" + mark1 + System.lineSeparator() +
                "  mark2=" + mark2 + System.lineSeparator() +
                "  markAverage=" + markAverage;
    }
}
