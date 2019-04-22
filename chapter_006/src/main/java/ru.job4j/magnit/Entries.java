package ru.job4j.magnit;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Entries {

    @XmlElement(name = "entry")
    private List<Entry> entries;

    public Entries(List<Entry> entries) {
        this.entries = entries;
    }

    public Entries() {
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }
}
