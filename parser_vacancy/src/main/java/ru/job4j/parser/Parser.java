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

public abstract class Parser<T> implements Iterable<T> {

    private final static Logger LOG = LogManager.getLogger(Parser.class.getName());

    protected Config config;

    public void setConfig(Config config) {
        this.config = config;
    }

    protected boolean matchFilter(String text, String filter) {
        boolean state = true;
        if (filter != null) {
            Pattern pattern = Pattern.compile(filter,
                    Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(text);
            state = matcher.find();
        }
        return state;
    }

    protected boolean conditionState(Long date) {
        return date <= config.getParseWith();
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
    protected abstract boolean filterTable(T entity);

    /**
     * Define filter page gate
     *
     * @param entity filtered entity
     * @return true if proper
     */
    protected abstract boolean filterPage(T entity);

    /**
     * Define siteState when iteration could be end
     *
     * @param entity
     * @return
     */
    protected abstract boolean condition(T entity);

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
                            if (filterPage(entity)) {
                                break;
                            }
                        }
                    } while (true);
                    if (entity != null) {
                        page(entity);
                    }
                    return entity;
                } catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                }
                return null;
            }

        };

    }
}
