package cn.leegq.course2;

import cn.leegq.course.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Li GQ 2018/10/20
 */
public class UserMapper {
    /**
     * 抽取获取连接逻辑为通用模块
     * 但是parse sql，设置参数，解析返回值繁琐
     * @param id
     * @return
     */
    public List<User> listUsers(int id) {
        List<User> users=new ArrayList<>();
        Transaction transaction=TransactionFactory.openTransaction();
        Connection conn=null;
        PreparedStatement pstm=null;
        ResultSet rs=null;
        try {
            conn=transaction.getConnection();
            pstm = conn.prepareStatement("select * from user where age>?");
            pstm.setInt(1,id);
            rs=pstm.executeQuery();
            //4.处理数据库的返回结果(使用ResultSet类)
            while (rs.next()) {
                User user=new User();
                user.setUid(rs.getInt("uid"));
                user.setAge(rs.getInt("age"));
                user.setName(rs.getString("name"));
                users.add(user);
            }
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
