package ru.job4j.bridging;

import java.util.Deque;
import java.util.LinkedList;

public class Group implements Comparable<Group> {
    int gnum;
    Deque<String[]> rows;

    public Group(int gnum, String[] row) {
        this.gnum = gnum;
        this.rows = new LinkedList<>() {
            {
                add(row);
            }
        };
    }

    @Override
    public int compareTo(Group o) {
        return  Integer.compare(o.rows.size(), rows.size());
    }

    public boolean putRow(String[] row) {
        boolean added = false;
        for (int i = 0; i < 3; i++) {
            String[] peek = rows.peekLast();
            if (peek[i] != null && row[i] != null && row[i].equals(peek[i])) {
                rows.addLast(row);
                added = true;
                break;
            }
        }
        return added;
    }

    public int size() {
        return rows.size();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (var row : rows) {
            stringBuilder.append(row[0]);
            stringBuilder.append(";");
            stringBuilder.append(row[1]);
            stringBuilder.append(";");
            stringBuilder.append(row[2]);
            stringBuilder.append("\n");
        }
        return String.format("\nGroup %s\n\r%s", gnum, stringBuilder);
    }
}
