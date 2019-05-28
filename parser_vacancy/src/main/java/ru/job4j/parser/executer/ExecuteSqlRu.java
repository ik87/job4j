package ru.job4j.parser.executer;

import ru.job4j.parser.Executable;
import ru.job4j.parser.Parser;
import ru.job4j.parser.StorageDB;
import ru.job4j.parser.parsers.ParserSqlRu;
import ru.job4j.parser.queries.QuerySqlRu;

public class ExecuteSqlRu implements Executable {
    @Override
    public Class<? extends StorageDB> database() {
        return QuerySqlRu.class;
    }

    @Override
    public Parser parser() {
        return new ParserSqlRu();
    }


}

