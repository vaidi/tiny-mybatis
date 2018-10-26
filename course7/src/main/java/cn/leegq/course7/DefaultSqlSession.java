package cn.leegq.course7;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Li GQ 2018/10/21
 */
public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;
    private Transaction transaction;

    public DefaultSqlSession(Configuration configuration,Transaction transaction){
        this.configuration=configuration;
        this.transaction=transaction;
    }


    @Override
    public <T> List<T> selectList(String statement, Object parameter) {
        List<T> list=new ArrayList<>();
        Configuration conf=Configuration.getInstance();
        ResultMap resultMap=conf.statementResultMaps.get(statement);
        BoundSql boundSql=conf.boundSqlMap.get(statement);
        boundSql.setParameterObject(parameter);

        Transaction transaction=TransactionFactory.openTransaction();
        Connection conn=null;
        try {
            conn=transaction.getConnection();
            PrepareStatementHandler prepareStatementHandler=new PrepareStatementHandler(boundSql,resultMap);
            Statement st=prepareStatementHandler.prepare(conn);
            list.addAll(prepareStatementHandler.query(st));
            transaction.commit(conn);
        } catch (SQLException e) {
            try {
                transaction.rollback(conn);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            try {
                transaction.close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        Class<?>[] interfaces=new Class[]{type};
        return (T)Proxy.newProxyInstance(type.getClassLoader(), interfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return selectList(type.getName()+"."+method.getName(),args[0]);
            }
        });
    }
}
