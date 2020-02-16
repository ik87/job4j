package ru.job4j.bridging;

import java.util.Deque;
import java.util.LinkedList;

public class Group implements Comparable<Group> {
    int gnum;
    Deque<Line> lines;

    Group(int gnum, Line line) {
        this.gnum = gnum;
        this.lines = new LinkedList<>() {
            {
                add(line);
            }
        };
    }

    @Override
    public int compareTo(Group o) {
        return  Integer.compare(o.size(), this.size());
    }

    boolean putLine(Line line) {
        boolean added = false;
        if(lines.peekLast().isContains(line)) {
            lines.addLast(line);
            added = true;
        }
        return added;
    }

    int size() {
        return lines.size();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (var line : lines) {
            stringBuilder.append(line);
        }
        return String.format("\nGroup %s\n\r%s", gnum, stringBuilder);
    }
}
