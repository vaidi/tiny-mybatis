package cn.leegq.course7;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author Li GQ 2018/10/20
 */
public interface StatementHandler {


    void parameterize(Statement statement)
            throws SQLException;

    Statement prepare(Connection connection)
            throws SQLException;

    <E> List<E> query(Statement statement)
            throws SQLException;
}
