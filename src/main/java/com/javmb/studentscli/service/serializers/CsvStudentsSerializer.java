package com.javmb.studentscli.service.serializers;

import com.javmb.studentscli.model.Student;
import com.javmb.studentscli.service.interfaces.StudentsSerializer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CsvStudentsSerializer implements StudentsSerializer {


    @Override
    public void serialize(List<Student> students, String filePath) {



    }
}
