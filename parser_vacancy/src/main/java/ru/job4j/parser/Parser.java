package ru.job4j.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class Parser<T extends Entity> implements Iterable<T> {

    private final static Logger LOG = LogManager.getLogger(Parser.class.getName());

    private String filter;
    private Long condition;

    public void setCondition(Long condition) {
        this.condition = condition;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    protected boolean conditionState(Long date) {
        return date <= condition;
    }

    /**
     * Define elements the row that have to get to entity
     *
     * @param element some row
     * @return entity
     */
    protected abstract T row(Element element);

    /**
     * Define rows of table, that could be parsed
     *
     * @param index index table (1,2,3...)
     * @return
     */
    protected abstract Elements table(Integer index) throws IOException;

    /**
     * Define settings for connect to table, use in {@link #table} method
     *
     * @param index index table
     * @return document
     * @throws IOException if something went wrong
     */
    protected abstract Document connectToTable(Integer index) throws IOException;

    /**
     * Define inner page, that could be parsed
     *
     * @param entity item that have to modify
     */
    protected abstract void page(T entity) throws IOException;

    /**
     * Define settings for connect to page, use in {@link #page} method
     *
     * @param url subUrl
     * @return document
     * @throws IOException if something went wrong
     */
    protected abstract Document connectToPage(String url) throws IOException;

    /**
     * Define filter table gate
     *
     * @param entity filtered entity
     * @return true if proper
     */
    protected boolean filterTable(Entity entity) {
        boolean state = true;
        if (filter != null) {
            Pattern pattern = Pattern.compile(filter,
                    Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(entity.getTextRow());
            state = matcher.find();
        }
        return state;
    }


    /**
     * Define siteState when iteration could be end
     *
     * @param entity
     * @return
     */
    protected boolean condition(Entity entity) {
        return conditionState(entity.getDate());
    }

    /**
     * Get list entities
     */
    public List<T> getEntity() {
        return StreamSupport.stream(spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Integer tableIndex = 0;
            Integer rowIndex = 0;
            Elements rows;
            T entity;

            {
                try {
                    rows = table(tableIndex); //get first table
                } catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                }

                entity = nextEntity(); //prepare entity
            }

            @Override
            public boolean hasNext() {
                return entity != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T tmp = entity;
                entity = nextEntity();
                return tmp;
            }

            private T nextEntity() {
                try {

                    T entity = null;

                    do {
                        entity = row(rows.get(rowIndex++));
                        if (rowIndex == rows.size()) {
                            rows = table(++tableIndex);
                            rowIndex = 0;
                            if (rows == null) {
                                entity = null;
                                break;
                            }
                        }
                        if (condition(entity)) {
                            entity = null;
                            break;
                        }
                        if (filterTable(entity)) {
                            page(entity);
                            break;
                        }
                    } while (true);
                    return entity;
                } catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                }
                return null;
            }

        };

    }
}
