import java.util.*;

/**
 * Групировка почты по уникальному автору.
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
class MailFilter {
    /**
     * Групировка по автору
     * @param originalUsers разные пользователи-почты
     * @return сформированный уникальный набор пользователи-почты
     */
    Map<String, Set<String>> groupByUniqueAuthor(Map<String, Set<String>> originalUsers) {
        //клонируем набор пользователей и почт
        var users = clone(originalUsers);
        //получаем итератор из клонированного набора
        var entries = users.entrySet().iterator();
        //создаем вспомогательный контейнер где будут храниться
        // именна ранее проверенных пользователей
        Set<String> checkedNames = new HashSet<>();

        //начинаем основной цикл
        while (entries.hasNext()) {
            var current = entries.next();   //получаем пользователя и список почт, этот опорный пользователь,
            // с ним сравниваются остальне пользователи entries
            if (!checkedNames.contains(current.getKey())) { //проверяем опорного пользователя в списке проверенных
                checkedNames.add(current.getKey()); //кладем опорного пользователя в список проверенных
                while (entries.hasNext()) { //вложенный цикл
                    var next = entries.next(); //получаем следующего пользователя и список его почт, это промежуточный пользователь
                    //списки его почт сравниваются с опорным
                    if (next.getValue().removeAll(current.getValue())) { //если есть дубликаты почт удаляем их, и возврощаем true
                        current.getValue().addAll(next.getValue()); //добовляем уникальные почты опорному пользователю
                        entries.remove();   //т.к почты были добавлены, значит был найден один и тот же автор, удаляем его
                    }
                }
                entries = users.entrySet().iterator(); //берем итератор, и начинаем сначала
            }
        }
        return users;
    }

    /**
     * Метод для клонирования оригинального списка
     * @param original оригинальный список
     * @return клон
     */
    private Map<String, Set<String>> clone(Map<String, Set<String>> original) {
        Map<String, Set<String>> map = new LinkedHashMap<>();
        var entries = original.entrySet();
        for (var entry : entries) {
            var set = new LinkedHashSet<>(entry.getValue());
            map.put(entry.getKey(), set);
        }
        return map;
    }
}
