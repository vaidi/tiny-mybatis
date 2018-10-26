package cn.leegq.course7;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;

/**
 * @author Li GQ 2018/10/20
 */
public class TransactionFactory {
    private static DataSource dataSource;
    static {
        String URL = "jdbc:mysql://127.0.0.1:3306/mybatis?useUnicode=true&characterEncoding=UTF8&serverTimezone=GMT%2B8";
        String USER = "root";
        String PASSWORD = "11111";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        MysqlDataSource dataSource = new MysqlConnectionPoolDataSource();
        dataSource.setURL(URL);
        dataSource.setUser(USER);
        dataSource.setPassword(PASSWORD);
        TransactionFactory.dataSource=dataSource;
    }

    public static Transaction openTransaction() {
        return new Transaction(dataSource);
    }
}
