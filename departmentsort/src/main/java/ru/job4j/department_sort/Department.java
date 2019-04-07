package ru.job4j.department_sort;

import java.util.*;

/**
 * This class stores department, order depends on comparator
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version 0.1
 */
public class Department {
    Map<String, List<String>> departments;

    /**
     * Create new departments
     * @param str raw array string
     */
    public Department(String[] str) {
        this.departments = new TreeMap<>(String::compareTo);
        addAll(str);
    }

    /**
     * Get every main head by "\" and other parts join to it
     * @param str raw  string array
     */
    private void addAll(String[] str) {
        for (String s : str) {
            String[] sub = s.split("\\\\");
            List<String> val = departments.containsKey(sub[0])
                    ? departments.get(sub[0]) : new ArrayList<>();
            val.add(s.substring(sub[0].length()));
            departments.put(sub[0], val);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var key : departments.keySet()) {
            List<String> values = departments.get(key);
            sb.append(key);
            sb.append(System.lineSeparator());
            for (var val : values) {
                if (!val.isEmpty()) {
                    sb.append(key);
                    sb.append(val);
                    sb.append(System.lineSeparator());
                }
            }
        }
        return sb.toString();
    }

    /**
     * Sort department (create new tree with changed comparator)
     *
     * @param comparator any string comparator
     */
    public void sort(Comparator<String> comparator) {
        Map<String, List<String>> dep = new TreeMap<>(comparator);
        dep.putAll(departments);
        departments = dep;
    }
}
