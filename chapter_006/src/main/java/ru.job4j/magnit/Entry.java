package ru.job4j.magnit;

import javax.xml.bind.annotation.*;
import java.util.Objects;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Entry {

    //@XmlAttribute(name="field")
    private Integer value;

    public Integer getValue() {
        return value;
    }

    public Entry() {

    }

    public Entry(Integer value) {
        this.value = value;
    }


    public Integer setValue(Integer value) {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entry entry = (Entry) o;
        return Objects.equals(value, entry.value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
