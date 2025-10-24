package com.javmb.studentscli.service.parsers;


import com.javmb.studentscli.model.Student;
import com.javmb.studentscli.service.parsers.mapper.MapToStudent;
import com.javmb.studentscli.util.SimpleXmlHandler;
import org.xml.sax.Attributes;

import java.util.List;
import java.util.Map;

public class StudentXmlParser extends SimpleXmlHandler {
    private final List<Student> students;
    private final String mainElement;

    public StudentXmlParser(List<Map<String, String>> items, List<Student> students, String mainElement) {
        super(items, mainElement);
        this.students = students;
        this.mainElement = mainElement;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        super.startElement(uri, localName, qName, attributes);
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        super.endElement(uri, localName, qName);
        if (mainElement.equals(qName) && !items.isEmpty()) {
            Map<String, String> last = items.getLast();
            Student s = MapToStudent.fromMap(last);
            students.add(s);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        super.characters(ch, start, length);
    }
}