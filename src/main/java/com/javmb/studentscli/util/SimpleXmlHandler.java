package com.javmb.studentscli.util;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class SimpleXmlHandler extends DefaultHandler {
    protected final List<Map<String, String>> items;
    private final String mainElement;
    private Map<String, String> currentItem;
    private StringBuilder currentValue;

    public SimpleXmlHandler(List<Map<String, String>> items, String mainElement) {
        this.items = items;
        this.mainElement = mainElement;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        currentValue = new StringBuilder();
        if (mainElement.equals(qName)) {
            currentItem = new HashMap<>();
            for (int i = 0; i < attributes.getLength(); i++) {
                currentItem.put(attributes.getQName(i), attributes.getValue(i));
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (currentItem != null && !mainElement.equals(qName)) {
            currentItem.put(qName, currentValue.toString().trim());
        }
        if (mainElement.equals(qName) && currentItem != null) {
            items.add(currentItem);
            currentItem = null;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (currentValue != null) {
            currentValue.append(ch, start, length);
        }
    }
}