package ru.job4j.stream.group_students;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Group {

    public static Map<String, Set<String>> sections(List<Student> students) {
        return students.stream()
                .flatMap(x -> {
                    List<Holder> holder = new ArrayList<>();
                    for (String section : x.getUnits()) {
                        holder.add(new Holder(section, x.getName()));
                    }
                    return holder.stream();
                }).collect(
                        Collectors.groupingBy(t -> t.key,
                                Collector.of(
                                        HashSet::new,
                                        (set, el) -> set.add(el.value),
                                        (left, right) -> {
                                            left.addAll(right);
                                            return left;
                                        }
                                )
                        )
                );
    }

}
