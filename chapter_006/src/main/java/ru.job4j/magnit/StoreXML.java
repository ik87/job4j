package ru.job4j.magnit;

import java.io.File;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class StoreXML {
    File target;

    public StoreXML(File target) {
        this.target = target;
    }

    public void save(List<Entry> list) {
        try {
            JAXBContext jc = JAXBContext.newInstance(Entries.class);
            Entries entries = new Entries(list);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(entries, target);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
