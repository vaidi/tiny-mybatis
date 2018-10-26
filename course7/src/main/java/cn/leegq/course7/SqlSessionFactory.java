package cn.leegq.course7;

import javax.sql.DataSource;

public class SqlSessionFactory {

    private DataSource dataSource;

    public SqlSession openSession() {
        DataSource datasource = this.dataSource;
        return new DefaultSqlSession(Configuration.getInstance(), new Transaction(datasource));
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}