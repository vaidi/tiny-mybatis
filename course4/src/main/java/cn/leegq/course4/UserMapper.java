package cn.leegq.course4;

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
     * 添加了ResultMap 设置了sql列和java bean 属性的对应关系，返回对象类型封装在里面
     * 拆分PrepareStatementHandler 操作为三部分
     * @param id
     * @return
     */
    public List<User> listUsers(int id) {
        String sql="select * from user where age>?";
        List<User> users=new ArrayList<>();
        ResultMapping uidMapping=new ResultMapping("uid","uid",Integer.class,null);
        ResultMapping ageMapping=new ResultMapping("age","age",Integer.class,null);
        ResultMapping nameMapping=new ResultMapping("name","name",String.class,null);
        List<ResultMapping> mappings=new ArrayList<>();
        mappings.add(uidMapping);
        mappings.add(ageMapping);
        mappings.add(nameMapping);

        ResultMap resultMap=new ResultMap();
        resultMap.setType(User.class);
        resultMap.setResultMappings(mappings);

        List<ParameterMapping> paramMappings=new ArrayList<>();
        ParameterMapping parameterMapping=new ParameterMapping("id",Integer.class);
        paramMappings.add(parameterMapping);

        BoundSql boundSql=new BoundSql(sql,paramMappings,id);

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
