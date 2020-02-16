package ru.job4j.bridging;

public class Line {
    String[] line;

    Line(String line) {
        this.line = line.split(";");
    }

    boolean isContains(Line second) {
        return
                second.line[0] != "" && line[0] != "" && line[0].equals(second.line[0]) ||
                        second.line[1] != "" && line[1] != "" && line[1].equals(second.line[1]) ||
                        second.line[2] != "" && line[2] != "" && line[2].equals(second.line[2]);

    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(line[0]);
        stringBuilder.append(";");
        stringBuilder.append(line[1]);
        stringBuilder.append(";");
        stringBuilder.append(line[2]);
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }
}
