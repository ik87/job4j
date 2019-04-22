package ru.job4j.magnit;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class SaxCounter {
    private File file;

    public Long countFieldFromXML() throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLHandler handler = new XMLHandler();
        parser.parse(file, handler);
        return handler.getSum();

    }

    public SaxCounter(File file) {
        this.file = file;
    }

    private class XMLHandler extends DefaultHandler {
        private Long sum = 0L;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("entry")) {
                sum += Long.parseLong(attributes.getValue("field"));
            }
        }

        public Long getSum() {
            return sum;
        }
    }
}
