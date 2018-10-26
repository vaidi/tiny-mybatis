package cn.leegq.course5;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 事务的关键在于 autocommit 属性的设置
 * @author Li GQ 2018/10/20
 */
public class Transaction {
    private DataSource dataSource;

    public Transaction(DataSource dataSource){
        this.dataSource=dataSource;
    }

    public Connection getConnection() throws SQLException {
        Connection conn= dataSource.getConnection();
        conn.setAutoCommit(false);
        return conn;
    }

    public void commit(Connection conn) throws SQLException {
        if(conn!=null&& !conn.getAutoCommit()){
            conn.commit();
        }
    }

    public void rollback(Connection conn) throws  SQLException{
        if(conn!=null && !conn.getAutoCommit()){
            conn.rollback();
        }
    }

    public void close(Connection conn) throws SQLException{
        if(conn!=null && !conn.isClosed()){
            conn.close();
        }
    }
}
