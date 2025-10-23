package com.javmb.studentscli.service.interfaces;

import com.javmb.studentscli.model.Student;

import java.util.List;

public interface StudentsSerializer {

    void serialize(List<Student> students, String filePath);
}
