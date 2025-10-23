package com.javmb.studentscli.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Student {
    private int id;
    private String name;
    private double mark1;
    private double mark2;
}
