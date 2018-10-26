package cn.leegq.course4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author Li GQ 2018/10/20
 */
public class PrepareStatementHandler implements StatementHandler {
    protected final ResultSetHandler resultSetHandler;
    protected final ParameterHandler parameterHandler;
    private final BoundSql boundSql;
    private final ResultMap resultMap;

    protected PrepareStatementHandler(BoundSql boundSql,ResultMap resultMap) {
        this.boundSql=boundSql;
        this.resultMap=resultMap;
        this.parameterHandler=new ParameterHandler(boundSql);
        this.resultSetHandler=new ResultSetHandler(parameterHandler,resultMap);
    }

    @Override
    public void parameterize(Statement statement) throws SQLException {
        parameterHandler.setParameters((PreparedStatement)statement);
    }

    @Override
    public Statement prepare(Connection connection) throws SQLException {
        PreparedStatement pstm=connection.prepareStatement(boundSql.getSql());
        parameterize(pstm);
        return pstm;
    }

    @Override
    public <E> List<E> query(Statement statement) throws SQLException {
        PreparedStatement ps = (PreparedStatement) statement;
        ps.execute();
        return resultSetHandler.<E> handleResultSets(ps);
    }
}
