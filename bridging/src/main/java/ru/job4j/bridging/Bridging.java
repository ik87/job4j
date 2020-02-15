package ru.job4j.bridging;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Bridging {
    Set<Group> groups = new TreeSet<>();

    public void putRows(List<String> rows) {
        Iterator<String> iterator = rows.iterator();
        int i = 1;
        String[] line = iterator.next().split(";");
        while (iterator.hasNext()) {
            Group group = new Group(i, line);
            boolean added = true;
            while (iterator.hasNext() && added) {
                line = iterator.next().split(";");
                added = group.putRow(line);
                if (!added || !iterator.hasNext()) {
                    if (group.size() > 1) {
                        groups.add(group);
                        i++;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Number groups: %s \n %s", groups.size(), groups);
    }
}
