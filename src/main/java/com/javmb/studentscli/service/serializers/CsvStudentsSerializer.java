package com.javmb.studentscli.service.serializers;

import com.javmb.studentscli.exception.CsvWriteException;
import com.javmb.studentscli.model.Student;
import com.javmb.studentscli.service.interfaces.StudentsSerializer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class CsvStudentsSerializer implements StudentsSerializer {


    @Override
    public void serialize(List<Student> students, String filePath) {

        try (var writer = Files.newBufferedWriter(Path.of(filePath))) {

            writer.write("id,name,mark1,mark2");
            writer.newLine();

            for (Student student : students) {
                String line = String.format(Locale.US, "%d,%s,%.2f,%.2f",
                        student.getId(),
                        student.getName(),
                        student.getMark1(),
                        student.getMark2());
                writer.write(line);
                writer.newLine();
            }

        } catch (IOException e) {
            throw new CsvWriteException("Error al escribir el fichero CSV", e);
        }

    }
}
