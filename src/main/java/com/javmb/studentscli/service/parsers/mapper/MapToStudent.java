package com.javmb.studentscli.service.parsers.mapper;

import com.javmb.studentscli.model.Student;
import java.util.Map;

public final class MapToStudent {
    private MapToStudent() { }

    public static Student fromMap(Map<String, String> m) {
        return Student.builder()
            .id(Integer.parseInt(m.get("id")))
            .name(m.get("name"))
            .mark1(Double.parseDouble(m.get("mark1")))
            .mark2(Double.parseDouble(m.get("mark2")))
            .markAverage(Double.parseDouble(m.get("markAverage")))
            .build();
    }
}
