package cn.leegq.course5;

import cn.leegq.course.domain.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Li GQ 2018/10/20
 */
public class UserMapper {
    /**
     * 将变量sql 、列与javabean 的属性对应关系转移到 配置文件，初始化到Configuration中
     * 通过方法id 找到 对应的配置
     *
     * 好像所有的方法都一样 ，那么提出来放到一个通用类里？ sqlSession
     * @param id
     * @return
     */
    public List<User> listUsers(int id) {
        List<User> users=new ArrayList<>();
        Configuration conf=Configuration.getInstance();
        ResultMap resultMap=conf.statementResultMaps.get("cn.leegq.course5.UserMapper.listUsers");
        BoundSql boundSql=conf.boundSqlMap.get("cn.leegq.course5.UserMapper.listUsers");
        boundSql.setParameterObject(id);

        Transaction transaction=TransactionFactory.openTransaction();
        Connection conn=null;
        try {
            conn=transaction.getConnection();
            PrepareStatementHandler prepareStatementHandler=new PrepareStatementHandler(boundSql,resultMap);
            Statement statement=prepareStatementHandler.prepare(conn);
            users.addAll(prepareStatementHandler.query(statement));
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
