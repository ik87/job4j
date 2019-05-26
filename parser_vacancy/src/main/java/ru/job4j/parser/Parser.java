package ru.job4j.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class Parser<T> implements Iterable<T> {

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
    protected abstract Elements table(Integer index);

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
    protected abstract void page(T entity);

    /**
     * Define settings for connect to page, use in {@link #page} method
     *
     * @param url url
     * @return document
     * @throws IOException if something went wrong
     */
    protected abstract Document connectToPage(String url) throws IOException;

    /**
     * Define filter gate
     *
     * @param entity filtered entity
     * @return true if proper
     */
    protected abstract boolean filter(T entity);

    /**
     * Define state when iteration could be end
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
                rows = table(tableIndex); //get first table
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
                } while (!filter(entity));
                if (entity != null) {
                    page(entity);
                }
                return entity;
            }

        };

    }
}
