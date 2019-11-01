import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class MailFilterTest {

    @Test
    public void whenPutSomeMailsThenGetGroupByUniqueAuthor() {
        Map<String, Set<String>> usersAndMails = new LinkedHashMap<>() {
            {
                put("user1", new HashSet<>(Arrays.asList("xxx@ya.ru", "foo@gmail.com", "lol@mail.ru")));
                put("user2", new HashSet<>(Arrays.asList("foo@gmail.com", "ups@pisem.net")));
                put("user3", new HashSet<>(Arrays.asList("xyz@pisem.net", "vasya@pupkin.com")));
                put("user4", new HashSet<>(Arrays.asList("ups@pisem.net", "aaa@bbb.ru")));
                put("user5", new HashSet<>(Arrays.asList("xyz@pisem.net")));
            }
        };
        Map<String, Set<String>> expected = new LinkedHashMap<>() {
            {
                put("user1", new HashSet<>(
                        Arrays.asList("xxx@ya.ru", "foo@gmail.com", "lol@mail.ru", "ups@pisem.net", "aaa@bbb.ru")));
                put("user3", new HashSet<>(
                        Arrays.asList("xyz@pisem.net", "vasya@pupkin.com")));
            }
        };
        Map<String, Set<String>> result = new MailFilter().groupByUniqueAuthor(usersAndMails);
        assertThat(expected, is(result));
    }
}