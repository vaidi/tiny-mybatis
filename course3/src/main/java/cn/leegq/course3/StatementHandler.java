package cn.leegq.course3;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Li GQ 2018/10/20
 */
public interface StatementHandler {

    <E> List<E> query(Connection connection)
            throws SQLException;
}
