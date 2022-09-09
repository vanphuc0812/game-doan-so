package cybersoft.javabackend.java18.gamedoanso.repository;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface JdbcExecute<T> {
    T processQuery(Connection connection) throws SQLException;
}
