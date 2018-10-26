package cn.leegq.course3;

import cn.leegq.course.domain.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Li GQ 2018/10/20
 */
public class UserMapper {
    /**
     * 抽取parse sql，设置参数，解析resultSet 为通用模块
     * @param id
     * @return
     */
    public List<User> listUsers(int id) {
        String sql="select uid,name,age from user where age>?";
        List<User> users=new ArrayList<>();

        List<ParameterMapping> paramMappings=new ArrayList<>();
        ParameterMapping parameterMapping=new ParameterMapping("id",Integer.class);
        paramMappings.add(parameterMapping);

        BoundSql boundSql=new BoundSql(sql,paramMappings,id);

        Transaction transaction=TransactionFactory.openTransaction();
        Connection conn=null;
        try {
            conn=transaction.getConnection();
            PrepareStatementHandler prepareStatementHandler=new PrepareStatementHandler(boundSql,User.class);
            users.addAll(prepareStatementHandler.query(conn));
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
        return users;
    }
}
